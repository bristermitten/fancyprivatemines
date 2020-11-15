package me.bristermitten.fancyprivatemines.component.tax

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

/**
 * Event encapsulating all other selling events from other plugins (eg AutoSellEvent, ShopGUI+ Sell events, etc)
 *
 * Note that this event should only be called when the selling action is done in a Private Mine
 */
class WrappedSellEvent(val player: Player, val selling: List<ItemStack>, var amount: Double, val source: Event) : Event() {
    companion object {
        private val handlers = HandlerList()
    }

    override fun getHandlers(): HandlerList = WrappedSellEvent.handlers
}
