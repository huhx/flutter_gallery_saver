import 'dart:async';

import 'package:flutter/services.dart';

class GallerySaver {
  static const MethodChannel _channel = MethodChannel('awesome_gallery_saver');

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
