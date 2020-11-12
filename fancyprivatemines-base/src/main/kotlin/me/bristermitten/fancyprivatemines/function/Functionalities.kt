package me.bristermitten.fancyprivatemines.function

open class Functionalities<T : Functionality>(default: T) {
    private val functionalities = mutableMapOf<String, T>()

    var active: T = default

    val all get() = functionalities.values.toSet()

    fun add(functionality: T) {
        functionalities[functionality.id] = functionality
    }

    fun setActiveTo(id: String, ifNull: (id: String) -> Unit = {}) {
        val get = get(id)
        if (get != null) {
            this.active = get
        } else {
            ifNull(id)
        }
    }

    operator fun get(id: String): T? {
        return functionalities[id]
    }

}
