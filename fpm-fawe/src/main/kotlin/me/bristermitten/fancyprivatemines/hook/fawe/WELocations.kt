package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.Vector
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import org.bukkit.Location
import org.bukkit.World


fun Location.toWorldEditVector(): Vector {
    return Vector(blockX, blockY, blockZ)
}
fun ImmutableLocation.toWorldEditVector(): Vector {
    return Vector(x, y, z)
}

fun Vector.toLocation(world: String) : ImmutableLocation {
    return ImmutableLocation(world, x, y, z)
}
