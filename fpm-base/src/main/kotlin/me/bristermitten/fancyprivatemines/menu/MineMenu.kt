package me.bristermitten.fancyprivatemines.menu

import me.mattstudios.mfgui.gui.components.buildItem
import me.mattstudios.mfgui.gui.guis.Gui
import me.mattstudios.mfgui.gui.guis.GuiItem
import me.mattstudios.mfgui.gui.guis.toGUIItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class MineMenu(val player: Player) {
    fun open() {
        val menu = Gui(3, "PrivateMine Management")
        menu.filler.fill(ItemStack( Material.STAINED_GLASS_PANE, 7).toGUIItem {

        })
    }
}
