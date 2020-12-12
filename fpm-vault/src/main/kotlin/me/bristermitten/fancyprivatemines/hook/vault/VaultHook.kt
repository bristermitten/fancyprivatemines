package me.bristermitten.fancyprivatemines.hook.vault

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.hook.Hook
import me.bristermitten.fancyprivatemines.hook.vault.requirement.MoneyRequirement
import me.bristermitten.fancyprivatemines.block.requirement.PermissionRequirement
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit


class VaultHook : Hook {
    override fun canRegister(): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled("Vault")
    }

    override fun register(plugin: FancyPrivateMines) {
        val economy = setupEconomy()
        if (economy == null) {
            plugin.logger.warning { "Could not setup Vault Economy - do you have an Economy plugin?" }
        } else {
            MoneyRequirement(economy) //TODO register
        }

        val permission = setupPermissions()
        if (permission == null) {
            plugin.logger.warning { "Could not setup Vault Permissions - do you have an Permissions plugin?" }
        } else {
            PermissionRequirement(permission)
        }
    }

    private fun setupEconomy(): Economy? {
        val rsp = Bukkit.getServicesManager().getRegistration(Economy::class.java)
        return rsp?.provider
    }

    private fun setupPermissions(): Permission? {
        val rsp = Bukkit.getServicesManager().getRegistration(Permission::class.java)
        return rsp.provider
    }

}
