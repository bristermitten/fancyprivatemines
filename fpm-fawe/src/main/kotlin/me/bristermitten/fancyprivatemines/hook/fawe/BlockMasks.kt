package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.function.pattern.RandomPattern
import me.bristermitten.fancyprivatemines.block.RandomBlockMask
import me.bristermitten.fancyprivatemines.block.BlockMask

fun BlockMask.toWEPattern(): RandomPattern {
    when (this) {
        is RandomBlockMask -> {
            val pattern = RandomPattern()
            percentageProbabilities.forEach {
                pattern.add(it.value.toBaseBlock(), it.key / 100f)
            }
            return pattern
        }
        else -> throw UnsupportedOperationException("Cannot convert $javaClass to WE Block Mask")
    }

}
