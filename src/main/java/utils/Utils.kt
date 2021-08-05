@file:JvmName("Utils")
package utils

import java.io.File

fun readFileFromMainResource(fileRelativePath: String): String = File("src/main/resources/$fileRelativePath").readText()

fun readFileFromTestResource(fileRelativePath: String): String = File("src/test/resources/$fileRelativePath").readText()
