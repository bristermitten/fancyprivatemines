package me.bristermitten.fancyprivatemines.component.blocks.schematic.scanner

import me.bristermitten.fancyprivatemines.data.Region

interface AreaScanner<T> {
    fun scan(region: Region, canBeAsync: Boolean): T
}
