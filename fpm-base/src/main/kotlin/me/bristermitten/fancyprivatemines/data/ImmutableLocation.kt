package me.bristermitten.fancyprivatemines.data

import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import kotlin.math.roundToInt

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

    fun add(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0, yaw: Float = 0f, pitch: Float = 0f) = copy(
        x = this.x + x,
        y = this.y + y,
        z = this.z + z,
        yaw = this.yaw + yaw,
        pitch = this.pitch + pitch
    )

    fun add(x: Int = 0, y: Int = 0, z: Int = 0, yaw: Float = 0f, pitch: Float = 0f) = copy(
        x = this.x + x,
        y = this.y + y,
        z = this.z + z,
        yaw = this.yaw + yaw,
        pitch = this.pitch + pitch
    )

    val bukkitWorld: World
        get() = Bukkit.getWorld(world)
}

val ImmutableLocation.point
    get() = ImmutablePoint(x.roundToInt(), y.roundToInt(), z.roundToInt())

fun Location.immutableCopy() = ImmutableLocation(world.name, x, y, z, yaw, pitch)
