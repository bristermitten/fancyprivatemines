package me.bristermitten.fancyprivatemines.component.blocks

import me.bristermitten.fancyprivatemines.pattern.BlockPattern
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.function.Functionality
import org.bukkit.Location

abstract class BlockSettingMethod : Functionality() {
    abstract fun setBlock(location: ImmutableLocation, data: BlockPattern)

    abstract fun setBlocksBulk(region: Region, pattern: BlockPattern)

    abstract fun setBlocksBulk(locations: List<Location>, pattern: BlockPattern)
}
