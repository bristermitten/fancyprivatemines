package me.bristermitten.fancyprivatemines.hook.blocks

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.hook.Hook

class VanillaHook : Hook {
    //We will always have vanilla functionality available
    override fun canRegister(): Boolean = true

    override fun register(plugin: FancyPrivateMines) {
        plugin.configuration.blockSetting.methods.add(VanillaBlockSettingMethod(plugin))
    }
}
