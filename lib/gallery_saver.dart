import 'dart:async';

import 'package:flutter/services.dart';

class GallerySaver {
  static const MethodChannel _channel = MethodChannel('awesome_gallery_saver');

  /// save image to Gallery
  static FutureOr<dynamic> saveImage(
    Uint8List bytes, {
    int quality = 80,
    String? name,
    bool isReturnIosPath = false,
  }) async {
    return await _channel.invokeMethod('saveImage', {
      'bytes': bytes,
      'quality': quality,
      'name': name,
      'isReturnIosPath': isReturnIosPath
    });
  }

  /// save the file with the path [file] to gallery
  static Future<dynamic> saveFile(
    String file, {
    String? name,
    bool isReturnIosPath = false,
  }) async {
    return await _channel.invokeMethod('saveFile', {
      'file': file,
      'name': name,
      'isReturnIosPath': isReturnIosPath,
    });
  }
}
