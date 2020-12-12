package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.function.pattern.Pattern
import com.sk89q.worldedit.function.pattern.RandomPattern
import me.bristermitten.fancyprivatemines.pattern.BlockPattern
import me.bristermitten.fancyprivatemines.pattern.FractionalBlockPattern
import me.bristermitten.fancyprivatemines.pattern.RandomBlockPattern
import me.bristermitten.fancyprivatemines.pattern.SimpleBlockPattern

fun BlockPattern.toWEPattern(): Pattern {
    when (this) {
        is RandomBlockPattern -> {
            val pattern = RandomPattern()
            percentageProbabilities.forEach {
                pattern.add(it.key.toBaseBlock(), 100f / it.value)
            }
            return pattern
        }
        is FractionalBlockPattern -> {
            val total = blockParts.size
            val pattern = RandomPattern()
            blockParts.forEach {
                pattern.add(it.toWEPattern(), 1.0 / total)
            }
            return pattern
        }
        is SimpleBlockPattern -> {
            return BaseBlock(block.material.id, block.data.toInt())
        }
        else -> throw UnsupportedOperationException("Cannot convert $javaClass to WE Block Mask")
    }

}
