package me.bristermitten.fancyprivatemines.component.blocks.schematic.scanner

import me.bristermitten.fancyprivatemines.component.blocks.schematic.Schematic
import me.bristermitten.fancyprivatemines.component.blocks.schematic.meta.SchematicMetadata
import org.bukkit.Location
import org.bukkit.block.Block

interface SchematicScanner {
    fun process(block: Block, origin: Location, schematic: Schematic, meta: SchematicMetadata)

    fun validate(schematic: Schematic)
}
