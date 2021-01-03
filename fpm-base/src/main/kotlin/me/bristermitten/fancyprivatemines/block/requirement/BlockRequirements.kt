package me.bristermitten.fancyprivatemines.block.requirement

import me.bristermitten.fancyprivatemines.logging.fpmLogger

class BlockRequirements {
    private val requirements = mutableMapOf<String, Requirement>()

    val parser = RequirementParser(this)

    init {
        loadStockBlockRequirements(this)
    }

    fun load(requirement: Requirement) {
        if (requirements.containsKey(requirement.type)) {
            fpmLogger.warning { "Duplicate Requirement ${requirement.type}" }
        }
        requirements[requirement.type] = requirement
    }

    operator fun get(type: String) = requirements[type]

    val all get() = requirements.values.toSet()
}
