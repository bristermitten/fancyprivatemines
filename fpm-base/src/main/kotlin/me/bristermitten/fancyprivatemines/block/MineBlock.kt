package me.bristermitten.fancyprivatemines.block

import me.bristermitten.fancyprivatemines.block.requirement.CachedRequirement
import me.bristermitten.fancyprivatemines.block.requirement.Requirement

data class MineBlock(
    val block: BlockData,
    val requirements: List<CachedRequirement> = emptyList()
)
