package me.bristermitten.fancyprivatemines.component.blocks.schematic.meta

import me.bristermitten.fancyprivatemines.component.blocks.schematic.Schematic
import me.bristermitten.fancyprivatemines.component.blocks.schematic.scanner.SchematicScanner
import me.bristermitten.fancyprivatemines.data.Region

class SchematicMetaSearcher(private val scanners: List<SchematicScanner>) {
    fun search(region: Region, schematic: Schematic) {
        val metadata = schematic.metadata

        region.points.parallelStream()
                .forEach { loc ->
                    val block = loc.block
                    scanners.forEach {
                        it.process(block, TODO(), schematic, metadata)
                    }
                }

        scanners.forEach {
            it.validate(schematic)
        }
    }
}
