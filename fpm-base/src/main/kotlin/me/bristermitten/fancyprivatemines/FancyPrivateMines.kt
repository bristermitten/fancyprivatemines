package me.bristermitten.fancyprivatemines

import me.bristermitten.fancyprivatemines.command.FancyPrivateMinesCommand
import me.bristermitten.fancyprivatemines.component.blocks.BlockSettingComponent
import me.bristermitten.fancyprivatemines.config.PrivateMinesConfig
import me.bristermitten.fancyprivatemines.config.PrivateMinesConfiguration
import me.bristermitten.fancyprivatemines.hook.Hook
import me.bristermitten.fancyprivatemines.lang.LangComponent
import me.bristermitten.fancyprivatemines.mine.PrivateMineStorage
import me.bristermitten.fancyprivatemines.schematic.SchematicLoader
import me.bristermitten.fancyprivatemines.schematic.SchematicScanner
import me.bristermitten.fancyprivatemines.schematic.paster.SchematicPasterComponent
import me.bristermitten.fancyprivatemines.serializer.SerializationComponent
import me.bristermitten.fancyprivatemines.util.fpmDebug
import me.bristermitten.fancyprivatemines.util.reflect.ZISScanner
import me.bristermitten.fancyprivatemines.util.reflect.filterHasNoArgConstructor
import org.bukkit.plugin.java.JavaPlugin

class FancyPrivateMines : JavaPlugin() {
    val configuration = PrivateMinesConfiguration(this)
    lateinit var pmConfig: PrivateMinesConfig
        private set

    @Suppress("MemberVisibilityCanBePrivate")
    val blockSettingComponent = BlockSettingComponent()
    val langComponent = LangComponent()
    val pastingComponent = SchematicPasterComponent()
    val serializationComponent = SerializationComponent()

    val mineStorage = PrivateMineStorage(this)

    val schematicsDir = dataFolder.resolve("schematics/")
    val schematicLoader = SchematicLoader(this)
    val schematicScanner = SchematicScanner(this, schematicLoader)

    private val minesFile = dataFolder.resolve("mines.dat")


    override fun onEnable() {
        loadConfig()
        loadHooks()
        loadComponents()
        loadCommands()
        loadMines()
        schematicsDir.mkdir()
    }

    private fun loadConfig() {
        saveDefaultConfig()
        pmConfig = PrivateMinesConfig.from(config)
    }

    private fun loadMines() {
        if (minesFile.exists()) {
            mineStorage.loadFrom(minesFile)
        }
    }

    private fun loadHooks() {
        logger.info { "Registering Hooks" }
        val scanner = ZISScanner(javaClass)

        val hooksLoaded = scanner.provideSubTypesOf<Hook>()
                .filterHasNoArgConstructor()
                .map { it.getConstructor().newInstance() }
                .filter(Hook::canRegister)
                .onEach {
                    logger.fpmDebug {
                        "Loaded hook ${it.javaClass.name}"
                    }
                    it.register(this)
                } //FP with side effects :)
                .count()

        logger.info { "Registered $hooksLoaded Hooks!" }
    }

    private fun loadComponents() {
        logger.info { "Loading Components" }

        blockSettingComponent.init(this)
        langComponent.init(this)
        pastingComponent.init(this)
        serializationComponent.init(this)

        logger.info { "Components Loaded" }
    }

    private fun reloadComponents() {
        logger.info { "Reloading Components" }

        blockSettingComponent.reload(this)
        langComponent.reload(this)
        pastingComponent.reload(this)
        serializationComponent.reload(this)

        logger.info { "Components Reloaded" }
    }


    private fun unloadComponents() {
        logger.info { "Unloading Components" }

        blockSettingComponent.destroy(this)
        langComponent.destroy(this)
        pastingComponent.destroy(this)
        serializationComponent.destroy(this)

        logger.info { "Components Unloaded" }
    }


    private fun loadCommands() {
        val cmd = FancyPrivateMinesCommand(this)
        getCommand("fancyprivatemines").executor = cmd
        getCommand("fancyprivatemines").tabCompleter = cmd
    }

    override fun onDisable() {
        saveMines()
        unloadComponents()
    }

    private fun saveMines() {
        mineStorage.saveTo(minesFile)
    }

    private fun reloadConfigData() {
        super.reloadConfig()
        this.pmConfig.loadFrom(PrivateMinesConfig.from(config))
    }

    fun reload() {
        reloadConfigData()
        reloadComponents()
    }
}
