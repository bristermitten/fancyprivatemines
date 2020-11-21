package me.bristermitten.fancyprivatemines.data

import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

@Serializable
data class ImmutableLocation(
        val world: String,
        val x: Double,
        val y: Double,
        val z: Double,
        val yaw: Float = 0f,
        val pitch: Float = 0f
) {
    fun toLocation() = Location(Bukkit.getWorld(world), x, y, z, yaw, pitch)

    fun add(x: Double, y: Double, z: Double, yaw: Float = 0f, pitch: Float = 0f) = copy(
            x = this.x + x,
            y = this.y + y,
            z = this.z + z,
            yaw = this.yaw + yaw,
            pitch = this.pitch + pitch
    )

    val bukkitWorld: World
        get() = Bukkit.getWorld(world)
}

fun Location.immutableCopy() = ImmutableLocation(world.name, x, y, z, yaw, pitch)
