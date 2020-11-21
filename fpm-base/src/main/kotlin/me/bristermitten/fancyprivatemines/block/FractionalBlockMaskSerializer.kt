package me.bristermitten.fancyprivatemines.block

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer

object FractionalBlockMaskSerializer : KSerializer<FractionalBlockMask> {
    private val delegate = ListSerializer(serializer<BlockMask>())
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: FractionalBlockMask) {
        delegate.serialize(encoder, value.blockParts)
    }

    override fun deserialize(decoder: Decoder): FractionalBlockMask {
        return FractionalBlockMask(delegate.deserialize(decoder))
    }
}
