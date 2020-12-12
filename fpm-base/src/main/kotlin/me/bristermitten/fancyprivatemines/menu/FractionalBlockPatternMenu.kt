package me.bristermitten.fancyprivatemines.menu

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.toBlockData
import me.bristermitten.fancyprivatemines.block.toBlockMask
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import me.bristermitten.fancyprivatemines.pattern.FractionalBlockPattern
import me.bristermitten.fancyprivatemines.util.color
import me.mattstudios.mfgui.gui.components.buildItem
import me.mattstudios.mfgui.gui.components.name
import me.mattstudios.mfgui.gui.guis.Gui
import me.mattstudios.mfgui.gui.guis.items
import me.mattstudios.mfgui.gui.guis.toGUIItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class FractionalBlockPatternMenu(private val plugin: FancyPrivateMines) : Menu {
    override fun open(player: Player, mine: PrivateMine) {
        val pattern = mine.pattern
        require(pattern is FractionalBlockPattern) { "Pattern must be FractionalBlockPattern" }

        val menu = Gui(3, "PrivateMine Management")
        menu.setDefaultClickAction {
            it.isCancelled = true
        }

        menu.filler.fill(ItemStack(Material.STAINED_GLASS_PANE, 1, 7).toGUIItem {})

        val increaseItem = buildItem(ItemStack(Material.STAINED_GLASS, 1, 5)) {
            name = "&aIncrease Count".color()
        }.build()

        val decreaseItem = buildItem(ItemStack(Material.STAINED_GLASS, 1, 14)) {
            name = "&cDecrease Count".color()
        }.build()

        val base = 9
        pattern.blockParts.groupingBy{it}.eachCount().entries.forEachIndexed { index, patterns ->
            val block = patterns.key
            menu.items[index + base] = buildItem(block).setName("$amount").setAmount(amount).build().toGUIItem {}
        }
        menu.items[3] = increaseItem.toGUIItem {
                blocks.add(Material.STONE.toBlockData().toBlockMask())
                menu.setStoneItem()
                menu.update()
                mine.fill(plugin)
        }
        menu.items[4] = increaseItem.toGUIItem {
            val blocks = mine.pattern
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
            val blocks = mine.pattern
            if (blocks is FractionalBlockPattern) {
                blocks.add(Material.COAL_BLOCK.toBlockData().toBlockMask())
                menu.setCoalBlockItem()
                menu.update()
                mine.fill(plugin)
            } else {
                throw UnsupportedOperationException(blocks.javaClass.name)
            }
        }


        menu.items[12 + 9] = decreaseItem.toGUIItem {
            val blocks = mine.pattern
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
            val blocks = mine.pattern
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
            val blocks = mine.pattern
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
        items[slot] = buildItem(item).setName("$amount").setAmount(amount).build().toGUIItem {}
    }
}
