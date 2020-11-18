package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.function.pattern.RandomPattern
import me.bristermitten.fancyprivatemines.block.BasicBlockMask
import me.bristermitten.fancyprivatemines.block.BlockMask
import me.bristermitten.fancyprivatemines.block.toBlockData
import org.bukkit.Material

fun BlockMask.toWEPattern(): RandomPattern {
    when (this) {
        is BasicBlockMask -> {
            val pattern = RandomPattern()
            percentageProbabilities.forEach {
                pattern.add(it.value.toBaseBlock(), it.key / 100f)
            }
            return pattern
        }
        else -> throw UnsupportedOperationException("Cannot convert $javaClass to WE Block Mask")
    }

}
