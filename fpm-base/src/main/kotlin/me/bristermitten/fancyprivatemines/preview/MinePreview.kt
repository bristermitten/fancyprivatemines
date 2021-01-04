package me.bristermitten.fancyprivatemines.preview

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.data.immutableCopy
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import me.bristermitten.fancyprivatemines.pattern.BlockPattern
import me.bristermitten.fancyprivatemines.util.filterVisible
import org.bukkit.entity.Player

fun PrivateMine.previewFill(player: Player, pattern: BlockPattern, plugin: FancyPrivateMines) {
    val miningBlocks = miningRegion.points
        .asSequence()
        .map { it.toLocation().block }
        .filterVisible()
        .map { it.location.immutableCopy() }

    miningBlocks.forEach { point ->
        val block = pattern.generate()
        plugin.nmsCompat.blockPreviewService.setBlock(player, point, block)
    }

}
