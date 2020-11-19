package me.bristermitten.fancyprivatemines.block

import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.ints.beInRange
import io.kotest.matchers.should
import org.bukkit.Material.COBBLESTONE
import org.bukkit.Material.STONE
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import kotlin.math.roundToInt

internal class RandomBlockMaskTest {
    private val mask = RandomBlockMask(
            mapOf(
                    30.0 to BlockData(STONE),
                    70.0 to BlockData(COBBLESTONE),
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

        val amount = 10000
        val results = mask.generateBulk(amount)


        results.count {
            it.material == STONE
        }.shouldBeRoughly((0.3 * amount).roundToInt())

        results.count {
            it.material == COBBLESTONE
        }.shouldBeRoughly((0.7 * amount).roundToInt())
    }

    private fun Int.shouldBeRoughly(value: Int, marginOfError: Int = value / 10) = this should beInRange(value - marginOfError..value + marginOfError)
}
