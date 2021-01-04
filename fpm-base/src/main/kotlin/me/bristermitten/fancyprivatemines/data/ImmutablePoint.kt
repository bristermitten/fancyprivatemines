package me.bristermitten.fancyprivatemines.data

import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World

@Serializable
data class ImmutablePoint(
    val x: Int,
    val y: Int,
    val z: Int
) {
    fun toLocation(world: World) = Location(world, x.toDouble(), y.toDouble(), z.toDouble())

    fun toImmutableLocation(world: String) = ImmutableLocation(world, x.toDouble(), y.toDouble(), z.toDouble())

    operator fun plus(other: ImmutablePoint) = ImmutablePoint(
        x + other.x,
        y + other.y,
        z + other.z,
    )

    fun add(x: Int = 0, y: Int = 0, z: Int = 0) = ImmutablePoint(
        this.x + x,
        this.y + y,
        this.z + z
    )
}
