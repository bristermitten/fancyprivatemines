package me.bristermitten.fancyprivatemines.lang

import net.md_5.bungee.api.ChatColor

class MessageData {
    val parts = mutableListOf<Message>()
}


data class Message(val color: ChatColor, val suggest: String? = null, val message: String)
