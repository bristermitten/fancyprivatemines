package me.bristermitten.fancyprivatemines.block.requirement

import me.bristermitten.fancyprivatemines.block.MineBlock
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import me.bristermitten.fancyprivatemines.block.requirement.Requirement
import net.milkbowl.vault.economy.Economy
import org.bukkit.entity.Player

class MoneyRequirement(private val economy: Economy) : Requirement {
    override val type: String = "MONEY"

    override fun meets(player: Player, mineBlock: MineBlock, privateMine: PrivateMine, data: String?): Boolean {
        requireNotNull(data) { "MONEY must have an amount" }
        val requirement = requireNotNull(data.toDouble()) { "Invalid number for MONEY $data" }


        return economy.getBalance(player) >= requirement
    }

    override fun format(data: String?): String {
        return "$$data"
    }

}
