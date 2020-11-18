package me.bristermitten.fancyprivatemines.schematic.attributes

import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.schematic.MineSchematic
import org.bukkit.block.Block

interface SchematicAttributeScanner {
    val attributesKey: String

    fun scan(block: Block, region: Region, schematic: MineSchematic)

    fun validate(schematic: MineSchematic)
}
