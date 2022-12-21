// ==================================================================
// This file is part of Player API.
//
// Player API is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as
// published by the Free Software Foundation, either version 3 of the
// License, or (at your option) any later version.
//
// Player API is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License and the GNU General Public License along with Player API.
// If not, see <http://www.gnu.org/licenses/>.
// ==================================================================

package api.player.client;

import java.io.*;

import org.objectweb.asm.*;

public final class ClientPlayerClassVisitor extends ClassVisitor
{
	public static final String targetClassName = "net.minecraft.client.entity.EntityPlayerSP";

	private boolean hadLocalAddExhaustion;
	private boolean hadLocalAddMovementStat;
	private boolean hadLocalAddStat;
	private boolean hadLocalAttackEntityFrom;
	private boolean hadLocalAttackTargetEntityWithCurrentItem;
	private boolean hadLocalCanBreatheUnderwater;
	private boolean hadLocalCanHarvestBlock;
	private boolean hadLocalCanPlayerEdit;
	private boolean hadLocalCanTriggerWalking;
	private boolean hadLocalCloseScreen;
	private boolean hadLocalDamageEntity;
	private boolean hadLocalDisplayGUIBrewingStand;
	private boolean hadLocalDisplayGUIChest;
	private boolean hadLocalDisplayGUIDispenser;
	private boolean hadLocalDisplayGUIEditSign;
	private boolean hadLocalDisplayGUIEnchantment;
	private boolean hadLocalDisplayGUIFurnace;
	private boolean hadLocalDisplayGUIWorkbench;
	private boolean hadLocalDropOneItem;
	private boolean hadLocalDropPlayerItem;
	private boolean hadLocalDropPlayerItemWithRandomChoice;
	private boolean hadLocalFall;
	private boolean hadLocalGetAIMoveSpeed;
	private boolean hadLocalGetBedOrientationInDegrees;
	private boolean hadLocalGetBrightness;
	private boolean hadLocalGetBrightnessForRender;
	private boolean hadLocalGetCurrentPlayerStrVsBlock;
	private boolean hadLocalGetCurrentPlayerStrVsBlockForge;
	private boolean hadLocalGetDistanceSq;
	private boolean hadLocalGetDistanceSqToEntity;
	private boolean hadLocalGetFOVMultiplier;
	private boolean hadLocalGetHurtSound;
	private boolean hadLocalGetItemIcon;
	private boolean hadLocalGetSleepTimer;
	private boolean hadLocalHandleLavaMovement;
	private boolean hadLocalHandleWaterMovement;
	private boolean hadLocalHeal;
	private boolean hadLocalIsEntityInsideOpaqueBlock;
	private boolean hadLocalIsInWater;
	private boolean hadLocalIsInsideOfMaterial;
	private boolean hadLocalIsOnLadder;
	private boolean hadLocalIsPlayerSleeping;
	private boolean hadLocalIsSneaking;
	private boolean hadLocalIsSprinting;
	private boolean hadLocalJump;
	private boolean hadLocalKnockBack;
	private boolean hadLocalMoveEntity;
	private boolean hadLocalMoveEntityWithHeading;
	private boolean hadLocalMoveFlying;
	private boolean hadLocalOnDeath;
	private boolean hadLocalOnLivingUpdate;
	private boolean hadLocalOnKillEntity;
	private boolean hadLocalOnStruckByLightning;
	private boolean hadLocalOnUpdate;
	private boolean hadLocalPlayStepSound;
	private boolean hadLocalPushOutOfBlocks;
	private boolean hadLocalRayTrace;
	private boolean hadLocalReadEntityFromNBT;
	private boolean hadLocalRespawnPlayer;
	private boolean hadLocalSetDead;
	private boolean hadLocalSetPlayerSPHealth;
	private boolean hadLocalSetPositionAndRotation;
	private boolean hadLocalSetSneaking;
	private boolean hadLocalSetSprinting;
	private boolean hadLocalSleepInBedAt;
	private boolean hadLocalSwingItem;
	private boolean hadLocalUpdateEntityActionState;
	private boolean hadLocalUpdateRidden;
	private boolean hadLocalWakeUpPlayer;
	private boolean hadLocalWriteEntityToNBT;

	public static byte[] transform(byte[] bytes, boolean isObfuscated)
	{
		try
		{
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ClassReader cr = new ClassReader(in);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			ClientPlayerClassVisitor p = new ClientPlayerClassVisitor(cw, isObfuscated);

			cr.accept(p, 0);

			byte[] result = cw.toByteArray();
			in.close();
			return result;
		}
		catch(IOException ioe)
		{
			throw new RuntimeException(ioe);
		}
	}

	private final boolean isObfuscated;

