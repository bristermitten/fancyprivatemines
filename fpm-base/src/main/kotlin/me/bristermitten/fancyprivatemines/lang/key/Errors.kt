package me.bristermitten.fancyprivatemines.lang.key

enum class Errors(private val messageKey: String? = null, override val default: String? = null) : LangKey {

    UnknownCommand(default = "<error>Unknown Command. Type <secondary>/fpm help<error> for help"),
    NoPermission
    ;

    override val key = run {
        val key = messageKey ?: name
        "Errors.$key"
    }
}
