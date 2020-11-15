package me.bristermitten.fancyprivatemines.lang

import me.bristermitten.fancyprivatemines.lang.key.LangKey
import org.bukkit.configuration.ConfigurationSection

data class Language(val languageName: String, private val langFile: ConfigurationSection) {

    operator fun get(langKey: LangKey): String {
        return if (langFile.isList(langKey.key)) {
            val list = langFile.getStringList(langKey.key)
            list.joinToString(separator = "\n")
        } else {
            langFile.getString(langKey.key, langKey.default)
        } ?: "No Message Configured in ${langKey.key} for language $languageName"
    }
}
