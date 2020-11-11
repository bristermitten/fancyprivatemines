package me.bristermitten.fancyprivatemines.hook.fawe

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.hook.Hook
import org.bukkit.Bukkit

class FAWEHook : Hook {
    override fun canRegister(): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled("FastAsyncWorldEdit")
    }

    override fun register(plugin: FancyPrivateMines) {
        val method = FAWEBlockSettingMethod(plugin)
        plugin.configuration.blockSetting.methods.add(method)
    }
}
