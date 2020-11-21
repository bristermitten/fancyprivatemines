package me.bristermitten.fancyprivatemines.mine

import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.data.ChunkData
import me.bristermitten.fancyprivatemines.serializer.UUIDSerializer
import java.io.File
import java.util.*

class PrivateMineStorage(private val plugin: FancyPrivateMines) {

    val all
        get() = minesById.values.toSet()

    private val minesByChunk = mutableMapOf<ChunkData, UUID>()
    private val minesById = mutableMapOf<UUID, PrivateMine>()

    operator fun get(chunkData: ChunkData) = minesByChunk[chunkData]?.let { minesById[it] }

    operator fun get(uuid: UUID) = minesById[uuid]

    fun add(chunks: Collection<ChunkData>, mine: PrivateMine) {
        chunks.forEach {
            minesByChunk[it] = mine.id
        }

        minesById[mine.id] = mine
    }

    fun loadFrom(file: File) {
        val newMines = plugin.configuration.serialization.active.load(file.readBytes(), Data.serializer())

        minesByChunk.clear()
        minesByChunk.putAll(newMines.byChunk)

        minesById.clear()
        minesById.putAll(newMines.byId)
    }

    fun saveTo(file: File) {
        val data = Data(minesByChunk, minesById)

        val bytes = plugin.configuration.serialization.active.save(data, Data.serializer())

        file.writeBytes(bytes)
    }

    @Serializable
    private data class Data(
            val byChunk: Map<ChunkData, @Serializable(with = UUIDSerializer::class) UUID>,
            val byId: Map<@Serializable(with = UUIDSerializer::class) UUID, PrivateMine>
    )

}


