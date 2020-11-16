package me.bristermitten.fancyprivatemines.util

import java.security.MessageDigest
import java.util.*

fun ByteArray.sha1Hash(): ByteArray {
    val hash = MessageDigest.getInstance("SHA-1").digest(this)
    return Base64.getEncoder().encode(hash)
}
