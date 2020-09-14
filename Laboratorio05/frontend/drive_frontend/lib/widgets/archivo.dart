import 'package:flutter/material.dart';

Widget archivo(BuildContext context, String name){
  return Padding(
    padding: const EdgeInsets.all(8.0),
    child: RaisedButton(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Icon(Icons.insert_drive_file),
          Text(name)
        ],

      ),
      onPressed: () {
      }
  ),
  );
}