@file:JvmName("Utils")
package ga.overfullstack.utils

import java.io.File

fun readFileFromTestResource(fileRelativePath: String): String =
  File("src/test/resources/$fileRelativePath").readText()
