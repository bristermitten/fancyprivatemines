package me.bristermitten.fancyprivatemines.hook.autosell

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.tax.WrappedSellEvent
import me.bristermitten.fancyprivatemines.hook.Hook
import me.bristermitten.fancyprivatemines.lang.formatter.PAPIFormatter
import me.clip.autosell.events.AutoSellEvent
import me.clip.autosell.events.SellAllEvent
import me.clip.autosell.events.SignSellEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class AutoSellHook : Hook, Listener {
    override fun canRegister(): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled("AutoSell")
    }

    override fun register(plugin: FancyPrivateMines) {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    override fun unregister(plugin: FancyPrivateMines) {
        AutoSellEvent.getHandlerList().unregister(this)
    }

    @EventHandler
    fun AutoSellEvent.onAutoSell() {
        val wrappedEvent = WrappedSellEvent(player, listOf(itemStackSold), price, this)
        Bukkit.getPluginManager().callEvent(wrappedEvent)

        //For example: sell 1 block for 50.0, tax brings it down to 47.5
        val newAmount = wrappedEvent.amount
        val change = newAmount / price
        this.multiplier = change
    }

    @EventHandler
    fun SellAllEvent.onSellAll() {
        val wrappedEvent = WrappedSellEvent(player, this.itemsSold.toList(), this.totalCost, this)
        Bukkit.getPluginManager().callEvent(wrappedEvent)

        val newAmount = wrappedEvent.amount
        this.totalCost = newAmount
    }

    @EventHandler
    fun SignSellEvent.onSignSell() {
        val wrappedEvent = WrappedSellEvent(player, this.itemsSold.toList(), this.totalCost, this)
        Bukkit.getPluginManager().callEvent(wrappedEvent)

        val newAmount = wrappedEvent.amount
        this.totalCost = newAmount
    }
}
