package org.example

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val directoryPath = "/Users/jtepjuk/Downloads/ScreenShots"

    val directory = File(directoryPath)

    val files = directory.listFiles() ?: emptyArray()

    files.forEach { file ->
        val modifiedDate = Date(file.lastModified())
        val month = SimpleDateFormat("MM", Locale.getDefault()).format(modifiedDate)

        val monthDirectory = File("$directory/$month")

        if(!monthDirectory.exists()) {
            monthDirectory.mkdir()
        }

        file.copyTo(File(monthDirectory, file.name))
        file.delete()
    }
}