package com.example.thomas.ydusjx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
//import org.json.JSONElement;
import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    MyDbHelper mydb;
    Button btnTeam;
    Button btnPlayer;
    Button btnPlayers;
    Button btnTest;
    //JSONParser jsonparser = new JSONParser();
    //JSONObject jobj = null;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new MyDbHelper(this);
        System.out.println("WORKS");

        //Used to open the team screen
        btnTeam = (Button) findViewById(R.id.TeamBtn);
        btnTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to team");
                //Used to pass a string to the team screen as the line "str = bundle.getString("PlayerSelected");" would bring back an error as no string was passed
                String TeamName = "PlayerName";
                Intent TeamDisplay = new Intent(MainActivity.this, Team.class);
                TeamDisplay.putExtra("PlayerSelected", TeamName);
                startActivity(TeamDisplay);
            }
        });

        //Used to open the screen playerlistmain
        btnPlayers= (Button) findViewById(R.id.PlayersBtn);
        btnPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to playerlistmain");
                Intent PlayList = new Intent(MainActivity.this, PlayerListMain.class);
                startActivity(PlayList);
            }
        });

        //Used to open the screen playerlistmain
        btnTest= (Button) findViewById(R.id.TestBtn);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Test API");
                //new JsonTask().execute("http://142.93.44.141/players/1?format=json");
                //new yourDataTask().execute("http://142.93.44.141/players/1?format=json");
                //Get all players
                new JsonTask().execute("http://142.93.44.141/players/");
                //new JsonTask().execute("https://jsonplaceholder.typicode.com/posts/1");

                //new retrievedata().execute();
                /*
                try {
                   MyGETRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */

            }
        });
    }



    //https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                /*
                JSONParser jp = new JSONParser(); //from gson
                try {
                    JsonElement root = jp.parse(new InputStreamReader((InputStream) connection.getContent()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                */

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                //String jsonText = readAll(reader);
                //JSONObject json = new JSONObject(jsonText);

                //JSONObject jobj = (JSONObject) parser.parse(reader); // Pass the Json formatted String
                //String internal_id = (String) jobj.get("Id"); // Extract the value from your key
                //System.out.println(internal_id);

                //System.out.println("BUFFER " + reader.readLine());

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }

                String playerName;

                JSONArray jsonArray = new JSONArray(buffer.toString());
                for(int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int teamId = (int) jsonObject.get("teamID");
                    //System.out.println("LOOP RESULT t " + teamId);
                    if(teamId == 1) {
                        String jsonObjectAsString = (String) jsonObject.get("name");
                        System.out.println("LOOP RESULT " + jsonObjectAsString);
                    }
                }

                /*
                JSONObject JSO = new JSONObject(buffer.toString());
                System.out.println("JSON " + JSO);
                playerName = (String) JSO.get("name");
                System.out.println("JSON " + playerName);
                */

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            //txtJson.setText(result);
            System.out.println("This is the results" + result);
        }
    }


    protected class yourDataTask extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... params)
        {

            String str="http://142.93.44.141/players/1?format=json";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(params[0]);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                return new JSONObject(stringBuffer.toString());
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(JSONObject response)
        {
            if(response != null)
            {
                try {
                    Log.e("App", "Success: " + response.getString("yourJsonElement") );
                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }




}
