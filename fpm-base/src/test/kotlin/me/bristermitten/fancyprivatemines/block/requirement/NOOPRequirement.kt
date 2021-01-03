package me.bristermitten.fancyprivatemines.block.requirement

import me.bristermitten.fancyprivatemines.block.MineBlock
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import org.bukkit.entity.Player

class NOOPRequirement(override val type: String) : Requirement {
    override fun meets(player: Player, mineBlock: MineBlock, privateMine: PrivateMine, data: String?): Boolean {
        return true
    }

    override fun format(data: String?): String {
        return "NOOP"
    }
}
