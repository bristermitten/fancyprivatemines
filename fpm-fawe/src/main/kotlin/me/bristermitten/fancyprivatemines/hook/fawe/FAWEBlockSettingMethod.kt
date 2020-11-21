package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.regions.CuboidRegion
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BlockMask
import me.bristermitten.fancyprivatemines.component.blocks.BlockSettingMethod
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.data.Region
import org.bukkit.Location

class FAWEBlockSettingMethod(val plugin: FancyPrivateMines) : BlockSettingMethod() {
    override val id: String = "FAWE"
    override val priority: Int = 5 //FAWE is the highest speed, so will be prioritised


    override fun setBlock(location: ImmutableLocation, data: BlockMask) {

        val session = location.bukkitWorld.editSession

        val block = data.generate()

        val baseBlock = block.toBaseBlock()
        session.setBlock(location.toWorldEditVector(), baseBlock)
    }

    override fun setBlocksBulk(region: Region, mask: BlockMask) {
        val session = region.world.editSession

        val weRegion = CuboidRegion(region.min.toWorldEditVector(), region.max.toWorldEditVector())
        session.setBlocks(weRegion, mask.toWEPattern())
        session.flushQueue()
    }

    override fun setBlocksBulk(locations: List<Location>, mask: BlockMask) {
        if (locations.isEmpty()) {
            return
        }
        val session = locations[0].world.editSession

        session.setBlocks(locations.asSequence().map { it.toWorldEditVector() }.toSet(), mask.toWEPattern())
    }
}
