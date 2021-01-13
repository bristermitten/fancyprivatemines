package me.bristermitten.fancyprivatemines.pattern

import kotlinx.serialization.*
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.menu.FractionalBlockPatternMenu
import me.bristermitten.fancyprivatemines.menu.Menu
import me.bristermitten.fancyprivatemines.serializer.DelegatingSerialDescriptor

@Serializable(with = FractionalBlockPattern.Serializer::class)
class FractionalBlockPattern(
    parts: Map<BlockData, Int> = emptyMap()
) : BlockPattern {
    private val parts = parts.toMutableMap()

    @Transient
    val blockParts get() = parts.flatMap { (key, value) -> List(value) { key } }

    val blockTypes get() = parts.keys.toSet()

    @Throws(IllegalStateException::class)
    fun add(pattern: BlockData) {
        if (parts.keys.size >= MAX_SIZE - 1) {
            throw IllegalStateException("Reached maximum size")
        }
        parts.merge(pattern, 1, Int::plus)
    }

    /**
     * Count how many of a given [BlockData] is present
     * This pattern is structured in a way that 0 will never be returned.
     * If the [pattern] is not present, -1 is returned
     */
    fun count(pattern: BlockData): Int {
        return parts[pattern] ?: -1
    }

    /**
     * Attempts to remove a single pattern
     * @param pattern The pattern to remove
     * @param fullRemove If the pattern should be fully removed (if only present once)
     * @return If the pattern changed state at all
     */
    fun remove(pattern: BlockData, fullRemove: Boolean = false) : Boolean {
        val cur = count(pattern)
        if (cur == -1) {
            return false//Not present
        }
        if (cur == 1) {
            return if (!fullRemove) {
                false
            } else {
                parts.remove(pattern)
                true
            }
        }

        parts[pattern] = cur - 1
        return true
    }

    fun removeAll(pattern: BlockData) {
        parts.remove(pattern)
    }

    fun replace(pattern: BlockData, replacement: BlockData) {
        val patternCount = count(pattern)
        if (patternCount == -1) {
            return //nothing to replace
        }
        val replacementCount = count(replacement)
        if (replacementCount == -1) {
            //Simply replace with the current count
            parts[replacement] = patternCount
            removeAll(pattern)
            return
        }
        parts[replacement] = patternCount + replacementCount
        removeAll(pattern)
    }

    /**
     * Resets the proportions of this mask back to an equally distributed ratio
     * For example, `[3 x STONE, 4 x COAL]` will be changed to `[1 x STONE, 1 x COAL]`
     */
    fun normalize() {
        parts.entries.forEach {
            it.setValue(1)
        }
    }

    override fun generate(): BlockData {
        return blockParts.random() //TODO surely this can be optimised a bit better
    }

    override fun createMenu(plugin: FancyPrivateMines): Menu {
        return FractionalBlockPatternMenu(plugin)
    }

    override fun copy(): BlockPattern {
        return FractionalBlockPattern(parts)
    }

    override fun toString(): String {
        return "FractionalBlockPattern(parts=$parts)"
    }


    object Serializer : KSerializer<FractionalBlockPattern> {
        private val delegate = MapSerializer(BlockData.serializer(), Int.serializer())
        @ExperimentalSerializationApi
        override val descriptor = DelegatingSerialDescriptor("FractionalBlockPattern", delegate.descriptor)

        override fun serialize(encoder: Encoder, value: FractionalBlockPattern) {
            delegate.serialize(encoder, value.parts)
        }

        override fun deserialize(decoder: Decoder): FractionalBlockPattern {
            return FractionalBlockPattern(delegate.deserialize(decoder))
        }
    }

    companion object {
        const val MAX_SIZE = 9
    }
}
