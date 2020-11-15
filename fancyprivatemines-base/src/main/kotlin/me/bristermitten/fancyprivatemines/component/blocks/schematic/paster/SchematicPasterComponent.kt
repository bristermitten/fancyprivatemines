package me.bristermitten.fancyprivatemines.component.blocks.schematic.paster

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.FPMComponent

class SchematicPasterComponent : FPMComponent {
    override fun init(plugin: FancyPrivateMines) {
        plugin.configuration.schematicPasters.active = plugin.configuration.schematicPasters.all.maxByOrNull {
            it.priority
        }!!

        plugin.configuration.schematicPasters.active.init()
    }
}
