import 'package:http_parser/http_parser.dart';
import 'package:http/http.dart' as http;
/*
class SendService{

  Future<String> makeRequest() async {
    var url = Uri.parse(
        "http://192.168.1.131/upload");
    var request = new http.MultipartRequest("POST", url);
    request.files.add(await http.MultipartFile.fromBytes('file', _selectedFile,
        contentType: new MediaType('application', 'octet-stream'),
        filename: "file_up"));

    request.send().then((response) {
      print("test");
      print(response.statusCode);
      if (response.statusCode == 200) print("Uploaded!");
    });

  }
}
*/