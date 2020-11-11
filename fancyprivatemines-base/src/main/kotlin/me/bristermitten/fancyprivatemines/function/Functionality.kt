package me.bristermitten.fancyprivatemines.function

interface Functionality {
    val id: String
    val priority: Int

    fun init() = Unit

    fun destroy() = Unit
}
