package me.bristermitten.fancyprivatemines.component.tax

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.component.FPMComponent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class TaxationComponent : FPMComponent, Listener {
    private var globalTax = 0.0
    private var registered = false

    override fun init(plugin: FancyPrivateMines) {
        super.init(plugin)

        if (!registered) {
            Bukkit.getPluginManager().registerEvents(this, plugin)
            registered = true
        }
    }

    @EventHandler
    fun WrappedSellEvent.onSell() {
        val reduction = globalTax / 100.0

        amount -= (amount * reduction)
    }
}
