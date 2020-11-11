package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.blocks.BaseBlock
import me.bristermitten.fancyprivatemines.data.block.BlockData

internal fun BlockData.toBaseBlock(): BaseBlock {
    @Suppress("DEPRECATION")
    return BaseBlock(material.id, data.toInt())
}
