package me.bristermitten.fancyprivatemines.serializer

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import me.bristermitten.fancyprivatemines.pattern.BlockPattern
import me.bristermitten.fancyprivatemines.pattern.FractionalBlockPattern
import me.bristermitten.fancyprivatemines.pattern.RandomBlockPattern
import me.bristermitten.fancyprivatemines.pattern.SimpleBlockPattern
import me.bristermitten.fancyprivatemines.function.Functionalities

class Serializers : Functionalities<AbstractSerializer>(CBORBasedSerializer)

internal val fpmSerializersModule = SerializersModule {
    polymorphic(BlockPattern::class) {
        subclass(SimpleBlockPattern::class, SimpleBlockPattern.serializer())
        subclass(RandomBlockPattern::class, RandomBlockPattern.serializer())
        subclass(FractionalBlockPattern::class, FractionalBlockPattern.serializer())
    }
}
