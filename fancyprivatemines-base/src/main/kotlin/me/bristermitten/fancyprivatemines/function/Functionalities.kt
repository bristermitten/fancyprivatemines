package me.bristermitten.fancyprivatemines.function

open class Functionalities<T : Functionality>(default: T) {
    private val functionalities = mutableMapOf<String, T>()

    var active: T = default

    val all get() = functionalities.values.toSet()

    fun add(functionality: T) {
        functionalities[functionality.id] = functionality
    }

    operator fun get(id: String): T? {
        return functionalities[id]
    }

}
