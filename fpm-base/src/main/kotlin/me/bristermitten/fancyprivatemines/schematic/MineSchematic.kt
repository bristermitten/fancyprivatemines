package me.bristermitten.fancyprivatemines.schematic

import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.schematic.attributes.SchematicAttributeScanner
import me.bristermitten.fancyprivatemines.schematic.attributes.SchematicAttributes


@Serializable
data class MineSchematic(val fileName: String,
                         var friendlyName: String,
                         val hash: String,
                         val attributes: SchematicAttributes
) {
    inline fun <reified R : AttributeValue<Z>, Z> getAttributeFor(key: SchematicAttributeScanner<R>): Z {
        return getAttribute<R, Z>(key.attributesKey)
    }

    inline fun <reified R : AttributeValue<Z>, Z> getAttribute(key: String, onNull: (String) -> Nothing = { throw IllegalStateException("No metadata under $key") }): Z {
        val value = attributes.data[key] ?: onNull(key)
        require(value is R) {
            "Wrong metadata type at $key: ${value.javaClass}. Expected ${R::class.java}"
        }

        return value.value
    }
}
