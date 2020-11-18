package me.bristermitten.fancyprivatemines.util

import java.security.MessageDigest
import java.util.*

fun ByteArray.sha256Hash(): String {
    val hash = MessageDigest.getInstance("SHA-256").digest(this)
    return Base64.getEncoder().encode(hash).decodeToString()
}
