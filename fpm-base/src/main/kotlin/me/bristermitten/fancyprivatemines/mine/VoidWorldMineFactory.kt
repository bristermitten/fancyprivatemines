package me.bristermitten.fancyprivatemines.mine

import io.papermc.lib.PaperLib
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.block.FractionalBlockMask
import me.bristermitten.fancyprivatemines.block.toBlockData
import me.bristermitten.fancyprivatemines.block.toBlockMask
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.data.toChunkData
import me.bristermitten.fancyprivatemines.schematic.LocationAttributeValue
import me.bristermitten.fancyprivatemines.schematic.MineSchematic
import me.bristermitten.fancyprivatemines.schematic.MultipleLocationAttributeValue
import me.bristermitten.fancyprivatemines.schematic.attributes.MiningRegionScanner
import me.bristermitten.fancyprivatemines.schematic.attributes.SpawnPointScanner
import me.bristermitten.fancyprivatemines.util.VoidWorldGenerator
import me.bristermitten.fancyprivatemines.util.center
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
                val region = paster.paste(schematicFile, location)

                val miningRegionScanner = MiningRegionScanner(Material.SEA_LANTERN)
                val spawnpointScanner = SpawnPointScanner(BlockData(Material.SAND, 1))
                plugin.schematicScanner.scan(region, mineSchematic, listOf(miningRegionScanner, spawnpointScanner))

                val miningRegionPoints = (mineSchematic.attributes.data[miningRegionScanner.attributesKey] as MultipleLocationAttributeValue).value
                        .map { it.toLocation(region.origin) }

                val spawnpoint = (mineSchematic.attributes.data[spawnpointScanner.attributesKey] as LocationAttributeValue).value
                        .toLocation(region.origin)

                plugin.configuration.blockSetting.methods.active.setBlock(spawnpoint, Material.AIR.toBlockData().toBlockMask())

                val mask = FractionalBlockMask(
                        listOf(
                                Material.STONE.toBlockData().toBlockMask(),
                                Material.COAL_ORE.toBlockData().toBlockMask(),
                                Material.COAL_BLOCK.toBlockData().toBlockMask(),
                        )
                )
                plugin.configuration.blockSetting.methods.active.setBlocksBulk(miningRegionPoints[0], miningRegionPoints[1],
                        mask)

                future.complete(PrivateMine(
                        owner.uniqueId,
                        true,
                        mask,
                        0.0,
                        spawnpoint,
                        region,
                        Region(miningRegionPoints[0], miningRegionPoints[1])
                ))
            } catch (e: Exception) {
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
