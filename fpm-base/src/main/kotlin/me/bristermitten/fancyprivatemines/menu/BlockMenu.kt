package me.bristermitten.fancyprivatemines.menu

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.MineBlock
import me.bristermitten.fancyprivatemines.block.MineBlocks
import me.bristermitten.fancyprivatemines.block.toItemStack
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import me.bristermitten.fancyprivatemines.util.color
import me.mattstudios.mfgui.gui.components.buildItem
import me.mattstudios.mfgui.gui.components.lore
import me.mattstudios.mfgui.gui.guis.paginatedGui
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

class BlockMenu(private val plugin: FancyPrivateMines, private val blocks: MineBlocks) : Menu {
    fun openChoosingBlock(player: Player, privateMine: PrivateMine): CompletableFuture<MineBlock> {
        val future = CompletableFuture<MineBlock>()

        paginatedGui(3, "Blocks") {
            blocks.all.forEach { mineBlock ->
                val item = buildItem(mineBlock.block.toItemStack()) {
                    val matches = mineBlock.requirements.all { cachedRequirement ->
                        cachedRequirement.requirement.meets(player, mineBlock, privateMine, cachedRequirement.data)
                    }
                    if (!matches) {
                        val loreHeader = listOf("&c&lLocked", "&7Requirements:")
                        lore = (loreHeader + mineBlock.requirements.map { "&7 - " + it.requirement.format(it.data) })
                            .map(String::color)
                    }
                }.asGuiItem { event ->
                    event.isCancelled = true
                    if (mineBlock.requirements.all { cachedRequirement ->
                            cachedRequirement.requirement.meets(player, mineBlock, privateMine, cachedRequirement.data)
                        }) {
                        future.complete(mineBlock)
                    }
                }
                addItem(item)
            }
        }.open(player)
        return future
    }

    override fun open(player: Player, privateMine: PrivateMine) {
        openChoosingBlock(player, privateMine)
    }
}
