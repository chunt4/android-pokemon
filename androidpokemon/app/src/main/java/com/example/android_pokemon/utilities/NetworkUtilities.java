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

    public static URL buildBerryUrl(String searchTerm){
        final String baseUrl = "https://pokeapi.co/api/v2/berry/";
        //final String endformat = ".json";
        URL berryUrl = null;
        String urlString = baseUrl +  searchTerm; //+ endformat;
        try{
            berryUrl = new URL(urlString);
            Log.d("info", "URL:" + urlString);
        } catch(MalformedURLException e){
            System.out.println("The url is not correctly formatted.");
            e.printStackTrace();
        }
        return berryUrl;
    } // end buildUrl

    public static String[] parseBerryJSON(String berryJSONData) {
        String[] berryData = new String[100];
        try {
            JSONObject allBerrydata = new JSONObject(berryJSONData);
            berryData = new String[berryJSONData.length()];

            berryData[0] = allBerrydata.getString("name");
            JSONObject firmness = allBerrydata.getJSONObject("firmness");
            berryData[1] = firmness.getString("name");

            berryData[2] = String.valueOf(allBerrydata.getInt("max_harvest"));
            berryData[3] = String.valueOf(allBerrydata.getInt("growth_time"));
            berryData[4] = String.valueOf(allBerrydata.getInt("natural_gift_power"));
            JSONObject natural_gift_type = allBerrydata.getJSONObject("natural_gift_type");
            String natural_gift_typeFinal = natural_gift_type.getString("natural_gift_type");
            berryData[5] = String.valueOf(allBerrydata.getInt("size"));
            berryData[6] = String.valueOf(allBerrydata.getInt("smoothness"));
            berryData[7] = String.valueOf(allBerrydata.getInt("soil_dryness"));

            JSONArray flavor = allBerrydata.getJSONArray("flavors");

            for (int i = 8; i < berryData.length; i++) {
                JSONObject childJson = flavor.getJSONObject(i);
                JSONObject childData = childJson.getJSONObject("flavor");
                String flavorName = childData.getString("name");
                int potency = flavor.getInt(Integer.parseInt("potency"));

                berryData[i] = (flavorName + potency);
            }   // end of for
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return berryData;
    }  // end of berryJSONata

    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A"); // this is an end of message delimiter
            boolean hasInput = scanner.hasNext();
            if (hasInput) return scanner.next();
            else return null;
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        } // end of finally
        return null;
    } // end of getResponseFromUrl



}
