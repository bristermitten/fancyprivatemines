package me.bristermitten.fancyprivatemines.schematic.attributes

import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.data.relativeTo
import me.bristermitten.fancyprivatemines.schematic.MineSchematic
import me.bristermitten.fancyprivatemines.schematic.MultipleLocationAttributeValue
import org.bukkit.Location
import org.bukkit.Material

class MiningRegionScanner(val compareTo: Material) : SchematicAttributeScanner<MultipleLocationAttributeValue> {
    override val attributesKey = "miningRegion"

    override fun scan(block: BlockData, location: ImmutableLocation, region: Region, schematic: MineSchematic) {
        if (block.material != compareTo) {
            return
        }
        val list = (schematic.attributes.data[attributesKey] as? MultipleLocationAttributeValue)?.value ?: emptyList()

        schematic.attributes.data[attributesKey] = MultipleLocationAttributeValue(list + (location relativeTo region.origin))
    }

    override fun validate(schematic: MineSchematic) {
        val regions = schematic.attributes.data[attributesKey]
        requireNotNull(regions) { "Schematic has no mining region" }
        require(regions is MultipleLocationAttributeValue) { "miningRegion must be List<RelativeLocation>" }

        val blocks = regions.value
        require(blocks.size == 2) { "Must be exactly 2 mining region points" }
    }
}
