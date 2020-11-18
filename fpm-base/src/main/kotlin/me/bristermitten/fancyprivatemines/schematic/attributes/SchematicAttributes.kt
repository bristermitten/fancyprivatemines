package me.bristermitten.fancyprivatemines.schematic.attributes

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.schematic.AttributeValue
import java.util.concurrent.ConcurrentMap

@Serializable
data class SchematicAttributes(
        @get:Synchronized val data: MutableMap<String, AttributeValue<out @Contextual Any>>
)
