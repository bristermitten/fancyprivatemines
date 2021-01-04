package me.bristermitten.fancyprivatemines.util

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import org.bukkit.plugin.java.JavaPlugin


internal val fpm by lazy { //Gross, but sometimes unavoidable
    JavaPlugin.getPlugin(FancyPrivateMines::class.java)
}
