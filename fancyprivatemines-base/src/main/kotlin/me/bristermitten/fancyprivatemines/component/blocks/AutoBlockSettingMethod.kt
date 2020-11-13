package me.bristermitten.fancyprivatemines.component.blocks

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.data.block.BlockMask
import org.bukkit.Location

class AutoBlockSettingMethod(private val plugin: FancyPrivateMines) : BlockSettingMethod {
    override val id: String = "Auto"

    override val priority: Int = Int.MIN_VALUE

    override fun init() {
        plugin.configuration.blockSetting.methods.active = plugin.configuration.blockSetting.methods.all.maxByOrNull {
            it.priority //Get the highest priority method
        }!!.also { it.init() } //Should never be empty
    }

    override fun setBlock(location: Location, data: BlockMask) {
        TODO("AutoBlockSettingMethod must delegate")
    }

    override fun setBlocksBulk(pos1: Location, pos2: Location, mask: BlockMask) {
        TODO("AutoBlockSettingMethod must delegate")
    }
}
