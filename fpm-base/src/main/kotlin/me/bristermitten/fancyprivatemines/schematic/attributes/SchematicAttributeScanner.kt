package me.bristermitten.fancyprivatemines.schematic.attributes

import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.schematic.AttributeValue
import me.bristermitten.fancyprivatemines.schematic.MineSchematic
import org.bukkit.Location

interface SchematicAttributeScanner<T: AttributeValue<*>> {
    val attributesKey: String

    fun scan(block: BlockData, location: ImmutableLocation, region: Region, schematic: MineSchematic)

    fun validate(schematic: MineSchematic)
}
