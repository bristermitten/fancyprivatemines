package me.bristermitten.fancyprivatemines.lang.key

enum class Info(private val messageKey: String? = null, override val default: String? = null) : LangKey {

    GeneratingMine(default = "<primary>Generating a new PrivateMine..."),
    ;

    override val key = run {
        val key = messageKey ?: name
        "Errors.$key"
    }
}
