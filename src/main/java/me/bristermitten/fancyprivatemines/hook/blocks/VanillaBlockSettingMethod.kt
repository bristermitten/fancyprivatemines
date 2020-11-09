package me.bristermitten.fancyprivatemines.hook.blocks

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.data.block.BlockMask
import me.bristermitten.fancyprivatemines.util.areaTo
import org.bukkit.Location

class VanillaBlockSettingMethod(val plugin: FancyPrivateMines) : BlockSettingMethod {

    init {
        plugin.logger.warning {
            "You are using the Vanilla Block Setting Method for mines. This will degrade performance and using FAWE / AWE / WE is highly recommended!"
        }
    }

    override fun setBlock(location: Location, data: BlockMask) {
        val block = location.block
        val data = data.generate()
        block.setType(data.material, false)
        block.setData(data.data, false)
    }

    override fun setBlocksBulk(pos1: Location, pos2: Location, mask: BlockMask) {
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
