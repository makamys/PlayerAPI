package api.player.launch;

import net.minecraft.launchwrapper.Launch;

public class LaunchHook {

    private static boolean hasInitialized;
    
    /**
     * <p>Called in the static initializer of the main class (Main or MinecraftServer).
     * 
     * <p>We want our transformer to run after Mixin, which registers its transformer at the last possible moment in LaunchWrapper.
     * 
     * <p>Rather than messing with tweakers, we just inserted a hook at the earliest possible point after launch.
     */
    public static void onLaunch()
    {   
        if(!hasInitialized)
        {
            Launch.classLoader.registerTransformer("api.player.forge.PlayerAPITransformer");
            
            try
            {
                // We don't want to be involved with Mixin, we want to run strictly *after* it.
                Class.forName("org.spongepowered.asm.mixin.MixinEnvironment").getMethod("addTransformerExclusion", String.class).invoke(null, "api.player.forge.PlayerAPITransformer");
            } catch(Exception e) {}
            
            hasInitialized = true;
        }
    }
    
}
