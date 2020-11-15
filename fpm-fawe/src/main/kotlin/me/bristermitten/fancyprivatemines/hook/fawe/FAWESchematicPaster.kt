package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.extent.clipboard.ClipboardFormats
import me.bristermitten.fancyprivatemines.component.blocks.schematic.paster.SchematicPaster
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.data.makeRegion
import org.bukkit.Location
import java.io.File

class FAWESchematicPaster : SchematicPaster() {
    override val id: String = "FAWE"
    override val priority: Int = 5

    override fun paste(file: File, at: Location): Region {
        val format = ClipboardFormats.findByFile(file)
        requireNotNull(format) { "Invalid schematic file $file" }

        val schematic = format.load(file)
        val world = BukkitWorld(at.world)

        val clipboard = requireNotNull(schematic.clipboard) {
            "Schematic does not have a clipboard!"
        }

        at.y = clipboard.origin.blockY.toDouble() //Required to make sure everything syncs in the Y axis properly
        val position = at.toWorldEditVector()

        schematic.paste(world, position, false, true, null)

        val region = clipboard.region
        region.world = world

        //The difference between the origin and the pasted position
        val differential = position.subtract(clipboard.origin)
        region.shift(differential)

        return makeRegion(region.minimumPoint.toLocation(at.world), region.maximumPoint.toLocation(at.world))
    }
}
