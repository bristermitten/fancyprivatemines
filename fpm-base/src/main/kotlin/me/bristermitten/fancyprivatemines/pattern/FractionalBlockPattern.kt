package me.bristermitten.fancyprivatemines.pattern

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.menu.FractionalBlockPatternMenu
import me.bristermitten.fancyprivatemines.menu.Menu

@Serializable(with = FractionalBlockPattern.Serializer::class)
class FractionalBlockPattern(parts: List<BlockData>? = null) : BlockPattern {
    private val parts = parts?.toMutableList() ?: mutableListOf()

    val blockParts get() = parts.toList()

    @Throws(IllegalStateException::class)
    fun add(pattern: BlockData) {
        if (parts.distinct().size >= MAX_SIZE) {
            throw IllegalStateException("Reached maximum size")
        }
        parts += pattern
    }

    fun count(pattern: BlockData): Int {
        return parts.count { it == pattern }
    }

    fun remove(pattern: BlockData, noRemove: Boolean = true) {
        if (noRemove && count(pattern) == 1) {
            return
        }
        parts -= pattern
    }

    fun replace(pattern: BlockData, replacement: BlockData) {
        parts.replaceAll {
            if (it == pattern) replacement else it
        }
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
        return parts.random()
    }

    override fun generateBulk(amount: Int): List<BlockData> {
        return List(amount) { parts.random() }
    }

    override fun createMenu(plugin: FancyPrivateMines): Menu {
        return FractionalBlockPatternMenu(plugin)
    }

    object Serializer : KSerializer<FractionalBlockPattern> {
        private val delegate = ListSerializer(BlockData.serializer())
        override val descriptor: SerialDescriptor = delegate.descriptor

        override fun serialize(encoder: Encoder, value: FractionalBlockPattern) {
            delegate.serialize(encoder, value.blockParts)
        }

        override fun deserialize(decoder: Decoder): FractionalBlockPattern {
            return FractionalBlockPattern(delegate.deserialize(decoder))
        }
    }

    companion object {
        const val MAX_SIZE = 9
    }
}
