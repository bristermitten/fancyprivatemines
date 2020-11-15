package me.bristermitten.fancyprivatemines.lang.formatter

import me.bristermitten.fancyprivatemines.lang.LangColour
import me.bristermitten.fancyprivatemines.lang.languageSettings
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

class MiniMessageFormatter {
    private val miniMessage = MiniMessage.get()

    fun format(message: String, player: Player?): Component {
        val settings = player?.languageSettings
        val primaryColor = settings?.colors?.get(LangColour.Primary) ?: LangColour.Primary.defaultValue
        val secondaryColor = settings?.colors?.get(LangColour.Secondary) ?: LangColour.Secondary.defaultValue
        val errorColor = settings?.colors?.get(LangColour.Error) ?: LangColour.Error.defaultValue


        return miniMessage.parse(
                message
                        .replace("primary>", primaryColor)
                        .replace("secondary>", secondaryColor)
                        .replace("error>", errorColor),
        )
    }
}
