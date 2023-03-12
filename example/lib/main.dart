import 'dart:typed_data';

import 'package:awesome_gallery_saver/gallery_saver.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Plugin app')),
        body: Center(
          child: Column(
            children: [
              Container(
                padding: const EdgeInsets.only(top: 15),
                width: 200,
                height: 50,
                child: ElevatedButton(
                  onPressed: _saveImage,
                  child: const Text("Save Image"),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  Future<void> _saveImage() async {
    final Response<dynamic> response = await Dio().get(
      "https://lmg.jj20.com/up/allimg/1114/040221103339/210402103339-8-1200.jpg",
      options: Options(responseType: ResponseType.bytes),
    );
    final result = await GallerySaver.saveImage(
      Uint8List.fromList(response.data),
      quality: 60,
      name: "hello",
    );
    debugPrint(result.toString());
    Fluttertoast.showToast(msg: "$result");
  }
}
