package me.bristermitten.fancyprivatemines.lang.formatter

import org.bukkit.command.CommandSender

class PlaceholderFormatter(
        vararg placeholders: String
) : LanguageFormatter {
    val placeholders = run {
        val map = mutableMapOf<String, String>()
        require(placeholders.size % 2 == 0) {
            "Placeholders array must be a multiple of 2"
        }
        var i = 0
        while (i < placeholders.size - 1) {
            map[placeholders[i]] = placeholders[i + 1]
            i += 2
        }

        map
    }

    override fun format(message: String, receiver: CommandSender): String {
        return placeholders.entries.fold(message) { acc, entry ->
            acc.replace(entry.key, entry.value)
        }
    }
}
