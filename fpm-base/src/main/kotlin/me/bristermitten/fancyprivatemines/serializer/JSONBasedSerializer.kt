package me.bristermitten.fancyprivatemines.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

@Suppress("UNCHECKED_CAST")
object JSONBasedSerializer : AbstractSerializer() {
    override val id: String = "JSON"
    override val priority = 0

    private val json = Json {
        serializersModule = fpmSerializersModule
        useArrayPolymorphism = true
    }

    @ExperimentalSerializationApi
    override fun <T : Any> load(data: ByteArray, type: KSerializer<T>): T {
        return json.decodeFromString(type, data.decodeToString())
    }

    @ExperimentalSerializationApi
    override fun <T : Any> save(data: T, serializer: KSerializer<T>): ByteArray {
        return json.encodeToString(serializer, data).encodeToByteArray()
    }
}
