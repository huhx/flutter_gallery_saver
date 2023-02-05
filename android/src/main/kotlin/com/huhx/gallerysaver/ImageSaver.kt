package com.huhx.gallerysaver

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import java.io.IOException

class ImageSaver(private val context: Context) : BaseSaver(context) {

    fun save(bytes: ByteArray, quality: Int, name: String?): SaveResult {
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        val fileUri = generateUri("jpg", name = name)
        return try {
            val fos = context.contentResolver?.openOutputStream(fileUri)!!
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)
            fos.flush()
            fos.close()
            MediaScannerConnection.scanFile(context, arrayOf(fileUri.toString()), null, null)
            bitmap.recycle()
            SaveResult.success(fileUri.toString())
        } catch (e: IOException) {
            SaveResult.fail(e.toString())
        }
    }
}