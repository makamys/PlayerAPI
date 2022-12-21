# WHAT THIS DOES: It looks for patterns like this:
#
#   isObfuscated ? "blk" : "net/minecraft/client/entity/EntityPlayerSP"
#
# And it replaces the string in the first condition with a SRG name instead of
# a Notch name.

import glob
import re
import os
import sys
from pathlib import Path
import csv

confDir = Path(sys.argv[1])
packaged = [x.strip().split(' ') for x in open(confDir / "packaged.srg")]
methods = list(csv.reader(open(confDir / "methods.csv"), delimiter=',', quotechar='"'))[1:]
fields = list(csv.reader(open(confDir / "fields.csv"), delimiter=',', quotechar='"'))[1:]

notch2srg_class = {}
notch2srg_field = {}
notch2srg_methodName = {}
mcp2srg_methodName = {}
mcp2srg_field = {}
srg2mcp_methodName = {}
srg2mcp_field = {}
notch2srg_method = {}

for x in methods:
    srg = x[0]
    mcp = x[1]
    
    mcp2srg_methodName[mcp] = (mcp2srg_methodName.get(mcp) or []) + [srg]
    if srg in srg2mcp_methodName:
        raise Exception()
    srg2mcp_methodName[srg] = mcp
    
for x in fields:
    srg = x[0]
    mcp = x[1]
    
    mcp2srg_field[mcp] = (mcp2srg_field.get(mcp) or []) + [srg]
    if srg in srg2mcp_field:
        raise Exception()
    srg2mcp_field[srg] = mcp

def splitLastSlash(s):
    lastSlash = s.rindex('/')
    
    return (s[:lastSlash], s[lastSlash + 1:])

superClassOf = {
    "blk": "blg",
    "blg": "yz",
    "yz": "sv",
    "sv": "sa",
    "mw": "yz",
}

def notch2srg_memberName(ownerNotch, notch, mcp, memberType):
    theMap = notch2srg_methodName if memberType == "method" else notch2srg_field
    srg2mcp_member = srg2mcp_methodName if memberType == "method" else srg2mcp_field
    
    candidates = []
    
    while True:
        name = ownerNotch + "/" + notch
        if name in theMap:
            srgs = theMap[name]
            for srg in srgs:
                srgName = splitLastSlash(srg)[1]
                if ((srg2mcp_member.get(srgName)) == mcp) or (srgName == mcp):
                    candidates.append(srg)
        if ownerNotch in superClassOf:
            ownerNotch = superClassOf[ownerNotch]
        else:
            break
    
    candidateNames = list(set(splitLastSlash(x)[1] for x in candidates))
    
    if not candidateNames:
        raise Exception("No SRG mapping found")
    elif len(candidateNames) > 1:
        raise Exception("Multiple candidates: " + str(candidates))
    else:
        return candidateNames[0]

def notch2srg_fieldFunc(ownerNotch, notch, mcp):
    return notch2srg_field[ownerNotch + "/" + notch]

def notch2srg_desc(desc):
    i = 0
    out = ""
    while True:
        nextL = desc.find("L", i)
        
        if nextL != -1:
            nextSemi = desc.find(";", nextL + 1)
            
            if nextSemi != -1:
                clazz = desc[nextL + 1 : nextSemi]
                out += desc[i : nextL + 1]
                out += notch2srg_class.get(clazz) or clazz
                out += ";"
                i = nextSemi + 1
            else:
                sys.exit("wrong desc: ", desc)
        else:
            break
    
    out += desc[i:]
    
    return out

for packagedElem in packaged:
    if packagedElem[0] == "MD:":
        type, obfFull, obfDesc, srgFull, srgDesc = packagedElem
        srgName = splitLastSlash(srgFull)[1]
        notch2srg_methodName[obfFull] = (notch2srg_methodName.get(obfFull) or []) + [srgFull]
        notch2srg_method[(obfFull, obfDesc)] = (srgFull, srgDesc)
    elif packagedElem[0] == "CL:":
        notch2srg_class[packagedElem[1]] = packagedElem[2]
    elif packagedElem[0] == "FD:":
        type, obfFull, srgFull = packagedElem
        
        notch2srg_field[obfFull] = [srgFull]

p1 = re.compile(r'visitMethodInsn\((.*?), (.*?), (.*?), (.*?)\);')
p2 = re.compile(r'visitFieldInsn\((.*?), (.*?), (.*?), (.*?)\);')
p3 = re.compile(r'visitMethod\((.*?), (.*?), (.*?)\, (.*?), (.*?)\);')
p4 = re.compile(r'if\(name\.equals\((.*)\) && desc.equals\((.*?)\)\)')
p5 = re.compile(r'owner\.equals\((.*?)\)\)')

questionGroup = re.compile(r'\(?isObfuscated \? \"(.*?)\" : \"(.*?)\"\)?')
multiWeirdGroup = re.compile(r'(.*?) + (.*?) + (.*?)')

def guessOwner(file):
    name = os.path.basename(file)
    return "" + {
        "ClientPlayerClassVisitor.java": "blk", # net/minecraft/client/entity/EntityPlayerSP
        "ServerPlayerClassVisitor.java": "mw", # net/minecraft/client/entity/EntityPlayerSP
    }[name] + ""

