import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'awesome_gallery_saver_method_channel.dart';

abstract class AwesomeGallerySaverPlatform extends PlatformInterface {
  /// Constructs a AwesomeGallerySaverPlatform.
  AwesomeGallerySaverPlatform() : super(token: _token);

  static final Object _token = Object();

  static AwesomeGallerySaverPlatform _instance = MethodChannelAwesomeGallerySaver();

  /// The default instance of [AwesomeGallerySaverPlatform] to use.
  ///
  /// Defaults to [MethodChannelAwesomeGallerySaver].
  static AwesomeGallerySaverPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [AwesomeGallerySaverPlatform] when
  /// they register themselves.
  static set instance(AwesomeGallerySaverPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
