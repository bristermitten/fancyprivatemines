package me.bristermitten.fancyprivatemines.schematic

import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.schematic.attributes.SchematicAttributeScanner

class SchematicScanner {

    fun scan(pastedRegion: Region, schematic: MineSchematic, scanners: List<SchematicAttributeScanner>) {
        val requireScanning = scanners.filter {
            schematic.attributes.data[it.attributesKey] == null
        }

        pastedRegion.points.parallelStream()
                .forEach { loc ->
                    val block = loc.block
                    requireScanning.forEach {
                        it.scan(block, pastedRegion, schematic)
                    }
                }

        scanners.forEach {
            it.validate(schematic)
        }
    }
}
