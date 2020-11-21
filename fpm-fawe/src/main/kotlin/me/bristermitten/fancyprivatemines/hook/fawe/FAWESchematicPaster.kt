package me.bristermitten.fancyprivatemines.hook.fawe

import com.boydti.fawe.FaweAPI
import com.boydti.fawe.`object`.visitor.FastIterator
import com.sk89q.worldedit.extent.clipboard.ClipboardFormats
import com.sk89q.worldedit.regions.CuboidRegion
import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.data.makeRegion
import me.bristermitten.fancyprivatemines.schematic.paster.SchematicPaster
import org.bukkit.Location
import org.bukkit.Material
import java.io.File

class FAWESchematicPaster : SchematicPaster() {
    override val id: String = "FAWE"
    override val priority: Int = 5

    override fun paste(schematic: File, at: Location): Region {
        val format = ClipboardFormats.findByFile(schematic)
        requireNotNull(format) { "Invalid schematic file $schematic" }

        val schematic = format.load(schematic)
        val world = at.world.editSession

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

        return makeRegion(region.minimumPoint.toLocation(at.world.name), region.maximumPoint.toLocation(at.world.name))
    }

    override fun iterateRegion(region: Region, consumer: (ImmutableLocation, BlockData) -> Unit) {
        val world = region.min.world

        val weWorld = FaweAPI.getWorld(world)
        val weRegion = CuboidRegion(weWorld, region.min.toWorldEditVector(), region.max.toWorldEditVector())

        val extent = region.min.bukkitWorld.editSession
        FastIterator(weRegion, extent).asSequence().map {
            val block = extent.getBlock(it)
            it.toLocation(world) to BlockData(Material.values()[block.type], block.data.toByte())
        }.filter {
            it.second.material != Material.AIR
        }.forEach {
            consumer(it.first, it.second)
        }
    }
}
