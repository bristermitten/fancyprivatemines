package me.bristermitten.fancyprivatemines.component

import me.bristermitten.fancyprivatemines.FancyPrivateMines

interface FPMComponent {

    fun init(plugin: FancyPrivateMines) = Unit

    fun destroy(plugin: FancyPrivateMines) = Unit

    fun reload(plugin: FancyPrivateMines) {
        destroy(plugin)
        init(plugin)
    }
}
