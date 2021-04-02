package me.bristermitten.fancyprivatemines.block.requirement

class RequirementParser(private val blockRequirements: BlockRequirements) {
    private val regex = "(\\[[\\w]+]) (.+)?".toRegex()

    fun parse(text: String): CachedRequirement {
        val match = regex.matchEntire(text)
        requireNotNull(match) { "Invalid Requirement format $text" }

        val groups = match.groupValues.drop(1) //Drop the entire match which is first in the list
        val type = groups.first().removeSurrounding("[", "]")
        val req = blockRequirements[type]

        requireNotNull(req) { "Invalid Requirement Type $type, are you missing a plugin?" }

        return CachedRequirement(req, groups.getOrNull(1))
    }
}
