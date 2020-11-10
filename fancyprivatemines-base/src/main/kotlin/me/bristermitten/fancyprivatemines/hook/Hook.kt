package me.bristermitten.fancyprivatemines.hook

import me.bristermitten.fancyprivatemines.FancyPrivateMines

interface Hook {
    fun load(plugin: FancyPrivateMines)
    fun unload(plugin: FancyPrivateMines)
}
