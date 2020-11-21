package me.bristermitten.fancyprivatemines.block

import kotlinx.serialization.Serializable
import org.bukkit.Material

@Serializable data class BlockData(val material: Material, val data: Byte = 0)

fun Material.toBlockData() = BlockData(this)

fun BlockData.toBlockMask() = SimpleBlockMask(this)
