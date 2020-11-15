package me.bristermitten.fancyprivatemines.util.reflect


fun <T> Set<Class<out T>>.filterHasNoArgConstructor() = filter { it.constructors.isNotEmpty() && it.constructors.any { c -> c.parameterCount == 0 } } //Hooks must have a no-arg constructor
