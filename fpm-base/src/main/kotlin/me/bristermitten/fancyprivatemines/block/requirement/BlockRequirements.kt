package me.bristermitten.fancyprivatemines.block.requirement

import me.bristermitten.fancyprivatemines.FancyPrivateMines

class BlockRequirements(private val plugin: FancyPrivateMines) {
    private val requirements = mutableMapOf<String, Requirement>()

    val parser = RequirementParser(this)

    fun load(requirement: Requirement) {
        if (requirements.containsKey(requirement.type)) {
            plugin.logger.warning { "Duplicate Requirement ${requirement.type}" }
        }
        requirements[requirement.type] = requirement
    }

    operator fun get(type: String) = requirements[type]

    val all get() = requirements.values.toSet()
}
