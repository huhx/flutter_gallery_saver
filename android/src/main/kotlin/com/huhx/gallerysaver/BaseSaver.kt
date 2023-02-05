package com.huhx.gallerysaver

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.webkit.MimeTypeMap
import java.util.*

open class BaseSaver(private val context: Context) {
    protected fun generateUri(extension: String = "", name: String? = null): Uri {
        val fileName = name ?: System.currentTimeMillis().toString()
        var uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        val mimeType = getMIMEType(extension)
        if (!TextUtils.isEmpty(mimeType)) {
            values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            if (mimeType!!.startsWith("video")) {
                uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MOVIES)
            }
        }
        return context.contentResolver?.insert(uri, values)!!
    }

    private fun getMIMEType(extension: String): String? {
        return if (extension.isEmpty()) {
            null
        } else {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.lowercase(Locale.ROOT))
        }
    }
}