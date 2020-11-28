package me.bristermitten.fancyprivatemines.block

import kotlinx.serialization.Serializable

@Serializable(with = FractionalBlockMaskSerializer::class)
class FractionalBlockMask(parts: List<BlockMask>? = null) : BlockMask {
    private val parts = parts?.toMutableList() ?: mutableListOf()

    val blockParts get() = parts.toList()

    fun add(mask: BlockMask) {
        parts += mask
    }

    fun count(mask: BlockMask): Int {
        return parts.count { it == mask }
    }

    fun remove(mask: BlockMask, noRemove: Boolean = true) {
        if (noRemove && count(mask) == 1) {
            return
        }
        parts -= mask
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
}
