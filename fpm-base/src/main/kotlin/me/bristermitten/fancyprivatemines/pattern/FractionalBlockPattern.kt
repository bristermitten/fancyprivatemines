package me.bristermitten.fancyprivatemines.pattern

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.bristermitten.fancyprivatemines.block.BlockData

@Serializable(with = FractionalBlockPattern.Serializer::class)
class FractionalBlockPattern(parts: List<BlockPattern>? = null) : BlockPattern {
    private val parts = parts?.toMutableList() ?: mutableListOf()

    val blockParts get() = parts.toList()

    fun add(pattern: BlockPattern) {
        parts += pattern
    }

    fun count(pattern: BlockPattern): Int {
        return parts.count { it == pattern }
    }

    fun remove(pattern: BlockPattern, noRemove: Boolean = true) {
        if (noRemove && count(pattern) == 1) {
            return
        }
        parts -= pattern
    }

    /**
     * Resets the proportions of this mask back to an equally distributed ratio
     * For example, `[3 x STONE, 4 x COAL]` will be changed to `[1 x STONE, 1 x COAL]`
     */
    fun normalize() {
        val partsSet = parts.toSet()
        parts.clear()
        parts.addAll(partsSet)
    }

    override fun generate(): BlockData {
        return parts.random().generate()
    }

    override fun generateBulk(amount: Int): List<BlockData> {
        return List(amount) { parts.random().generate() }
    }

    object Serializer : KSerializer<FractionalBlockPattern> {
        private val delegate = ListSerializer(kotlinx.serialization.serializer<BlockPattern>())
        override val descriptor: SerialDescriptor = delegate.descriptor

        override fun serialize(encoder: Encoder, value: FractionalBlockPattern) {
            delegate.serialize(encoder, value.blockParts)
        }

        override fun deserialize(decoder: Decoder): FractionalBlockPattern {
            return FractionalBlockPattern(delegate.deserialize(decoder))
        }
    }
}
