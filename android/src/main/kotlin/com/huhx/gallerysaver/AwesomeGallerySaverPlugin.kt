package com.huhx.gallerysaver

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.webkit.MimeTypeMap
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class AwesomeGallerySaverPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel
    private lateinit var context: Context

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "saveImage" -> {
                val bytes = call.argument<ByteArray>("bytes") ?: return
                val quality = call.argument<Int>("quality") ?: return
                val name = call.argument<String>("name")

                val saveResult = ImageSaver(context).save(BitmapFactory.decodeByteArray(bytes, 0, bytes.size), quality, name)

                result.success(saveResult)
            }
            "saveFile" -> {
                val path = call.argument<String>("file") ?: return
                val name = call.argument<String>("name")

                val saveResult = saveFile(path, name)

                result.success(saveResult)
            }
            else -> result.notImplemented()
        }
    }

    private fun saveFile(filePath: String, name: String?): SaveResult {
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

    private fun getMIMEType(extension: String): String? {
        return if (extension.isEmpty()) {
            null
        } else {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.lowercase(Locale.ROOT))
        }
    }

    private fun generateUri(extension: String = "", name: String? = null): Uri {
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

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        this.context = flutterPluginBinding.applicationContext
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "awesome_gallery_saver")
        channel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
