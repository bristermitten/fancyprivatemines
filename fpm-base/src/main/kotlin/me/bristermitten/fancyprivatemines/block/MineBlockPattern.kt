package me.bristermitten.fancyprivatemines.block

import me.mattstudios.mfgui.gui.guis.Gui
import org.bukkit.entity.Player

data class MineBlockPattern(
        val mask: BlockMask,
        val gui: ((Player) -> Gui)? = null
)
