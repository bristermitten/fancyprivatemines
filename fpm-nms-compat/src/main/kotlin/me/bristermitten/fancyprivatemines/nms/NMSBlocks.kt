package me.bristermitten.fancyprivatemines.nms

import me.bristermitten.fancyprivatemines.block.BlockData

val BlockData.nmsCombinedId
    get() = material.id + (data.toInt() shl 12)
