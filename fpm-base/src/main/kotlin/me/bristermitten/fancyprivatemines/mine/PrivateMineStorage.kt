package me.bristermitten.fancyprivatemines.mine

import me.bristermitten.fancyprivatemines.data.ChunkData

class PrivateMineStorage {

    /*
    At the moment, a mine could either be
    1) placed in a plot
    2) summoned in a privatemines world
     */

    val mines = mapOf<ChunkData, PrivateMine>()
}
