package me.bristermitten.fancyprivatemines.hook.blocks

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.data.block.BlockMask
import org.bukkit.Location

class AutoBlockSettingMethod(private val plugin: FancyPrivateMines) : BlockSettingMethod {
    override val id: String = "Auto"

    override val priority: Int = Int.MIN_VALUE

    private lateinit var using: BlockSettingMethod

    override fun init() {
        plugin.configuration.blockSetting.methods.active = plugin.configuration.blockSetting.methods.all.maxByOrNull {
            it.priority
        }!! //Should never be empty
    }

    override fun setBlock(location: Location, data: BlockMask) {
        TODO("AutoBlockSettingMethod must delegate")
    }

    override fun setBlocksBulk(pos1: Location, pos2: Location, mask: BlockMask) {
        TODO("AutoBlockSettingMethod must delegate")
    }
}
