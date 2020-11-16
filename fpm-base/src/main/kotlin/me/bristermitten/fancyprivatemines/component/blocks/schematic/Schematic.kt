package me.bristermitten.fancyprivatemines.component.blocks.schematic

import me.bristermitten.fancyprivatemines.component.blocks.schematic.meta.SchematicMetadata

data class Schematic(val name: String, val fileName: String, var metadata: SchematicMetadata)
