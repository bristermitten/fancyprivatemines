package me.bristermitten.fancyprivatemines.component.blocks

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.Component

class BlockSettingComponent : Component {

    override fun init(plugin: FancyPrivateMines) {
        val configuration = plugin.configuration
        configuration.blockSetting.methods.setActiveTo(plugin.pmConfig.blockSettingMethod)
        configuration.blockSetting.methods.active.init()

        plugin.logger.info {
            "Using ${configuration.blockSetting.methods.active.javaClass.simpleName} for Block Setting"
        }
    }
}
