package me.bristermitten.fancyprivatemines.block

import kotlinx.serialization.Serializable
import java.util.*
import kotlin.streams.toList

@Serializable(with = RandomBlockMaskSerializer::class)
class RandomBlockMask(
        probabilities: Map<BlockData, Double>,
) : BlockMask {
    private val random = SplittableRandom()

    private val probabilityMap = run {
        val map = TreeMap<Double, BlockData>()

        var total = 0.0
        probabilities.forEach {
            map[it.value + total] = it.key
            total += it.value
        }

        if (total != 100.0) {
            throw IllegalStateException("Probabilities must add up to 100! $total")
        }

        map
    }

    val percentageProbabilities = probabilities.toMap()

    private val sum by lazy {
        probabilities.values.sum()
    }

    init {
        require(probabilities.isNotEmpty()) {
            "Must have at least 1 BlockData in probabilities map"
        }
    }

    override fun generate(): BlockData {
        val threshold = random.nextDouble(sum)
        try {
            return probabilityMap.ceilingEntry(threshold).value
        } catch (e: Exception) {
            throw e
        }
    }

    override fun generateBulk(amount: Int): List<BlockData> {
        return random.doubles(amount.toLong(), 0.0, sum)
                .parallel()
                .mapToObj {
                    probabilityMap.ceilingEntry(it).value
                }.filter(Objects::nonNull)
                .toList()
    }
}
