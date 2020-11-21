package me.bristermitten.fancyprivatemines.lang.key

enum class Commands(private val messageKey: String? = null, override val default: String? = null) : LangKey {

    HELP_HEADER(default = "<primary><bold> == FancyPrivateMines Help =="),
    HELP_COMMAND(default = "<primary>%cmd_name% - <secondary>%cmd_description%"),
    HELP_FOOTER(default = "<primary><bold> ============================"),

    RENAME_SUCCESSFUL,
    RELOAD_DONE(default = "Reloaded!")
    ;

    override val key = run {
        val key = (messageKey ?: name).split("_").joinToString(".") { it.toLowerCase().capitalize() }
        "Command.$key"
    }
}
