package me.bristermitten.fancyprivatemines.menu

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
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
import kotlin.coroutines.resume

class BlockMenu(private val plugin: FancyPrivateMines, private val blocks: MineBlocks) : Menu {
    suspend fun openChoosingBlock(player: Player, privateMine: PrivateMine): MineBlock = suspendCancellableCoroutine {
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
                        it.resume(mineBlock)
                    }
                }
                addItem(item)
            }
        }.apply {
            setCloseGuiAction { _ ->
                it.cancel()
            }
        }.open(player)
    }

    override fun open(player: Player, privateMine: PrivateMine) {
        GlobalScope.launch { openChoosingBlock(player, privateMine) }
    }
}
