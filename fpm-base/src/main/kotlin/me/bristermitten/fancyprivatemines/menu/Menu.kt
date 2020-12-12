package me.bristermitten.fancyprivatemines.menu

import me.bristermitten.fancyprivatemines.mine.PrivateMine
import org.bukkit.entity.Player

interface Menu {
    fun open(player: Player, privateMine: PrivateMine)
}
