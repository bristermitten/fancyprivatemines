package me.bristermitten.fancyprivatemines.data.block

import me.bristermitten.fancyprivatemines.data.block.BasicBlockMask
import me.bristermitten.fancyprivatemines.data.block.BlockData
import io.kotest.assertions.retry
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.ints.beInRange
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.should
import io.kotest.mpp.replay
import org.bukkit.Material.COBBLESTONE
import org.bukkit.Material.STONE
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import kotlin.math.roundToInt

internal class BasicBlockMaskTest {
    private val mask = BasicBlockMask(
            mapOf(
                    3.0 to BlockData(STONE),
                    4.0 to BlockData(COBBLESTONE),
            )
    )

    @Test
    fun generate() {
        mask.generate() shouldBeIn setOf(
                BlockData(STONE),
                BlockData(COBBLESTONE)
        )
    }

    @RepeatedTest(20)
    fun generateBulk() {
        //Since it's random, this may fail occasionally.

        val amount = 100_000
        val results = mask.generateBulk(amount)


        results.count {
            it.material == STONE
        }.shouldBeRoughly((3.0 / 7 * amount).roundToInt())

        results.count {
            it.material == COBBLESTONE
        }.shouldBeRoughly((4.0 / 7 * amount).roundToInt())
    }

    private fun Int.shouldBeRoughly(value: Int, marginOfError: Int = value / 10) = this should beInRange(value - marginOfError..value + marginOfError)

}
