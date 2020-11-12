package me.bristermitten.fancyprivatemines.component.blocks

import me.bristermitten.fancyprivatemines.data.block.BlockMask
import me.bristermitten.fancyprivatemines.function.Functionality
import org.bukkit.Location

interface BlockSettingMethod : Functionality {
    fun setBlock(location: Location, data: BlockMask)

    fun setBlocksBulk(pos1: Location, pos2: Location, mask: BlockMask)
}
