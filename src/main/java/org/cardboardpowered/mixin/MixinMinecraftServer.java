package org.cardboardpowered.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import org.cardboardpowered.CardboardMod;
import org.cardboardpowered.command.CardboardCommandMap;
import org.cardboardpowered.paper.PaperCommandRegistration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin into MinecraftServer to inject Bukkit and Paper commands
 * into Fabric's Brigadier dispatcher.
 */
@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {

    @Shadow
    public abstract net.minecraft.server.command.CommandManager getCommandManager();

    /**
     * After vanilla/Fabric commands are registered, hook in Bukkit + Paper commands.
     */
    @Inject(method = "setupServer", at = @At("RETURN"))
    private void cardboard$registerCommands(CallbackInfo ci) {
        CommandDispatcher<ServerCommandSource> dispatcher = this.getCommandManager().getDispatcher();

        // Register Bukkit commands
        CardboardCommandMap commandMap = (CardboardCommandMap) CardboardMod.getPluginManager().getCommandMap();
        commandMap.registerToFabric(dispatcher);

        // Register Paper brigadier commands
        PaperCommandRegistration.registerPaperCommands(
                CardboardMod.getPluginManager().getPlugins(),
                dispatcher
        );

        System.out.println("[Cardboard] Bukkit + Paper commands registered into Fabric dispatcher.");
    }
}
