package api.player.launch;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class PlayerAPILaunchTransformer implements IClassTransformer
{
    public static final String[] targetClassNames = {"net.minecraft.client.main.Main", "net.minecraft.server.MinecraftServer"};
    
    public byte[] transform(String name, String transformedName, byte[] bytes)
    {
        for(String target : targetClassNames)
            if(transformedName.equals(target))
                return doTransform(bytes);
        
        return bytes;
    }
    
    public static byte[] doTransform(byte[] bytes)
    {
        try
        {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(bytes);
            classReader.accept(classNode, 0);
            
            for(MethodNode m : classNode.methods)
            {
                if(m.name.equals("<clinit>"))
                {
                    m.instructions.insert(new MethodInsnNode(INVOKESTATIC, "api/player/launch/LaunchHook", "onLaunch", "()V", false));
                }
            }
            
            ClassWriter writer = new ClassWriter(0);
            classNode.accept(writer);
            byte[] result = writer.toByteArray();
            return result;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
