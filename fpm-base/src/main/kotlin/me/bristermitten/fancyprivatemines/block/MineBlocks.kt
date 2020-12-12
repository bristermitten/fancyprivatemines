package me.bristermitten.fancyprivatemines.block

import me.bristermitten.fancyprivatemines.block.requirement.BlockRequirements
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection

class MineBlocks(private val blockRequirements: BlockRequirements) {
    private val blocks = mutableMapOf<BlockData, MineBlock>()

    val all get() = blocks.values.toSet()

    fun loadFrom(name: String, entries: List<String>): MineBlock {
        val block = name.parseBlockData()

        val requirements = entries.map(blockRequirements.parser::parse)

        return MineBlock(block, requirements).apply {
            blocks[block] = this
        }
    }

    fun loadFrom(section: ConfigurationSection) {
        section.getKeys(false).mapNotNull {
            it to (section.getStringList(it) ?: return@mapNotNull null)
        }.map {
            loadFrom(it.first, it.second)
        }
    }

    private fun String.parseBlockData(): BlockData {
        val parts = split("/")
        if (parts.size == 1) {
            return BlockData(Material.matchMaterial(parts.first()))
        }
        return BlockData(Material.matchMaterial(parts.first()), parts[1].toShort())
    }
}
