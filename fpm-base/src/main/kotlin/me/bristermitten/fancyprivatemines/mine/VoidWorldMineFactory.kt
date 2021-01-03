package me.bristermitten.fancyprivatemines.mine

import io.papermc.lib.PaperLib
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.pattern.FractionalBlockPattern
import me.bristermitten.fancyprivatemines.block.toBlockData
import me.bristermitten.fancyprivatemines.block.toBlockMask
import me.bristermitten.fancyprivatemines.data.Region
import me.bristermitten.fancyprivatemines.data.makeRegion
import me.bristermitten.fancyprivatemines.data.toChunkData
import me.bristermitten.fancyprivatemines.schematic.MineSchematic
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

    private val world: World = Bukkit.createWorld(
            WorldCreator(plugin.pmConfig.mineWorld)
                    .generator(VoidWorldGenerator).generateStructures(false))
            .apply {
                difficulty = Difficulty.PEACEFUL
            }

    override fun create(schematicFile: File, mineSchematic: MineSchematic, owner: Player): CompletableFuture<PrivateMine> {
        val future = CompletableFuture<PrivateMine>()
        val paster = plugin.configuration.schematicPasters.active

        findFreeLocation().thenAccept { location ->
            try {
                val region = paster.paste(schematicFile, location)

                val miningRegionScanner = MiningRegionScanner(Material.POWERED_RAIL)
                val spawnPointScanner = SpawnPointScanner(BlockData(Material.WOOL, 5))
                plugin.schematicScanner.scan(region, mineSchematic, listOf(miningRegionScanner, spawnPointScanner))

                val miningRegionPoints = mineSchematic.getAttributeFor(miningRegionScanner)
                        .map { it.toLocation(region.origin) }

                val spawnPoint = mineSchematic.getAttributeFor(spawnPointScanner)
                        .toLocation(region.origin)

                plugin.configuration.blockSetting.methods.active.setBlock(spawnPoint, Material.AIR.toBlockData().toBlockMask())

                val mask = FractionalBlockPattern(
                        listOf(
                                Material.STONE.toBlockData(),
                                Material.COAL_ORE.toBlockData(),
                                Material.COAL_BLOCK.toBlockData(),
                        )
                )
                plugin.configuration.blockSetting.methods.active.setBlocksBulk(makeRegion(miningRegionPoints[0], miningRegionPoints[1]), mask)

                val mine = PrivateMine(
                        PrivateMine.nextId,
                        owner.uniqueId,
                        null,
                        true,
                        mask,
                        0.0,
                        spawnPoint,
                        region,
                        Region(miningRegionPoints[0], miningRegionPoints[1])
                )

                plugin.mineStorage.add(region.chunks, mine)
                future.complete(mine)
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

        return PaperLib.getChunkAtAsync(world, x, z, false).thenCompose {
            if (plugin.mineStorage[it.toChunkData()] != null) {
                findFreeLocation()
            } else {
                CompletableFuture.completedFuture(it.center)
            }
        }
    }
}
