package me.bristermitten.fancyprivatemines.block

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object RandomBlockMaskSerializer : KSerializer<RandomBlockMask> {
    private val mapSerializer = MapSerializer(BlockData.serializer(), Double.serializer())
    override val descriptor: SerialDescriptor = mapSerializer.descriptor

    override fun serialize(encoder: Encoder, value: RandomBlockMask) {
        mapSerializer.serialize(encoder, value.percentageProbabilities)
    }

    override fun deserialize(decoder: Decoder): RandomBlockMask {
        return RandomBlockMask(mapSerializer.deserialize(decoder))
    }
}
