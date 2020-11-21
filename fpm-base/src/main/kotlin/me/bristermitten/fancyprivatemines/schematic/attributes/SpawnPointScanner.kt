package me.bristermitten.fancyprivatemines.schematic.attributes

import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.data.RelativeLocation
import me.bristermitten.fancyprivatemines.data.relativeTo
import me.bristermitten.fancyprivatemines.schematic.LocationAttributeValue
import me.bristermitten.fancyprivatemines.schematic.MineSchematic

class SpawnPointScanner(val compareTo: BlockData) : SchematicAttributeScanner<LocationAttributeValue> {
    override val attributesKey = "spawnPoint"

    override fun scan(block: BlockData, location: ImmutableLocation, region: Region, schematic: MineSchematic) {
        if (block.material != compareTo.material || block.data != compareTo.data) {
            return
        }

        val location = LocationAttributeValue(location relativeTo region.origin)
        schematic.attributes.data[attributesKey] = location
    }

    override fun validate(schematic: MineSchematic) {
        schematic.getAttribute<LocationAttributeValue, RelativeLocation>(attributesKey) {
            throw IllegalStateException("Schematic has no spawn point")
        }
    }
}
