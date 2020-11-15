package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.Vector
import org.bukkit.Location
import org.bukkit.World


fun Location.toWorldEditVector(): Vector {
    return Vector(blockX, blockY, blockZ)
}

fun Vector.toLocation(world: World) : Location {
    return Location(world, x, y, z)
}
