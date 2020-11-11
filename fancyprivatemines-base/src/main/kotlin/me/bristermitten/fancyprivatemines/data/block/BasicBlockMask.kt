package me.bristermitten.fancyprivatemines.data.block

import java.util.*
import kotlin.streams.toList

class BasicBlockMask(
        probabilities: Map<Double, BlockData>,
        private val random: SplittableRandom = SplittableRandom()
) : BlockMask {

    private val probabilities = run {
        val map = TreeMap<Double, BlockData>()

        var total = 0.0
        probabilities.forEach {
            map[it.key + total] = it.value
            total += it.key
        }

        if (total != 100.0) {
            throw IllegalStateException("Probabilities must add up to 100!")
        }

        map
    }

    val percentageProbabilities = TreeMap(probabilities)

    private val sum by lazy {
        probabilities.keys.sum()
    }

    init {
        require(probabilities.isNotEmpty()) {
            "Must have at least 1 BlockData in probabilities map"
        }

    }

    override fun generate(): BlockData {
        val threshold = random.nextDouble(sum)
        try {
            return probabilities.ceilingEntry(threshold).value
        } catch (e: Exception) {
            println(threshold)
            throw e
        }
    }

    override fun generateBulk(amount: Int): List<BlockData> {
        return random.doubles(amount.toLong(), 0.0, sum)
                .parallel()
                .mapToObj {
                    probabilities.ceilingEntry(it).value
                }.filter(Objects::nonNull)
                .toList()
    }

}
