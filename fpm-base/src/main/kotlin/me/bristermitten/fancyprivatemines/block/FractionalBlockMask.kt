package me.bristermitten.fancyprivatemines.block

class FractionalBlockMask(parts: List<BlockMask>? = null) : BlockMask {
    private val parts = parts?.toMutableList() ?: mutableListOf()

    val blockParts get() = parts.toList()
    fun add(mask: BlockMask) {
        parts += mask
    }

    fun count(mask: BlockMask): Int {
        return parts.count { it == mask }
    }

    fun remove(mask: BlockMask) {
        parts -= mask
    }

    override fun generate(): BlockData {
        return parts.random().generate()
    }

    override fun generateBulk(amount: Int): List<BlockData> {
        return List(amount) { parts.random().generate() }
    }
}
