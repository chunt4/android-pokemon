package com.example.android_pokemon.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;



public class NetworkUtilities {

    public static URL buildBerryUrl(String searchTerm){  // creates the berry url with the berry name provided by the user
        final String baseUrl = "https://pokeapi.co/api/v2/berry/";
       
        URL berryUrl = null;
        String urlString = baseUrl +  searchTerm; 
        try{
            berryUrl = new URL(urlString);
            
        } catch(MalformedURLException e){
            System.out.println("The url is not correctly formatted.");
            e.printStackTrace();
        }
        return berryUrl;
    } // end buildUrl

    public static String[] parseBerryJSON(String berryJSONData) {
        String[] berryData = new String[13];
        
        // parsing the json data and getting the key value pairs to display to the user
        try {
            JSONObject allBerrydata = new JSONObject(berryJSONData);
            berryData = new String[berryJSONData.length()];

            berryData[0] = "name: " + allBerrydata.getString("name");
            JSONObject firmness = allBerrydata.getJSONObject("firmness");
            berryData[1] = "firmness: " + firmness.getString("name");

            berryData[2] = "max harvest: " + String.valueOf(allBerrydata.getInt("max_harvest"));
            berryData[3] = "growth time: " + String.valueOf(allBerrydata.getInt("growth_time"));
            berryData[4] = "gift power: " + String.valueOf(allBerrydata.getInt("natural_gift_power"));
            JSONObject natural_gift_type = allBerrydata.getJSONObject("natural_gift_type");
            berryData[5] = "gift type: " + natural_gift_type.getString("name");
            berryData[6] = "size: " + String.valueOf(allBerrydata.getInt("size"));
            berryData[7] = "smoothness: " + String.valueOf(allBerrydata.getInt("smoothness"));
            berryData[8] = "soil dryness: " + String.valueOf(allBerrydata.getInt("soil_dryness"));

            if (!allBerrydata.has("flavors"))
                return null;

            JSONArray flavor = (JSONArray) allBerrydata.get("flavors");
            Log.d("debug","flavorArray " + flavor.toString());
            int a = 9;
            for (int i = 0; i < flavor.length(); i++) {
                Log.d("debug","inside for loop");
                JSONObject childJson = flavor.getJSONObject(i);
                Log.d("debug","childJson " + childJson.toString());
                JSONObject childData = childJson.getJSONObject("flavor");
                String flavorName = (String)childData.getString("name");
                String potency = String.valueOf(childJson.getInt(("potency")));
                Log.d("debug","flavorName: " + flavorName);
                berryData[a++] = flavorName + ": " + potency;
            }   // end of for
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return berryData;
    }  // end of berryJSONata

    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Log.d("debug","entered getResponse");
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A"); // this is an end of message delimiter
            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput){
                response = scanner.next();
                Log.d("debug","response = " + response.toString());
                return response;
            }
            else{
                Log.d("debug","no input");
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        } // end of finally
        return null;
    } // end of getResponseFromUrl



}
