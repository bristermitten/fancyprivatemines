package me.bristermitten.fancyprivatemines.block

import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.pattern.SimpleBlockPattern
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@Serializable data class BlockData(val material: Material, val data: Short = 0)

fun Material.toBlockData() = BlockData(this)

fun BlockData.toBlockMask() = SimpleBlockPattern(this)

fun BlockData.toItemStack() = ItemStack(material, 1, data)
