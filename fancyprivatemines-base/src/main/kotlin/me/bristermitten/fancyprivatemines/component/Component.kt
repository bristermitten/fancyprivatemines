package me.bristermitten.fancyprivatemines.component

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.function.Functionality

interface Component {

    fun init(plugin: FancyPrivateMines) = Unit

    fun destroy(plugin: FancyPrivateMines) = Unit
}
