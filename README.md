# awesome_gallery_saver

Save images or videos to the gallery.

## Usage

To use this plugin, add `awesome_gallery_saver` as a dependency in your pubspec.yaml file. For example:
```yaml
dependencies:
  awesome_gallery_saver: ^0.0.3
```

## iOS
Your project need create with swift.
Add the following keys to your Info.plist file, located in <project root>/ios/Runner/Info.plist:
 * NSPhotoLibraryAddUsageDescription - describe why your app needs permission for the photo library. This is called Privacy - Photo Library Additions Usage Description in the visual editor
 
 ##  Android
 You need to ask for storage permission to save an image to the gallery. You can handle the storage permission using [flutter_permission_handler](https://github.com/BaseflowIT/flutter-permission-handler).
 In Android version 10, Open the manifest file and add this line to your application tag
 ```
 <application android:requestLegacyExternalStorage="true" .....>
 ```

## Example
Saving an image from the internet, quality and name is option
``` dart
Future<void> _saveImage() async {
    final Response<dynamic> response = await Dio().get(
        "https://lmg.jj20.com/up/allimg/1114/040221103339/210402103339-8-1200.jpg",
        options: Options(responseType: ResponseType.bytes));
    final result = await GallerySaver.saveImage(
      Uint8List.fromList(response.data),
      quality: 60,
      name: "hello",
    );
    print(result);
}
```

Saving file(ig: images/video/gif/others) from the internet
``` dart
Future<void> _saveFile() async {
    final appDocDir = await getTemporaryDirectory();
    String savePath = appDocDir.path + "/image.jpg";
    await Dio().download("https://lmg.jj20.com/up/allimg/1114/040221103339/210402103339-8-1200.jpg", savePath);
    final result = await GallerySaver.saveFile(savePath);
    print(result);
 }
```