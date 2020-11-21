package me.bristermitten.fancyprivatemines.mine

import com.google.common.collect.HashMultimap
import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.data.ChunkData
import java.io.File
import java.util.*

class PrivateMineStorage(private val plugin: FancyPrivateMines) {

    val all
        get() = minesById.values.toSet()

    private val minesByChunk = mutableMapOf<ChunkData, Long>()
    private val minesById = mutableMapOf<Long, PrivateMine>()

    private val minesByName = mutableMapOf<String, Long>()
    private val minesByOwner = HashMultimap.create<UUID, PrivateMine>()

    operator fun get(chunkData: ChunkData) = minesByChunk[chunkData]?.let { minesById[it] }
    operator fun get(id: Long) = minesById[id]
    operator fun get(name: String) = minesByName[name]?.let { minesById[it] }
    operator fun get(owner: UUID) = minesByOwner[owner].toSet()

    fun add(chunks: Collection<ChunkData>, mine: PrivateMine) {
        chunks.forEach {
            minesByChunk[it] = mine.id
        }

        minesById[mine.id] = mine

        rebuildIndexes()
    }

    fun loadFrom(file: File) {
        val newMines = plugin.configuration.serialization.active.load(file.readBytes(), Data.serializer())

        minesByChunk.clear()
        minesByChunk.putAll(newMines.byChunk)

        minesById.clear()
        minesById.putAll(newMines.byId)

        rebuildIndexes()
    }

    fun saveTo(file: File) {
        val data = Data(minesByChunk, minesById)

        val bytes = plugin.configuration.serialization.active.save(data, Data.serializer())

        file.writeBytes(bytes)
    }

    private fun rebuildIndexes() {
        minesById.values.forEach {
            val name = it.name
            if (name != null) {
                minesByName[name.toLowerCase()] = it.id
            }
            minesByOwner.put(it.owner, it)
        }
    }

    @Serializable
    private data class Data(
            val byChunk: Map<ChunkData, Long>,
            val byId: Map<Long, PrivateMine>
    )

}


