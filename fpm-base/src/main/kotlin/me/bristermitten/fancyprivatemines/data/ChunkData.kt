package me.bristermitten.fancyprivatemines.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.bristermitten.fancyprivatemines.util.pack
import me.bristermitten.fancyprivatemines.util.unpack
import org.bukkit.Chunk
import org.bukkit.Location

@Serializable(with = ChunkDataSerializer::class)
data class ChunkData(val x: Int, val z: Int)

internal object ChunkDataSerializer : KSerializer<ChunkData> {
    override val descriptor = PrimitiveSerialDescriptor("ChunkData", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): ChunkData {
        return decoder.decodeInt().unpackToChunkData()
    }

    override fun serialize(encoder: Encoder, value: ChunkData) {
        encoder.encodeInt(value.packed)
    }
}

fun Chunk.toChunkData() = ChunkData(x, z)

val ChunkData.packed
    get() = pack(x, z)

fun Int.unpackToChunkData() = unpack(this).run { ChunkData(first, second) }

val Location.chunkData
    get() = ChunkData(blockZ shr 4, blockZ shr 4)

val ImmutableLocation.chunkData
    get() = ChunkData(x.toInt() shr 4, z.toInt() shr 4)
