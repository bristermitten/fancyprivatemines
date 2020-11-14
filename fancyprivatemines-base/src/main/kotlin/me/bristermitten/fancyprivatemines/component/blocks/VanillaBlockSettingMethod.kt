package me.bristermitten.fancyprivatemines.component.blocks

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BlockMask
import me.bristermitten.fancyprivatemines.util.areaTo
import org.bukkit.Location
import org.fusesource.jansi.Ansi
import org.fusesource.jansi.Ansi.Color

class VanillaBlockSettingMethod(val plugin: FancyPrivateMines) : BlockSettingMethod {
    override val id: String = "Vanilla"
    override val priority: Int = -1

    override fun init() {
        plugin.logger.warning {
            Ansi.ansi().fg(Color.RED).boldOff().toString() + """
                
            == You are using the Vanilla Block Setting Method for mines. This will degrade performance and using FAWE / AWE / WE is highly recommended! ==
            >       https://www.spigotmc.org/resources/fast-async-worldedit.13932/
            >       https://www.spigotmc.org/resources/asyncworldedit.327/
            >       https://dev.bukkit.org/projects/worldedit
            """.trimIndent() + Ansi.ansi().reset()
        }
    }

    override fun setBlock(location: Location, data: BlockMask) {
        val block = location.block
        val data = data.generate()
        block.setType(data.material, false)
        block.setData(data.data, false)
    }

    override fun setBlocksBulk(pos1: Location, pos2: Location, mask: BlockMask) {
        val area = pos1 areaTo pos2
        val blocks = mask.generateBulk(area.size)

        area.forEachIndexed { index, location ->
            val block = location.block
            val data = blocks[index]
            block.setType(data.material, false)
            block.setData(data.data, false)
        }
    }
}
