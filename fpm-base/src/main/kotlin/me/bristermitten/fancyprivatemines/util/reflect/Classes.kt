package me.bristermitten.fancyprivatemines.util.reflect

import java.lang.reflect.Modifier


fun <T> Sequence<Class<out T>>.filterHasNoArgConstructor() = filter {
    it.constructors.isNotEmpty() && it.constructors.any { c -> c.parameterCount == 0 }
}

val Class<*>.isConcrete
    get() = Modifier.isAbstract(modifiers).not()
