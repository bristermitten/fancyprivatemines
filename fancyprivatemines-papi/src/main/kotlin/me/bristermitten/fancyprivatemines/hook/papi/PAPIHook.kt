package me.bristermitten.fancyprivatemines.hook.papi

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.hook.Hook
import me.bristermitten.fancyprivatemines.lang.formatter.PAPIFormatter
import org.bukkit.Bukkit

class PAPIHook : Hook {
    override fun canRegister(): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")
    }

    override fun register(plugin: FancyPrivateMines) {
        plugin.langComponent.addFormatter(PAPIFormatter())
    }
}
