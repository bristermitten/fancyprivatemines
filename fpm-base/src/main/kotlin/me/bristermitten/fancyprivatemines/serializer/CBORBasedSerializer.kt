package me.bristermitten.fancyprivatemines.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor

@Suppress("UNCHECKED_CAST")
object CBORBasedSerializer : AbstractSerializer() {
    override val id = "CBOR"
    override val priority = 1
    @Suppress("EXPERIMENTAL_API_USAGE")
    private val cbor = Cbor {
        serializersModule = fpmSerializersModule
    }

    @ExperimentalSerializationApi
    override fun <T: Any> load(data: ByteArray, type: KSerializer<T>): T {
        return cbor.decodeFromByteArray(type, data)
    }

    @ExperimentalSerializationApi
    override fun <T: Any> save(data: T, serializer: KSerializer<T>): ByteArray {
        return cbor.encodeToByteArray(serializer, data)
    }
}
