package me.bristermitten.fancyprivatemines.util

data class Time(val millis: Long)

fun now() = Time(System.currentTimeMillis())

fun timeSince(time: Time) = now().millis - time.millis
