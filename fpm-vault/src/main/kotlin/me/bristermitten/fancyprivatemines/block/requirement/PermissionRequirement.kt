package me.bristermitten.fancyprivatemines.block.requirement

import me.bristermitten.fancyprivatemines.block.MineBlock
import me.bristermitten.fancyprivatemines.mine.PrivateMine
import net.milkbowl.vault.permission.Permission
import org.bukkit.entity.Player

class PermissionRequirement(private val permission: Permission) : Requirement {
    override val type: String = "PERMISSION"

    override fun meets(player: Player, mineBlock: MineBlock, privateMine: PrivateMine, data: String?): Boolean {
        requireNotNull(data) { "$type must have a permission" }

        return permission.has(player, data)
    }

    override fun format(data: String?): String {
        return "Permission: $data"
    }

}
