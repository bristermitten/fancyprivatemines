package me.bristermitten.fancyprivatemines.hook.fawe

import com.sk89q.worldedit.Vector
import org.bukkit.Location


fun Location.toWorldEditVector(): Vector {
    return Vector(blockX, blockY, blockZ)
}
