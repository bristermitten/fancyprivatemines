package me.bristermitten.fancyprivatemines.util

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import java.io.File
import java.util.jar.JarFile


fun getFolderInResources(folderName: String): List<String> {
    val names = mutableListOf<String>()
    val jarFile = FancyPrivateMines::class.java.protectionDomain.codeSource.location.path.run(::File)

    if (jarFile.isFile) {
        val jar = JarFile(jarFile)

        val entries = jar.entries()

        while (entries.hasMoreElements()) {
            val nextElement = entries.nextElement()
            val name = nextElement.name

            if (name.startsWith("$folderName/") && name != "$folderName/") {
                names += name
            }
        }
        jar.close()
    }
    //TODO IDE
    return names
}
