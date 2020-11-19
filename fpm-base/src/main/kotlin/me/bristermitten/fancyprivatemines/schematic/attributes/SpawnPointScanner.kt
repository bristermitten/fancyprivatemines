package me.bristermitten.fancyprivatemines.schematic.attributes

import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.data.relativeTo
import me.bristermitten.fancyprivatemines.schematic.LocationAttributeValue
import me.bristermitten.fancyprivatemines.schematic.MineSchematic
import org.bukkit.Location

class SpawnPointScanner(val compareTo: BlockData) : SchematicAttributeScanner {
    override val attributesKey = "spawnPoint"

    override fun scan(block: BlockData, location: Location, region: Region, schematic: MineSchematic) {
        if (block.material != compareTo.material || block.data != compareTo.data) {
            return
        }

        val location = LocationAttributeValue(location relativeTo region.origin)
        schematic.attributes.data[attributesKey] = location
    }

    override fun validate(schematic: MineSchematic) {
        val spawnpoint = schematic.attributes.data[attributesKey]
        requireNotNull(spawnpoint) { "Schematic has no spawn point" }
        require(spawnpoint is LocationAttributeValue) { "spawnPoint must be RelativeLocation" }
    }
}
