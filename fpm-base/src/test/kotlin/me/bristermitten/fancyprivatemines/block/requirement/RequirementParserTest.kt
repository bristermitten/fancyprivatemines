package me.bristermitten.fancyprivatemines.block.requirement

import org.junit.Before
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll

class RequirementParserTest {

    val requirements = BlockRequirements()
    val parser = RequirementParser(requirements)

    init {
        requirements.load(NOOPRequirement("MONEY"))
    }
    @Test
    fun parse() {
        val tag = "[MONEY] 50"
        val res = parser.parse(tag)
        assertEquals(50, res.data?.toIntOrNull())
        assertEquals("MONEY", res.requirement.type)
    }
}
