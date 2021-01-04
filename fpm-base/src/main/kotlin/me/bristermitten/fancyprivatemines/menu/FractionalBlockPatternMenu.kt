package me.bristermitten.fancyprivatemines.menu

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.toItemStack
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import me.bristermitten.fancyprivatemines.pattern.FractionalBlockPattern
import me.bristermitten.fancyprivatemines.preview.previewFill
import me.bristermitten.fancyprivatemines.util.color
import me.mattstudios.mfgui.gui.components.amount
import me.mattstudios.mfgui.gui.components.buildItem
import me.mattstudios.mfgui.gui.components.lore
import me.mattstudios.mfgui.gui.components.name
import me.mattstudios.mfgui.gui.guis.Gui
import me.mattstudios.mfgui.gui.guis.items
import me.mattstudios.mfgui.gui.guis.toGUIItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class FractionalBlockPatternMenu(private val plugin: FancyPrivateMines) : Menu {
    private val blockMenu = BlockMenu(plugin, plugin.blocks)
    private val increaseItem = buildItem(ItemStack(Material.STAINED_GLASS, 1, 5)) {
        name = "&aIncrease Count".color()
    }.build()

    private val decreaseItem = buildItem(ItemStack(Material.STAINED_GLASS, 1, 14)) {
        name = "&cDecrease Count".color()
    }.build()

    override fun open(player: Player, privateMine: PrivateMine) {
        val pattern = privateMine.pattern.copy()
        require(pattern is FractionalBlockPattern) { "Pattern must be FractionalBlockPattern" }

        val menu = Gui(3, "PrivateMine Management")
        menu.setDefaultClickAction {
            it.isCancelled = true
        }
        menu.setCloseGuiAction {
            privateMine.pattern = pattern
            privateMine.fill(plugin)
        }

        menu.filler.fill(ItemStack(Material.STAINED_GLASS_PANE, 1, 7).toGUIItem {})


        val slotBase = 9 //first column of the second row
        val maxPlacedSlot = pattern.blockParts.groupingBy { it }
            .eachCount().entries
            .withIndex().maxByOrNull { (index, patterns) ->
                val block = patterns.key
                val blockAmount = patterns.value
                val item = block.toItemStack()

                menu.items[index + slotBase] = buildItem(item) { //Information item
                    name = block.material.toString()
                    amount = blockAmount
                }.asGuiItem {
                    blockMenu.openChoosingBlock(player, privateMine).whenComplete { newBlock, u ->
                        if (u != null) {
                            throw u
                        }
                        pattern.replace(block, newBlock.block)
                        privateMine.previewFill(player, pattern, plugin)
                        open(player, privateMine)
                    }
                }

                menu.items[index + slotBase - 9] = buildItem(item) { //+1 item
                    name = "&a+1".color()
                    amount = 1
                }.asGuiItem {
                    pattern.add(block)
                    privateMine.previewFill(player, pattern, plugin)
                    open(
                        player,
                        privateMine
                    ) //TODO check the block's requirement every time it's added to ensure things
                    //like MAX_PERCENTAGE work
                }

                menu.items[index + slotBase + 9] = buildItem(item) { //-1 item
                    name = "&c-1".color()
                    amount = 1
                }.asGuiItem {
                    pattern.remove(block)
                    privateMine.previewFill(player, pattern, plugin)
                    open(player, privateMine)
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
                blockMenu.openChoosingBlock(player, privateMine).whenComplete { newBlock, u ->
                    if (u != null) {
                        throw u
                    }
                    pattern.add(newBlock.block)
                    privateMine.previewFill(player, pattern, plugin)
                    open(player, privateMine)
                }
            }
        }
        menu.open(player)
    }
}
