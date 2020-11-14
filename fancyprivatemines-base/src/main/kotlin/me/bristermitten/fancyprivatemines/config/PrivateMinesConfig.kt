package me.bristermitten.fancyprivatemines.config

import org.bukkit.configuration.ConfigurationSection

data class PrivateMinesConfig(
        val blockSettingMethod: String,
        val defaultLanguage: String
) {

    companion object {
        fun from(section: ConfigurationSection): PrivateMinesConfig {
            val blockSettingMethod = section.getString("BlockSettingMethod", "Auto")
            val defaultLanguage = section.getString("DefaultLanguage", "en")

            return PrivateMinesConfig(blockSettingMethod, defaultLanguage)
        }
    }
}
