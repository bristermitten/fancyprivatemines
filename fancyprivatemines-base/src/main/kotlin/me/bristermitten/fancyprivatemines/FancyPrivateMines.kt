package me.bristermitten.fancyprivatemines

import me.bristermitten.fancyprivatemines.config.PrivateMinesConfiguration
import me.bristermitten.fancyprivatemines.hook.Hook
import me.bristermitten.fancyprivatemines.util.reflect.ZISScanner
import org.bukkit.plugin.java.JavaPlugin

class FancyPrivateMines : JavaPlugin() {
    val configuration = PrivateMinesConfiguration(this)

    private val scanner = ZISScanner(javaClass)

    override fun onEnable() {
        loadHooks()

        configuration.blockSetting.methods.active.init()
        logger.info {
            "Using ${configuration.blockSetting.methods.active.javaClass.simpleName} for Block Setting"
        }
    }

    private fun loadHooks() {
        scanner.provideSubTypesOf<Hook>()
                .filter { it.constructors.isNotEmpty() && it.constructors.any { c -> c.parameterCount == 0 } } //Hooks must have a no-arg constructor
                .forEach {
                    val hook = it.getConstructor().newInstance()
                    if (hook.canRegister()) {
                        hook.register(this)
                    }
                }

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
