package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.function.pattern.RandomPattern
import me.bristermitten.fancyprivatemines.block.BlockMask
import me.bristermitten.fancyprivatemines.block.RandomBlockMask

fun BlockMask.toWEPattern(): RandomPattern {
    when (this) {
        is RandomBlockMask -> {
            val pattern = RandomPattern()
            percentageProbabilities.forEach {
                pattern.add(it.key.toBaseBlock(), 100f / it.value)
            }
            return pattern
        }
        else -> throw UnsupportedOperationException("Cannot convert $javaClass to WE Block Mask")
    }

}
