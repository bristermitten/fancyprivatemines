package me.bristermitten.fancyprivatemines.command

import me.bristermitten.fancyprivatemines.lang.key.Errors
import me.bristermitten.fancyprivatemines.lang.key.LangKey
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class CommandRequirementNotSatisfiedException(val langKey: LangKey, vararg val placeholders: String) : Exception(langKey.default)


@OptIn(ExperimentalContracts::class)
internal fun CommandSender.mustBePlayer() {
    contract {
        returns() implies (this@mustBePlayer is Player)
    }

    if (this !is Player) {
        throw CommandRequirementNotSatisfiedException(Errors.MustBePlayer)
    }
}

internal infix fun Array<String>.lengthMustBeAtLeast(minLength: Int) {
    if (size < minLength) {
        throw CommandRequirementNotSatisfiedException(Errors.NotEnoughArgs,
                ARGS_REQUIRED_PLACEHOLDER, minLength.toString(),
                ARGS_LEN_PLACEHOLDER, size.toString()
        )
    }
}

internal infix fun Array<String>.lengthMustBeAtMost(maxLength: Int) {
    if (size > maxLength) {
        throw CommandRequirementNotSatisfiedException(Errors.TooManyArgs,
                ARGS_REQUIRED_PLACEHOLDER, maxLength.toString(),
                ARGS_LEN_PLACEHOLDER, size.toString()
        )
    }
}

internal infix fun Array<String>.lengthMustBeExactly(length: Int) {
    if (size != length) {
        throw CommandRequirementNotSatisfiedException(Errors.WrongArgsLength,
                ARGS_REQUIRED_PLACEHOLDER, length.toString(),
                ARGS_LEN_PLACEHOLDER, size.toString()
        )
    }
}


const val ARGS_REQUIRED_PLACEHOLDER = "%args_required%"
const val ARGS_LEN_PLACEHOLDER = "%args_length%"
