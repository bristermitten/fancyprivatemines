package me.bristermitten.fancyprivatemines.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor

/**
 * This means we can have polymorphic serialization of 2 types with the same descriptor (eg 2 Map<BlockType, Int>s
 */
@ExperimentalSerializationApi
class DelegatingSerialDescriptor(override val serialName: String, private val delegate: SerialDescriptor) : SerialDescriptor by delegate
