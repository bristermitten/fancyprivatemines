package me.bristermitten.fancyprivatemines.mine

import io.papermc.lib.PaperLib
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BasicBlockMask
import me.bristermitten.fancyprivatemines.block.toBlockData
import me.bristermitten.fancyprivatemines.data.toChunkData
import me.bristermitten.fancyprivatemines.schematic.MineSchematic
import me.bristermitten.fancyprivatemines.schematic.MultipleLocationAttributeValue
import me.bristermitten.fancyprivatemines.schematic.SchematicScanner
import me.bristermitten.fancyprivatemines.schematic.attributes.MiningRegionScanner
import me.bristermitten.fancyprivatemines.util.VoidWorldGenerator
import me.bristermitten.fancyprivatemines.util.center
import me.bristermitten.fancyprivatemines.util.fpmDebug
import org.bukkit.*
import org.bukkit.entity.Player
import java.io.File
import java.util.*
import java.util.concurrent.CompletableFuture

class VoidWorldMineFactory(private val plugin: FancyPrivateMines) : MineFactory() {
    private val random = SplittableRandom()

    private val world: World = Bukkit.createWorld(WorldCreator(plugin.pmConfig.mineWorld).generator(VoidWorldGenerator).generateStructures(false))
            .apply {
                difficulty = Difficulty.PEACEFUL
            }

    override fun create(schematicFile: File, mineSchematic: MineSchematic, owner: Player): CompletableFuture<PrivateMine> {
        val future = CompletableFuture<PrivateMine>()
        val paster = plugin.configuration.schematicPasters.active

        findFreeLocation().thenAccept { location ->
            try {
                plugin.logger.fpmDebug { "Found Free Location $location" }
                val region = paster.paste(schematicFile, location)
                plugin.logger.fpmDebug { "Pasted $schematicFile at $location" }

                val miningRegionScanner = MiningRegionScanner(Material.SEA_LANTERN)
                SchematicScanner().scan(region, mineSchematic, listOf(miningRegionScanner))

                plugin.logger.fpmDebug { "Scanned region" }
                val miningRegionPoints = (mineSchematic.attributes.data[miningRegionScanner.attributesKey] as MultipleLocationAttributeValue).value
                        .map { it.toLocation(region.origin) }

                plugin.configuration.blockSetting.methods.active.setBlocksBulk(miningRegionPoints[0], miningRegionPoints[1],
                        BasicBlockMask(
                                mapOf(49.0 to Material.STONE.toBlockData(), 51.0 to Material.STONE.toBlockData())
                        ))

                plugin.logger.fpmDebug { "Filled mineable area $miningRegionPoints" }

                future.complete(
                        PrivateMine(owner.uniqueId, true, BasicBlockMask(mapOf(100.0 to Material.STONE.toBlockData())), 0.0, location, region.min, region.max)
                )
            } catch (e: Throwable) {
                e.printStackTrace()
            }
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
