package me.bristermitten.fancyprivatemines.block

import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.pattern.SimpleBlockPattern
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@Serializable
data class BlockData(val material: Material, val data: Short = 0) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BlockData) return false

        if (material != other.material) return false
        if (data < 0 || other.data < 0) return true
        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        var result = material.hashCode()
        result = 31 * result + data.coerceAtLeast(0)
        return result
    }
}

fun Material.toBlockData() = BlockData(this)

fun BlockData.toBlockMask() = SimpleBlockPattern(this)

fun BlockData.toItemStack() = ItemStack(material, 1, data)

