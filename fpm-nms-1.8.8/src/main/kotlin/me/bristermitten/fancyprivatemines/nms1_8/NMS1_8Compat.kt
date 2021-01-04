package me.bristermitten.fancyprivatemines.nms1_8

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.nms.NMSCompat

class NMS1_8Compat : NMSCompat() {
    override val blockPreviewService = NMS1_8BlockPreviewService()

    override fun canRegister(): Boolean {
        return bukkitVersion.contains("v1_8")
    }

    override fun register(plugin: FancyPrivateMines) {
        plugin.nmsCompat = this
    }

}
