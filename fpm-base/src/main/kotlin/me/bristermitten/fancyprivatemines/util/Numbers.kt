package me.bristermitten.fancyprivatemines.util

fun pack(a: Int, b: Int): Int {
    return a and 0xFFFF shl 16 or (b and 0xFFFF)
}

fun unpack(packed: Int): Pair<Int, Int> {
    var val1 = packed shr 16 and 0xFFFF
    // restore sign
    if (val1 and 0x8000 != 0) val1 = val1 or -0x10000
    var val2 = packed and 0xFFFF
    // restore sign
    if (val2 and 0x8000 != 0) val2 = val2 or -0x10000
    return val1 to val2
}
