package me.bristermitten.fancyprivatemines.util.reflect

import java.util.stream.Collectors
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

//Credit to PiggyPiglet (https://github.com/PiggyPiglet/Framework/blob/master/core/src/main/java/me/piggypiglet/framework/scanning/implementations/ZISScanner.java)
open class ZISScanner(main: Class<*>) {

    private val classes = mutableSetOf<Class<*>>()

    @Suppress("UNCHECKED_CAST")
    fun <T> provideSubTypesOf(type: Class<T>): Set<Class<out T>> {
        return classes.parallelStream()
                .filter { cls -> type.isAssignableFrom(cls) }
                .map { c -> c as Class<out T> }
                .collect(Collectors.toSet())
    }

    inline fun <reified T> provideSubTypesOf(): Set<Class<out T>> {
        return provideSubTypesOf(T::class.java)
    }

    init {
        val basePackage = main.getPackage().name.replace('.', '/')

        val loader = main.classLoader
        val src = main.protectionDomain.codeSource
        if (src != null) {
            val zip = ZipInputStream(src.location.openStream())
            while (true) {
                val entry = zip.nextEntry ?: break

                val name = entry.name

                if (name.endsWith(".class") && name.startsWith(basePackage)) {
                    try {
                        val clazz = loader.loadClass(name.replace('/', '.').replace(".class", ""))
                        classes.add(clazz)
                    } catch (e: Exception) {
                        continue
                    }
                }
            }
        }
    }
}
