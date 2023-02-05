import 'package:flutter_test/flutter_test.dart';
import 'package:awesome_gallery_saver/awesome_gallery_saver.dart';
import 'package:awesome_gallery_saver/awesome_gallery_saver_platform_interface.dart';
import 'package:awesome_gallery_saver/awesome_gallery_saver_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockAwesomeGallerySaverPlatform
    with MockPlatformInterfaceMixin
    implements AwesomeGallerySaverPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final AwesomeGallerySaverPlatform initialPlatform = AwesomeGallerySaverPlatform.instance;

  test('$MethodChannelAwesomeGallerySaver is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelAwesomeGallerySaver>());
  });

  test('getPlatformVersion', () async {
    AwesomeGallerySaver awesomeGallerySaverPlugin = AwesomeGallerySaver();
    MockAwesomeGallerySaverPlatform fakePlatform = MockAwesomeGallerySaverPlatform();
    AwesomeGallerySaverPlatform.instance = fakePlatform;

    expect(await awesomeGallerySaverPlugin.getPlatformVersion(), '42');
  });
}
