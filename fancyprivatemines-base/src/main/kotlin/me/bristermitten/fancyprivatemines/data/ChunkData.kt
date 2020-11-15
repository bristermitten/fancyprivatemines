package me.bristermitten.fancyprivatemines.data

import org.bukkit.Chunk

data class ChunkData(val x: Int, val z: Int)

fun Chunk.toChunkData() = ChunkData(x, z)
