package me.bristermitten.fancyprivatemines.menu

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.FractionalBlockMask
import me.bristermitten.fancyprivatemines.block.toBlockData
import me.bristermitten.fancyprivatemines.block.toBlockMask
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import me.mattstudios.mfgui.gui.components.buildItem
import me.mattstudios.mfgui.gui.guis.Gui
import me.mattstudios.mfgui.gui.guis.items
import me.mattstudios.mfgui.gui.guis.toGUIItem
import net.kyori.adventure.text.minimessage.fancy.Fancy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class MineMenu(val plugin: FancyPrivateMines, val player: Player, val mine: PrivateMine) {
    fun open() {
        val menu = Gui(3, "PrivateMine Management")
        menu.filler.fill(ItemStack(Material.STAINED_GLASS_PANE, 7).toGUIItem {
        })

        menu.items[2] = ItemStack(Material.STAINED_GLASS, 1, 5).toGUIItem {
            val blocks = mine.blocks
            if (blocks is FractionalBlockMask) {
                blocks.add(Material.STONE.toBlockData().toBlockMask())
                menu.setStoneItem()
                menu.update()
                mine.fill(plugin)
            } else {
                throw UnsupportedOperationException(blocks.javaClass.name)
            }
        }
        menu.setStoneItem()
        menu.items[11 + 9] = ItemStack(Material.STAINED_GLASS, 1, 14).toGUIItem {
            val blocks = mine.blocks
            if (blocks is FractionalBlockMask) {
                blocks.remove(Material.STONE.toBlockData().toBlockMask())
                menu.setStoneItem()
                menu.update()
                mine.fill(plugin)
            } else {
                throw UnsupportedOperationException(blocks.javaClass.name)
            }
        }
    }

    private fun Gui.setStoneItem() {
        val blocks = mine.blocks
        val count = if (blocks is FractionalBlockMask) {
            blocks.count(Material.STONE.toBlockData().toBlockMask()).coerceAtLeast(1)
        } else TODO()
        setBlockItem(11, count, Material.STONE)
    }

    private fun Gui.setBlockItem(slot: Int, amount: Int, item: Material) {
        items[slot] = buildItem(item).setName("$amount").build().toGUIItem {

        }
    }
}
