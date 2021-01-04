package me.bristermitten.fancyprivatemines.nms

import me.bristermitten.fancyprivatemines.hook.Hook
import org.bukkit.Bukkit

abstract class NMSCompat : Hook {
    abstract val blockPreviewService: BlockPreviewService

    protected val bukkitVersion
        get() = Bukkit.getServer().javaClass.`package`.name.split(".")[3]
}
