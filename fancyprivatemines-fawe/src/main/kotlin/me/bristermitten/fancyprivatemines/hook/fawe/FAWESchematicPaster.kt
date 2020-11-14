package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.extent.clipboard.ClipboardFormats
import me.bristermitten.fancyprivatemines.component.blocks.schematic.SchematicPaster
import org.bukkit.Location
import java.io.File

class FAWESchematicPaster : SchematicPaster {
    override val id: String = "FAWE"
    override val priority: Int = 5

    override fun paste(file: File, at: Location) {
        val format = ClipboardFormats.findByFile(file)
        requireNotNull(format) { "Invalid schematic file $file" }

        val schematic = format.load(file)
        val world = BukkitWorld(at.world)
        val position = at.toWorldEditVector()
        val allowUndo = false
        val noAir = false

        schematic.paste(world, position, allowUndo, !noAir, null)
    }
}
