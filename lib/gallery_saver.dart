// You have generated a new plugin project without specifying the `--platforms`
// flag. A plugin project with no platform support was generated. To add a
// platform, run `flutter create -t plugin --platforms <platforms> .` under the
// same directory. You can also find a detailed instruction on how to add
// platforms in the `pubspec.yaml` at
// https://flutter.dev/docs/development/packages-and-plugins/developing-packages#plugin-platforms.

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
      'isReturnImagePathOfIOS': isReturnIosPath
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
      'isReturnPathOfIOS': isReturnIosPath,
    });
  }
}
