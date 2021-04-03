package me.bristermitten.fancyprivatemines.menu

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.toItemStack
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import me.bristermitten.fancyprivatemines.pattern.BlockPattern
import me.bristermitten.fancyprivatemines.pattern.FractionalBlockPattern
import me.bristermitten.fancyprivatemines.preview.previewFill
import me.bristermitten.fancyprivatemines.util.color
import me.bristermitten.fancyprivatemines.util.dispatcher
import me.bristermitten.fancyprivatemines.util.prettyName
import me.mattstudios.mfgui.gui.components.*
import me.mattstudios.mfgui.gui.guis.Gui
import me.mattstudios.mfgui.gui.guis.items
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class FractionalBlockPatternMenu(private val plugin: FancyPrivateMines) : Menu {
    private val blockMenu = BlockMenu(plugin, plugin.blocks)
    private val backgroundItem = buildItem(ItemStack(Material.STAINED_GLASS_PANE, 1, 7)) {
        name = " "
    }.asGuiItem()

    private companion object {
        const val ROW_SLOT_BASE = 9 //Second row, first column
    }

    private fun handle(player: Player, privateMine: PrivateMine, pattern: BlockPattern = privateMine.pattern.copy()) {
        require(pattern is FractionalBlockPattern) { "Pattern must be FractionalBlockPattern" }

        val menu = Gui(3, "PrivateMine Management")
        menu.setDefaultClickAction {
            it.isCancelled = true
        }
        menu.filler.fill(backgroundItem)

        openPatternMenu(pattern, menu, player, privateMine)

        menu.open(player)
    }

    private fun openPatternMenu(pattern: FractionalBlockPattern, menu: Gui, player: Player, privateMine: PrivateMine) {
        menu.setCloseGuiAction {
            privateMine.pattern = pattern
            privateMine.fill(plugin)
        }
        pattern.blockParts.groupingBy { it }
            .eachCount().entries.forEachIndexed { index, patterns ->
                val block = patterns.key
                val blockAmount = patterns.value

                val item = block.toItemStack()
                val centerSlot = index + ROW_SLOT_BASE

                menu.items[centerSlot] = ItemBuilder.from(item.clone()).apply { //Information item
                    name = block.material.prettyName()
                    amount = blockAmount
                    lore = listOf(
                        "&aLeft Click to change type",
                        "&cRight Click to remove"
                    ).map(String::color)
                }.asGuiItem {
                    if (it.isLeftClick) {
                        menu.setCloseGuiAction { } //Don't regen until we have to

                        CoroutineScope(plugin.dispatcher()).launch {
                            val newBlock = blockMenu.openChoosingBlock(player, privateMine)

                            pattern.replace(block, newBlock.block)
                            withContext(Dispatchers.IO) {
                                privateMine.previewFill(player, pattern, plugin)
                            }

                            menu.setCloseGuiAction { } //Don't regen until we have to
                            handle(player, privateMine, pattern)
                        }
                    }
                    if (it.isRightClick) {
                        pattern.removeAll(block)
                        privateMine.previewFill(player, pattern, plugin)
                        openPatternMenu(pattern, menu, player, privateMine)
                        menu.update()
                    }
                }

                menu.items[centerSlot - 9] = buildItem(item.clone()) { //+1 item
                    name = "&a+1".color()
                    amount = 1
                }.asGuiItem {
                    pattern.add(block)
                    privateMine.previewFill(player, pattern, plugin)
                    openPatternMenu(pattern, menu, player, privateMine)
                    //TODO check the block's requirement every time it's added to ensure things like MAX_PERCENTAGE work
                }

                menu.items[centerSlot + 9] = buildItem(item.clone()) { //-1 item
                    name = "&c-1".color()
                    amount = 1
                }.asGuiItem {
                    pattern.remove(block, true)
                    privateMine.previewFill(player, pattern, plugin)
                    openPatternMenu(pattern, menu, player, privateMine)
                }
            }

        val nextSlot = pattern.blockTypes.size

        for (i in (nextSlot until 9)) {
            //choose a block
            val slot = i + 9
            val glass = ItemStack(Material.STAINED_GLASS_PANE, 1, 5)

            menu.items[slot + 9] = backgroundItem
            menu.items[slot - 9] = backgroundItem
            //Remove the +- 1 items in case they're leftover

            menu.items[slot] = buildItem(glass) {
                lore = listOf(
                    "&7Empty Block Slot",
                    "&a&lUNLOCKED"
                ).map(String::color)
            }.asGuiItem {
                menu.setCloseGuiAction {  }
                CoroutineScope(plugin.dispatcher()).launch {
                    val newBlock = blockMenu.openChoosingBlock(player, privateMine)
                    pattern.add(newBlock.block)
                    withContext(Dispatchers.IO) {
                        privateMine.previewFill(player, pattern, plugin)
                    }
                    openPatternMenu(pattern, menu, player, privateMine)
                    menu.open(player)
                }
            }
        }
        menu.update()
    }

    override fun open(player: Player, privateMine: PrivateMine) {
        handle(player, privateMine)
    }
}
