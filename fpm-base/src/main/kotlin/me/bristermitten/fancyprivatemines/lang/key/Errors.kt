package me.bristermitten.fancyprivatemines.lang.key

enum class Errors(private val messageKey: String? = null, override val default: String? = null) : LangKey {

    UnknownCommand(default = "Unknown Command."),
    MustBePlayer(default = "Only players can run this command"),
    NotEnoughArgs(default = "Not enough arguments."),
    TooManyArgs(default = "Too many arguments."),
    WrongArgsLength(default = "Wrong number of arguments."),
    UnknownPrivateMine(default = "Unknown PrivateMine."),
    NoPermission
    ;

    override val key = run {
        val key = messageKey ?: name
        "Errors.$key"
    }
}
