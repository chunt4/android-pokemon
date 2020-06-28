package com.example.android_pokemon;

import java.io.*;

import org.json.JSONException;
import org.json.simple.JSONArray;
//import org.json.JSONException;
import org.json.JSONObject;
//import java.lang.Iterable;
import org.json.simple.parser.JSONParser;
//import org.json.simple;

//import java.io.FileReader;

//public class CustomJSONParser {
//    public static String[] parseBerries() {
//        JSONArray a;
//        try {
//            FileReader fn = new FileReader("berrydata.json");
//            Object obj = new JSONParser().parse(fn);
//            a = (JSONArray) obj;
//            String[] berrynames = parseBerries(a);
//            for (String berry : berrynames) {
//                mSearchResultsDisplay.append("\n\n" + berry);
//            }
//        }
//    }
//}

//class Berry{
//    String name;
//
//    public String toString(){
//        String s = this.name;
//
//        return s;
//    }
//    Berry(String nameIn){
//        this.name= nameIn;
//    }
//}// end of berry class

public class CustomJSONParser {

    public static String[] parseJsonBerry(JSONArray ja) throws JSONException {
        String[] berries = new String[64];

        int i = 0;
        for(Object o : ja){
            JSONObject jo = (JSONObject)o;

            //String bName = (String)jo.getString("name");
            berries[i] = (String)jo.getString("name");
            //berries[i] = new Berry(bName);
            i++;
        }

        return berries;
    } // end of parseJsonMovies

    public static String[] BerryParse() {
        String[] berries = new String[64];
        try {
            FileReader fn = new FileReader("berrydata.json");
            Object obj = new JSONParser().parse(fn);
            JSONArray a = (JSONArray) obj;

            berries = parseJsonBerry(a);
            //for (String berry : berrynames) {
            //   mSearchResultsDisplay.append("\n\n" + berry);
            //}
        } catch (Exception e) {
            System.out.println("other exception caught");
        }

        return berries;
    }
}
