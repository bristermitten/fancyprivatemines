package me.bristermitten.fancyprivatemines.lang.key

enum class Errors(private val messageKey: String? = null, override val default: String? = null) : LangKey {

    UnknownCommand(default = "Unknown Command."),
    MustBePlayer(default = "Only players can run this command"),
    NotEnoughArgs("Not enough arguments."),
    TooManyArgs("Too many arguments."),
    WrongArgsLength("Wrong number of arguments."),
    NoPermission
    ;

    override val key = run {
        val key = messageKey ?: name
        "Errors.$key"
    }
}
