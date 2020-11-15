package me.bristermitten.fancyprivatemines.lang.key

enum class Errors(private val messageKey: String? = null, override val default: String? = null) : LangKey {

    UnknownCommand(default = "Unknown Command. Type [2 suggest: '/fpm help']/fpm help[/2] for help");

    override val key = run {
        val key = messageKey ?: name
        "Errors.$key"
    }
}
