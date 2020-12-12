package me.bristermitten.fancyprivatemines.pattern

import me.mattstudios.mfgui.gui.guis.Gui
import org.bukkit.entity.Player

data class MineBlockPattern(
    val pattern: BlockPattern,
    val gui: ((Player) -> Gui)? = null
)
