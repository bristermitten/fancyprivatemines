package me.bristermitten.fancyprivatemines.mine

import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.pattern.BlockPattern
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.serializer.UUIDSerializer
import java.util.*
import kotlin.math.max

@Serializable
data class PrivateMine(
    val id: Long = nextId,
    @Serializable(with = UUIDSerializer::class)
        val owner: UUID,
    var name: String?,
    var open: Boolean = true,
    var blocks: BlockPattern,
    var taxPercentage: Double,
    var spawnLocation: ImmutableLocation,
    var region: Region,
    var miningRegion: Region
) {

    init {
        highestId = max(id, highestId) //If we're deserializing something with an ID of eg 6, we need to make sure that highestId compensates for this
    }

    companion object {
        private var highestId = 0L
        val nextId get() = ++highestId
    }

    fun fill(plugin: FancyPrivateMines) {
        plugin.configuration.blockSetting.methods.active.setBlocksBulk(miningRegion, blocks)
    }
}
