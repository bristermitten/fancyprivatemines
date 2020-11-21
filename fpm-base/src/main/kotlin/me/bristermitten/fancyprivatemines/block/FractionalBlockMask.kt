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

    override fun generate(): BlockData {
        return parts.random().generate()
    }

    override fun generateBulk(amount: Int): List<BlockData> {
        return List(amount) { parts.random().generate() }
    }
}
