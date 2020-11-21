package me.bristermitten.fancyprivatemines.serializer

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.FPMComponent

class SerializationComponent : FPMComponent {
    override fun init(plugin: FancyPrivateMines) {
        val configuration = plugin.configuration

        configuration.serialization.add(JSONBasedSerializer)

        configuration.serialization.setActiveToOrElse(plugin.pmConfig.dataFormat) {
            plugin.logger.warning {
                "Invalid Data Format \"${plugin.pmConfig.dataFormat}\"."
            }
        }

        configuration.serialization.active.init()

        plugin.logger.info {
            "Using ${configuration.serialization.active.javaClass.simpleName} for Data Storage"
        }
    }
}
