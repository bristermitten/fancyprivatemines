package me.bristermitten.fancyprivatemines.component.blocks

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.FPMComponent

class BlockSettingComponent : FPMComponent {

    override fun init(plugin: FancyPrivateMines) {
        val configuration = plugin.configuration
        configuration.blockSetting.methods.setActiveTo(plugin.pmConfig.blockSettingMethod) {
            plugin.logger.warning {
                "No Valid Block Setting Method \"${plugin.pmConfig.blockSettingMethod}\". Are you missing a required plugin?"
            }
        }
        configuration.blockSetting.methods.active.init()

        plugin.logger.info {
            "Using ${configuration.blockSetting.methods.active.javaClass.simpleName} for Block Setting"
        }
    }
}
