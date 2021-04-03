package me.bristermitten.fancyprivatemines.util

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import java.io.File
import java.util.jar.JarFile


fun getFolderInResources(folderName: String): List<String> {
    val jarFile = FancyPrivateMines::class.java.protectionDomain.codeSource.location.path.run(::File)

    if (jarFile.isFile) {
        return JarFile(jarFile).use { jar ->
            jar.entries().asSequence()
                .filter { it.name.startsWith("$folderName/") && it.name != "$folderName/" }
                .map { it.name }
                .toList()
        }
    }
    //TODO IDE
    return emptyList()
}
