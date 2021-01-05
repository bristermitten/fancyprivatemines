package me.bristermitten.fancyprivatemines.menu

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.toItemStack
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import me.bristermitten.fancyprivatemines.pattern.BlockPattern
import me.bristermitten.fancyprivatemines.pattern.FractionalBlockPattern
import me.bristermitten.fancyprivatemines.preview.previewFill
import me.bristermitten.fancyprivatemines.util.color
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

    private fun handle(player: Player, privateMine: PrivateMine, pattern: BlockPattern = privateMine.pattern.copy()) {
        require(pattern is FractionalBlockPattern) { "Pattern must be FractionalBlockPattern" }

        val menu = Gui(3, "PrivateMine Management")
        menu.setDefaultClickAction {
            it.isCancelled = true
        }
        var handleClose = true
        menu.setCloseGuiAction {
            if(handleClose) {
                privateMine.pattern = pattern
                privateMine.fill(plugin)
            }
        }

        menu.filler.fill(backgroundItem)

        val slotBase = 9 //first column of the second row

        val maxPlacedSlot = pattern.blockParts.groupingBy { it }
            .eachCount().entries
            .withIndex().maxByOrNull { (index, patterns) ->
                val block = patterns.key
                val blockAmount = patterns.value

                val item = block.toItemStack()
                val centerSlot = index + slotBase


                menu.items[centerSlot] = ItemBuilder.from(item.clone()).apply { //Information item
                    name = block.material.prettyName()
                    amount = blockAmount
                    lore = listOf(
                        "&aLeft Click to change type",
                        "&cRight Click to remove"
                    ).map(String::color)
                }.asGuiItem {
                    if (it.isLeftClick) {
                        handleClose = false
                        blockMenu.openChoosingBlock(player, privateMine).whenComplete { newBlock, u ->
                            if (u != null) {
                                throw u
                            }
                            pattern.replace(block, newBlock.block)
                            println(pattern)
                            privateMine.previewFill(player, pattern, plugin)
                            handle(player, privateMine, pattern)
                        }
                    }
                    if (it.isRightClick) {
                        pattern.removeAll(block)
                        privateMine.previewFill(player, pattern, plugin)
                        handle(player, privateMine, pattern)
                    }
                }


                menu.items[centerSlot - 9] = buildItem(item.clone()) { //+1 item
                    name = "&a+1".color()
                    amount = 1
                }.asGuiItem {
                    pattern.add(block)
                    privateMine.previewFill(player, pattern, plugin)
                    handle(player, privateMine, pattern)
                    //TODO check the block's requirement every time it's added to ensure things like MAX_PERCENTAGE work
                }

                menu.items[centerSlot + 9] = buildItem(item.clone()) { //-1 item
                    name = "&c-1".color()
                    amount = 1
                }.asGuiItem {
                    pattern.remove(block)
                    privateMine.previewFill(player, pattern, plugin)
                    handle(player, privateMine, pattern)
                }

                index
            }?.index


        val nextSlot = (maxPlacedSlot ?: -1) + 1
        for (i in (nextSlot until 9)) {
            //choose a block
            val slot = i + 9
            val glass = ItemStack(Material.STAINED_GLASS_PANE, 1, 5)
            menu.items[slot] = buildItem(glass) {
                lore = listOf(
                    "&7Empty Block Slot",
                    "&a&lUNLOCKED"
                ).map(String::color)
            }.asGuiItem {
                handleClose = false
                blockMenu.openChoosingBlock(player, privateMine).whenComplete { newBlock, u ->
                    if (u != null) {
                        throw u
                    }
                    pattern.add(newBlock.block)
                    privateMine.previewFill(player, pattern, plugin)
                    handle(player, privateMine, pattern)
                }
            }
        }
        menu.open(player)
    }
    override fun open(player: Player, privateMine: PrivateMine) {
        handle(player, privateMine)
    }
}
