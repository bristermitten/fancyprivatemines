package me.bristermitten.fancyprivatemines.mine

import io.papermc.lib.PaperLib
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BasicBlockMask
import me.bristermitten.fancyprivatemines.block.toBlockData
import me.bristermitten.fancyprivatemines.data.toChunkData
import me.bristermitten.fancyprivatemines.util.VoidWorldGenerator
import me.bristermitten.fancyprivatemines.util.center
import org.bukkit.*
import org.bukkit.entity.Player
import java.io.File
import java.util.*
import java.util.concurrent.CompletableFuture

class VoidWorldMineFactory(private val plugin: FancyPrivateMines) : MineFactory() {
    private val random = SplittableRandom()

    private val world: World = Bukkit.createWorld(WorldCreator(plugin.pmConfig.mineWorld).generator(VoidWorldGenerator))

    override fun create(schematic: File, owner: Player): CompletableFuture<PrivateMine> {
        val future = CompletableFuture<PrivateMine>()
        val paster = plugin.configuration.schematicPasters.active

        findFreeLocation().thenAccept { location ->
            paster.paste(schematic, location)
            future.complete(
                    PrivateMine(owner.uniqueId, true, BasicBlockMask(mapOf(100.0 to Material.STONE.toBlockData())), 0.0, location, location, location)
            )
        }

        return future
    }

    private fun findFreeLocation(): CompletableFuture<Location> {
        //TODO config instead of magic values
        val x = random.nextInt(0, 5000)
        val z = random.nextInt(0, 5000)

        return PaperLib.getChunkAtAsync(world, x, z, true).thenCompose {
            if (plugin.storage.mines[it.toChunkData()] != null) {
                findFreeLocation()
            } else {
                CompletableFuture.completedFuture(it.center)
            }
        }
    }
}
