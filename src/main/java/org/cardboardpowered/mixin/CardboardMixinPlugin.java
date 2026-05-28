package org.cardboardpowered.mixin;

import org.cardboardpowered.CardboardMod;
import org.cardboardpowered.paper.PaperCommandRegistration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/**
 * Mixin plugin entrypoint. This is called when mixins are loaded,
 * so we can initialize Paper brigadier glue early.
 */
@Mixin(CardboardMixinPlugin.class)
public class CardboardMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
        System.out.println("[Cardboard] Mixin plugin loaded: " + mixinPackage);

        // Initialize Paper brigadier command glue
        try {
            PaperCommandRegistration.registerPaperCommands(
                    CardboardMod.getPluginManager().getPlugins(),
                    CardboardMod.getPluginManager().getCommandMap().getDispatcher()
            );
            System.out.println("[Cardboard] Paper brigadier commands initialized at mixin bootstrap.");
        } catch (Exception e) {
            System.err.println("[Cardboard] Failed to initialize Paper brigadier commands: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
