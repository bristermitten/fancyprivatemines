package me.bristermitten.fancyprivatemines.block

import org.bukkit.Material

data class BlockData(val material: Material, val data: Byte = 0)

fun Material.toBlockData() = BlockData(this)
