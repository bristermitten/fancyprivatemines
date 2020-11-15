package me.bristermitten.fancyprivatemines.hook.plotsquared

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.hook.Hook
import org.bukkit.Bukkit

class PlotSquaredHook : Hook {
    override fun canRegister(): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled("PlotSquared")
    }

    override fun register(plugin: FancyPrivateMines) {
        TODO()
    }
}
