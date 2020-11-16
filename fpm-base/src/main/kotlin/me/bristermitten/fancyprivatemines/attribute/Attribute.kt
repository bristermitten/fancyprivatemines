package me.bristermitten.fancyprivatemines.attribute

interface Attribute<T> {
    val id: String
    
    val default: T?
        get() = null
}
