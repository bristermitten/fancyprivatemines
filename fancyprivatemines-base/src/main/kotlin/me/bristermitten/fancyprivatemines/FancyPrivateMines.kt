package me.bristermitten.fancyprivatemines

import me.bristermitten.fancyprivatemines.component.blocks.BlockSettingComponent
import me.bristermitten.fancyprivatemines.config.PrivateMinesConfig
import me.bristermitten.fancyprivatemines.config.PrivateMinesConfiguration
import me.bristermitten.fancyprivatemines.hook.Hook
import me.bristermitten.fancyprivatemines.util.reflect.ZISScanner
import me.bristermitten.fancyprivatemines.util.reflect.filterHasNoArgConstructor
import org.bukkit.plugin.java.JavaPlugin
import kotlin.math.log

class FancyPrivateMines : JavaPlugin() {
    val configuration = PrivateMinesConfiguration(this)
    lateinit var pmConfig: PrivateMinesConfig
        private set

    private val blockSettingComponent = BlockSettingComponent()

    override fun onEnable() {
        loadConfig()
        loadHooks()
        loadComponents()
    }

    private fun loadConfig() {
        saveDefaultConfig()
        pmConfig = PrivateMinesConfig.from(config)
    }

    private fun loadHooks() {
        logger.info { "Registering Hooks" }
        val scanner = ZISScanner(javaClass)

        val hooksLoaded = scanner.provideSubTypesOf<Hook>()
                .filterHasNoArgConstructor()
                .map { it.getConstructor().newInstance() }
                .filter(Hook::canRegister)
                .onEach { it.register(this) } //FP with side effects :)
                .count()

        logger.info { "Registered $hooksLoaded Hooks!" }
    }

    private fun loadComponents() {
        logger.info { "Loading Components" }

        blockSettingComponent.init(this)

        logger.info { "Components Loaded" }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
