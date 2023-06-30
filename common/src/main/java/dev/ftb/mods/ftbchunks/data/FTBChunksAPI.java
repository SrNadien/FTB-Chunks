package dev.ftb.mods.ftbchunks.data;

import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.net.SendGeneralDataPacket;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Comparator;

/**
 * @author LatvianModder
 */
public class FTBChunksAPI {
	public static final TagKey<Block> EDIT_WHITELIST_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(FTBChunks.MOD_ID, "edit_whitelist"));
	public static final TagKey<Block> INTERACT_WHITELIST_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(FTBChunks.MOD_ID, "interact_whitelist"));
	public static final TagKey<Item> RIGHT_CLICK_BLACKLIST_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(FTBChunks.MOD_ID, "right_click_blacklist"));
	public static final TagKey<Item> RIGHT_CLICK_WHITELIST_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(FTBChunks.MOD_ID, "right_click_whitelist"));
	public static final TagKey<EntityType<?>> ENTITY_INTERACT_WHITELIST_TAG = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(FTBChunks.MOD_ID, "entity_interact_whitelist"));
	public static final TagKey<EntityType<?>> NONLIVING_ENTITY_ATTACK_WHITELIST_TAG = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(FTBChunks.MOD_ID, "nonliving_entity_attack_whitelist"));

	public static final TicketType<ChunkPos> FORCE_LOADED_TICKET = TicketType.create(FTBChunks.MOD_ID + ":force_loaded", Comparator.comparingLong(ChunkPos::toLong));

	public static ClaimedChunkManager manager;

	public static ClaimedChunkManager getManager() {
		if (manager == null) {
			throw new NullPointerException("FTB Chunks Manager hasn't been loaded yet!");
		}

		return manager;
	}

	public static boolean isManagerLoaded() {
		return manager != null;
	}

	public static ClaimResult claimAsPlayer(ServerPlayer player, ResourceKey<Level> dimension, ChunkPos pos, boolean checkOnly) {
		return getManager().getOrCreateData(player).claim(player.createCommandSourceStack(), new ChunkDimPos(dimension, pos), checkOnly);
	}

	public static void syncPlayer(ServerPlayer player) {
		SendGeneralDataPacket.send(getManager().getOrCreateData(player), player);
	}

	public static boolean isChunkForceLoaded(ResourceKey<Level> dimension, int x, int z) {
		return isManagerLoaded() && getManager().isChunkForceLoaded(dimension, x, z);
	}
}