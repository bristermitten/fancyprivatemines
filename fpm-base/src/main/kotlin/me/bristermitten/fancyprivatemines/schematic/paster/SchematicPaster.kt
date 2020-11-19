package me.bristermitten.fancyprivatemines.schematic.paster

import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.function.Functionality
import org.bukkit.Location
import java.io.File

abstract class SchematicPaster : Functionality() {
    abstract fun paste(schematic: File, at: Location): Region

    abstract fun iterateRegion(region: Region, consumer: (Location, BlockData) -> Unit)
}
