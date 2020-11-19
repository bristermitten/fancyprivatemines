package me.bristermitten.fancyprivatemines.schematic

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.schematic.attributes.SchematicAttributeScanner
import org.bukkit.Bukkit

class SchematicScanner(private val plugin: FancyPrivateMines,
                       private val loader: SchematicLoader) {

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

        //Attributes / metadata may have changed, so let's save it
        Bukkit.getScheduler().runTaskAsynchronously(plugin) {
            loader.saveSchematic(schematic)
        }

        scanners.forEach {
            it.validate(schematic)
        }
    }
}
