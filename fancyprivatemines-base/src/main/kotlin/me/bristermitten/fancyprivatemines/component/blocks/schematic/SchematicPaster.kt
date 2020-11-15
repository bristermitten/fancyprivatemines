package me.bristermitten.fancyprivatemines.component.blocks.schematic

import me.bristermitten.fancyprivatemines.function.Functionality
import org.bukkit.Location
import java.io.File

interface SchematicPaster : Functionality{
    //File loading too?
    fun paste(file: File, at: Location)
}
