package me.bristermitten.fancyprivatemines.config

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.hook.blocks.AutoBlockSettingMethod
import me.bristermitten.fancyprivatemines.hook.blocks.BlockSettingMethods

class PrivateMinesConfiguration(val plugin: FancyPrivateMines) {
    val blockSetting = BlockSetting()

    inner class BlockSetting {
        private val auto = AutoBlockSettingMethod(plugin)

        val methods = BlockSettingMethods(auto)

    }

}
