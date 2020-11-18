package me.bristermitten.fancyprivatemines.schematic

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.schematic.attributes.SchematicAttributes
import me.bristermitten.fancyprivatemines.util.fpmDebug
import me.bristermitten.fancyprivatemines.util.sha256Hash
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import kotlin.Exception

class SchematicLoader(private val plugin: FancyPrivateMines) {
    private val json = Json
    private val metaFormat = "%s.metadata"

    fun loadSchematic(file: File): MineSchematic {
        val metaFileName = metaFormat.format(file.name)
        val metaFile = file.parentFile.resolve(metaFileName)

        val hash = file.readBytes().sha256Hash()

        if (metaFile.exists()) {
            try {
                val meta = json.decodeFromString<MineSchematic>(metaFile.readText())
                if (hash == meta.hash) {
                    return meta //It's safe to use, so return it
                }
                plugin.logger.fpmDebug { "Mine Schematic ${file.name} has changed (hashes do not match). Its meta will be re-computed." }
            } catch (e: Exception) {
                plugin.logger.warning { "Invalid Schematic Metadata for ${file.name}. It will be re-computed once pasted." }
                if (plugin.pmConfig.debug) {
                    e.printStackTrace()
                }
            }
        }

        val name = file.nameWithoutExtension
        val attributes = SchematicAttributes(ConcurrentHashMap())

        val schematic = MineSchematic(
                fileName = name,
                friendlyName = name,
                hash = hash,
                attributes = attributes
        )

        metaFile.writeText(json.encodeToString(schematic))

        return schematic
    }
}
