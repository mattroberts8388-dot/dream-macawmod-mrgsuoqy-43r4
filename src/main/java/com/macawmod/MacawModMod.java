package com.macawmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class MacawModMod implements ModInitializer {
    public static final String MOD_ID = "macawmod";

    @Override
    public void onInitialize() {
        MacawModEntities.register();

        BiomeModifications.addSpawn(
            BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE),
            SpawnGroup.CREATURE,
            MacawModEntities.MACAW,
            10, 1, 3
        );

        // Grant night vision to any player that has a Macaw sitting on their shoulder.
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (hasShoulderMacaw(player)) {
                    player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.NIGHT_VISION, 220, 0, true, false, false));
                }
            }
        });
    }

    private static boolean hasShoulderMacaw(ServerPlayerEntity player) {
        String id = MOD_ID + ":macaw";
        return matchesShoulder(player.getShoulderEntityLeft(), id)
            || matchesShoulder(player.getShoulderEntityRight(), id);
    }

    private static boolean matchesShoulder(net.minecraft.nbt.NbtCompound nbt, String id) {
        return nbt != null && !nbt.isEmpty() && id.equals(nbt.getString("id"));
    }
}