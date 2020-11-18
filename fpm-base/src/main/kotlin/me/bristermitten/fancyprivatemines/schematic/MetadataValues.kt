package me.bristermitten.fancyprivatemines.schematic

import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.data.RelativeLocation

@Serializable
sealed class AttributeValue<T>(open val value: T)

data class StringAttributeValue(override val value: String) : AttributeValue<String>(value)

data class LocationAttributeValue(override val value: RelativeLocation) : AttributeValue<RelativeLocation>(value)

data class MultipleLocationAttributeValue(override val value: List<RelativeLocation>) : AttributeValue<List<RelativeLocation>>(value)
