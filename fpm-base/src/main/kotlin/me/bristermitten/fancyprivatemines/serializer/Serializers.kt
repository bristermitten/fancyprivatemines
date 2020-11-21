package me.bristermitten.fancyprivatemines.serializer

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import me.bristermitten.fancyprivatemines.block.BlockMask
import me.bristermitten.fancyprivatemines.block.FractionalBlockMask
import me.bristermitten.fancyprivatemines.block.RandomBlockMask
import me.bristermitten.fancyprivatemines.block.SimpleBlockMask
import me.bristermitten.fancyprivatemines.function.Functionalities

class Serializers : Functionalities<AbstractSerializer>(CBORBasedSerializer)

internal val fpmSerializersModule = SerializersModule {
    polymorphic(BlockMask::class) {
        subclass(SimpleBlockMask::class, SimpleBlockMask.serializer())
        subclass(RandomBlockMask::class, RandomBlockMask.serializer())
        subclass(FractionalBlockMask::class, FractionalBlockMask.serializer())
    }
}
