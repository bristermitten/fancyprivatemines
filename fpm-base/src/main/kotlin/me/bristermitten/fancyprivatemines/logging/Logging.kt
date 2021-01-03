package me.bristermitten.fancyprivatemines.logging

import java.util.logging.Logger


internal var fpmLogger: Logging = JDKLogging(Logger.getGlobal())
interface Logging {
    fun warning(message: () -> String)
}

class JDKLogging(val logger: Logger) : Logging{
    override fun warning(message: () -> String) {
        logger.warning(message)
    }
}
