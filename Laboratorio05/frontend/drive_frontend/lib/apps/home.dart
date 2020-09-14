import 'dart:typed_data';
import 'dart:convert' as convert;
import 'package:flutter/material.dart';
import 'dart:html' as html;
import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
final _formKey = GlobalKey<FormState>();
List<int> _selectedFile;
Uint8List _bytesData;
String _name_folder = '';
String _filename;
String _path_sep = '';
//Se usa para las consultas
String _path = '';
var _content;


@override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: Text(widget.title),
        ),
        body: SingleChildScrollView(
            child: Column(
              children: <Widget>[
                  MaterialButton(
                        color: Colors.blue,
                        elevation: 8,
                        highlightElevation: 2,
                        shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(4)),
                        textColor: Colors.white,
                        child: Text('Subir Archivo'),
                        onPressed: () {
                          webFilePicked();
                        },
                        
                  ),
                  Divider(
                        color: Colors.teal,
                      ),
                  RaisedButton(
                        color: Colors.purple,
                        elevation: 8.0,
                        textColor: Colors.white,
                        onPressed: () {
                          //realizar envio al servidor
                          uploadFile(_path);
                        },
                        child: Text('Enviar archivo al servidor'),
                  ),
                  Form(
                    key: _formKey,
                    child: Column(
                      //crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                      SizedBox(
                        height: 50
                      ),
                      Container(
                        width: 300,
                        alignment: Alignment.topCenter,
                        child: TextFormField(
                          
                          decoration: InputDecoration(
                            hintText: "Nombre Carpeta",
                            contentPadding: EdgeInsets.all(15.0),
                            border: InputBorder.none,
                            filled: true,
                            fillColor: Colors.grey[200],
                          ),
                          validator: (String value) {
                            if (value.isEmpty) {
                              return ('Ingresa el nombre de la carpeta');
                            } else if(value.length < 3){
                              return ('Debe tener 3 caracteres o mas');

                            }
                            return null;
                          },
                          onSaved: (String value) {
                            _name_folder = value;
                          },
                        ),
                      ),
                      RaisedButton(
                        color: Colors.green,
                        elevation: 8.0,
                        textColor: Colors.white,
                        onPressed: () {
                          if (_formKey.currentState.validate()) {
                            _formKey.currentState.save();
                            createDir(_path, _name_folder);
                            
                          }
                        },
                        child: Text('Crear carpeta'),
                      ),
                    ],) 
                    ),
                  
                  FutureBuilder<dynamic>(
                    future: getContent(_path),
                    builder: (context, snapshot){
                      if (snapshot.hasData){
                        return Container(
                                  child: ListView(
                                    shrinkWrap: true,
                                    children: <Widget>[
                                    Padding(
                                        padding: const EdgeInsets.all(8.0),
                                        child: RaisedButton(
                                          child: Column(
                                            mainAxisSize: MainAxisSize.min,
                                            children: <Widget>[
                                              Icon(Icons.arrow_back),
                                              Text("Atrás")
                                            ],

                                          ),
                                          onPressed: () {
                                            setState(() {
                                              //delimitar path
                                              if((_content["path"] == '\\\\') || (_content["path"] == '/')){
                                                _path = '';
                                              }else{
                                                String path_replace = _path.replaceAll(_path_sep, '-');
                                                List<String> path_div= path_replace.split('-');
                                                path_div.removeLast();
                                                _path = path_div.join('-');

                                              }

                                            });
                                          }
                                      ),
                                      ),
                                        Container(
                                          child: GridView.builder(
                                          gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                                                  crossAxisCount: (MediaQuery.of(context).orientation == Orientation.portrait) ? 4 : 5),
                                   
                                            shrinkWrap: true,
                                            itemCount: _content["content"]["directories"].length,
                                            // Provide a builder function. This is where the magic happens.
                                            // Convert each item into a widget based on the type of item it is.
                                            itemBuilder: (context, index) {
                                              final item = _content["content"]["directories"][index];
                                              return directorio(context, item);
                                            },
                                          ),
                                        ),
                                        Container(
                                          child: GridView.builder(
                                            scrollDirection: Axis.vertical,                                          
                                            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                                                  crossAxisCount: (MediaQuery.of(context).orientation == Orientation.portrait) ? 4 : 5),
                                            shrinkWrap: true,
                                            itemCount: _content["content"]["files"].length,
                                            // Provide a builder function. This is where the magic happens.
                                            // Convert each item into a widget based on the type of item it is.
                                            itemBuilder: (context, index) {
                                              final item = _content["content"]["files"][index];
                                              return archivo(context, item);
                                            },
                                          ),
                                        )
                                    ]
                                  )
                                );
                      } else if (snapshot.hasError){
                        return Text(snapshot.error);
                      }
                      return CircularProgressIndicator();

                    }
                  
                  )
                 

              ]
          ),
        ),
      ),
    );
  }

  void _handleResult(Object result, String filename) {
    setState(() {
      _bytesData = Base64Decoder().convert(result.toString().split(",").last);
      _selectedFile = _bytesData;
      _filename = filename;
    });
  }
  
  webFilePicked() async{
    html.InputElement uploadInput = html.FileUploadInputElement();
    uploadInput.multiple = true;
    uploadInput.draggable = true;
    uploadInput.click(); 
    uploadInput.onChange.listen((e) {
      final files = uploadInput.files;
      final file = files[0];
      final reader = new html.FileReader();
      reader.onLoadEnd.listen((e) {
        _handleResult(reader.result, file.name);
      });
      reader.readAsDataUrl(file);
    });
    }

    Future<String> uploadFile(String path) async {
    var url = Uri.parse(
        "http://192.168.1.122:5000/upload/" + path);
    var request = new http.MultipartRequest("POST", url);
    request.files.add(await http.MultipartFile.fromBytes('file', _selectedFile,
        contentType: new MediaType('application', 'octet-stream'),
        filename: _filename));

    request.send().then((res) {
      if (res.statusCode == 200) {
        setState(() {
          
        });
        //print("Uploaded!");
      }else if (res.statusCode == 403) {
        //print("Error!");
      }
        
    });

  }
  
  Future<dynamic> createDir(String path, String namedir) async {
    var url = Uri.parse(
        "http://192.168.1.122:5000/newdir/" + path);
    //print(url);
    var body = json.encode({"name": namedir});
    var res = await http.post(url, body: body, headers: {"Content-Type": "application/json"});
    if (res.statusCode == 200) {
      setState(() {
        
      });
      var jsonResponse = convert.jsonDecode(res.body);
      print(jsonResponse);
    } else {
      print('Request failed: ${res.statusCode}.');
    }
  }


   Future<dynamic> getContent(String path) async {
   
    var url = Uri.parse(
        "http://192.168.1.122:5000/content/" + path);
    
    var res = await http.get(url);
    if (res.statusCode == 200) {
      var jsonResponse = convert.jsonDecode(res.body);
      _content = jsonResponse;
      if (_path == ''){
        _path_sep = _content["path"];
      }
      

      return _content;
    } else {
      print('Request failed: ${res.statusCode}.');
    }
  }

  Future<dynamic> download(String path, String name) async {
    
    
    var url = Uri.parse(
    "http://192.168.1.122:5000/download/" + path + "-" + name );
    String url2 = "http://192.168.1.122:5000/download/" + path + "-" + name ;
    //launch("data:application/octet-stream;base64,${base64Encode(url2)}");
    html.window.open(url2, name);
     /*   
    var res = await http.get(url);
    if (res.statusCode == 200) {
      //html.File file = res;

      

      return _content;
    } else {
      print('Request failed: ${res.statusCode}.');
    }*/
  }

  Widget directorio(BuildContext context, String name){
  return Padding(
    padding: const EdgeInsets.all(8.0),
    child: RaisedButton(
      child: GridView.count(
        scrollDirection: Axis.horizontal,
        crossAxisCount: 2 ,
        //mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Icon(Icons.folder),
          Text(name, textAlign: TextAlign.center,),
          PopupMenuButton<int>(
            itemBuilder: (context) => [
                PopupMenuItem(
                  value: 1,
                  child: Text(
                    "Descargar",
                    style: TextStyle(
                        color: Colors.black, 
                        //fontWeight: FontWeight.w700
                        ),
                  ),
                ),
            ],
            onSelected: (value) {
              download(_path, name);
              print("value:$value");
            },
          ),

        ],
        
      ),
      onPressed: () {
        setState(() {
          _path = _path + '-' + name;
        });
      }
  ),
  );
}

Widget archivo(BuildContext context, String name){
  return Padding(
    padding: const EdgeInsets.all(8.0),
    child: RaisedButton(
      child: GridView.count(
        scrollDirection: Axis.horizontal,
        crossAxisCount: 2 ,
        children: <Widget>[
          Icon(Icons.insert_drive_file),
          Text(name),
          PopupMenuButton<int>(
            itemBuilder: (context) => [
                PopupMenuItem(
                  value: 1,
                  child: Text(
                    "Descargar",
                    style: TextStyle(
                        color: Colors.black, 
                        //fontWeight: FontWeight.w700
                        ),
                  ),
                ),
            ],
            onSelected: (value) {
              //decargar
              download(_path, name);
              print("value:$value");
            },
          ),
            
        ],

      ),
      onPressed: () {
      }
  ),
  );
}



}


