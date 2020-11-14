package me.bristermitten.fancyprivatemines.lang

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.Component
import me.bristermitten.fancyprivatemines.lang.formatter.LanguageFormatter
import me.bristermitten.fancyprivatemines.lang.formatter.MiniMessageFormatter
import me.bristermitten.fancyprivatemines.lang.key.LangKey
import me.bristermitten.fancyprivatemines.util.getFolderInResources
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import net.kyori.adventure.text.Component as AdventureComponent

class LangComponent : Component {

    private val languages = mutableMapOf<String, Language>()
    private val miniMessageFormatter = MiniMessageFormatter()
    private val formatters = mutableListOf<LanguageFormatter>()

    private lateinit var defaultLanguage: Language
    private lateinit var audiences: BukkitAudiences

    override fun init(plugin: FancyPrivateMines) {
        this.audiences = BukkitAudiences.create(plugin)

        val langFiles = loadLangFiles(plugin)

        this.languages.putAll(langFiles.map(this::loadLang).map { it.languageName to it }.toMap())

        this.defaultLanguage = requireNotNull(languages[plugin.pmConfig.defaultLanguage]) {
            "Unknown Default Language ${plugin.pmConfig.defaultLanguage}!"
        }

    }

    fun addFormatter(formatter: LanguageFormatter) {
        formatters += formatter
    }

    private fun loadLangFiles(plugin: FancyPrivateMines): List<File> {
        val langFiles = getFolderInResources("lang")
        if (langFiles.isEmpty()) {
            plugin.logger.warning {
                "No Lang directory in Jar - Messages will not work!"
            }
            return emptyList()
        }

        //Copy files to plugin directory
        return langFiles.map {
            plugin.dataFolder.resolve("lang").mkdir()
            val targetFile = plugin.dataFolder.resolve(it)

            if (targetFile.exists()) {
                return@map targetFile
            }

            val data = plugin.javaClass.classLoader.getResourceAsStream(it)
            requireNotNull(data) { "Jar did not contain file $it" }

            data.copyTo(targetFile.outputStream())

            targetFile
        }
    }

    override fun destroy(plugin: FancyPrivateMines) {
        languages.clear()
    }

    private fun loadLang(file: File): Language {
        val langName = file.name.removePrefix("lang_").removeSuffix(".yml")

        val config = YamlConfiguration.loadConfiguration(file)

        return Language(langName, config)
    }


    fun getMessage(key: LangKey, sender: CommandSender): AdventureComponent {
        val player = sender as? Player
        val language = languages[player?.languageSettings?.lang ?: defaultLanguage] ?: languages[defaultLanguage]

        requireNotNull(language) {
            "No applicable language files!"
        }

        val message = formatters.fold(language[key]) { msg, f ->
            f.format(msg, sender)
        }

        return miniMessageFormatter.format(message, player)
    }

    fun message(receiver: CommandSender, key: LangKey) {

        val message = getMessage(key, receiver)

        val player = receiver as? Player

        val audience = player?.let { audiences.player(it) } ?: audiences.sender(receiver)
        audience.sendMessage(message)
    }
}