	public ClientPlayerClassVisitor(ClassVisitor classVisitor, boolean isObfuscated)
	{
		super(262144, classVisitor);
		this.isObfuscated = isObfuscated;
	}

	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces)
	{
		String[] newInterfaces = new String[interfaces.length + 1];
		for(int i=0; i<interfaces.length; i++)
			newInterfaces[i] = interfaces[i];
		newInterfaces[interfaces.length] = "api/player/client/IClientPlayerAPI";
		super.visit(version, access, name, signature, superName, newInterfaces);
	}

	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
	{
		if(name.equals("<init>"))
			return new ClientPlayerConstructorVisitor(super.visitMethod(access, name, desc, signature, exceptions), isObfuscated);

		if(name.equals(isObfuscated ? "func_71020_j" : "addExhaustion") && desc.equals("(F)V"))
		{
			hadLocalAddExhaustion = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localAddExhaustion", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71000_j" : "addMovementStat") && desc.equals("(DDD)V"))
		{
			hadLocalAddMovementStat = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localAddMovementStat", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71064_a" : "addStat") && desc.equals(isObfuscated ? "(Lnet/minecraft/stats/StatBase;I)V" : "(Lnet/minecraft/stats/StatBase;I)V"))
		{
			hadLocalAddStat = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localAddStat", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70097_a" : "attackEntityFrom") && desc.equals(isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)Z" : "(Lnet/minecraft/util/DamageSource;F)Z"))
		{
			hadLocalAttackEntityFrom = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localAttackEntityFrom", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71059_n" : "attackEntityPlayerSPEntityWithCurrentItem") && desc.equals(isObfuscated ? "(Lnet/minecraft/entity/Entity;)V" : "(Lnet/minecraft/entity/Entity;)V")) // TODO is this wrong? Shouldn't it be attackTargetEntityWithCurrentItem?
		{
			hadLocalAttackTargetEntityWithCurrentItem = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localAttackTargetEntityWithCurrentItem", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70648_aU" : "canBreatheUnderwater") && desc.equals("()Z"))
		{
			hadLocalCanBreatheUnderwater = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localCanBreatheUnderwater", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_146099_a" : "canHarvestBlock") && desc.equals(isObfuscated ? "(Lnet/minecraft/block/Block;)Z" : "(Lnet/minecraft/block/Block;)Z"))
		{
			hadLocalCanHarvestBlock = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localCanHarvestBlock", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_82247_a" : "canPlayerEdit") && desc.equals(isObfuscated ? "(IIIILnet/minecraft/item/ItemStack;)Z" : "(IIIILnet/minecraft/item/ItemStack;)Z"))
		{
			hadLocalCanPlayerEdit = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localCanPlayerEdit", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70041_e_" : "canTriggerWalking") && desc.equals("()Z"))
		{
			hadLocalCanTriggerWalking = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localCanTriggerWalking", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71053_j" : "closeScreen") && desc.equals("()V"))
		{
			hadLocalCloseScreen = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localCloseScreen", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70665_d" : "damageEntity") && desc.equals(isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)V" : "(Lnet/minecraft/util/DamageSource;F)V"))
		{
			hadLocalDamageEntity = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDamageEntity", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_146098_a" : "func_146098_a") && desc.equals(isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V" : "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V"))
		{
			hadLocalDisplayGUIBrewingStand = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIBrewingStand", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71007_a" : "displayGUIChest") && desc.equals(isObfuscated ? "(Lnet/minecraft/inventory/IInventory;)V" : "(Lnet/minecraft/inventory/IInventory;)V"))
		{
			hadLocalDisplayGUIChest = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIChest", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_146102_a" : "func_146102_a") && desc.equals(isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityDispenser;)V" : "(Lnet/minecraft/tileentity/TileEntityDispenser;)V"))
		{
			hadLocalDisplayGUIDispenser = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIDispenser", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_146100_a" : "func_146100_a") && desc.equals(isObfuscated ? "(Lnet/minecraft/tileentity/TileEntity;)V" : "(Lnet/minecraft/tileentity/TileEntity;)V"))
		{
			hadLocalDisplayGUIEditSign = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIEditSign", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71002_c" : "displayGUIEnchantment") && desc.equals("(IIILjava/lang/String;)V"))
		{
			hadLocalDisplayGUIEnchantment = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIEnchantment", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_146101_a" : "func_146101_a") && desc.equals(isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityFurnace;)V" : "(Lnet/minecraft/tileentity/TileEntityFurnace;)V"))
		{
			hadLocalDisplayGUIFurnace = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIFurnace", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71058_b" : "displayGUIWorkbench") && desc.equals("(III)V"))
		{
			hadLocalDisplayGUIWorkbench = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIWorkbench", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71040_bB" : "dropOneItem") && desc.equals(isObfuscated ? "(Z)Lnet/minecraft/entity/item/EntityItem;" : "(Z)Lnet/minecraft/entity/item/EntityItem;"))
		{
			hadLocalDropOneItem = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDropOneItem", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71019_a" : "dropPlayerItemWithRandomChoice") && desc.equals(isObfuscated ? "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;"))
		{
			hadLocalDropPlayerItem = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDropPlayerItem", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_146097_a" : "func_146097_a") && desc.equals(isObfuscated ? "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;"))
		{
			hadLocalDropPlayerItemWithRandomChoice = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDropPlayerItemWithRandomChoice", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70069_a" : "fall") && desc.equals("(F)V"))
		{
			hadLocalFall = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localFall", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70689_ay" : "getAIMoveSpeed") && desc.equals("()F"))
		{
			hadLocalGetAIMoveSpeed = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetAIMoveSpeed", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71051_bG" : "getBedOrientationInDegrees") && desc.equals("()F"))
		{
			hadLocalGetBedOrientationInDegrees = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetBedOrientationInDegrees", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70013_c" : "getBrightness") && desc.equals("(F)F"))
		{
			hadLocalGetBrightness = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetBrightness", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70070_b" : "getBrightnessForRender") && desc.equals("(F)I"))
		{
			hadLocalGetBrightnessForRender = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetBrightnessForRender", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_146096_a" : "getCurrentPlayerStrVsBlock") && desc.equals(isObfuscated ? "(Lnet/minecraft/block/Block;Z)F" : "(Lnet/minecraft/block/Block;Z)F"))
		{
			hadLocalGetCurrentPlayerStrVsBlock = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetCurrentPlayerStrVsBlock", desc, signature, exceptions);
		}

		if(name.equals("getBreakSpeed") && desc.equals(isObfuscated ? "(Lnet/minecraft/block/Block;ZI)F" : "(Lnet/minecraft/block/Block;ZI)F"))
		{
			hadLocalGetCurrentPlayerStrVsBlockForge = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetCurrentPlayerStrVsBlockForge", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70092_e" : "getDistanceSq") && desc.equals("(DDD)D"))
		{
			hadLocalGetDistanceSq = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetDistanceSq", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70068_e" : "getDistanceSqToEntity") && desc.equals(isObfuscated ? "(Lnet/minecraft/entity/Entity;)D" : "(Lnet/minecraft/entity/Entity;)D"))
		{
			hadLocalGetDistanceSqToEntity = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetDistanceSqToEntity", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71151_f" : "getFOVMultiplier") && desc.equals("()F"))
		{
			hadLocalGetFOVMultiplier = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetFOVMultiplier", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70621_aR" : "getHurtSound") && desc.equals("()Ljava/lang/String;"))
		{
			hadLocalGetHurtSound = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetHurtSound", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70620_b" : "getItemIcon") && desc.equals(isObfuscated ? "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;" : "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;"))
		{
			hadLocalGetItemIcon = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetItemIcon", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71060_bI" : "getSleepTimer") && desc.equals("()I"))
		{
			hadLocalGetSleepTimer = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetSleepTimer", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70058_J" : "handleLavaMovement") && desc.equals("()Z"))
		{
			hadLocalHandleLavaMovement = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localHandleLavaMovement", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70072_I" : "handleWaterMovement") && desc.equals("()Z"))
		{
			hadLocalHandleWaterMovement = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localHandleWaterMovement", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70691_i" : "heal") && desc.equals("(F)V"))
		{
			hadLocalHeal = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localHeal", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70094_T" : "isEntityInsideOpaqueBlock") && desc.equals("()Z"))
		{
			hadLocalIsEntityInsideOpaqueBlock = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsEntityInsideOpaqueBlock", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70090_H" : "isInWater") && desc.equals("()Z"))
		{
			hadLocalIsInWater = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsInWater", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70055_a" : "isInsideOfMaterial") && desc.equals(isObfuscated ? "(Lnet/minecraft/block/material/Material;)Z" : "(Lnet/minecraft/block/material/Material;)Z"))
		{
			hadLocalIsInsideOfMaterial = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsInsideOfMaterial", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70617_f_" : "isOnLadder") && desc.equals("()Z"))
		{
			hadLocalIsOnLadder = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsOnLadder", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70608_bn" : "isPlayerSleeping") && desc.equals("()Z"))
		{
			hadLocalIsPlayerSleeping = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsPlayerSleeping", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70093_af" : "isSneaking") && desc.equals("()Z"))
		{
			hadLocalIsSneaking = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsSneaking", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70051_ag" : "isSprinting") && desc.equals("()Z"))
		{
			hadLocalIsSprinting = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsSprinting", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70664_aZ" : "jump") && desc.equals("()V"))
		{
			hadLocalJump = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localJump", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70653_a" : "knockBack") && desc.equals(isObfuscated ? "(Lnet/minecraft/entity/Entity;FDD)V" : "(Lnet/minecraft/entity/Entity;FDD)V"))
		{
			hadLocalKnockBack = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localKnockBack", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70091_d" : "moveEntity") && desc.equals("(DDD)V"))
		{
			hadLocalMoveEntity = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localMoveEntity", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70612_e" : "moveEntityWithHeading") && desc.equals("(FF)V"))
		{
			hadLocalMoveEntityWithHeading = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localMoveEntityWithHeading", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70060_a" : "moveFlying") && desc.equals("(FFF)V"))
		{
			hadLocalMoveFlying = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localMoveFlying", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70645_a" : "onDeath") && desc.equals(isObfuscated ? "(Lnet/minecraft/util/DamageSource;)V" : "(Lnet/minecraft/util/DamageSource;)V"))
		{
			hadLocalOnDeath = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localOnDeath", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70636_d" : "onLivingUpdate") && desc.equals("()V"))
		{
			hadLocalOnLivingUpdate = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localOnLivingUpdate", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70074_a" : "onKillEntity") && desc.equals(isObfuscated ? "(Lnet/minecraft/entity/EntityLivingBase;)V" : "(Lnet/minecraft/entity/EntityLivingBase;)V"))
		{
			hadLocalOnKillEntity = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localOnKillEntity", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70077_a" : "onStruckByLightning") && desc.equals(isObfuscated ? "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V" : "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V"))
		{
			hadLocalOnStruckByLightning = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localOnStruckByLightning", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70071_h_" : "onUpdate") && desc.equals("()V"))
		{
			hadLocalOnUpdate = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localOnUpdate", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_145780_a" : "func_145780_a") && desc.equals(isObfuscated ? "(IIILnet/minecraft/block/Block;)V" : "(IIILnet/minecraft/block/Block;)V"))
		{
			hadLocalPlayStepSound = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localPlayStepSound", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_145771_j" : "func_145771_j") && desc.equals("(DDD)Z"))
		{
			hadLocalPushOutOfBlocks = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localPushOutOfBlocks", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70614_a" : "rayTrace") && desc.equals(isObfuscated ? "(DF)Lnet/minecraft/util/MovingObjectPosition;" : "(DF)Lnet/minecraft/util/MovingObjectPosition;"))
		{
			hadLocalRayTrace = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localRayTrace", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70037_a" : "readEntityFromNBT") && desc.equals(isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V"))
		{
			hadLocalReadEntityFromNBT = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localReadEntityFromNBT", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71004_bE" : "respawnPlayer") && desc.equals("()V"))
		{
			hadLocalRespawnPlayer = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localRespawnPlayer", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70106_y" : "setDead") && desc.equals("()V"))
		{
			hadLocalSetDead = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSetDead", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71150_b" : "setPlayerSPHealth") && desc.equals("(F)V"))
		{
			hadLocalSetPlayerSPHealth = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSetPlayerSPHealth", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70080_a" : "setPositionAndRotation") && desc.equals("(DDDFF)V"))
		{
			hadLocalSetPositionAndRotation = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSetPositionAndRotation", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70095_a" : "setSneaking") && desc.equals("(Z)V"))
		{
			hadLocalSetSneaking = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSetSneaking", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70031_b" : "setSprinting") && desc.equals("(Z)V"))
		{
			hadLocalSetSprinting = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSetSprinting", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71018_a" : "sleepInBedAt") && desc.equals(isObfuscated ? "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;" : "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;"))
		{
			hadLocalSleepInBedAt = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSleepInBedAt", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_71038_i" : "swingItem") && desc.equals("()V"))
		{
			hadLocalSwingItem = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSwingItem", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70626_be" : "updateEntityActionState") && desc.equals("()V"))
		{
			hadLocalUpdateEntityActionState = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localUpdateEntityActionState", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70098_U" : "updateRidden") && desc.equals("()V"))
		{
			hadLocalUpdateRidden = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localUpdateRidden", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70999_a" : "wakeUpPlayer") && desc.equals("(ZZZ)V"))
		{
			hadLocalWakeUpPlayer = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localWakeUpPlayer", desc, signature, exceptions);
		}

		if(name.equals(isObfuscated ? "func_70014_b" : "writeEntityToNBT") && desc.equals(isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V"))
		{
			hadLocalWriteEntityToNBT = true;
			return super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localWriteEntityToNBT", desc, signature, exceptions);
		}

		return super.visitMethod(access, name, desc, signature, exceptions);
	}

	public void visitEnd()
	{
		MethodVisitor mv;

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71020_j" : "addExhaustion", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "addExhaustion", "(Lapi/player/client/IClientPlayerAPI;F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realAddExhaustion", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71020_j" : "addExhaustion", "(F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superAddExhaustion", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71020_j" : "addExhaustion", "(F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalAddExhaustion)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localAddExhaustion", "(F)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.FLOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71020_j" : "addExhaustion", "(F)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71000_j" : "addMovementStat", "(DDD)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "addMovementStat", "(Lapi/player/client/IClientPlayerAPI;DDD)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realAddMovementStat", "(DDD)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71000_j" : "addMovementStat", "(DDD)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superAddMovementStat", "(DDD)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71000_j" : "addMovementStat", "(DDD)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalAddMovementStat)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localAddMovementStat", "(DDD)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.DLOAD, 1);
			mv.visitVarInsn(Opcodes.DLOAD, 3);
			mv.visitVarInsn(Opcodes.DLOAD, 5);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71000_j" : "addMovementStat", "(DDD)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71064_a" : "addStat", "" + (isObfuscated ? "(Lnet/minecraft/stats/StatBase;I)V" : "(Lnet/minecraft/stats/StatBase;I)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "addStat", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/stats/StatBase;I" : "Lnet/minecraft/stats/StatBase;I") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realAddStat", "" + (isObfuscated ? "(Lnet/minecraft/stats/StatBase;I)V" : "(Lnet/minecraft/stats/StatBase;I)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71064_a" : "addStat", "" + (isObfuscated ? "(Lnet/minecraft/stats/StatBase;I)V" : "(Lnet/minecraft/stats/StatBase;I)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superAddStat", "" + (isObfuscated ? "(Lnet/minecraft/stats/StatBase;I)V" : "(Lnet/minecraft/stats/StatBase;I)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71064_a" : "addStat", "" + (isObfuscated ? "(Lnet/minecraft/stats/StatBase;I)V" : "(Lnet/minecraft/stats/StatBase;I)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalAddStat)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localAddStat", "" + (isObfuscated ? "(Lnet/minecraft/stats/StatBase;I)V" : "(Lnet/minecraft/stats/StatBase;I)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71064_a" : "addStat", "" + (isObfuscated ? "(Lnet/minecraft/stats/StatBase;I)V" : "(Lnet/minecraft/stats/StatBase;I)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70097_a" : "attackEntityFrom", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)Z" : "(Lnet/minecraft/util/DamageSource;F)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "attackEntityFrom", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/util/DamageSource;F" : "Lnet/minecraft/util/DamageSource;F") + ")Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realAttackEntityFrom", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)Z" : "(Lnet/minecraft/util/DamageSource;F)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70097_a" : "attackEntityFrom", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)Z" : "(Lnet/minecraft/util/DamageSource;F)Z") + "");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superAttackEntityFrom", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)Z" : "(Lnet/minecraft/util/DamageSource;F)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70097_a" : "attackEntityFrom", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)Z" : "(Lnet/minecraft/util/DamageSource;F)Z") + "");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalAttackEntityFrom)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localAttackEntityFrom", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)Z" : "(Lnet/minecraft/util/DamageSource;F)Z") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitVarInsn(Opcodes.FLOAD, 2);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70097_a" : "attackEntityFrom", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)Z" : "(Lnet/minecraft/util/DamageSource;F)Z") + "");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71059_n" : "attackEntityPlayerSPEntityWithCurrentItem", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)V" : "(Lnet/minecraft/entity/Entity;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "attackTargetEntityWithCurrentItem", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/entity/Entity;" : "Lnet/minecraft/entity/Entity;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realAttackTargetEntityWithCurrentItem", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)V" : "(Lnet/minecraft/entity/Entity;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71059_n" : "attackEntityPlayerSPEntityWithCurrentItem", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)V" : "(Lnet/minecraft/entity/Entity;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superAttackTargetEntityWithCurrentItem", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)V" : "(Lnet/minecraft/entity/Entity;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71059_n" : "attackEntityPlayerSPEntityWithCurrentItem", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)V" : "(Lnet/minecraft/entity/Entity;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalAttackTargetEntityWithCurrentItem)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localAttackTargetEntityWithCurrentItem", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)V" : "(Lnet/minecraft/entity/Entity;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71059_n" : "attackEntityPlayerSPEntityWithCurrentItem", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)V" : "(Lnet/minecraft/entity/Entity;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70648_aU" : "canBreatheUnderwater", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "canBreatheUnderwater", "(Lapi/player/client/IClientPlayerAPI;)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realCanBreatheUnderwater", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70648_aU" : "canBreatheUnderwater", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superCanBreatheUnderwater", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70648_aU" : "canBreatheUnderwater", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalCanBreatheUnderwater)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localCanBreatheUnderwater", "()Z", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70648_aU" : "canBreatheUnderwater", "()Z");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_146099_a" : "canHarvestBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;)Z" : "(Lnet/minecraft/block/Block;)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "canHarvestBlock", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/block/Block;" : "Lnet/minecraft/block/Block;") + ")Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realCanHarvestBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;)Z" : "(Lnet/minecraft/block/Block;)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_146099_a" : "canHarvestBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;)Z" : "(Lnet/minecraft/block/Block;)Z") + "");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superCanHarvestBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;)Z" : "(Lnet/minecraft/block/Block;)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146099_a" : "canHarvestBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;)Z" : "(Lnet/minecraft/block/Block;)Z") + "");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalCanHarvestBlock)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localCanHarvestBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;)Z" : "(Lnet/minecraft/block/Block;)Z") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146099_a" : "canHarvestBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;)Z" : "(Lnet/minecraft/block/Block;)Z") + "");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_82247_a" : "canPlayerEdit", "" + (isObfuscated ? "(IIIILnet/minecraft/item/ItemStack;)Z" : "(IIIILnet/minecraft/item/ItemStack;)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitVarInsn(Opcodes.ILOAD, 4);
		mv.visitVarInsn(Opcodes.ALOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "canPlayerEdit", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "IIIILnet/minecraft/item/ItemStack;" : "IIIILnet/minecraft/item/ItemStack;") + ")Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realCanPlayerEdit", "" + (isObfuscated ? "(IIIILnet/minecraft/item/ItemStack;)Z" : "(IIIILnet/minecraft/item/ItemStack;)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitVarInsn(Opcodes.ILOAD, 4);
		mv.visitVarInsn(Opcodes.ALOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_82247_a" : "canPlayerEdit", "" + (isObfuscated ? "(IIIILnet/minecraft/item/ItemStack;)Z" : "(IIIILnet/minecraft/item/ItemStack;)Z") + "");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superCanPlayerEdit", "" + (isObfuscated ? "(IIIILnet/minecraft/item/ItemStack;)Z" : "(IIIILnet/minecraft/item/ItemStack;)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitVarInsn(Opcodes.ILOAD, 4);
		mv.visitVarInsn(Opcodes.ALOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_82247_a" : "canPlayerEdit", "" + (isObfuscated ? "(IIIILnet/minecraft/item/ItemStack;)Z" : "(IIIILnet/minecraft/item/ItemStack;)Z") + "");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalCanPlayerEdit)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localCanPlayerEdit", "" + (isObfuscated ? "(IIIILnet/minecraft/item/ItemStack;)Z" : "(IIIILnet/minecraft/item/ItemStack;)Z") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ILOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitVarInsn(Opcodes.ILOAD, 3);
			mv.visitVarInsn(Opcodes.ILOAD, 4);
			mv.visitVarInsn(Opcodes.ALOAD, 5);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_82247_a" : "canPlayerEdit", "" + (isObfuscated ? "(IIIILnet/minecraft/item/ItemStack;)Z" : "(IIIILnet/minecraft/item/ItemStack;)Z") + "");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70041_e_" : "canTriggerWalking", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "canTriggerWalking", "(Lapi/player/client/IClientPlayerAPI;)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realCanTriggerWalking", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70041_e_" : "canTriggerWalking", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superCanTriggerWalking", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70041_e_" : "canTriggerWalking", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalCanTriggerWalking)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localCanTriggerWalking", "()Z", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70041_e_" : "canTriggerWalking", "()Z");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71053_j" : "closeScreen", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "closeScreen", "(Lapi/player/client/IClientPlayerAPI;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realCloseScreen", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71053_j" : "closeScreen", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superCloseScreen", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71053_j" : "closeScreen", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalCloseScreen)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localCloseScreen", "()V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71053_j" : "closeScreen", "()V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70665_d" : "damageEntity", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)V" : "(Lnet/minecraft/util/DamageSource;F)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "damageEntity", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/util/DamageSource;F" : "Lnet/minecraft/util/DamageSource;F") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realDamageEntity", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)V" : "(Lnet/minecraft/util/DamageSource;F)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70665_d" : "damageEntity", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)V" : "(Lnet/minecraft/util/DamageSource;F)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superDamageEntity", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)V" : "(Lnet/minecraft/util/DamageSource;F)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70665_d" : "damageEntity", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)V" : "(Lnet/minecraft/util/DamageSource;F)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalDamageEntity)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDamageEntity", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)V" : "(Lnet/minecraft/util/DamageSource;F)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitVarInsn(Opcodes.FLOAD, 2);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70665_d" : "damageEntity", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;F)V" : "(Lnet/minecraft/util/DamageSource;F)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_146098_a" : "func_146098_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V" : "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "displayGUIBrewingStand", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/tileentity/TileEntityBrewingStand;" : "Lnet/minecraft/tileentity/TileEntityBrewingStand;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realDisplayGUIBrewingStand", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V" : "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_146098_a" : "func_146098_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V" : "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superDisplayGUIBrewingStand", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V" : "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146098_a" : "func_146098_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V" : "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalDisplayGUIBrewingStand)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIBrewingStand", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V" : "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146098_a" : "func_146098_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V" : "(Lnet/minecraft/tileentity/TileEntityBrewingStand;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71007_a" : "displayGUIChest", "" + (isObfuscated ? "(Lnet/minecraft/inventory/IInventory;)V" : "(Lnet/minecraft/inventory/IInventory;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "displayGUIChest", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/inventory/IInventory;" : "Lnet/minecraft/inventory/IInventory;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realDisplayGUIChest", "" + (isObfuscated ? "(Lnet/minecraft/inventory/IInventory;)V" : "(Lnet/minecraft/inventory/IInventory;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71007_a" : "displayGUIChest", "" + (isObfuscated ? "(Lnet/minecraft/inventory/IInventory;)V" : "(Lnet/minecraft/inventory/IInventory;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superDisplayGUIChest", "" + (isObfuscated ? "(Lnet/minecraft/inventory/IInventory;)V" : "(Lnet/minecraft/inventory/IInventory;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71007_a" : "displayGUIChest", "" + (isObfuscated ? "(Lnet/minecraft/inventory/IInventory;)V" : "(Lnet/minecraft/inventory/IInventory;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalDisplayGUIChest)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIChest", "" + (isObfuscated ? "(Lnet/minecraft/inventory/IInventory;)V" : "(Lnet/minecraft/inventory/IInventory;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71007_a" : "displayGUIChest", "" + (isObfuscated ? "(Lnet/minecraft/inventory/IInventory;)V" : "(Lnet/minecraft/inventory/IInventory;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_146102_a" : "func_146102_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityDispenser;)V" : "(Lnet/minecraft/tileentity/TileEntityDispenser;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "displayGUIDispenser", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/tileentity/TileEntityDispenser;" : "Lnet/minecraft/tileentity/TileEntityDispenser;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realDisplayGUIDispenser", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityDispenser;)V" : "(Lnet/minecraft/tileentity/TileEntityDispenser;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_146102_a" : "func_146102_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityDispenser;)V" : "(Lnet/minecraft/tileentity/TileEntityDispenser;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superDisplayGUIDispenser", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityDispenser;)V" : "(Lnet/minecraft/tileentity/TileEntityDispenser;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146102_a" : "func_146102_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityDispenser;)V" : "(Lnet/minecraft/tileentity/TileEntityDispenser;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalDisplayGUIDispenser)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIDispenser", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityDispenser;)V" : "(Lnet/minecraft/tileentity/TileEntityDispenser;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146102_a" : "func_146102_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityDispenser;)V" : "(Lnet/minecraft/tileentity/TileEntityDispenser;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_146100_a" : "func_146100_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntity;)V" : "(Lnet/minecraft/tileentity/TileEntity;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "displayGUIEditSign", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/tileentity/TileEntity;" : "Lnet/minecraft/tileentity/TileEntity;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realDisplayGUIEditSign", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntity;)V" : "(Lnet/minecraft/tileentity/TileEntity;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_146100_a" : "func_146100_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntity;)V" : "(Lnet/minecraft/tileentity/TileEntity;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superDisplayGUIEditSign", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntity;)V" : "(Lnet/minecraft/tileentity/TileEntity;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146100_a" : "func_146100_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntity;)V" : "(Lnet/minecraft/tileentity/TileEntity;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalDisplayGUIEditSign)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIEditSign", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntity;)V" : "(Lnet/minecraft/tileentity/TileEntity;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146100_a" : "func_146100_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntity;)V" : "(Lnet/minecraft/tileentity/TileEntity;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71002_c" : "displayGUIEnchantment", "(IIILjava/lang/String;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "displayGUIEnchantment", "(Lapi/player/client/IClientPlayerAPI;IIILjava/lang/String;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realDisplayGUIEnchantment", "(IIILjava/lang/String;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71002_c" : "displayGUIEnchantment", "(IIILjava/lang/String;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superDisplayGUIEnchantment", "(IIILjava/lang/String;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71002_c" : "displayGUIEnchantment", "(IIILjava/lang/String;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalDisplayGUIEnchantment)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIEnchantment", "(IIILjava/lang/String;)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ILOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitVarInsn(Opcodes.ILOAD, 3);
			mv.visitVarInsn(Opcodes.ALOAD, 4);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71002_c" : "displayGUIEnchantment", "(IIILjava/lang/String;)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_146101_a" : "func_146101_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityFurnace;)V" : "(Lnet/minecraft/tileentity/TileEntityFurnace;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "displayGUIFurnace", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/tileentity/TileEntityFurnace;" : "Lnet/minecraft/tileentity/TileEntityFurnace;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realDisplayGUIFurnace", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityFurnace;)V" : "(Lnet/minecraft/tileentity/TileEntityFurnace;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_146101_a" : "func_146101_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityFurnace;)V" : "(Lnet/minecraft/tileentity/TileEntityFurnace;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superDisplayGUIFurnace", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityFurnace;)V" : "(Lnet/minecraft/tileentity/TileEntityFurnace;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146101_a" : "func_146101_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityFurnace;)V" : "(Lnet/minecraft/tileentity/TileEntityFurnace;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalDisplayGUIFurnace)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIFurnace", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityFurnace;)V" : "(Lnet/minecraft/tileentity/TileEntityFurnace;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146101_a" : "func_146101_a", "" + (isObfuscated ? "(Lnet/minecraft/tileentity/TileEntityFurnace;)V" : "(Lnet/minecraft/tileentity/TileEntityFurnace;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71058_b" : "displayGUIWorkbench", "(III)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "displayGUIWorkbench", "(Lapi/player/client/IClientPlayerAPI;III)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realDisplayGUIWorkbench", "(III)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71058_b" : "displayGUIWorkbench", "(III)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superDisplayGUIWorkbench", "(III)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71058_b" : "displayGUIWorkbench", "(III)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalDisplayGUIWorkbench)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDisplayGUIWorkbench", "(III)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ILOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitVarInsn(Opcodes.ILOAD, 3);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71058_b" : "displayGUIWorkbench", "(III)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71040_bB" : "dropOneItem", "" + (isObfuscated ? "(Z)Lnet/minecraft/entity/item/EntityItem;" : "(Z)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "dropOneItem", "(Lapi/player/client/IClientPlayerAPI;Z)" + (isObfuscated ? "Lnet/minecraft/entity/item/EntityItem;" : "Lnet/minecraft/entity/item/EntityItem;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realDropOneItem", "" + (isObfuscated ? "(Z)Lnet/minecraft/entity/item/EntityItem;" : "(Z)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71040_bB" : "dropOneItem", "" + (isObfuscated ? "(Z)Lnet/minecraft/entity/item/EntityItem;" : "(Z)Lnet/minecraft/entity/item/EntityItem;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superDropOneItem", "" + (isObfuscated ? "(Z)Lnet/minecraft/entity/item/EntityItem;" : "(Z)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71040_bB" : "dropOneItem", "" + (isObfuscated ? "(Z)Lnet/minecraft/entity/item/EntityItem;" : "(Z)Lnet/minecraft/entity/item/EntityItem;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalDropOneItem)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDropOneItem", "" + (isObfuscated ? "(Z)Lnet/minecraft/entity/item/EntityItem;" : "(Z)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ILOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71040_bB" : "dropOneItem", "" + (isObfuscated ? "(Z)Lnet/minecraft/entity/item/EntityItem;" : "(Z)Lnet/minecraft/entity/item/EntityItem;") + "");
			mv.visitInsn(Opcodes.ARETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71019_a" : "dropPlayerItemWithRandomChoice", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "dropPlayerItem", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/item/ItemStack;Z" : "Lnet/minecraft/item/ItemStack;Z") + ")" + (isObfuscated ? "Lnet/minecraft/entity/item/EntityItem;" : "Lnet/minecraft/entity/item/EntityItem;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realDropPlayerItem", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71019_a" : "dropPlayerItemWithRandomChoice", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superDropPlayerItem", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71019_a" : "dropPlayerItemWithRandomChoice", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalDropPlayerItem)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDropPlayerItem", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71019_a" : "dropPlayerItemWithRandomChoice", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;") + "");
			mv.visitInsn(Opcodes.ARETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_146097_a" : "func_146097_a", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "dropPlayerItemWithRandomChoice", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/item/ItemStack;ZZ" : "Lnet/minecraft/item/ItemStack;ZZ") + ")" + (isObfuscated ? "Lnet/minecraft/entity/item/EntityItem;" : "Lnet/minecraft/entity/item/EntityItem;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realDropPlayerItemWithRandomChoice", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_146097_a" : "func_146097_a", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superDropPlayerItemWithRandomChoice", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146097_a" : "func_146097_a", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalDropPlayerItemWithRandomChoice)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localDropPlayerItemWithRandomChoice", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitVarInsn(Opcodes.ILOAD, 3);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146097_a" : "func_146097_a", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;" : "(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;") + "");
			mv.visitInsn(Opcodes.ARETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70069_a" : "fall", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "fall", "(Lapi/player/client/IClientPlayerAPI;F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realFall", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70069_a" : "fall", "(F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superFall", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70069_a" : "fall", "(F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalFall)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localFall", "(F)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.FLOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70069_a" : "fall", "(F)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70689_ay" : "getAIMoveSpeed", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getAIMoveSpeed", "(Lapi/player/client/IClientPlayerAPI;)F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetAIMoveSpeed", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70689_ay" : "getAIMoveSpeed", "()F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetAIMoveSpeed", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70689_ay" : "getAIMoveSpeed", "()F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetAIMoveSpeed)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetAIMoveSpeed", "()F", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70689_ay" : "getAIMoveSpeed", "()F");
			mv.visitInsn(Opcodes.FRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71051_bG" : "getBedOrientationInDegrees", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getBedOrientationInDegrees", "(Lapi/player/client/IClientPlayerAPI;)F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetBedOrientationInDegrees", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71051_bG" : "getBedOrientationInDegrees", "()F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetBedOrientationInDegrees", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71051_bG" : "getBedOrientationInDegrees", "()F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetBedOrientationInDegrees)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetBedOrientationInDegrees", "()F", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71051_bG" : "getBedOrientationInDegrees", "()F");
			mv.visitInsn(Opcodes.FRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70013_c" : "getBrightness", "(F)F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getBrightness", "(Lapi/player/client/IClientPlayerAPI;F)F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetBrightness", "(F)F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70013_c" : "getBrightness", "(F)F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetBrightness", "(F)F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70013_c" : "getBrightness", "(F)F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetBrightness)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetBrightness", "(F)F", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.FLOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70013_c" : "getBrightness", "(F)F");
			mv.visitInsn(Opcodes.FRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70070_b" : "getBrightnessForRender", "(F)I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getBrightnessForRender", "(Lapi/player/client/IClientPlayerAPI;F)I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetBrightnessForRender", "(F)I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70070_b" : "getBrightnessForRender", "(F)I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetBrightnessForRender", "(F)I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70070_b" : "getBrightnessForRender", "(F)I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetBrightnessForRender)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetBrightnessForRender", "(F)I", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.FLOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70070_b" : "getBrightnessForRender", "(F)I");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_146096_a" : "getCurrentPlayerStrVsBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;Z)F" : "(Lnet/minecraft/block/Block;Z)F") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getCurrentPlayerStrVsBlock", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/block/Block;Z" : "Lnet/minecraft/block/Block;Z") + ")F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetCurrentPlayerStrVsBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;Z)F" : "(Lnet/minecraft/block/Block;Z)F") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_146096_a" : "getCurrentPlayerStrVsBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;Z)F" : "(Lnet/minecraft/block/Block;Z)F") + "");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetCurrentPlayerStrVsBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;Z)F" : "(Lnet/minecraft/block/Block;Z)F") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146096_a" : "getCurrentPlayerStrVsBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;Z)F" : "(Lnet/minecraft/block/Block;Z)F") + "");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetCurrentPlayerStrVsBlock)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetCurrentPlayerStrVsBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;Z)F" : "(Lnet/minecraft/block/Block;Z)F") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_146096_a" : "getCurrentPlayerStrVsBlock", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;Z)F" : "(Lnet/minecraft/block/Block;Z)F") + "");
			mv.visitInsn(Opcodes.FRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, "getBreakSpeed", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;ZI)F" : "(Lnet/minecraft/block/Block;ZI)F") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getCurrentPlayerStrVsBlockForge", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/block/Block;ZI" : "Lnet/minecraft/block/Block;ZI") + ")F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetCurrentPlayerStrVsBlockForge", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;ZI)F" : "(Lnet/minecraft/block/Block;ZI)F") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", "getBreakSpeed", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;ZI)F" : "(Lnet/minecraft/block/Block;ZI)F") + "");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetCurrentPlayerStrVsBlockForge", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;ZI)F" : "(Lnet/minecraft/block/Block;ZI)F") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", "getBreakSpeed", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;ZI)F" : "(Lnet/minecraft/block/Block;ZI)F") + "");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetCurrentPlayerStrVsBlockForge)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetCurrentPlayerStrVsBlockForge", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;ZI)F" : "(Lnet/minecraft/block/Block;ZI)F") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitVarInsn(Opcodes.ILOAD, 3);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", "getBreakSpeed", "" + (isObfuscated ? "(Lnet/minecraft/block/Block;ZI)F" : "(Lnet/minecraft/block/Block;ZI)F") + "");
			mv.visitInsn(Opcodes.FRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70092_e" : "getDistanceSq", "(DDD)D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getDistanceSq", "(Lapi/player/client/IClientPlayerAPI;DDD)D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetDistanceSq", "(DDD)D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70092_e" : "getDistanceSq", "(DDD)D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetDistanceSq", "(DDD)D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70092_e" : "getDistanceSq", "(DDD)D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetDistanceSq)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetDistanceSq", "(DDD)D", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.DLOAD, 1);
			mv.visitVarInsn(Opcodes.DLOAD, 3);
			mv.visitVarInsn(Opcodes.DLOAD, 5);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70092_e" : "getDistanceSq", "(DDD)D");
			mv.visitInsn(Opcodes.DRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70068_e" : "getDistanceSqToEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)D" : "(Lnet/minecraft/entity/Entity;)D") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getDistanceSqToEntity", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/entity/Entity;" : "Lnet/minecraft/entity/Entity;") + ")D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetDistanceSqToEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)D" : "(Lnet/minecraft/entity/Entity;)D") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70068_e" : "getDistanceSqToEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)D" : "(Lnet/minecraft/entity/Entity;)D") + "");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetDistanceSqToEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)D" : "(Lnet/minecraft/entity/Entity;)D") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70068_e" : "getDistanceSqToEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)D" : "(Lnet/minecraft/entity/Entity;)D") + "");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetDistanceSqToEntity)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetDistanceSqToEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)D" : "(Lnet/minecraft/entity/Entity;)D") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70068_e" : "getDistanceSqToEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;)D" : "(Lnet/minecraft/entity/Entity;)D") + "");
			mv.visitInsn(Opcodes.DRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71151_f" : "getFOVMultiplier", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getFOVMultiplier", "(Lapi/player/client/IClientPlayerAPI;)F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetFOVMultiplier", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71151_f" : "getFOVMultiplier", "()F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetFOVMultiplier", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71151_f" : "getFOVMultiplier", "()F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetFOVMultiplier)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetFOVMultiplier", "()F", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71151_f" : "getFOVMultiplier", "()F");
			mv.visitInsn(Opcodes.FRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70621_aR" : "getHurtSound", "()Ljava/lang/String;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getHurtSound", "(Lapi/player/client/IClientPlayerAPI;)Ljava/lang/String;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetHurtSound", "()Ljava/lang/String;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70621_aR" : "getHurtSound", "()Ljava/lang/String;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetHurtSound", "()Ljava/lang/String;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70621_aR" : "getHurtSound", "()Ljava/lang/String;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetHurtSound)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetHurtSound", "()Ljava/lang/String;", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70621_aR" : "getHurtSound", "()Ljava/lang/String;");
			mv.visitInsn(Opcodes.ARETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70620_b" : "getItemIcon", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;" : "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getItemIcon", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/item/ItemStack;I" : "Lnet/minecraft/item/ItemStack;I") + ")" + (isObfuscated ? "Lnet/minecraft/util/IIcon;" : "Lnet/minecraft/util/IIcon;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetItemIcon", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;" : "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70620_b" : "getItemIcon", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;" : "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetItemIcon", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;" : "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70620_b" : "getItemIcon", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;" : "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetItemIcon)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetItemIcon", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;" : "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70620_b" : "getItemIcon", "" + (isObfuscated ? "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;" : "(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;") + "");
			mv.visitInsn(Opcodes.ARETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71060_bI" : "getSleepTimer", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getSleepTimer", "(Lapi/player/client/IClientPlayerAPI;)I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realGetSleepTimer", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71060_bI" : "getSleepTimer", "()I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superGetSleepTimer", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71060_bI" : "getSleepTimer", "()I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalGetSleepTimer)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localGetSleepTimer", "()I", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71060_bI" : "getSleepTimer", "()I");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70058_J" : "handleLavaMovement", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "handleLavaMovement", "(Lapi/player/client/IClientPlayerAPI;)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realHandleLavaMovement", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70058_J" : "handleLavaMovement", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superHandleLavaMovement", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70058_J" : "handleLavaMovement", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalHandleLavaMovement)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localHandleLavaMovement", "()Z", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70058_J" : "handleLavaMovement", "()Z");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70072_I" : "handleWaterMovement", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "handleWaterMovement", "(Lapi/player/client/IClientPlayerAPI;)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realHandleWaterMovement", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70072_I" : "handleWaterMovement", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superHandleWaterMovement", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70072_I" : "handleWaterMovement", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalHandleWaterMovement)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localHandleWaterMovement", "()Z", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70072_I" : "handleWaterMovement", "()Z");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70691_i" : "heal", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "heal", "(Lapi/player/client/IClientPlayerAPI;F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realHeal", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70691_i" : "heal", "(F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superHeal", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70691_i" : "heal", "(F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalHeal)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localHeal", "(F)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.FLOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70691_i" : "heal", "(F)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70094_T" : "isEntityInsideOpaqueBlock", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "isEntityInsideOpaqueBlock", "(Lapi/player/client/IClientPlayerAPI;)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realIsEntityInsideOpaqueBlock", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70094_T" : "isEntityInsideOpaqueBlock", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superIsEntityInsideOpaqueBlock", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70094_T" : "isEntityInsideOpaqueBlock", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalIsEntityInsideOpaqueBlock)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsEntityInsideOpaqueBlock", "()Z", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70094_T" : "isEntityInsideOpaqueBlock", "()Z");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70090_H" : "isInWater", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "isInWater", "(Lapi/player/client/IClientPlayerAPI;)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realIsInWater", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70090_H" : "isInWater", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superIsInWater", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70090_H" : "isInWater", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalIsInWater)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsInWater", "()Z", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70090_H" : "isInWater", "()Z");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70055_a" : "isInsideOfMaterial", "" + (isObfuscated ? "(Lnet/minecraft/block/material/Material;)Z" : "(Lnet/minecraft/block/material/Material;)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "isInsideOfMaterial", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/block/material/Material;" : "Lnet/minecraft/block/material/Material;") + ")Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realIsInsideOfMaterial", "" + (isObfuscated ? "(Lnet/minecraft/block/material/Material;)Z" : "(Lnet/minecraft/block/material/Material;)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70055_a" : "isInsideOfMaterial", "" + (isObfuscated ? "(Lnet/minecraft/block/material/Material;)Z" : "(Lnet/minecraft/block/material/Material;)Z") + "");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superIsInsideOfMaterial", "" + (isObfuscated ? "(Lnet/minecraft/block/material/Material;)Z" : "(Lnet/minecraft/block/material/Material;)Z") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70055_a" : "isInsideOfMaterial", "" + (isObfuscated ? "(Lnet/minecraft/block/material/Material;)Z" : "(Lnet/minecraft/block/material/Material;)Z") + "");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalIsInsideOfMaterial)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsInsideOfMaterial", "" + (isObfuscated ? "(Lnet/minecraft/block/material/Material;)Z" : "(Lnet/minecraft/block/material/Material;)Z") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70055_a" : "isInsideOfMaterial", "" + (isObfuscated ? "(Lnet/minecraft/block/material/Material;)Z" : "(Lnet/minecraft/block/material/Material;)Z") + "");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70617_f_" : "isOnLadder", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "isOnLadder", "(Lapi/player/client/IClientPlayerAPI;)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realIsOnLadder", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70617_f_" : "isOnLadder", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superIsOnLadder", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70617_f_" : "isOnLadder", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalIsOnLadder)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsOnLadder", "()Z", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70617_f_" : "isOnLadder", "()Z");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70608_bn" : "isPlayerSleeping", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "isPlayerSleeping", "(Lapi/player/client/IClientPlayerAPI;)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realIsPlayerSleeping", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70608_bn" : "isPlayerSleeping", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superIsPlayerSleeping", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70608_bn" : "isPlayerSleeping", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalIsPlayerSleeping)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsPlayerSleeping", "()Z", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70608_bn" : "isPlayerSleeping", "()Z");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70093_af" : "isSneaking", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "isSneaking", "(Lapi/player/client/IClientPlayerAPI;)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realIsSneaking", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70093_af" : "isSneaking", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superIsSneaking", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70093_af" : "isSneaking", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalIsSneaking)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsSneaking", "()Z", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70093_af" : "isSneaking", "()Z");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70051_ag" : "isSprinting", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "isSprinting", "(Lapi/player/client/IClientPlayerAPI;)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realIsSprinting", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70051_ag" : "isSprinting", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superIsSprinting", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70051_ag" : "isSprinting", "()Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalIsSprinting)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localIsSprinting", "()Z", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70051_ag" : "isSprinting", "()Z");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70664_aZ" : "jump", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "jump", "(Lapi/player/client/IClientPlayerAPI;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realJump", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70664_aZ" : "jump", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superJump", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70664_aZ" : "jump", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalJump)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localJump", "()V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70664_aZ" : "jump", "()V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70653_a" : "knockBack", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;FDD)V" : "(Lnet/minecraft/entity/Entity;FDD)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "knockBack", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/entity/Entity;FDD" : "Lnet/minecraft/entity/Entity;FDD") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realKnockBack", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;FDD)V" : "(Lnet/minecraft/entity/Entity;FDD)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70653_a" : "knockBack", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;FDD)V" : "(Lnet/minecraft/entity/Entity;FDD)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superKnockBack", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;FDD)V" : "(Lnet/minecraft/entity/Entity;FDD)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70653_a" : "knockBack", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;FDD)V" : "(Lnet/minecraft/entity/Entity;FDD)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalKnockBack)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localKnockBack", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;FDD)V" : "(Lnet/minecraft/entity/Entity;FDD)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitVarInsn(Opcodes.FLOAD, 2);
			mv.visitVarInsn(Opcodes.DLOAD, 3);
			mv.visitVarInsn(Opcodes.DLOAD, 5);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70653_a" : "knockBack", "" + (isObfuscated ? "(Lnet/minecraft/entity/Entity;FDD)V" : "(Lnet/minecraft/entity/Entity;FDD)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70091_d" : "moveEntity", "(DDD)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "moveEntity", "(Lapi/player/client/IClientPlayerAPI;DDD)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realMoveEntity", "(DDD)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70091_d" : "moveEntity", "(DDD)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superMoveEntity", "(DDD)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70091_d" : "moveEntity", "(DDD)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalMoveEntity)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localMoveEntity", "(DDD)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.DLOAD, 1);
			mv.visitVarInsn(Opcodes.DLOAD, 3);
			mv.visitVarInsn(Opcodes.DLOAD, 5);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70091_d" : "moveEntity", "(DDD)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70612_e" : "moveEntityWithHeading", "(FF)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "moveEntityWithHeading", "(Lapi/player/client/IClientPlayerAPI;FF)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realMoveEntityWithHeading", "(FF)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70612_e" : "moveEntityWithHeading", "(FF)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superMoveEntityWithHeading", "(FF)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70612_e" : "moveEntityWithHeading", "(FF)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalMoveEntityWithHeading)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localMoveEntityWithHeading", "(FF)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.FLOAD, 1);
			mv.visitVarInsn(Opcodes.FLOAD, 2);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70612_e" : "moveEntityWithHeading", "(FF)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70060_a" : "moveFlying", "(FFF)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitVarInsn(Opcodes.FLOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "moveFlying", "(Lapi/player/client/IClientPlayerAPI;FFF)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realMoveFlying", "(FFF)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitVarInsn(Opcodes.FLOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70060_a" : "moveFlying", "(FFF)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superMoveFlying", "(FFF)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 2);
		mv.visitVarInsn(Opcodes.FLOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70060_a" : "moveFlying", "(FFF)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalMoveFlying)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localMoveFlying", "(FFF)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.FLOAD, 1);
			mv.visitVarInsn(Opcodes.FLOAD, 2);
			mv.visitVarInsn(Opcodes.FLOAD, 3);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70060_a" : "moveFlying", "(FFF)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70645_a" : "onDeath", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;)V" : "(Lnet/minecraft/util/DamageSource;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "onDeath", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/util/DamageSource;" : "Lnet/minecraft/util/DamageSource;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realOnDeath", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;)V" : "(Lnet/minecraft/util/DamageSource;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70645_a" : "onDeath", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;)V" : "(Lnet/minecraft/util/DamageSource;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superOnDeath", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;)V" : "(Lnet/minecraft/util/DamageSource;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70645_a" : "onDeath", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;)V" : "(Lnet/minecraft/util/DamageSource;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalOnDeath)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localOnDeath", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;)V" : "(Lnet/minecraft/util/DamageSource;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70645_a" : "onDeath", "" + (isObfuscated ? "(Lnet/minecraft/util/DamageSource;)V" : "(Lnet/minecraft/util/DamageSource;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70636_d" : "onLivingUpdate", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "onLivingUpdate", "(Lapi/player/client/IClientPlayerAPI;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realOnLivingUpdate", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70636_d" : "onLivingUpdate", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superOnLivingUpdate", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70636_d" : "onLivingUpdate", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalOnLivingUpdate)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localOnLivingUpdate", "()V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70636_d" : "onLivingUpdate", "()V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70074_a" : "onKillEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/EntityLivingBase;)V" : "(Lnet/minecraft/entity/EntityLivingBase;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "onKillEntity", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/entity/EntityLivingBase;" : "Lnet/minecraft/entity/EntityLivingBase;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realOnKillEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/EntityLivingBase;)V" : "(Lnet/minecraft/entity/EntityLivingBase;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70074_a" : "onKillEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/EntityLivingBase;)V" : "(Lnet/minecraft/entity/EntityLivingBase;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superOnKillEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/EntityLivingBase;)V" : "(Lnet/minecraft/entity/EntityLivingBase;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70074_a" : "onKillEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/EntityLivingBase;)V" : "(Lnet/minecraft/entity/EntityLivingBase;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalOnKillEntity)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localOnKillEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/EntityLivingBase;)V" : "(Lnet/minecraft/entity/EntityLivingBase;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70074_a" : "onKillEntity", "" + (isObfuscated ? "(Lnet/minecraft/entity/EntityLivingBase;)V" : "(Lnet/minecraft/entity/EntityLivingBase;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70077_a" : "onStruckByLightning", "" + (isObfuscated ? "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V" : "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "onStruckByLightning", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/entity/effect/EntityLightningBolt;" : "Lnet/minecraft/entity/effect/EntityLightningBolt;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realOnStruckByLightning", "" + (isObfuscated ? "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V" : "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70077_a" : "onStruckByLightning", "" + (isObfuscated ? "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V" : "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superOnStruckByLightning", "" + (isObfuscated ? "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V" : "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70077_a" : "onStruckByLightning", "" + (isObfuscated ? "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V" : "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalOnStruckByLightning)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localOnStruckByLightning", "" + (isObfuscated ? "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V" : "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70077_a" : "onStruckByLightning", "" + (isObfuscated ? "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V" : "(Lnet/minecraft/entity/effect/EntityLightningBolt;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70071_h_" : "onUpdate", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "onUpdate", "(Lapi/player/client/IClientPlayerAPI;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realOnUpdate", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70071_h_" : "onUpdate", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superOnUpdate", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70071_h_" : "onUpdate", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalOnUpdate)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localOnUpdate", "()V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70071_h_" : "onUpdate", "()V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_145780_a" : "func_145780_a", "" + (isObfuscated ? "(IIILnet/minecraft/block/Block;)V" : "(IIILnet/minecraft/block/Block;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "playStepSound", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "IIILnet/minecraft/block/Block;" : "IIILnet/minecraft/block/Block;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realPlayStepSound", "" + (isObfuscated ? "(IIILnet/minecraft/block/Block;)V" : "(IIILnet/minecraft/block/Block;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_145780_a" : "func_145780_a", "" + (isObfuscated ? "(IIILnet/minecraft/block/Block;)V" : "(IIILnet/minecraft/block/Block;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superPlayStepSound", "" + (isObfuscated ? "(IIILnet/minecraft/block/Block;)V" : "(IIILnet/minecraft/block/Block;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_145780_a" : "func_145780_a", "" + (isObfuscated ? "(IIILnet/minecraft/block/Block;)V" : "(IIILnet/minecraft/block/Block;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalPlayStepSound)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localPlayStepSound", "" + (isObfuscated ? "(IIILnet/minecraft/block/Block;)V" : "(IIILnet/minecraft/block/Block;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ILOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitVarInsn(Opcodes.ILOAD, 3);
			mv.visitVarInsn(Opcodes.ALOAD, 4);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_145780_a" : "func_145780_a", "" + (isObfuscated ? "(IIILnet/minecraft/block/Block;)V" : "(IIILnet/minecraft/block/Block;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_145771_j" : "func_145771_j", "(DDD)Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "pushOutOfBlocks", "(Lapi/player/client/IClientPlayerAPI;DDD)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realPushOutOfBlocks", "(DDD)Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_145771_j" : "func_145771_j", "(DDD)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superPushOutOfBlocks", "(DDD)Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_145771_j" : "func_145771_j", "(DDD)Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalPushOutOfBlocks)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localPushOutOfBlocks", "(DDD)Z", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.DLOAD, 1);
			mv.visitVarInsn(Opcodes.DLOAD, 3);
			mv.visitVarInsn(Opcodes.DLOAD, 5);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_145771_j" : "func_145771_j", "(DDD)Z");
			mv.visitInsn(Opcodes.IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70614_a" : "rayTrace", "" + (isObfuscated ? "(DF)Lnet/minecraft/util/MovingObjectPosition;" : "(DF)Lnet/minecraft/util/MovingObjectPosition;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "rayTrace", "(Lapi/player/client/IClientPlayerAPI;DF)" + (isObfuscated ? "Lnet/minecraft/util/MovingObjectPosition;" : "Lnet/minecraft/util/MovingObjectPosition;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realRayTrace", "" + (isObfuscated ? "(DF)Lnet/minecraft/util/MovingObjectPosition;" : "(DF)Lnet/minecraft/util/MovingObjectPosition;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70614_a" : "rayTrace", "" + (isObfuscated ? "(DF)Lnet/minecraft/util/MovingObjectPosition;" : "(DF)Lnet/minecraft/util/MovingObjectPosition;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superRayTrace", "" + (isObfuscated ? "(DF)Lnet/minecraft/util/MovingObjectPosition;" : "(DF)Lnet/minecraft/util/MovingObjectPosition;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.FLOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70614_a" : "rayTrace", "" + (isObfuscated ? "(DF)Lnet/minecraft/util/MovingObjectPosition;" : "(DF)Lnet/minecraft/util/MovingObjectPosition;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalRayTrace)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localRayTrace", "" + (isObfuscated ? "(DF)Lnet/minecraft/util/MovingObjectPosition;" : "(DF)Lnet/minecraft/util/MovingObjectPosition;") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.DLOAD, 1);
			mv.visitVarInsn(Opcodes.FLOAD, 3);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70614_a" : "rayTrace", "" + (isObfuscated ? "(DF)Lnet/minecraft/util/MovingObjectPosition;" : "(DF)Lnet/minecraft/util/MovingObjectPosition;") + "");
			mv.visitInsn(Opcodes.ARETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70037_a" : "readEntityFromNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "readEntityFromNBT", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/nbt/NBTTagCompound;" : "Lnet/minecraft/nbt/NBTTagCompound;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realReadEntityFromNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70037_a" : "readEntityFromNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superReadEntityFromNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70037_a" : "readEntityFromNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalReadEntityFromNBT)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localReadEntityFromNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70037_a" : "readEntityFromNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71004_bE" : "respawnPlayer", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "respawnPlayer", "(Lapi/player/client/IClientPlayerAPI;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realRespawnPlayer", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71004_bE" : "respawnPlayer", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superRespawnPlayer", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71004_bE" : "respawnPlayer", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalRespawnPlayer)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localRespawnPlayer", "()V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71004_bE" : "respawnPlayer", "()V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70106_y" : "setDead", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "setDead", "(Lapi/player/client/IClientPlayerAPI;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realSetDead", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70106_y" : "setDead", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superSetDead", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70106_y" : "setDead", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalSetDead)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSetDead", "()V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70106_y" : "setDead", "()V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71150_b" : "setPlayerSPHealth", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "setPlayerSPHealth", "(Lapi/player/client/IClientPlayerAPI;F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realSetPlayerSPHealth", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71150_b" : "setPlayerSPHealth", "(F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superSetPlayerSPHealth", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71150_b" : "setPlayerSPHealth", "(F)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalSetPlayerSPHealth)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSetPlayerSPHealth", "(F)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.FLOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71150_b" : "setPlayerSPHealth", "(F)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70080_a" : "setPositionAndRotation", "(DDDFF)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitVarInsn(Opcodes.FLOAD, 7);
		mv.visitVarInsn(Opcodes.FLOAD, 8);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "setPositionAndRotation", "(Lapi/player/client/IClientPlayerAPI;DDDFF)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realSetPositionAndRotation", "(DDDFF)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitVarInsn(Opcodes.FLOAD, 7);
		mv.visitVarInsn(Opcodes.FLOAD, 8);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70080_a" : "setPositionAndRotation", "(DDDFF)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superSetPositionAndRotation", "(DDDFF)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitVarInsn(Opcodes.DLOAD, 3);
		mv.visitVarInsn(Opcodes.DLOAD, 5);
		mv.visitVarInsn(Opcodes.FLOAD, 7);
		mv.visitVarInsn(Opcodes.FLOAD, 8);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70080_a" : "setPositionAndRotation", "(DDDFF)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalSetPositionAndRotation)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSetPositionAndRotation", "(DDDFF)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.DLOAD, 1);
			mv.visitVarInsn(Opcodes.DLOAD, 3);
			mv.visitVarInsn(Opcodes.DLOAD, 5);
			mv.visitVarInsn(Opcodes.FLOAD, 7);
			mv.visitVarInsn(Opcodes.FLOAD, 8);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70080_a" : "setPositionAndRotation", "(DDDFF)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70095_a" : "setSneaking", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "setSneaking", "(Lapi/player/client/IClientPlayerAPI;Z)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realSetSneaking", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70095_a" : "setSneaking", "(Z)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superSetSneaking", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70095_a" : "setSneaking", "(Z)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalSetSneaking)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSetSneaking", "(Z)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ILOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70095_a" : "setSneaking", "(Z)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70031_b" : "setSprinting", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "setSprinting", "(Lapi/player/client/IClientPlayerAPI;Z)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realSetSprinting", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70031_b" : "setSprinting", "(Z)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superSetSprinting", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70031_b" : "setSprinting", "(Z)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalSetSprinting)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSetSprinting", "(Z)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ILOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70031_b" : "setSprinting", "(Z)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71018_a" : "sleepInBedAt", "" + (isObfuscated ? "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;" : "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "sleepInBedAt", "(Lapi/player/client/IClientPlayerAPI;III)" + (isObfuscated ? "Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;" : "Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realSleepInBedAt", "" + (isObfuscated ? "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;" : "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71018_a" : "sleepInBedAt", "" + (isObfuscated ? "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;" : "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superSleepInBedAt", "" + (isObfuscated ? "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;" : "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71018_a" : "sleepInBedAt", "" + (isObfuscated ? "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;" : "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;") + "");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalSleepInBedAt)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSleepInBedAt", "" + (isObfuscated ? "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;" : "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ILOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitVarInsn(Opcodes.ILOAD, 3);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71018_a" : "sleepInBedAt", "" + (isObfuscated ? "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;" : "(III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;") + "");
			mv.visitInsn(Opcodes.ARETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_71038_i" : "swingItem", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "swingItem", "(Lapi/player/client/IClientPlayerAPI;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realSwingItem", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_71038_i" : "swingItem", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superSwingItem", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71038_i" : "swingItem", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalSwingItem)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localSwingItem", "()V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_71038_i" : "swingItem", "()V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70626_be" : "updateEntityActionState", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "updateEntityActionState", "(Lapi/player/client/IClientPlayerAPI;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realUpdateEntityActionState", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70626_be" : "updateEntityActionState", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superUpdateEntityActionState", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70626_be" : "updateEntityActionState", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalUpdateEntityActionState)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localUpdateEntityActionState", "()V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70626_be" : "updateEntityActionState", "()V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70098_U" : "updateRidden", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "updateRidden", "(Lapi/player/client/IClientPlayerAPI;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realUpdateRidden", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70098_U" : "updateRidden", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superUpdateRidden", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70098_U" : "updateRidden", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalUpdateRidden)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localUpdateRidden", "()V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70098_U" : "updateRidden", "()V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70999_a" : "wakeUpPlayer", "(ZZZ)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "wakeUpPlayer", "(Lapi/player/client/IClientPlayerAPI;ZZZ)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realWakeUpPlayer", "(ZZZ)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70999_a" : "wakeUpPlayer", "(ZZZ)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superWakeUpPlayer", "(ZZZ)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70999_a" : "wakeUpPlayer", "(ZZZ)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalWakeUpPlayer)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localWakeUpPlayer", "(ZZZ)V", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ILOAD, 1);
			mv.visitVarInsn(Opcodes.ILOAD, 2);
			mv.visitVarInsn(Opcodes.ILOAD, 3);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70999_a" : "wakeUpPlayer", "(ZZZ)V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC, isObfuscated ? "func_70014_b" : "writeEntityToNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "writeEntityToNBT", "(Lapi/player/client/IClientPlayerAPI;" + (isObfuscated ? "Lnet/minecraft/nbt/NBTTagCompound;" : "Lnet/minecraft/nbt/NBTTagCompound;") + ")V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "realWriteEntityToNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "func_70014_b" : "writeEntityToNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "superWriteEntityToNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70014_b" : "writeEntityToNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if(!hadLocalWriteEntityToNBT)
		{
			mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "localWriteEntityToNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, isObfuscated ? "net/minecraft/client/entity/AbstractClientPlayer" : "net/minecraft/client/entity/AbstractClientPlayer", isObfuscated ? "func_70014_b" : "writeEntityToNBT", "" + (isObfuscated ? "(Lnet/minecraft/nbt/NBTTagCompound;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V") + "");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getAddedToChunkField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70175_ag" : "addedToChunk", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setAddedToChunkField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70175_ag" : "addedToChunk", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getArrowHitTimerField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70720_be" : "arrowHitTimer", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setArrowHitTimerField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70720_be" : "arrowHitTimer", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getAttackTimeField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70724_aR" : "attackTime", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setAttackTimeField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70724_aR" : "attackTime", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getAttackedAtYawField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70739_aP" : "attackedAtYaw", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setAttackedAtYawField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70739_aP" : "attackedAtYaw", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getAttackingPlayerField", isObfuscated ? "()Lnet/minecraft/entity/player/EntityPlayer;" : "()Lnet/minecraft/entity/player/EntityPlayer;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70717_bb" : "attackingPlayer", isObfuscated ? "Lnet/minecraft/entity/player/EntityPlayer;" : "Lnet/minecraft/entity/player/EntityPlayer;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setAttackingPlayerField", isObfuscated ? "(Lnet/minecraft/entity/player/EntityPlayer;)V" : "(Lnet/minecraft/entity/player/EntityPlayer;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70717_bb" : "attackingPlayer", isObfuscated ? "Lnet/minecraft/entity/player/EntityPlayer;" : "Lnet/minecraft/entity/player/EntityPlayer;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getBoundingBoxField", isObfuscated ? "()Lnet/minecraft/util/AxisAlignedBB;" : "()Lnet/minecraft/util/AxisAlignedBB;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70121_D" : "boundingBox", isObfuscated ? "Lnet/minecraft/util/AxisAlignedBB;" : "Lnet/minecraft/util/AxisAlignedBB;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getCameraPitchField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70726_aT" : "cameraPitch", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setCameraPitchField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70726_aT" : "cameraPitch", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getCameraYawField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71109_bG" : "cameraYaw", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setCameraYawField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71109_bG" : "cameraYaw", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getCapabilitiesField", isObfuscated ? "()Lnet/minecraft/entity/player/PlayerCapabilities;" : "()Lnet/minecraft/entity/player/PlayerCapabilities;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71075_bZ" : "capabilities", isObfuscated ? "Lnet/minecraft/entity/player/PlayerCapabilities;" : "Lnet/minecraft/entity/player/PlayerCapabilities;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setCapabilitiesField", isObfuscated ? "(Lnet/minecraft/entity/player/PlayerCapabilities;)V" : "(Lnet/minecraft/entity/player/PlayerCapabilities;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71075_bZ" : "capabilities", isObfuscated ? "Lnet/minecraft/entity/player/PlayerCapabilities;" : "Lnet/minecraft/entity/player/PlayerCapabilities;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getChunkCoordXField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70176_ah" : "chunkCoordX", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setChunkCoordXField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70176_ah" : "chunkCoordX", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getChunkCoordYField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70162_ai" : "chunkCoordY", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setChunkCoordYField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70162_ai" : "chunkCoordY", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getChunkCoordZField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70164_aj" : "chunkCoordZ", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setChunkCoordZField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70164_aj" : "chunkCoordZ", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getDataWatcherField", isObfuscated ? "()Lnet/minecraft/entity/DataWatcher;" : "()Lnet/minecraft/entity/DataWatcher;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70180_af" : "dataWatcher", isObfuscated ? "Lnet/minecraft/entity/DataWatcher;" : "Lnet/minecraft/entity/DataWatcher;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setDataWatcherField", isObfuscated ? "(Lnet/minecraft/entity/DataWatcher;)V" : "(Lnet/minecraft/entity/DataWatcher;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70180_af" : "dataWatcher", isObfuscated ? "Lnet/minecraft/entity/DataWatcher;" : "Lnet/minecraft/entity/DataWatcher;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getDeadField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70729_aU" : "dead", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setDeadField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70729_aU" : "dead", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getDeathTimeField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70725_aQ" : "deathTime", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setDeathTimeField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70725_aQ" : "deathTime", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getDimensionField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71093_bK" : "dimension", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setDimensionField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71093_bK" : "dimension", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getDistanceWalkedModifiedField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70140_Q" : "distanceWalkedModified", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setDistanceWalkedModifiedField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70140_Q" : "distanceWalkedModified", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getDistanceWalkedOnStepModifiedField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_82151_R" : "distanceWalkedOnStepModified", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setDistanceWalkedOnStepModifiedField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_82151_R" : "distanceWalkedOnStepModified", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getEntityAgeField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70708_bq" : "entityAge", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setEntityAgeField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70708_bq" : "entityAge", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getEntityCollisionReductionField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70144_Y" : "entityCollisionReduction", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setEntityCollisionReductionField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70144_Y" : "entityCollisionReduction", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getEntityUniqueIDField", "()Ljava/util/UUID;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_96093_i" : "entityUniqueID", "Ljava/util/UUID;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setEntityUniqueIDField", "(Ljava/util/UUID;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_96093_i" : "entityUniqueID", "Ljava/util/UUID;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getExperienceField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71106_cc" : "experience", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setExperienceField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71106_cc" : "experience", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getExperienceLevelField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71068_ca" : "experienceLevel", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setExperienceLevelField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71068_ca" : "experienceLevel", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getExperienceTotalField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71067_cb" : "experienceTotal", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setExperienceTotalField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71067_cb" : "experienceTotal", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getFallDistanceField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70143_R" : "fallDistance", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setFallDistanceField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70143_R" : "fallDistance", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_110154_aXField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110154_aX" : "field_110154_aX", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_110154_aXField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110154_aX" : "field_110154_aX", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_70135_KField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70135_K" : "field_70135_K", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_70135_KField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70135_K" : "field_70135_K", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_70741_aBField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70741_aB" : "field_70741_aB", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_70741_aBField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70741_aB" : "field_70741_aB", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_70763_axField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70763_ax" : "field_70763_ax", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_70763_axField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70763_ax" : "field_70763_ax", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_70764_awField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70764_aw" : "field_70764_aw", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_70764_awField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70764_aw" : "field_70764_aw", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_70768_auField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70768_au" : "field_70768_au", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_70768_auField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70768_au" : "field_70768_au", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_70769_aoField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70769_ao" : "field_70769_ao", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_70769_aoField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70769_ao" : "field_70769_ao", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_70770_apField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70770_ap" : "field_70770_ap", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_70770_apField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70770_ap" : "field_70770_ap", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71079_bUField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71079_bU" : "field_71079_bU", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71079_bUField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71079_bU" : "field_71079_bU", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71082_cxField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71082_cx" : "field_71082_cx", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71082_cxField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71082_cx" : "field_71082_cx", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71085_bRField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71085_bR" : "field_71085_bR", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71085_bRField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71085_bR" : "field_71085_bR", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71089_bVField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71089_bV" : "field_71089_bV", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71089_bVField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71089_bV" : "field_71089_bV", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71091_bMField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71091_bM" : "field_71091_bM", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71091_bMField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71091_bM" : "field_71091_bM", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71094_bPField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71094_bP" : "field_71094_bP", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71094_bPField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71094_bP" : "field_71094_bP", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71095_bQField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71095_bQ" : "field_71095_bQ", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71095_bQField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71095_bQ" : "field_71095_bQ", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71096_bNField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71096_bN" : "field_71096_bN", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71096_bNField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71096_bN" : "field_71096_bN", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71097_bOField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71097_bO" : "field_71097_bO", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71097_bOField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71097_bO" : "field_71097_bO", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71160_ciField", isObfuscated ? "()Lnet/minecraft/util/MouseFilter;" : "()Lnet/minecraft/util/MouseFilter;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71160_ci" : "field_71160_ci", isObfuscated ? "Lnet/minecraft/util/MouseFilter;" : "Lnet/minecraft/util/MouseFilter;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71160_ciField", isObfuscated ? "(Lnet/minecraft/util/MouseFilter;)V" : "(Lnet/minecraft/util/MouseFilter;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71160_ci" : "field_71160_ci", isObfuscated ? "Lnet/minecraft/util/MouseFilter;" : "Lnet/minecraft/util/MouseFilter;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71161_cjField", isObfuscated ? "()Lnet/minecraft/util/MouseFilter;" : "()Lnet/minecraft/util/MouseFilter;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71161_cj" : "field_71161_cj", isObfuscated ? "Lnet/minecraft/util/MouseFilter;" : "Lnet/minecraft/util/MouseFilter;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71161_cjField", isObfuscated ? "(Lnet/minecraft/util/MouseFilter;)V" : "(Lnet/minecraft/util/MouseFilter;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71161_cj" : "field_71161_cj", isObfuscated ? "Lnet/minecraft/util/MouseFilter;" : "Lnet/minecraft/util/MouseFilter;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getField_71162_chField", isObfuscated ? "()Lnet/minecraft/util/MouseFilter;" : "()Lnet/minecraft/util/MouseFilter;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71162_ch" : "field_71162_ch", isObfuscated ? "Lnet/minecraft/util/MouseFilter;" : "Lnet/minecraft/util/MouseFilter;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setField_71162_chField", isObfuscated ? "(Lnet/minecraft/util/MouseFilter;)V" : "(Lnet/minecraft/util/MouseFilter;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71162_ch" : "field_71162_ch", isObfuscated ? "Lnet/minecraft/util/MouseFilter;" : "Lnet/minecraft/util/MouseFilter;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getFireResistanceField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70174_ab" : "fireResistance", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setFireResistanceField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70174_ab" : "fireResistance", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getFishEntityField", isObfuscated ? "()Lnet/minecraft/entity/projectile/EntityFishHook;" : "()Lnet/minecraft/entity/projectile/EntityFishHook;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71104_cf" : "fishEntity", isObfuscated ? "Lnet/minecraft/entity/projectile/EntityFishHook;" : "Lnet/minecraft/entity/projectile/EntityFishHook;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setFishEntityField", isObfuscated ? "(Lnet/minecraft/entity/projectile/EntityFishHook;)V" : "(Lnet/minecraft/entity/projectile/EntityFishHook;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71104_cf" : "fishEntity", isObfuscated ? "Lnet/minecraft/entity/projectile/EntityFishHook;" : "Lnet/minecraft/entity/projectile/EntityFishHook;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getFlyToggleTimerField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71101_bC" : "flyToggleTimer", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setFlyToggleTimerField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71101_bC" : "flyToggleTimer", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getFoodStatsField", isObfuscated ? "()Lnet/minecraft/util/FoodStats;" : "()Lnet/minecraft/util/FoodStats;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71100_bB" : "foodStats", isObfuscated ? "Lnet/minecraft/util/FoodStats;" : "Lnet/minecraft/util/FoodStats;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setFoodStatsField", isObfuscated ? "(Lnet/minecraft/util/FoodStats;)V" : "(Lnet/minecraft/util/FoodStats;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71100_bB" : "foodStats", isObfuscated ? "Lnet/minecraft/util/FoodStats;" : "Lnet/minecraft/util/FoodStats;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getForceSpawnField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_98038_p" : "forceSpawn", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setForceSpawnField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_98038_p" : "forceSpawn", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getHeightField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70131_O" : "height", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setHeightField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70131_O" : "height", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getHorseJumpPowerField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110321_bQ" : "horseJumpPower", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setHorseJumpPowerField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110321_bQ" : "horseJumpPower", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getHorseJumpPowerCounterField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110320_a" : "horseJumpPowerCounter", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setHorseJumpPowerCounterField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110320_a" : "horseJumpPowerCounter", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getHurtResistantTimeField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70172_ad" : "hurtResistantTime", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setHurtResistantTimeField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70172_ad" : "hurtResistantTime", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getHurtTimeField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70737_aN" : "hurtTime", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setHurtTimeField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70737_aN" : "hurtTime", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getIgnoreFrustumCheckField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70158_ak" : "ignoreFrustumCheck", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setIgnoreFrustumCheckField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70158_ak" : "ignoreFrustumCheck", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getInPortalField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71087_bX" : "inPortal", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setInPortalField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71087_bX" : "inPortal", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getInWaterField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70171_ac" : "inWater", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setInWaterField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70171_ac" : "inWater", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getInventoryField", isObfuscated ? "()Lnet/minecraft/entity/player/InventoryPlayer;" : "()Lnet/minecraft/entity/player/InventoryPlayer;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71071_by" : "inventory", isObfuscated ? "Lnet/minecraft/entity/player/InventoryPlayer;" : "Lnet/minecraft/entity/player/InventoryPlayer;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setInventoryField", isObfuscated ? "(Lnet/minecraft/entity/player/InventoryPlayer;)V" : "(Lnet/minecraft/entity/player/InventoryPlayer;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71071_by" : "inventory", isObfuscated ? "Lnet/minecraft/entity/player/InventoryPlayer;" : "Lnet/minecraft/entity/player/InventoryPlayer;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getInventoryContainerField", isObfuscated ? "()Lnet/minecraft/inventory/Container;" : "()Lnet/minecraft/inventory/Container;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71069_bz" : "inventoryContainer", isObfuscated ? "Lnet/minecraft/inventory/Container;" : "Lnet/minecraft/inventory/Container;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setInventoryContainerField", isObfuscated ? "(Lnet/minecraft/inventory/Container;)V" : "(Lnet/minecraft/inventory/Container;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71069_bz" : "inventoryContainer", isObfuscated ? "Lnet/minecraft/inventory/Container;" : "Lnet/minecraft/inventory/Container;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getIsAirBorneField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70160_al" : "isAirBorne", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setIsAirBorneField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70160_al" : "isAirBorne", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getIsCollidedField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70132_H" : "isCollided", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setIsCollidedField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70132_H" : "isCollided", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getIsCollidedHorizontallyField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70123_F" : "isCollidedHorizontally", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setIsCollidedHorizontallyField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70123_F" : "isCollidedHorizontally", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getIsCollidedVerticallyField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70124_G" : "isCollidedVertically", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setIsCollidedVerticallyField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70124_G" : "isCollidedVertically", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getIsDeadField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70128_L" : "isDead", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setIsDeadField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70128_L" : "isDead", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getIsImmuneToFireField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70178_ae" : "isImmuneToFire", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setIsImmuneToFireField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70178_ae" : "isImmuneToFire", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getIsInWebField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70134_J" : "isInWeb", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setIsInWebField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70134_J" : "isInWeb", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getIsJumpingField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70703_bu" : "isJumping", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setIsJumpingField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70703_bu" : "isJumping", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getIsSwingInProgressField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_82175_bq" : "isSwingInProgress", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setIsSwingInProgressField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_82175_bq" : "isSwingInProgress", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getJumpMovementFactorField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70747_aH" : "jumpMovementFactor", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setJumpMovementFactorField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70747_aH" : "jumpMovementFactor", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getLastDamageField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110153_bc" : "lastDamage", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setLastDamageField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110153_bc" : "lastDamage", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getLastTickPosXField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70142_S" : "lastTickPosX", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setLastTickPosXField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70142_S" : "lastTickPosX", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getLastTickPosYField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70137_T" : "lastTickPosY", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setLastTickPosYField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70137_T" : "lastTickPosY", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getLastTickPosZField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70136_U" : "lastTickPosZ", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setLastTickPosZField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70136_U" : "lastTickPosZ", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getLimbSwingField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70754_ba" : "limbSwing", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setLimbSwingField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70754_ba" : "limbSwing", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getLimbSwingAmountField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70721_aZ" : "limbSwingAmount", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setLimbSwingAmountField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70721_aZ" : "limbSwingAmount", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getMaxHurtResistantTimeField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70771_an" : "maxHurtResistantTime", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setMaxHurtResistantTimeField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70771_an" : "maxHurtResistantTime", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getMaxHurtTimeField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70738_aO" : "maxHurtTime", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setMaxHurtTimeField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70738_aO" : "maxHurtTime", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getMcField", isObfuscated ? "()Lnet/minecraft/client/Minecraft;" : "()Lnet/minecraft/client/Minecraft;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71159_c" : "mc", isObfuscated ? "Lnet/minecraft/client/Minecraft;" : "Lnet/minecraft/client/Minecraft;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setMcField", isObfuscated ? "(Lnet/minecraft/client/Minecraft;)V" : "(Lnet/minecraft/client/Minecraft;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71159_c" : "mc", isObfuscated ? "Lnet/minecraft/client/Minecraft;" : "Lnet/minecraft/client/Minecraft;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getMotionXField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70159_w" : "motionX", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setMotionXField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70159_w" : "motionX", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getMotionYField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70181_x" : "motionY", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setMotionYField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70181_x" : "motionY", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getMotionZField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70179_y" : "motionZ", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setMotionZField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70179_y" : "motionZ", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getMoveForwardField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70701_bs" : "moveForward", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setMoveForwardField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70701_bs" : "moveForward", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getMoveStrafingField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70702_br" : "moveStrafing", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setMoveStrafingField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70702_br" : "moveStrafing", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getMovementInputField", isObfuscated ? "()Lnet/minecraft/util/MovementInput;" : "()Lnet/minecraft/util/MovementInput;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71158_b" : "movementInput", isObfuscated ? "Lnet/minecraft/util/MovementInput;" : "Lnet/minecraft/util/MovementInput;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setMovementInputField", isObfuscated ? "(Lnet/minecraft/util/MovementInput;)V" : "(Lnet/minecraft/util/MovementInput;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71158_b" : "movementInput", isObfuscated ? "Lnet/minecraft/util/MovementInput;" : "Lnet/minecraft/util/MovementInput;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getMyEntitySizeField", isObfuscated ? "()Lnet/minecraft/entity/Entity$EnumEntitySize;" : "()Lnet/minecraft/entity/Entity$EnumEntitySize;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70168_am" : "myEntitySize", isObfuscated ? "Lnet/minecraft/entity/Entity$EnumEntitySize;" : "Lnet/minecraft/entity/Entity$EnumEntitySize;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setMyEntitySizeField", isObfuscated ? "(Lnet/minecraft/entity/Entity$EnumEntitySize;)V" : "(Lnet/minecraft/entity/Entity$EnumEntitySize;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70168_am" : "myEntitySize", isObfuscated ? "Lnet/minecraft/entity/Entity$EnumEntitySize;" : "Lnet/minecraft/entity/Entity$EnumEntitySize;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getNewPosRotationIncrementsField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70716_bi" : "newPosRotationIncrements", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setNewPosRotationIncrementsField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70716_bi" : "newPosRotationIncrements", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getNewPosXField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70709_bj" : "newPosX", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setNewPosXField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70709_bj" : "newPosX", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getNewPosYField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70710_bk" : "newPosY", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setNewPosYField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70710_bk" : "newPosY", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getNewPosZField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110152_bk" : "newPosZ", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setNewPosZField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110152_bk" : "newPosZ", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getNewRotationPitchField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70705_bn" : "newRotationPitch", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setNewRotationPitchField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70705_bn" : "newRotationPitch", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getNewRotationYawField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70712_bm" : "newRotationYaw", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setNewRotationYawField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70712_bm" : "newRotationYaw", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getNoClipField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70145_X" : "noClip", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setNoClipField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70145_X" : "noClip", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getOnGroundField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70122_E" : "onGround", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setOnGroundField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70122_E" : "onGround", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getOpenContainerField", isObfuscated ? "()Lnet/minecraft/inventory/Container;" : "()Lnet/minecraft/inventory/Container;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71070_bA" : "openContainer", isObfuscated ? "Lnet/minecraft/inventory/Container;" : "Lnet/minecraft/inventory/Container;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setOpenContainerField", isObfuscated ? "(Lnet/minecraft/inventory/Container;)V" : "(Lnet/minecraft/inventory/Container;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71070_bA" : "openContainer", isObfuscated ? "Lnet/minecraft/inventory/Container;" : "Lnet/minecraft/inventory/Container;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPlayerLocationField", isObfuscated ? "()Lnet/minecraft/util/ChunkCoordinates;" : "()Lnet/minecraft/util/ChunkCoordinates;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71081_bT" : "playerLocation", isObfuscated ? "Lnet/minecraft/util/ChunkCoordinates;" : "Lnet/minecraft/util/ChunkCoordinates;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPlayerLocationField", isObfuscated ? "(Lnet/minecraft/util/ChunkCoordinates;)V" : "(Lnet/minecraft/util/ChunkCoordinates;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71081_bT" : "playerLocation", isObfuscated ? "Lnet/minecraft/util/ChunkCoordinates;" : "Lnet/minecraft/util/ChunkCoordinates;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPortalCounterField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_82153_h" : "portalCounter", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPortalCounterField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_82153_h" : "portalCounter", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPosXField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70165_t" : "posX", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPosXField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70165_t" : "posX", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPosYField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70163_u" : "posY", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPosYField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70163_u" : "posY", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPosZField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70161_v" : "posZ", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPosZField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70161_v" : "posZ", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevCameraPitchField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70727_aS" : "prevCameraPitch", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevCameraPitchField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70727_aS" : "prevCameraPitch", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevCameraYawField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71107_bF" : "prevCameraYaw", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevCameraYawField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71107_bF" : "prevCameraYaw", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevDistanceWalkedModifiedField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70141_P" : "prevDistanceWalkedModified", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevDistanceWalkedModifiedField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70141_P" : "prevDistanceWalkedModified", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevHealthField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70735_aL" : "prevHealth", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevHealthField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70735_aL" : "prevHealth", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevLimbSwingAmountField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70722_aY" : "prevLimbSwingAmount", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevLimbSwingAmountField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70722_aY" : "prevLimbSwingAmount", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevPosXField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70169_q" : "prevPosX", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevPosXField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70169_q" : "prevPosX", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevPosYField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70167_r" : "prevPosY", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevPosYField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70167_r" : "prevPosY", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevPosZField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70166_s" : "prevPosZ", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevPosZField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70166_s" : "prevPosZ", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevRenderArmPitchField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71164_i" : "prevRenderArmPitch", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevRenderArmPitchField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71164_i" : "prevRenderArmPitch", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevRenderArmYawField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71163_h" : "prevRenderArmYaw", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevRenderArmYawField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71163_h" : "prevRenderArmYaw", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevRenderYawOffsetField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70760_ar" : "prevRenderYawOffset", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevRenderYawOffsetField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70760_ar" : "prevRenderYawOffset", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevRotationPitchField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70127_C" : "prevRotationPitch", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevRotationPitchField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70127_C" : "prevRotationPitch", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevRotationYawField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70126_B" : "prevRotationYaw", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevRotationYawField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70126_B" : "prevRotationYaw", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevRotationYawHeadField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70758_at" : "prevRotationYawHead", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevRotationYawHeadField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70758_at" : "prevRotationYawHead", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevSwingProgressField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70732_aI" : "prevSwingProgress", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevSwingProgressField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70732_aI" : "prevSwingProgress", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPrevTimeInPortalField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71080_cy" : "prevTimeInPortal", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPrevTimeInPortalField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71080_cy" : "prevTimeInPortal", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getPreventEntitySpawningField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70156_m" : "preventEntitySpawning", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setPreventEntitySpawningField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70156_m" : "preventEntitySpawning", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRandField", "()Ljava/util/Random;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70146_Z" : "rand", "Ljava/util/Random;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRandField", "(Ljava/util/Random;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70146_Z" : "rand", "Ljava/util/Random;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRandomYawVelocityField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70704_bt" : "randomYawVelocity", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRandomYawVelocityField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70704_bt" : "randomYawVelocity", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRecentlyHitField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70718_bc" : "recentlyHit", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRecentlyHitField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70718_bc" : "recentlyHit", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRenderArmPitchField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71155_g" : "renderArmPitch", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRenderArmPitchField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71155_g" : "renderArmPitch", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRenderArmYawField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71154_f" : "renderArmYaw", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRenderArmYawField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71154_f" : "renderArmYaw", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRenderDistanceWeightField", "()D", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70155_l" : "renderDistanceWeight", "D");
		mv.visitInsn(Opcodes.DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRenderDistanceWeightField", "(D)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.DLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70155_l" : "renderDistanceWeight", "D");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRenderYawOffsetField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70761_aq" : "renderYawOffset", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRenderYawOffsetField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70761_aq" : "renderYawOffset", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRiddenByEntityField", isObfuscated ? "()Lnet/minecraft/entity/Entity;" : "()Lnet/minecraft/entity/Entity;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70153_n" : "riddenByEntity", isObfuscated ? "Lnet/minecraft/entity/Entity;" : "Lnet/minecraft/entity/Entity;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRiddenByEntityField", isObfuscated ? "(Lnet/minecraft/entity/Entity;)V" : "(Lnet/minecraft/entity/Entity;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70153_n" : "riddenByEntity", isObfuscated ? "Lnet/minecraft/entity/Entity;" : "Lnet/minecraft/entity/Entity;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRidingEntityField", isObfuscated ? "()Lnet/minecraft/entity/Entity;" : "()Lnet/minecraft/entity/Entity;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70154_o" : "ridingEntity", isObfuscated ? "Lnet/minecraft/entity/Entity;" : "Lnet/minecraft/entity/Entity;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRidingEntityField", isObfuscated ? "(Lnet/minecraft/entity/Entity;)V" : "(Lnet/minecraft/entity/Entity;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70154_o" : "ridingEntity", isObfuscated ? "Lnet/minecraft/entity/Entity;" : "Lnet/minecraft/entity/Entity;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRotationPitchField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70125_A" : "rotationPitch", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRotationPitchField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70125_A" : "rotationPitch", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRotationYawField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70177_z" : "rotationYaw", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRotationYawField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70177_z" : "rotationYaw", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getRotationYawHeadField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70759_as" : "rotationYawHead", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setRotationYawHeadField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70759_as" : "rotationYawHead", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getScoreValueField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70744_aE" : "scoreValue", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setScoreValueField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70744_aE" : "scoreValue", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getServerPosXField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70118_ct" : "serverPosX", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setServerPosXField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70118_ct" : "serverPosX", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getServerPosYField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70117_cu" : "serverPosY", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setServerPosYField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70117_cu" : "serverPosY", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getServerPosZField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70116_cv" : "serverPosZ", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setServerPosZField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70116_cv" : "serverPosZ", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getSleepingField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71083_bS" : "sleeping", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setSleepingField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71083_bS" : "sleeping", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getSpeedInAirField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71102_ce" : "speedInAir", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setSpeedInAirField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71102_ce" : "speedInAir", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getSpeedOnGroundField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71108_cd" : "speedOnGround", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setSpeedOnGroundField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71108_cd" : "speedOnGround", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getSprintToggleTimerField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71156_d" : "sprintToggleTimer", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setSprintToggleTimerField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71156_d" : "sprintToggleTimer", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getSprintingTicksLeftField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71157_e" : "sprintingTicksLeft", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setSprintingTicksLeftField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71157_e" : "sprintingTicksLeft", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getStepHeightField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70138_W" : "stepHeight", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setStepHeightField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70138_W" : "stepHeight", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getSwingProgressField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70733_aJ" : "swingProgress", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setSwingProgressField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70733_aJ" : "swingProgress", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getSwingProgressIntField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110158_av" : "swingProgressInt", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setSwingProgressIntField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_110158_av" : "swingProgressInt", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getTeleportDirectionField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_82152_aq" : "teleportDirection", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setTeleportDirectionField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_82152_aq" : "teleportDirection", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getTicksExistedField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70173_aa" : "ticksExisted", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setTicksExistedField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70173_aa" : "ticksExisted", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getTimeInPortalField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71086_bY" : "timeInPortal", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setTimeInPortalField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71086_bY" : "timeInPortal", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getTimeUntilPortalField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71088_bW" : "timeUntilPortal", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setTimeUntilPortalField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71088_bW" : "timeUntilPortal", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getVelocityChangedField", "()Z", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70133_I" : "velocityChanged", "Z");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setVelocityChangedField", "(Z)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70133_I" : "velocityChanged", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getWidthField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70130_N" : "width", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setWidthField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70130_N" : "width", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getWorldObjField", isObfuscated ? "()Lnet/minecraft/world/World;" : "()Lnet/minecraft/world/World;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70170_p" : "worldObj", isObfuscated ? "Lnet/minecraft/world/World;" : "Lnet/minecraft/world/World;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setWorldObjField", isObfuscated ? "(Lnet/minecraft/world/World;)V" : "(Lnet/minecraft/world/World;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70170_p" : "worldObj", isObfuscated ? "Lnet/minecraft/world/World;" : "Lnet/minecraft/world/World;");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getXpCooldownField", "()I", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71090_bL" : "xpCooldown", "I");
		mv.visitInsn(Opcodes.IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setXpCooldownField", "(I)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_71090_bL" : "xpCooldown", "I");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getYOffsetField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70129_M" : "yOffset", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setYOffsetField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70129_M" : "yOffset", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getYSizeField", "()F", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70139_V" : "ySize", "F");
		mv.visitInsn(Opcodes.FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "setYSizeField", "(F)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.FLOAD, 1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", isObfuscated ? "field_70139_V" : "ySize", "F");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getClientPlayerBase", "(Ljava/lang/String;)Lapi/player/client/ClientPlayerBase;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getClientPlayerBase", "(Lapi/player/client/IClientPlayerAPI;Ljava/lang/String;)Lapi/player/client/ClientPlayerBase;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getClientPlayerBaseIds", "()Ljava/util/Set;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "getClientPlayerBaseIds", "(Lapi/player/client/IClientPlayerAPI;)Ljava/util/Set;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "dynamic", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "api/player/client/ClientPlayerAPI", "dynamic", "(Lapi/player/client/IClientPlayerAPI;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getClientPlayerAPI", "()Lapi/player/client/ClientPlayerAPI;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, isObfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "net/minecraft/client/entity/EntityPlayerSP", "clientPlayerAPI", "Lapi/player/client/ClientPlayerAPI;");
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		mv = cv.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "getEntityPlayerSP", isObfuscated ? "()Lnet/minecraft/client/entity/EntityPlayerSP;" : "()Lnet/minecraft/client/entity/EntityPlayerSP;", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		cv.visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL, "clientPlayerAPI", "Lapi/player/client/ClientPlayerAPI;", null, null);
	}
}
