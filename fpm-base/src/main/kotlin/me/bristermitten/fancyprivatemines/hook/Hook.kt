package me.bristermitten.fancyprivatemines.hook

import me.bristermitten.fancyprivatemines.FancyPrivateMines

interface Hook {
    fun canRegister() : Boolean

    fun register(plugin: FancyPrivateMines)

    fun unregister(plugin: FancyPrivateMines) = Unit
}
