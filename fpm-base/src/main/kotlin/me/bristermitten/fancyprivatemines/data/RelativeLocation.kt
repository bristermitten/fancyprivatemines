package me.bristermitten.fancyprivatemines.data

data class RelativeLocation(
        val dx: Double,
        val dy: Double,
        val dz: Double,
        val dYaw: Float = 0f,
        val dPitch: Float = 0f,
) {
}
