package me.bristermitten.fancyprivatemines.component.blocks.schematic.scanner

import me.bristermitten.fancyprivatemines.component.blocks.schematic.SchematicAttribute
import org.bukkit.Location

object MiningRegionAttribute : SchematicAttribute<List<Location>> {
    override val id: String = "MiningRegion"

}
