package me.bristermitten.fancyprivatemines.nms1_8

import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import net.minecraft.server.v1_8_R3.BlockPosition
import net.minecraft.server.v1_8_R3.EntityPlayer
import net.minecraft.server.v1_8_R3.WorldServer
import org.bukkit.World
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player

val World.nmsWorld: WorldServer
    get() = (this as CraftWorld).handle

val ImmutableLocation.nmsPosition
    get() = BlockPosition(x, y, z)

val Player.nms: EntityPlayer
    get() = (this as CraftPlayer).handle
