package me.bristermitten.fancyprivatemines.component.blocks.schematic.meta

import me.bristermitten.fancyprivatemines.attribute.Attribute

data class SchematicMetadata(
        val hash: String,
        private val attributes: MutableMap<Attribute<*>, Any>
)
