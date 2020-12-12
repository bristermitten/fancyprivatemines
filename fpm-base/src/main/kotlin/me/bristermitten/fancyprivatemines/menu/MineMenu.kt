package me.bristermitten.fancyprivatemines.menu

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.pattern.FractionalBlockPattern
import me.bristermitten.fancyprivatemines.block.toBlockData
import me.bristermitten.fancyprivatemines.block.toBlockMask
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import me.bristermitten.fancyprivatemines.util.color
import me.mattstudios.mfgui.gui.components.buildItem
import me.mattstudios.mfgui.gui.components.name
import me.mattstudios.mfgui.gui.guis.Gui
import me.mattstudios.mfgui.gui.guis.items
import me.mattstudios.mfgui.gui.guis.toGUIItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class MineMenu(val plugin: FancyPrivateMines) {
    fun open(player: Player, mine: PrivateMine) {
        fun Gui.setStoneItem() {
            val blocks = mine.blocks
            val count = if (blocks is FractionalBlockPattern) {
                blocks.count(Material.STONE.toBlockData().toBlockMask()).coerceAtLeast(1)
            } else TODO()
            setBlockItem(12, count, Material.STONE)
        }

        fun Gui.setCoalOreItem() {
            val blocks = mine.blocks
            val count = if (blocks is FractionalBlockPattern) {
                blocks.count(Material.COAL_ORE.toBlockData().toBlockMask()).coerceAtLeast(1)
            } else TODO()
            setBlockItem(13, count, Material.COAL_ORE)
        }

        fun Gui.setCoalBlockItem() {
            val blocks = mine.blocks
            val count = if (blocks is FractionalBlockPattern) {
                blocks.count(Material.COAL_BLOCK.toBlockData().toBlockMask()).coerceAtLeast(1)
            } else TODO()
            setBlockItem(14, count, Material.COAL_BLOCK)
        }

        val menu = Gui(3, "PrivateMine Management")
        menu.setDefaultClickAction {
            it.isCancelled = true
        }
        menu.filler.fill(ItemStack(Material.STAINED_GLASS_PANE, 1, 7).toGUIItem {
        })

        val increaseItem = buildItem(ItemStack(Material.STAINED_GLASS, 1, 5)) {
            name = "&aIncrease Count".color()
        }.build()

        val decreaseItem = buildItem(ItemStack(Material.STAINED_GLASS, 1, 14)) {
            name = "&cDecrease Count".color()
        }.build()

        menu.items[3] = increaseItem.toGUIItem {
            val blocks = mine.blocks
            if (blocks is FractionalBlockPattern) {
                blocks.add(Material.STONE.toBlockData().toBlockMask())
                menu.setStoneItem()
                menu.update()
                mine.fill(plugin)
            } else {
                throw UnsupportedOperationException(blocks.javaClass.name)
            }
        }
        menu.items[4] = increaseItem.toGUIItem {
            val blocks = mine.blocks
            if (blocks is FractionalBlockPattern) {
                blocks.add(Material.COAL_ORE.toBlockData().toBlockMask())
                menu.setCoalOreItem()
                menu.update()
                mine.fill(plugin)
            } else {
                throw UnsupportedOperationException(blocks.javaClass.name)
            }
        }
        menu.items[5] = increaseItem.toGUIItem {
            val blocks = mine.blocks
            if (blocks is FractionalBlockPattern) {
                blocks.add(Material.COAL_BLOCK.toBlockData().toBlockMask())
                menu.setCoalBlockItem()
                menu.update()
                mine.fill(plugin)
            } else {
                throw UnsupportedOperationException(blocks.javaClass.name)
            }
        }

        menu.setStoneItem()
        menu.setCoalOreItem()
        menu.setCoalBlockItem()

        menu.items[12 + 9] = decreaseItem.toGUIItem {
            val blocks = mine.blocks
            if (blocks is FractionalBlockPattern) {
                blocks.remove(Material.STONE.toBlockData().toBlockMask())
                menu.setStoneItem()
                menu.update()
                mine.fill(plugin)
            } else {
                throw UnsupportedOperationException(blocks.javaClass.name)
            }
        }
        menu.items[13 + 9] = decreaseItem.toGUIItem {
            val blocks = mine.blocks
            if (blocks is FractionalBlockPattern) {
                blocks.remove(Material.COAL_ORE.toBlockData().toBlockMask())
                menu.setCoalOreItem()
                menu.update()
                mine.fill(plugin)
            } else {
                throw UnsupportedOperationException(blocks.javaClass.name)
            }
        }
        menu.items[14 + 9] = decreaseItem.toGUIItem {
            val blocks = mine.blocks
            if (blocks is FractionalBlockPattern) {
                blocks.remove(Material.COAL_BLOCK.toBlockData().toBlockMask())
                menu.setCoalBlockItem()
                menu.update()
                mine.fill(plugin)
            } else {
                throw UnsupportedOperationException(blocks.javaClass.name)
            }
        }
        menu.open(player)
    }
    private fun Gui.setBlockItem(slot: Int, amount: Int, item: Material) {
        items[slot] = buildItem(item).setName("$amount").setAmount(amount).build().toGUIItem {

        }
    }
}
