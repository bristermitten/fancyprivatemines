package me.bristermitten.fancyprivatemines.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import me.bristermitten.fancyprivatemines.function.Functionality

abstract class AbstractSerializer : Functionality() {
    abstract fun <T : Any> load(data: ByteArray, type: KSerializer<T>): T
    abstract fun <T : Any> save(data: T, serializer: KSerializer<T>): ByteArray
}

@Suppress("EXPERIMENTAL_API_USAGE")
inline fun <reified T : Any> AbstractSerializer.load(data: ByteArray) = load(data, serializer(T::class.java))
