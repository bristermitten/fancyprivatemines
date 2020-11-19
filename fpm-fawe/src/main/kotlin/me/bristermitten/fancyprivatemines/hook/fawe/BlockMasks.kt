package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.function.pattern.Pattern
import com.sk89q.worldedit.function.pattern.RandomPattern
import me.bristermitten.fancyprivatemines.block.BlockMask
import me.bristermitten.fancyprivatemines.block.FractionalBlockMask
import me.bristermitten.fancyprivatemines.block.RandomBlockMask
import me.bristermitten.fancyprivatemines.block.SimpleBlockMask

fun BlockMask.toWEPattern(): Pattern {
    when (this) {
        is RandomBlockMask -> {
            val pattern = RandomPattern()
            percentageProbabilities.forEach {
                pattern.add(it.key.toBaseBlock(), 100f / it.value)
            }
            return pattern
        }
        is FractionalBlockMask -> {
            val total = blockParts.size
            val pattern = RandomPattern()
            blockParts.forEach {
                pattern.add(it.toWEPattern(), 1.0 / total)
            }
            return pattern
        }
        is SimpleBlockMask -> {
            return BaseBlock(block.material.id, block.data.toInt())
        }
        else -> throw UnsupportedOperationException("Cannot convert $javaClass to WE Block Mask")
    }

}
