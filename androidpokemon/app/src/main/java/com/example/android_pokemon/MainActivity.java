package com.example.android_pokemon;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.*;
import java.net.URL;
import java.io.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.*;
//import com.example.android_pokemon.CustomJSONParser;

import com.example.android_pokemon.utilities.NetworkUtilities;

public class MainActivity extends AppCompatActivity {

    private Button mSearchButton;
    private Button mResetButton;
    private TextView mSearchResultsDisplay;
    private EditText mSearchTermEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchResultsDisplay = (TextView) findViewById(R.id.tv_display_text);
        mSearchTermEditText = (EditText) findViewById(R.id.et_search_box);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mResetButton = (Button) findViewById(R.id.reset_button);


        // Berry[] berrynames = CustomJSONParser.BerryParse();
        final String[] berrynames = {"cheri", "chesto", "pecha", "rawst", "aspear"};

        for (String berry : berrynames) {
            mSearchResultsDisplay.append("\n\n" + berry);
        } // end of for

        final String defaultDisplayText = mSearchResultsDisplay.getText().toString();
        // responding to search button
        mSearchButton.setOnClickListener(
                new View.OnClickListener() { // a unnamed object
                    //inner method def
                    public void onClick(View v) {
                        //get search string from user
                        String searchText = mSearchTermEditText.getText().toString();

                        makeNetworkSearchQuery();

//                        for (String berry : berrynames) {
//                            if (berry.toLowerCase().equals(searchText.toLowerCase())) {
//                                makeNetworkSearchQuery();
//                                break;
//                            } else {
//                                mSearchResultsDisplay.setText("No results match.");
//                            }
//                        }
                    }

                }
        );

        // responding to reset button
        mResetButton.setOnClickListener(
                new View.OnClickListener() { // a unnamed object
                    //inner method def
                    public void onClick(View v) {
                        // reset the text
                        mSearchResultsDisplay.setText(defaultDisplayText);

                    } // end of onClick method

                } // end of View.OnClickListener
        ); // end of setOnClickListener

    }// end of on Create

    public void makeNetworkSearchQuery(){
        // get search string
        String searchTerm = mSearchTermEditText.getText().toString();
        // reset search results
        mSearchResultsDisplay.setText("Results for " + searchTerm + ":\n\n");
        // make network query
        new FetchNetworkData().execute(searchTerm);
    } // end of makeNetworkSearchQuery


    // inner Networking Async class
    public class FetchNetworkData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if (params.length == 0) return null;
            String searchTerm = params[0];

            URL searchUrl = NetworkUtilities.buildBerryUrl(searchTerm);
            //perform networking task
            String responseString = null;
            try {
                responseString = NetworkUtilities.getResponseFromUrl(searchUrl);
                Log.d("info", responseString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseString;
        } // end of doInBackground

        @Override
        protected void onPostExecute(String responseData){
            // this is invoked when the network thread finishes its networking call.
            String[] berryInfo = NetworkUtilities.parseBerryJSON(responseData);
            // display news titles in GUI
            for (String info: berryInfo){
                mSearchResultsDisplay.append("\n\n" + info);
            } // end for

        } // end of onPostExecute



    }
}
