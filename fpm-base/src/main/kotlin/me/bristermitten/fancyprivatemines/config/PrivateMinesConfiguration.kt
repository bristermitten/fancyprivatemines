package me.bristermitten.fancyprivatemines.config

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.blocks.AutoBlockSettingMethod
import me.bristermitten.fancyprivatemines.component.blocks.BlockSettingMethods
import me.bristermitten.fancyprivatemines.component.blocks.schematic.paster.SchematicPasters

class PrivateMinesConfiguration(val plugin: FancyPrivateMines) {
    val blockSetting = BlockSetting()
    val schematicPasters = SchematicPasters()

    inner class BlockSetting {
        private val auto = AutoBlockSettingMethod(plugin)
        val methods = BlockSettingMethods(auto)
    }

}
