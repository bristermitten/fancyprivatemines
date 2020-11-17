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
    fun toLocation(base: Location): Location {
        return base.clone()
                .add(dx, dy, dz,)
                .apply {
                    yaw += dYaw
                    pitch += dPitch
                }
    }
}
