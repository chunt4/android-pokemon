package com.example.android_pokemon;

import org.json.JSONArray;
import org.json.simple.parser.*;

import java.io.FileReader;

public class CustomJSONParser {
    public static String[] parseBerries(){
    JSONArray a;
    FileReader fn = new FileReader("berrydata.json");
    Object obj = new JSONParser().parse(fn);
    a = (JSONArray)obj;
    String[] berrynames = parseBerries(a);
       for(String berry : berrynames) {
           mSearchResultsDisplay.append("\n\n" + berry);
       }
    }

}
