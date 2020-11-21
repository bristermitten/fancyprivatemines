package me.bristermitten.fancyprivatemines.component.blocks

import me.bristermitten.fancyprivatemines.block.BlockMask
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.function.Functionality
import org.bukkit.Location

abstract class BlockSettingMethod : Functionality() {
    abstract fun setBlock(location: ImmutableLocation, data: BlockMask)

    abstract fun setBlocksBulk(region: Region, mask: BlockMask)

    abstract fun setBlocksBulk(locations: List<Location>, mask: BlockMask)
}
