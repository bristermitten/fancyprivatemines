package me.bristermitten.fancyprivatemines.lang

import java.util.ArrayDeque

class MessageParser(
        private val tagValues: Map<String, String>,
        private val defaultPrefix: String? = null
) {

    fun parse(input: String): String {
        val findAll = COLOUR_REGEX.findAll(input).toList()
        val split = input.split(COLOUR_REGEX)
        val iterator = findAll.iterator()

        val opened = ArrayDeque<String>()

        var message = defaultPrefix ?: ""
        for ((index, s) in split.withIndex()) {
            if (index == 0) {
                message += s
                continue
            }

            if (iterator.hasNext()) {
                val tagValue = iterator.next()
                val isClosing = tagValue.groupValues[1] == "/"
                val tag = tagValue.groupValues[2]

                if (tag.isBlank()) {
                    throw IllegalArgumentException("Empty tag")
                }

                val tagReplacement = requireNotNull(tagValues[tag]) {
                    "Unknown tag $tag"
                }

                if (isClosing) {
                    require(opened.pop() == tag) {
                        "Closed incorrect tag $tag"
                    }
                    //Closing a tag, return to the default colour
                    message += tagValues[opened.peek()] ?: defaultPrefix ?: ""
                    message += s
                    continue
                }

                message += tagReplacement
                message += s
                opened.push(tag)
            }
        }

        if (opened.isNotEmpty()) {
            throw IllegalArgumentException("Unclosed tag(s): $opened")
        }

        return message

    }

    companion object {
        private val COLOUR_REGEX = Regex("\\[(/?)(\\d)]")
    }
}
