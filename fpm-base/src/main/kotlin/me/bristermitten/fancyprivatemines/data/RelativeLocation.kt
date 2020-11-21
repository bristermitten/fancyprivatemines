package me.bristermitten.fancyprivatemines.data

import kotlinx.serialization.Serializable
import org.bukkit.Location

@Serializable
data class RelativeLocation(
        val dx: Double,
        val dy: Double,
        val dz: Double,
        val dYaw: Float = 0f,
        val dPitch: Float = 0f,
) {
    fun toLocation(base: ImmutableLocation): ImmutableLocation {
        return base.add(dx, dy, dz, dYaw, dPitch)
    }
}

infix fun ImmutableLocation.relativeTo(origin: ImmutableLocation): RelativeLocation {
    return RelativeLocation(
            x - origin.x,
            y - origin.y,
            z - origin.z,
            yaw - origin.yaw,
            pitch - origin.pitch,
    )
}
