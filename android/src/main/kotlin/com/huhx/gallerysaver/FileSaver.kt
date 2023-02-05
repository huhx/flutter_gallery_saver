package com.huhx.gallerysaver

import android.content.Context
import android.media.MediaScannerConnection
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class FileSaver(private val context: Context) : BaseSaver(context) {
    fun save(filePath: String, name: String?): SaveResult {
        return try {
            val originalFile = File(filePath)
            val fileUri = generateUri(originalFile.extension, name)

            val outputStream = context.contentResolver?.openOutputStream(fileUri)!!
            val fileInputStream = FileInputStream(originalFile)

            val buffer = ByteArray(10240)
            var count: Int
            while (fileInputStream.read(buffer).also { count = it } > 0) {
                outputStream.write(buffer, 0, count)
            }

            outputStream.flush()
            outputStream.close()
            fileInputStream.close()

            MediaScannerConnection.scanFile(context, arrayOf(fileUri.toString()), null, null)
            SaveResult.success(fileUri.toString())
        } catch (e: IOException) {
            SaveResult.fail(e.toString())
        }
    }
}