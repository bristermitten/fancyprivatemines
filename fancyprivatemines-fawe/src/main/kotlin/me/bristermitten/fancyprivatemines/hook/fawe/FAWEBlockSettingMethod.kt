package me.bristermitten.fancyprivatemines.hook.fawe

import com.boydti.fawe.util.EditSessionBuilder
import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.regions.CuboidRegion
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.data.block.BlockMask
import me.bristermitten.fancyprivatemines.hook.blocks.BlockSettingMethod
import me.bristermitten.fancyprivatemines.util.areaTo
import org.bukkit.Location
import org.bukkit.World
import java.util.*

class FAWEBlockSettingMethod(val plugin: FancyPrivateMines) : BlockSettingMethod {
    private val sessionCache = WeakHashMap<World, EditSession>()

    private val World.editSession
        get() = sessionCache.getOrPut(this) {
            EditSessionBuilder(this.name)
                    .allowedRegionsEverywhere()
                    .fastmode(true)
                    .build()
        }

    override fun setBlock(location: Location, data: BlockMask) {

        val session = location.world.editSession

        val block = data.generate()

        @Suppress("DEPRECATION")
        val baseBlock = BaseBlock(block.material.id, block.data.toInt())
        session.setBlock(location.toWorldEditVector(), baseBlock)
    }

    override fun setBlocksBulk(pos1: Location, pos2: Location, mask: BlockMask) {
        val session = pos1.world.editSession

        session.setBlocks(CuboidRegion(pos1.toWorldEditVector(), pos2.toWorldEditVector()),
        )
        val area = pos1 areaTo pos2

        val blocks = mask.generateBulk(area.size)

        area.forEachIndexed { index, location ->
            val block = location.block
            val data = blocks[index]
            block.setType(data.material, false)
            block.setData(data.data, false)
        }
    }
}
