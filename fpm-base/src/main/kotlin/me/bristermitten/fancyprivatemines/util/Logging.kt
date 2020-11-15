package me.bristermitten.fancyprivatemines.util

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

private val fpm by lazy {
    JavaPlugin.getPlugin(FancyPrivateMines::class.java)
}

fun Logger.fpmDebug(messageSupplier: () -> String) {
    if (fpm.pmConfig.debug) {
        warning {
            "[DEBUG] " + messageSupplier()
        }
    }
}
