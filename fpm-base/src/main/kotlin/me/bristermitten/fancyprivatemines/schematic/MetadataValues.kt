package me.bristermitten.fancyprivatemines.schematic

import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.data.RelativeLocation

@Serializable
sealed class AttributeValue<T> {
    abstract val value: T
}

@Serializable
data class StringAttributeValue(override val value: String) : AttributeValue<String>()

@Serializable
data class LocationAttributeValue(override val value: RelativeLocation) : AttributeValue<RelativeLocation>()

@Serializable
data class MultipleLocationAttributeValue(override val value: List<RelativeLocation>) : AttributeValue<List<RelativeLocation>>()
