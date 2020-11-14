package me.bristermitten.fancyprivatemines.config

import org.bukkit.configuration.ConfigurationSection

data class PrivateMinesConfig(
        var blockSettingMethod: String,
        var defaultLanguage: String
) {

    fun loadFrom(other: PrivateMinesConfig) {
        this.blockSettingMethod = other.blockSettingMethod
        this.defaultLanguage = other.defaultLanguage
    }

    companion object {
        fun from(section: ConfigurationSection): PrivateMinesConfig {
            val blockSettingMethod = section.getString("BlockSettingMethod", "Auto")
            val defaultLanguage = section.getString("DefaultLanguage", "en")

            return PrivateMinesConfig(blockSettingMethod, defaultLanguage)
        }
    }
}
