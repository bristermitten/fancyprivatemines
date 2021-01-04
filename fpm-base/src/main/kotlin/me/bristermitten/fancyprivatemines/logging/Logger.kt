package me.bristermitten.fancyprivatemines.logging

import me.bristermitten.fancyprivatemines.util.fpm
import java.util.logging.Logger as JDKLogger


internal var fpmLogger: Logger = JDKLogger(JDKLogger.getGlobal())

interface Logger {
    fun warning(message: () -> String)
    fun info(message: () -> String)
}

class JDKLogger(val logger: java.util.logging.Logger) : Logger {
    override fun warning(message: () -> String) = logger.warning(message)
    override fun info(message: () -> String) = logger.info(message)
}

internal inline fun Logger.debug(crossinline messageSupplier: () -> String) {
    if (fpm.pmConfig.debug) {
        warning {
            "[DEBUG] " + messageSupplier()
        }
    }
}
