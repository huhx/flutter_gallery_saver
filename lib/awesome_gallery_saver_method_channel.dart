import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'awesome_gallery_saver_platform_interface.dart';

/// An implementation of [AwesomeGallerySaverPlatform] that uses method channels.
class MethodChannelAwesomeGallerySaver extends AwesomeGallerySaverPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('awesome_gallery_saver');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
