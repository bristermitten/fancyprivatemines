package me.bristermitten.fancyprivatemines.block

data class SimpleBlockMask(val block: BlockData) : BlockMask {
    override fun generate(): BlockData {
        return block
    }

    override fun generateBulk(amount: Int): List<BlockData> {
        return List(amount) { block }
    }
}
