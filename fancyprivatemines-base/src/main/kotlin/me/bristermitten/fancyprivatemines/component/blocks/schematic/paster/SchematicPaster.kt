package me.bristermitten.fancyprivatemines.component.blocks.schematic.paster

import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.function.Functionality
import org.bukkit.Location
import java.io.File

abstract class SchematicPaster : Functionality() {
    //File loading too?
    abstract fun paste(file: File, at: Location) : Region
}
