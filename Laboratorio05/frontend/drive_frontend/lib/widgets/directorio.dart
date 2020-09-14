import 'package:flutter/material.dart';

Widget directorio(BuildContext context, String name){
  return Padding(
    padding: const EdgeInsets.all(8.0),
    child: RaisedButton(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Icon(Icons.folder),
          Text(name)
        ],

      ),
      onPressed: () {

      }
  ),
  );
}