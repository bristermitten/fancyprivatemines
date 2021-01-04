package me.bristermitten.fancyprivatemines.nms1_8

import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.nms.BlockPreviewService
import me.bristermitten.fancyprivatemines.nms.nmsCombinedId
import net.minecraft.server.v1_8_R3.Block
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange
import org.bukkit.entity.Player

class NMS1_8BlockPreviewService : BlockPreviewService {

    override fun setBlock(player: Player, location: ImmutableLocation, block: BlockData) {
        val packet = PacketPlayOutBlockChange(location.bukkitWorld.nmsWorld, location.nmsPosition)
        val data = Block.getByCombinedId(block.nmsCombinedId)
        packet.block = data

        player.nms.playerConnection.sendPacket(packet)
    }

    override fun setChunk(player: Player, chunkX: Int, chunkZ: Int) {
        TODO("Not yet implemented")
    }
}
