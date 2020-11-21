package me.bristermitten.fancyprivatemines.config

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.blocks.AutoBlockSettingMethod
import me.bristermitten.fancyprivatemines.component.blocks.BlockSettingMethods
import me.bristermitten.fancyprivatemines.schematic.paster.SchematicPasters
import me.bristermitten.fancyprivatemines.serializer.Serializers

class PrivateMinesConfiguration(val plugin: FancyPrivateMines) {
    val blockSetting = BlockSetting()
    val schematicPasters = SchematicPasters()
    val serialization = Serializers()

    inner class BlockSetting {
        private val auto = AutoBlockSettingMethod(plugin)
        val methods = BlockSettingMethods(auto)
    }

}
