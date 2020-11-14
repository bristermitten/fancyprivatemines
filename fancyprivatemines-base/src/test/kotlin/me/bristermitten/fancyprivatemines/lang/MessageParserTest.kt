package me.bristermitten.fancyprivatemines.lang

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MessageParserTest {
    private val parser = MessageParser(
            mapOf("2" to "&2", "1" to "&1"),
            "&r"
    )

    @Test
    fun parse() {
        val output = parser.parse("Test [2]Tags[/2]")

        output shouldBe "&rTest &2Tags&r"
    }

    @Test
    fun validateNesting() {
        val output = parser.parse("Test [2]Tags [1]Nested[/1][/2]")

        output shouldBe "&rTest &2Tags &1Nested&2&r"
    }


    @Test
    fun validateIncorrectParsing() {
        shouldThrow<IllegalArgumentException> {
            parser.parse("Unclosed [2]tag")
        }
    }

    @Test
    fun validateEscaping() {
        parser.parse("Escaped \\[tag]") shouldBe "&rEscaped \\[tag]"
    }
}
