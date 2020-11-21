package me.bristermitten.fancyprivatemines.config

import org.bukkit.configuration.ConfigurationSection

data class PrivateMinesConfig(
        var blockSettingMethod: String,
        var defaultLanguage: String,
        var debug: Boolean,
        var mineWorld: String,
        var dataFormat: String
) {

    fun loadFrom(other: PrivateMinesConfig) {
        this.blockSettingMethod = other.blockSettingMethod
        this.defaultLanguage = other.defaultLanguage
        this.debug = other.debug
        this.mineWorld = other.mineWorld
        this.dataFormat = other.dataFormat
    }

    companion object {
        fun from(section: ConfigurationSection): PrivateMinesConfig {
            val blockSettingMethod = section.getString("BlockSettingMethod", "Auto")
            val defaultLanguage = section.getString("DefaultLanguage", "en")
            val debug = section.getBoolean("Debug")
            val mineWorld = section.getString("MineWorld", "PrivateMines")
            val dataFormat = section.getString("DataFormat", "CBOR")

            return PrivateMinesConfig(blockSettingMethod, defaultLanguage, debug, mineWorld, dataFormat)
        }
    }
}
