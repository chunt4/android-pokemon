package com.example.android_pokemon;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

//import org.json.JSONException;
//import org.json.simple.JSONArray;
////import org.json.JSONException;
//import org.json.JSONObject;
////import java.lang.Iterable;
//import org.json.simple.parser.JSONParser;
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

    public static String[] parseJsonBerry(JSONObject jo) throws JSONException {
        Log.d("debug","entered parseJsonBerry");
        String[] berries = new String[64];
        if (!jo.has("results"))
            return null;
        JSONArray ja = (JSONArray) jo.get("results");
        Log.d("debug","inside parseJsonBerry ja = " + ja.toString());
        for(int i = 0; i < ja.length(); i++){
            JSONObject jso = ja.getJSONObject(i);
            //String bName = (String)jo.getString("name");
            berries[i] = (String)jso.getString("name");
            //berries[i] = new Berry(bName);
        }
        return berries;
    } // end of parseJsonMovies

    public static String[] BerryParse(Context c) {
        Log.d("debug","entered berryparse");
        String[] berries = new String[64];
        try {
            InputStream is = c.getResources().openRawResource(R.raw.berrydata);
            Scanner sc = new Scanner(is);
            sc.useDelimiter("\\A");
            if (sc.hasNext()) {
                String response = sc.next();
                JSONObject jso = new JSONObject(response);
                Log.d("debug","in berryParse, jo = " + jso.toString());
                berries = parseJsonBerry(jso);
                Log.d("debug","berries = " + berries.toString());
            }
            //for (String berry : berrynames) {
            //   mSearchResultsDisplay.append("\n\n" + berry);
            //}
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("other exception caught");
        }

        return berries;
    }
}
