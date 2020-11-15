package me.bristermitten.fancyprivatemines.util

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*

object VoidWorldGenerator : ChunkGenerator() {

    override fun generateChunkData(world: World, random: Random, x: Int, z: Int, biome: BiomeGrid): ChunkData {
        val data = createChunkData(world)
        repeat(15) { x ->
            repeat(15) { y ->
                repeat(15) { z ->
                    data.setBlock(x, y, z, Material.AIR)
                }
            }
        }
        return data
    }
}
