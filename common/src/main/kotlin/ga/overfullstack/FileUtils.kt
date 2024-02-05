@file:JvmName("FileUtils")

package ga.overfullstack

import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import okio.source
import java.io.File

fun bufferFileInResources(fileRelativePath: String): BufferedSource =
  FileSystem.RESOURCES.source(fileRelativePath.toPath()).buffer()

fun readFileInResourcesToString(fileRelativePath: String): String =
  bufferFileInResources(fileRelativePath).readUtf8()

fun bufferFile(file: File): BufferedSource = file.source().buffer()

fun readFileToString(file: File): String = bufferFile(file).readUtf8()
