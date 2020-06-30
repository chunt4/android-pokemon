package com.example.android_pokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.net.URL;
import com.example.android_pokemon.CustomJSONParser;
import java.io.InputStream;


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

        Context c = MainActivity.this;
        final String[] berrynames = CustomJSONParser.BerryParse(c);

        for (String berry : berrynames) {
            mSearchResultsDisplay.append("\n\n" + berry);//.toString());
        } // end of for

        final String defaultDisplayText = mSearchResultsDisplay.getText().toString();
        // responding to search button
        mSearchButton.setOnClickListener(
                new View.OnClickListener() { // a unnamed object
                    //inner method def
                    public void onClick(View v) {
                        //get search string from user
                        String searchText = mSearchTermEditText.getText().toString();

                        for (String berry : berrynames) {
                            if (berry.toLowerCase().equals(searchText.toLowerCase())) {
                                makeNetworkSearchQuery();
                                break;
                            } else {
                                mSearchResultsDisplay.setText("No results match.");
                            }
                        }
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
        mSearchResultsDisplay.setText("Results for " + searchTerm + ": ");
        // make network query
        new FetchNetworkData().execute(searchTerm);
    } // end of makeNetworkSearchQuery


    //@SuppressLint("StaticFieldLeak")
    class FetchNetworkData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params){ // this is called when execute runs
            if(params.length == 0) return null;
            String searchTerm = params[0];

            Log.d("debug","doInBackground searchTerm = " + searchTerm);
            URL searchUrl = NetworkUtilities.buildBerryUrl(searchTerm);
            Log.d("debug", "post build" + searchUrl.toString());

            //perform networking task
            String responseString = null;
            try {
                Log.d("debug","before response");

                responseString = NetworkUtilities.getResponseFromUrl(searchUrl);
                Log.d("informational", "response inside doInBackGround: " + responseString);
            } catch (Exception e){
                e.printStackTrace();
            }
            return responseString;
        } // end of doInBackground

        @Override
        protected void onPostExecute(String responseData){
            // this is invoked when the network thread finishes its networking call.
            Log.d("debug","begin onPostExecute. responseData = " + responseData);
            String[] berryInfo = NetworkUtilities.parseBerryJSON(responseData);
            Log.d("debug","exit berryJSON. berryInfo: " + berryInfo.toString());
            // display news titles in GUI
            for (String ber: berryInfo){
                if (ber==null) // checks to make sure no null data is displayed
                    break;
                mSearchResultsDisplay.append("\n\n" + ber);
            } // end for

        } // end of onPostExecute


    } // end of inner class FetchNetworkData

}
