package me.bristermitten.fancyprivatemines.component.blocks.schematic.scanner

import me.bristermitten.fancyprivatemines.component.blocks.schematic.Schematic
import me.bristermitten.fancyprivatemines.component.blocks.schematic.meta.SchematicMetadata
import org.bukkit.Material
import org.bukkit.block.Block

class MiningRegionSchematicScanner(val compareTo: Material) : SchematicScanner {

    override fun process(block: Block, schematic: Schematic, meta: SchematicMetadata) {
        if (block.type != compareTo) {
            return
        }
        val miningRegions = meta.getAttribute(MiningRegionAttribute, emptyList()).toMutableList()
        miningRegions += block.location

        if (miningRegions.size > 2) {
            throw IllegalArgumentException("Mine Schematic must contain exactly 2 mining corners!")
        }

        meta.setAttribute(MiningRegionAttribute, miningRegions)
    }

    override fun validate(schematic: Schematic) {
        val miningRegions = schematic.metadata.getAttribute(MiningRegionAttribute, emptyList())

        if (miningRegions.size != 2) {
            throw IllegalArgumentException("Mine Schematic must contain exactly 2 mining corners!")
        }
    }
}
