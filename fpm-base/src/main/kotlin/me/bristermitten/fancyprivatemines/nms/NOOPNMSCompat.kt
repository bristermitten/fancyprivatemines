package me.bristermitten.fancyprivatemines.nms

import me.bristermitten.fancyprivatemines.FancyPrivateMines

object NOOPNMSCompat : NMSCompat() {
    override val blockPreviewService: BlockPreviewService
        get() = noop

    override fun canRegister(): Boolean = false

    override fun register(plugin: FancyPrivateMines) = noop

    private val noop: Nothing
        get() = throw UnsupportedOperationException("Incompatible NMS version")
}
