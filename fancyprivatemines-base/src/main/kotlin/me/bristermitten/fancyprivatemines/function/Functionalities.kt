package me.bristermitten.fancyprivatemines.function

open class Functionalities<T : Functionality>(default: T?) {
    private val functionalities = mutableMapOf<String, T>()

    lateinit var active: T

    init {
        if (default != null) {
            active = default
            add(default)
        }
    }

    val all get() = functionalities.values.toSet()

    fun add(functionality: T) {
        functionalities[functionality.id] = functionality
    }

    fun setActiveToOrElse(id: String, ifNull: (id: String) -> Unit = {}) {
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
