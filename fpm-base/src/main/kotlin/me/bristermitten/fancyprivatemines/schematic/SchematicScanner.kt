package me.bristermitten.fancyprivatemines.schematic

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.schematic.attributes.SchematicAttributeScanner
import me.bristermitten.fancyprivatemines.util.fpmDebug
import org.bukkit.Bukkit

class SchematicScanner(private val plugin: FancyPrivateMines,
                       private val loader: SchematicLoader) {

    fun scan(pastedRegion: Region, schematic: MineSchematic, scanners: List<SchematicAttributeScanner>) {
        val requireScanning = scanners.filter {
            schematic.attributes.data[it.attributesKey] == null
        }

        if (requireScanning.isNotEmpty()) {
            plugin.configuration.schematicPasters.active.iterateRegion(pastedRegion) { loc, block ->
                requireScanning.forEach {
                    it.scan(block, loc, pastedRegion, schematic)
                }
            }
        } else {
            plugin.logger.fpmDebug { "Did not have to scan the region!" }
        }

        if (requireScanning.isNotEmpty()) {
            //Attributes / metadata may have changed, so let's save it
            Bukkit.getScheduler().runTaskAsynchronously(plugin) {
                loader.saveSchematic(schematic)
                plugin.logger.fpmDebug { "Updated Schematic Metadata for ${schematic.fileName}" }
            }
        }

        scanners.forEach {
            it.validate(schematic)
        }
    }
}
