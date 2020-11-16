package me.bristermitten.fancyprivatemines.component.blocks.schematic.meta

import me.bristermitten.fancyprivatemines.attribute.Attribute
import java.util.concurrent.ConcurrentMap

data class SchematicMetadata(
        val hash: String,
        private val attributes: ConcurrentMap<Attribute<*>, Any>
) {
    @Suppress("UNCHECKED_CAST")
    fun <T> getAttribute(attribute: Attribute<T>): T? {
        return attributes[attribute] as T?
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getAttribute(attribute: Attribute<T>, default: T): T {
        return getAttribute(attribute) ?: default
    }

    fun <T: Any> setAttribute(attribute: Attribute<T>, value: T) {
        attributes[attribute] = value
    }
}
