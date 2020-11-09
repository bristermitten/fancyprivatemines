package me.bristermitten.fancyprivatemines.hook.blocks

import me.bristermitten.fancyprivatemines.data.block.BlockMask
import org.bukkit.Location

interface BlockSettingMethod {

    fun setBlock(location: Location, data: BlockMask)

    fun setBlocksBulk(pos1: Location, pos2: Location, mask: BlockMask)
}
