package me.bristermitten.fancyprivatemines.function

abstract class Functionality {
    abstract val id: String
    abstract val priority: Int

    open fun init() = Unit

    open fun destroy() = Unit

    override fun toString(): String {
        return "${javaClass.simpleName}[$id]"
    }
}
