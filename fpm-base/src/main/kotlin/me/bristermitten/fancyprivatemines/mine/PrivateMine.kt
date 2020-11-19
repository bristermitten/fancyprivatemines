package me.bristermitten.fancyprivatemines.mine

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BlockMask
import me.bristermitten.fancyprivatemines.data.Region
import org.bukkit.Location
import java.util.*

data class PrivateMine(
        val owner: UUID,
        var open: Boolean = true,
        var blocks: BlockMask,
        var taxPercentage: Double,
        var spawnLocation: Location,
        var region: Region,
        var miningRegion: Region
) {
    fun fill(plugin: FancyPrivateMines) {
        plugin.configuration.blockSetting.methods.active.setBlocksBulk(miningRegion.min, miningRegion.max, blocks)
    }
}
