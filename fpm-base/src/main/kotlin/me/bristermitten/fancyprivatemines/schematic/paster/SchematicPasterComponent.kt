package me.bristermitten.fancyprivatemines.schematic.paster

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.FPMComponent

class SchematicPasterComponent : FPMComponent {
    override fun init(plugin: FancyPrivateMines) {
        val configuration = plugin.configuration

        configuration.schematicPasters.active = configuration.schematicPasters.all.maxByOrNull { it.priority }!!

        configuration.schematicPasters.active.init()

        plugin.logger.info {
            "Using ${configuration.schematicPasters.active.javaClass.simpleName} for Schematic Pasting"
        }
    }
}
