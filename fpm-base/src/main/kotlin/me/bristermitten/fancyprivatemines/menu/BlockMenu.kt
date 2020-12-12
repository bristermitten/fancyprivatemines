package me.bristermitten.fancyprivatemines.menu

import me.bristermitten.fancyprivatemines.block.MineBlock
import me.bristermitten.fancyprivatemines.block.MineBlocks
import me.bristermitten.fancyprivatemines.block.toItemStack
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import me.mattstudios.mfgui.gui.components.buildItem
import me.mattstudios.mfgui.gui.components.lore
import me.mattstudios.mfgui.gui.guis.paginatedGui
import org.bukkit.entity.Player
import java.nio.file.Files
import java.util.concurrent.CompletableFuture

class BlockMenu(private val blocks: MineBlocks) : Menu {
    fun openChoosingBlock(player: Player, privateMine: PrivateMine): CompletableFuture<MineBlock> {
        val future = CompletableFuture<MineBlock>()

        paginatedGui(3, "Blocks") {
            blocks.all.forEach { mineBlock ->
                val item = buildItem(mineBlock.block.toItemStack()) {
                    lore = mineBlock.requirements.map { it.requirement.format(it.data) }
                }.asGuiItem { event ->
                    event.isCancelled = true
                    if (mineBlock.requirements.all {
                            it.requirement.meets(player, mineBlock, privateMine, it.data)
                        }) {
                        future.complete(mineBlock)
                    }
                }
                addItem(item)
            }
        }

        return future
    }

    override fun open(player: Player, privateMine: PrivateMine) {
        openChoosingBlock(player, privateMine)
    }
}
