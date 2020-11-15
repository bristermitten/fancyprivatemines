package me.bristermitten.fancyprivatemines.component.blocks

import me.bristermitten.fancyprivatemines.block.BlockMask
import me.bristermitten.fancyprivatemines.function.Functionality
import org.bukkit.Location

abstract class BlockSettingMethod : Functionality() {
    abstract fun setBlock(location: Location, data: BlockMask)

    abstract fun setBlocksBulk(pos1: Location, pos2: Location, mask: BlockMask)

    abstract fun setBlocksBulk(locations: List<Location>, mask: BlockMask)
}
