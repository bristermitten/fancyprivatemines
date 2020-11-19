package me.bristermitten.fancyprivatemines.schematic.attributes

import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.schematic.MineSchematic
import org.bukkit.Location

interface SchematicAttributeScanner {
    val attributesKey: String

    fun scan(block: BlockData, location: Location, region: Region, schematic: MineSchematic)

    fun validate(schematic: MineSchematic)
}
