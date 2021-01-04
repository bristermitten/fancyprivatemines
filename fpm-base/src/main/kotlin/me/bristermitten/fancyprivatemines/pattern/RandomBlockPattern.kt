package me.bristermitten.fancyprivatemines.pattern

import kotlinx.serialization.*
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.menu.Menu
import me.bristermitten.fancyprivatemines.serializer.DelegatingSerialDescriptor
import java.util.*
import kotlin.streams.toList

@Serializable(with = RandomBlockPattern.Serializer::class)
class RandomBlockPattern(
    probabilities: Map<BlockData, Double>,
) : BlockPattern {
    private val random = SplittableRandom()

    @Transient
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

    override fun createMenu(plugin: FancyPrivateMines): Menu? {
        return null
    }

    override fun copy(): BlockPattern {
        return RandomBlockPattern(percentageProbabilities)
    }
    object Serializer : KSerializer<RandomBlockPattern> {
        private val mapSerializer = MapSerializer(BlockData.serializer(), Double.serializer())
        @ExperimentalSerializationApi
        override val descriptor: SerialDescriptor = DelegatingSerialDescriptor("RandomBlockPattern", mapSerializer.descriptor)

        override fun serialize(encoder: Encoder, value: RandomBlockPattern) {
            mapSerializer.serialize(encoder, value.percentageProbabilities)
        }

        override fun deserialize(decoder: Decoder): RandomBlockPattern {
            return RandomBlockPattern(mapSerializer.deserialize(decoder))
        }
    }
}
