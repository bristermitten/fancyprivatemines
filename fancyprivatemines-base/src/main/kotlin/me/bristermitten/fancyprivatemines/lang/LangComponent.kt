package me.bristermitten.fancyprivatemines.lang

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.Component
import me.bristermitten.fancyprivatemines.lang.formatter.LanguageFormatter
import me.bristermitten.fancyprivatemines.lang.formatter.MiniMessageFormatter
import me.bristermitten.fancyprivatemines.lang.key.LangKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import net.kyori.adventure.text.Component as AdventureComponent

class LangComponent : Component {

    private val languages = mutableMapOf<String, Language>()

    private lateinit var defaultLanguage: String

    private val formatters = mutableListOf<LanguageFormatter>()

    private val miniMessageFormatter = MiniMessageFormatter()

    override fun init(plugin: FancyPrivateMines) {
        this.defaultLanguage = plugin.pmConfig.defaultLanguage

        val langFiles = plugin.javaClass.getResource("lang/").toURI().run(::File).listFiles()
        if (langFiles == null) {
            plugin.logger.warning {
                "No Lang directory in jar"
            }
            return
        }

        //Copy files to plugin directory
        val langFilesInPluginDirectory = langFiles.mapNotNull {
            val targetFile = plugin.dataFolder.resolve("lang").apply { mkdir() }
                    .resolve(it.name)

            if (targetFile.exists()) {
                return@mapNotNull null
            }
            it.copyTo(targetFile)
            targetFile
        }

        this.languages.putAll(langFilesInPluginDirectory.map(this::loadLang).map { it.languageName to it }.toMap())
    }

    override fun destroy(plugin: FancyPrivateMines) {
        languages.clear()
    }

    private fun loadLang(file: File): Language {
        val langName = file.name.removePrefix("lang_").removeSuffix(".yml")

        val config = YamlConfiguration.loadConfiguration(file)

        return Language(langName, config)
    }


    fun getMessage(key: LangKey, player: Player?): AdventureComponent {
        val language = languages[player?.languageSettings?.lang ?: defaultLanguage] ?: languages[defaultLanguage]

        requireNotNull(language) {
            "No applicable language files!"
        }

        val message = formatters.fold(language[key]) { msg, f ->
            f.format(msg, player)
        }

        return miniMessageFormatter.format(message, player)
    }
}
