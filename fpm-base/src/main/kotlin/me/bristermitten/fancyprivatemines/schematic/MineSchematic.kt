package me.bristermitten.fancyprivatemines.schematic

import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.schematic.attributes.SchematicAttributes


@Serializable
data class MineSchematic(val fileName: String,
                         var friendlyName: String,
                         val hash: String,
                         val attributes: SchematicAttributes
)
