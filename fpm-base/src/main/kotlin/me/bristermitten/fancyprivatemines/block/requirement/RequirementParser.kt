package me.bristermitten.fancyprivatemines.block.requirement

class RequirementParser(private val blockRequirements: BlockRequirements) {
    private val regex = "([\\w]+)( .+)?".toRegex()

    fun parse(text: String): CachedRequirement {
        val match = regex.find(text)
        requireNotNull(match) { "Invalid Requirement format $text" }

        val groups = match.groupValues
        val type = groups.first()
        val req = blockRequirements[type]
        requireNotNull(req) { "Invalid Requirement Type $type" }

        return CachedRequirement(req, groups.getOrNull(1))
    }
}
