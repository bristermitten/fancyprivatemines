package me.bristermitten.fancyprivatemines

import me.bristermitten.fancyprivatemines.block.MineBlocks
import me.bristermitten.fancyprivatemines.block.requirement.BlockRequirements
import me.bristermitten.fancyprivatemines.command.FancyPrivateMinesCommand
import me.bristermitten.fancyprivatemines.component.blocks.BlockSettingComponent
import me.bristermitten.fancyprivatemines.config.PrivateMinesConfig
import me.bristermitten.fancyprivatemines.config.PrivateMinesConfiguration
import me.bristermitten.fancyprivatemines.hook.Hook
import me.bristermitten.fancyprivatemines.lang.LangComponent
import me.bristermitten.fancyprivatemines.logging.JDKLogger
import me.bristermitten.fancyprivatemines.logging.debug
import me.bristermitten.fancyprivatemines.logging.fpmLogger
import me.bristermitten.fancyprivatemines.mine.PrivateMineStorage
import me.bristermitten.fancyprivatemines.nms.NMSCompat
import me.bristermitten.fancyprivatemines.nms.NOOPNMSCompat
import me.bristermitten.fancyprivatemines.schematic.SchematicLoader
import me.bristermitten.fancyprivatemines.schematic.SchematicScanner
import me.bristermitten.fancyprivatemines.schematic.paster.SchematicPasterComponent
import me.bristermitten.fancyprivatemines.serializer.SerializationComponent
import me.bristermitten.fancyprivatemines.util.reflect.ZISScanner
import me.bristermitten.fancyprivatemines.util.reflect.filterHasNoArgConstructor
import me.bristermitten.fancyprivatemines.util.reflect.isConcrete
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin

class FancyPrivateMines : JavaPlugin() {
    val configuration = PrivateMinesConfiguration(this)
    lateinit var pmConfig: PrivateMinesConfig
        private set

    val blockSettingComponent = BlockSettingComponent()
    val langComponent = LangComponent()
    val pastingComponent = SchematicPasterComponent()
    val serializationComponent = SerializationComponent()

    val mineStorage = PrivateMineStorage(this)

    val schematicsDir = dataFolder.resolve("schematics/")
    val schematicLoader = SchematicLoader(this)
    val schematicScanner = SchematicScanner(this, schematicLoader)

    val blocks = MineBlocks(BlockRequirements())

    var nmsCompat: NMSCompat = NOOPNMSCompat

    private val minesFile = dataFolder.resolve("mines.dat")

    override fun onEnable() {
        fpmLogger = JDKLogger(logger)
        loadConfig()
        loadHooks()
        loadComponents()
        loadCommands()
        loadMines()
        loadBlocks()
        schematicsDir.mkdir()
    }

    private fun loadConfig() {
        saveDefaultConfig()
        pmConfig = PrivateMinesConfig.from(config)
    }

    private fun loadBlocks() {
        saveResource("blocks.yml", false)
        val blocksFile = dataFolder.resolve("blocks.yml")
        val blocksConfig = YamlConfiguration.loadConfiguration(blocksFile)
        val section = blocksConfig.getConfigurationSection("Blocks")
        blocks.loadFrom(section)
    }

    private fun loadMines() {
        if (minesFile.exists()) {
            mineStorage.loadFrom(minesFile)
        }
    }

    private fun loadHooks() {
        fpmLogger.info { "Registering Hooks" }
        val scanner = ZISScanner(javaClass)

        val hooksLoaded = scanner.provideSubTypesOf<Hook>().asSequence()
            .filterHasNoArgConstructor()
            .filter { it.isConcrete }
            .mapNotNull { hook ->
                runCatching { hook.getConstructor().newInstance() }.onFailure {
                    fpmLogger.warning { "Could not load hook $hook" }
                    it.printStackTrace()
                }.getOrNull()
            }
            .filter(Hook::canRegister)
            .onEach {
                fpmLogger.debug { "Loaded hook ${it.javaClass.name}" }
                it.register(this)
            } //FP with side effects :)
            .count()

        fpmLogger.info { "Registered $hooksLoaded Hooks!" }
    }

    private fun loadComponents() {
        fpmLogger.info { "Loading Components" }

        blockSettingComponent.init(this)
        langComponent.init(this)
        pastingComponent.init(this)
        serializationComponent.init(this)

        fpmLogger.info { "Components Loaded" }
    }

    private fun reloadComponents() {
        fpmLogger.info { "Reloading Components" }

        blockSettingComponent.reload(this)
        langComponent.reload(this)
        pastingComponent.reload(this)
        serializationComponent.reload(this)

        fpmLogger.info { "Components Reloaded" }
    }


    private fun unloadComponents() {
        fpmLogger.info { "Unloading Components" }

        blockSettingComponent.destroy(this)
        langComponent.destroy(this)
        pastingComponent.destroy(this)
        serializationComponent.destroy(this)

        fpmLogger.info { "Components Unloaded" }
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
