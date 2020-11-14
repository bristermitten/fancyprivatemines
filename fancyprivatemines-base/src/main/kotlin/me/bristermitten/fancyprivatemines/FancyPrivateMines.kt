package me.bristermitten.fancyprivatemines

import me.bristermitten.fancyprivatemines.command.FancyPrivateMinesCommand
import me.bristermitten.fancyprivatemines.component.blocks.BlockSettingComponent
import me.bristermitten.fancyprivatemines.config.PrivateMinesConfig
import me.bristermitten.fancyprivatemines.config.PrivateMinesConfiguration
import me.bristermitten.fancyprivatemines.hook.Hook
import me.bristermitten.fancyprivatemines.lang.LangComponent
import me.bristermitten.fancyprivatemines.util.reflect.ZISScanner
import me.bristermitten.fancyprivatemines.util.reflect.filterHasNoArgConstructor
import org.bukkit.plugin.java.JavaPlugin

class FancyPrivateMines : JavaPlugin() {
    val configuration = PrivateMinesConfiguration(this)
    lateinit var pmConfig: PrivateMinesConfig
        private set

    internal val blockSettingComponent = BlockSettingComponent()
    val langComponent = LangComponent()

    override fun onEnable() {
        loadConfig()
        loadHooks()
        loadComponents()

        loadCommands()
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
        langComponent.init(this)

        logger.info { "Components Loaded" }
    }

    private fun reloadComponents() {
        logger.info { "Reloading Components" }

        blockSettingComponent.reload(this)
        langComponent.reload(this)

        logger.info { "Components Reloaded" }
    }


    private fun unloadComponents() {
        logger.info { "Unloading Components" }

        blockSettingComponent.destroy(this)
        langComponent.destroy(this)

        logger.info { "Components Unloaded" }
    }


    private fun loadCommands() {
        getCommand("fancyprivatemines").executor = FancyPrivateMinesCommand(this)
    }

    override fun onDisable() {
        unloadComponents()
    }

    fun reloadConfigData() {
        super.reloadConfig()
        this.pmConfig.loadFrom(PrivateMinesConfig.from(config))
    }

    fun reload() {
        reloadConfigData()

        reloadComponents()
    }
}
