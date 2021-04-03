package me.bristermitten.fancyprivatemines.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

fun Plugin.dispatcher(async: Boolean = false) = if (async) AsyncBukkitDispatcher(this) else SyncBukkitDispatcher(this)

sealed class BukkitDispatcher(val plugin: Plugin) : CoroutineDispatcher()

private class SyncBukkitDispatcher(plugin: Plugin) : BukkitDispatcher(plugin) {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        Bukkit.getScheduler().runTask(plugin, block)
    }
}

private class AsyncBukkitDispatcher(plugin: Plugin) : BukkitDispatcher(plugin) {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, block)
    }
}
