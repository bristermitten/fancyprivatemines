package me.bristermitten.fancyprivatemines.pattern

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.menu.Menu


interface BlockPattern {

    fun generate(): BlockData

    fun generateBulk(amount: Int): List<BlockData>

    fun createMenu(plugin: FancyPrivateMines) : Menu?
}
