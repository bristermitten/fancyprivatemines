package me.bristermitten.fancyprivatemines.mine

import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BlockMask
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.serializer.UUIDSerializer
import java.util.*

@Serializable
data class PrivateMine(
        @Serializable(with = UUIDSerializer::class)
        val id: UUID = UUID.randomUUID(),
        @Serializable(with = UUIDSerializer::class)
        val owner: UUID,
        var open: Boolean = true,
        var blocks: BlockMask,
        var taxPercentage: Double,
        var spawnLocation: ImmutableLocation,
        var region: Region,
        var miningRegion: Region
) {
    fun fill(plugin: FancyPrivateMines) {
        plugin.configuration.blockSetting.methods.active.setBlocksBulk(miningRegion, blocks)
    }
}
