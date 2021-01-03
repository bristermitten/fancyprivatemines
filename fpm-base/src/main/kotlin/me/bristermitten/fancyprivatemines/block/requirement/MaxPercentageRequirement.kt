package me.bristermitten.fancyprivatemines.block.requirement

import me.bristermitten.fancyprivatemines.block.MineBlock
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import org.bukkit.entity.Player

object MaxPercentageRequirement : Requirement {
    override val type: String = "MAX_PERCENTAGE"

    override fun meets(player: Player, mineBlock: MineBlock, privateMine: PrivateMine, data: String?): Boolean {
        requireNotNull(data) { "$type must have an amount" }
        val requirement = requireNotNull(data.toDouble()) { "Invalid number for $type $data" }

        val matching = privateMine.miningRegion.count { it.toLocation().block.type == mineBlock.block.material }
        return (100 * matching / privateMine.miningRegion.points.size) < requirement
    }

    override fun format(data: String?): String {
        return "Maximum of $data% in the mine"
    }

}
