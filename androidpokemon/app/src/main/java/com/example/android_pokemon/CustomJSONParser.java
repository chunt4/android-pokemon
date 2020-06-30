package com.example.android_pokemon;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;



public class CustomJSONParser {

    public static String[] parseJsonBerry(JSONObject jo) throws JSONException {
        String[] berries = new String[64];
        if (!jo.has("results")) // check if the key "results" is in theJSON object
            return null;
        JSONArray ja = (JSONArray) jo.get("results");
        
        for(int i = 0; i < ja.length(); i++){
            JSONObject jso = ja.getJSONObject(i);
            // parse the json array and get each berry name
            berries[i] = (String)jso.getString("name");
            
        }
        return berries;
    } // end of parseJson

    public static String[] BerryParse(Context c) {
       
        String[] berries = new String[64];
        try {
            InputStream is = c.getResources().openRawResource(R.raw.berrydata);
            Scanner sc = new Scanner(is);
            sc.useDelimiter("\\A");
            if (sc.hasNext()) {
                String response = sc.next();
                JSONObject jso = new JSONObject(response);
                
                berries = parseJsonBerry(jso);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("other exception caught");
        }

        return berries;
    }
}
