package me.bristermitten.fancyprivatemines.util

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*

object VoidWorldGenerator : ChunkGenerator() {

    override fun generateChunkData(world: World, random: Random, cx: Int, cz: Int, biome: BiomeGrid): ChunkData {
        val data = createChunkData(world)
        return data
    }
}