errors = 0

for file in glob.glob("src/**/*.java", recursive=True):
    
    lines = list(open(file, "r", encoding="utf8"))
    
    newLines = []
    
    for lineI in range(len(lines)):
        line = lines[lineI]
        
        if "isObfuscated ?" in line:
            m = None
            # mapping of which thing corresponds to which regex group.
            # -1 means none
            # -2 means none, and this is an owner field whose value can be guessed from the file name.
            owner, member, desc = (-1, -1, -1)
            memberType = -1
            
            error = None
            
            if m := p1.search(line):
                # mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "addExhaustion", "(Lapi/player/client/IClientPlayerAPI;F)V");
                owner = 2
                member = 3
                desc = 4
                memberType = "method"
            elif m := p2.search(line):
                # mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "blk" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "ag" : "addedToChunk", "Z");
                owner = 2
                member = 3
                desc = 4
                memberType = "field"
            elif m := p3.search(line):
                # mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getEntityPlayerMP", isObfuscated ? "()Lmw;" : "()Lnet/minecraft/entity/player/EntityPlayerMP;", null, null)
                #owner = -2
                member = 2
                desc = 3
                memberType = "method"
            elif m := p4.search(line):
                # if(name.equals(isObfuscated ? "a" : "addStat") && desc.equals(isObfuscated ? "(Lph;I)V" : "(Lnet/minecraft/stats/StatBase;I)V"))
                #owner = -2
                member = 1
                desc = 2
                memberType = "method"
            elif m := p5.search(line):
                # if(name.equals("<init>") && owner.equals(isObfuscated ? "blg" : "net/minecraft/client/entity/AbstractClientPlayer"))
                owner = 1
            else:
                print(line)
            
            # rule 1: isObfuscated ? "NOTCH" : "MCP"
            ownerVal = m[owner] if owner > 0 else None
            
            ownerMatch = questionGroup.match(ownerVal) if ownerVal else None
            
            ownerNotch = ownerMatch[1] if "isObfuscated" in (ownerVal or "") else (ownerVal if ownerVal else guessOwner(file))
            
            if owner > 0:
                if "isObfuscated" in ownerVal:
                    ownerVal = ownerVal[:ownerMatch.start(1)] + notch2srg_class[ownerMatch[1]] + ownerVal[ownerMatch.end(1):]
            
            # rule 1: thing that doesn't need obfuscation
            # rule 2: isObfuscated ? "NOTCH" : "MCP"
            memberVal = m[member] if member > 0 else None
            
            memberNotch = None
            
            if member > 0:
                if "isObfuscated" in memberVal:
                    memberMatch = questionGroup.match(memberVal)
                    memberNotch = memberMatch[1]
                    
                    try:
                        memberVal = memberVal[:memberMatch.start(1)] + notch2srg_memberName(ownerNotch, memberMatch[1], memberMatch[2], memberType) + memberVal[memberMatch.end(1):]
                    except Exception as e:
                        error = "No SRG mapping found for " + memberType + " " + ownerNotch + "/" + memberMatch[1] + ": " + str(e)
            
            # rule 1: thing that doesn't need obfuscation
            # rule 2: isObfuscated ? "NOTCH" : "MCP"
            # rule 3: WEIRD_GROUP + WEIRD_GROUP + WEIRD_GROUP
            
            # WEIRD_GROUP rule 1: thing that doesn't need obfuscation
            # WEIRD_GROUP rule 2: (isObfuscated ? "NOTCH" : "MCP")
            descVal = m[desc] if desc > 0 else None
            
            def remapWeirdGroup(s):
                questionMatch = questionGroup.match(s)
                
                return s[:questionMatch.start(1)] + notch2srg_desc(questionMatch[1]) + s[questionMatch.end(1):] if "isObfuscated" in s else s
            
            if desc > 0:
                weirdGroups = []
                if " + " in descVal:
                    weirdGroups = descVal.split(" + ")
                elif "isObfuscated" in descVal:
                    weirdGroups = [descVal]
                
                if weirdGroups:
                    descVal = " + ".join([remapWeirdGroup(x) for x in weirdGroups])
    
            # Put it back together
            
            i = 0
            newLine = ""
            
            if owner > 0:
                newLine += line[i:m.start(owner)]
                newLine += ownerVal
                
                i = m.end(owner)
            
            if member > 0:
                newLine += line[i:m.start(member)]
                newLine += memberVal
                
                i = m.end(member)
            
            if desc > 0:
                newLine += line[i:m.start(desc)]
                newLine += descVal
                
                i = m.end(desc)
            
            newLine += line[i:]
            
            if error != None:
                print(os.path.basename(file), lineI, "Error:", error)
                newLine = newLine[:-1] + " //! remap warning: " + error + newLine[-1]
                errors += 1
            
            line = newLine
        
        newLines.append(line)
    
    with open(file, "w", encoding="utf8") as fp:
        fp.write("".join(newLines))

if errors:
    print(errors, "errors")