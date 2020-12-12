package me.bristermitten.fancyprivatemines.block.requirement

import me.bristermitten.fancyprivatemines.block.MineBlock
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import org.bukkit.entity.Player

interface Requirement {
    val type: String

    fun meets(player: Player, mineBlock: MineBlock, privateMine: PrivateMine, data: String?): Boolean

    fun format(data: String?) : String
}
