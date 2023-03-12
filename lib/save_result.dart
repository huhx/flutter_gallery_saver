/// the result of the file saving to gallery
class SaveResult {
  final bool isSuccess;
  final String? filePah;
  final String? errorMessage;

  const SaveResult({
    required this.isSuccess,
    this.filePah,
    this.errorMessage,
  });
}
