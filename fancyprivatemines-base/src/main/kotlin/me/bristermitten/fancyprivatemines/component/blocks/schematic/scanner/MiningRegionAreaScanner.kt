package me.bristermitten.fancyprivatemines.component.blocks.schematic.scanner

import me.bristermitten.fancyprivatemines.data.Region
import org.bukkit.Location
import org.bukkit.Material
import kotlin.streams.toList

class MiningRegionAreaScanner : AreaScanner<List<Location>> {

    override fun scan(region: Region, canBeAsync: Boolean): List<Location> {
        val points = region.points
        val railLocations = points.parallelStream()
                .map { it.block }
                .filter { it.type == Material.POWERED_RAIL }
                .map { it.location }
                .toList()

        if (railLocations.size != 2) {
            throw IllegalArgumentException("Mine Schematic must contain exactly 2 mining corners!")
        }
        return railLocations
    }
}
