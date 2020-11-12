package me.bristermitten.fancyprivatemines.hook

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.blocks.VanillaBlockSettingMethod

class VanillaHook : Hook {
    //We will always have vanilla functionality available
    override fun canRegister(): Boolean = true

    override fun register(plugin: FancyPrivateMines) {
        plugin.configuration.blockSetting.methods.add(VanillaBlockSettingMethod(plugin))
    }
}
