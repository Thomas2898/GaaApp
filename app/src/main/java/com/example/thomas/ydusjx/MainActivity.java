package com.example.thomas.ydusjx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.example.thomas.ydusjx.Team.player1;

public class MainActivity extends AppCompatActivity {
    MyDbHelper mydb;
    apiCalls apiPlayer;
    Button btnTeam;
    Button btnPlayer;
    Button btnPlayers;
    Button btnTest;
    JSONArray players;
    //JSONParser jsonparser = new JSONParser();
    //JSONObject jobj = null;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new MyDbHelper(this);
        System.out.println("WORKS");

        apiCalls.getTeams getTeam = new apiCalls.getTeams();
        getTeam.execute("http://142.93.44.141/teams", "teams");

        /*
        apiCalls.getFixtures getFixtures = new apiCalls.getFixtures();
        getFixtures.execute("http://142.93.44.141/fixtures", "fixtures");
        */



        /*
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        */

        //Used to open the team screen
        btnTeam = (Button) findViewById(R.id.TeamBtn);
        btnTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to team list");
                Intent TeamDisplay = new Intent(MainActivity.this, TeamSelection.class);
                startActivity(TeamDisplay);
            }
        });

        /*
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
        */

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

        //myApi = new apiCalls(this);
        //Used to open the screen playerlistmain
        btnTest= (Button) findViewById(R.id.TestBtn);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Test API");
                //sendPost();
                //updateApi();
                //apiPlayer = new apiCalls();
                //JSONArray playersID = apiPlayer.callApi();
                // System.out.println("This is the returned values" + playersID);
                /*
                getPlayers getPlayer = new getPlayers();
                getPlayer.execute("http://142.93.44.141/teams/");
                System.out.println("This is the returned values " + getPlayer);
                */

                getPlayersID getPlayersIDs = new getPlayersID();
                getPlayersIDs.execute("http://142.93.44.141/teams/");
                /*
                apiCalls.getTeams getTeam = new apiCalls.getTeams();
                getTeam.execute("http://142.93.44.141/", "teams");
                System.out.println("This is the returned values " + getTeam);
                */



                //apiCalls.JsonTask().execute("http://142.93.44.141/players/1?format=json");
                //new JsonTask().execute("http://142.93.44.141/players/1?format=json");
                //new yourDataTask().execute("http://142.93.44.141/players/1?format=json");
                //Get all players
                //new JsonTask().execute("http://142.93.44.141/players/");
                //new CallAPI().execute("http://142.93.44.141/players/");
                //AsyncTask<String, String, String> Players;
                //Players = new JsonTask().execute("http://142.93.44.141/players/");
                //System.out.println("This is Players value " + Players);
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
                connection.setRequestMethod("GET");
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
                    Log.d("Response: ", "> " + line);
                }

                String playerName;
                String[] PlayersNames = {"Test1", "Test2"};
                //String[] Players = {Team.player1,Team.player2,Team.player3};
                JSONArray jsonArray = new JSONArray(buffer.toString());
                for(int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int teamId = (int) jsonObject.get("teamID");
                    jsonObject.put("teamID", 2);
                    //System.out.println("LOOP RESULT t " + teamId);
                    if(teamId == 1) {
                        String jsonObjectAsString = (String) jsonObject.get("name");
                        int TeamID = (int) jsonObject.get("teamID");
                        PlayersNames[i] = jsonObjectAsString;
                        //PlayerList.PlayersNames.add("A");
                        //jsonObject.put("teamID", 2);
                        System.out.println("LOOP RESULT " + jsonObjectAsString);
                        System.out.println(TeamID);
                    }
                }

                System.out.println("Player name " + PlayersNames[0]);


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




    public class getPlayers extends AsyncTask<String, String, JSONArray> {

        public JSONArray p1;
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected JSONArray doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
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
                    Log.d("Response: ", "> " + line);
                }

                String playerName;
                String[] PlayersNames = {"Test1", "Test2"};
                //String[] Players = {Team.player1,Team.player2,Team.player3};
                JSONArray jsonArray = new JSONArray(buffer.toString());
                for(int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //int teamId = (int) jsonObject.get("teamID");
                    //jsonObject.put("teamID", 2);
                    //System.out.println("LOOP RESULT t " + teamId);
                    //if(teamId == 1) {
                    //String jsonObjectAsString = (String) jsonObject.get("players");
                    JSONArray pl = new JSONArray(buffer.toString());

                    p1 = (JSONArray) jsonObject.get("players");

                    //int TeamID = (int) jsonObject.get("teamID");
                    //PlayersNames[i] = jsonObjectAsString;
                    //PlayersNames.add(jsonObjectAsString);
                    //PlayerList.PlayersNames.add("A");
                    //jsonObject.put("teamID", 2);
                    System.out.println("LOOP RESULT " + pl);
                    //System.out.println(TeamID);
                    //}
                }

                //return buffer.toString();
                //return p1;
                displayPlayers(p1);



                /*
                JSONObject JSO = new JSONObject(buffer.toString());
                System.out.println("JSON " + JSO);
                playerName = (String) JSO.get("name");
                System.out.println("JSON " + playerName);
                */



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

            System.out.println("This is the result at the bottom " + p1);
            return p1;
            //return null;
        }



        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            //txtJson.setText(result);
            System.out.println("This is the results" + result);
        }
    }


    static void displayPlayers(JSONArray result) {

        //txtJson.setText(result);
        System.out.println("This is the results in displayPlayers" + result);
    }










    // Where i learned how to post to an api https://stackoverflow.com/questions/42767249/android-post-request-with-json 
    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://142.93.44.141/players/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    //jsonParam.put("id", 3);
                    jsonParam.put("name", "Gerry");
                    jsonParam.put("DOB", "26/03/1992");
                    jsonParam.remove("teamID");
                    jsonParam.put("teamID", 2);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    static public void updateApi() {
        final String API = "http://142.93.44.141/players/3";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(API);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    //jsonParam.put("id", 3);
                    jsonParam.put("name", "Gerry");
                    jsonParam.put("DOB", "26/03/1992");
                    //jsonParam.remove("teamID");
                    jsonParam.put("teamID", 10);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    static public void callApi() {
        final String API = "http://142.93.44.141/teams/";
        //HttpURLConnection connection = null;
        //BufferedReader reader = null;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = null;
                    BufferedReader reader = null;
                    URL url = new URL(API);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    InputStream stream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    //System.out.println("BUFFER " + reader.readLine());

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line+"\n");
                        Log.d("Response: ", "> " + line);
                    }

                    String playerName;
                    //String[] PlayersNames = {"Test1", "Test2"};
                    ArrayList<String> theList = new ArrayList<String>();
                    ArrayList<String> PlayersNames = new ArrayList<String>();
                    //PlayersNames.add("A");
                    //PlayersNames.add("B");
                    //PlayersNames.add("C");
                    //PlayersNames.add("D");
                    //String[] Players = {Team.player1,Team.player2,Team.player3};
                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //int teamId = (int) jsonObject.get("teamID");
                        //jsonObject.put("teamID", 2);
                        //System.out.println("LOOP RESULT t " + teamId);
                        //if(teamId == 1) {
                            //String jsonObjectAsString = (String) jsonObject.get("players");
                        JSONArray pl = (JSONArray) jsonObject.get("players");

                            //int TeamID = (int) jsonObject.get("teamID");
                            //PlayersNames[i] = jsonObjectAsString;
                            //PlayersNames.add(jsonObjectAsString);
                            //PlayerList.PlayersNames.add("A");
                            //jsonObject.put("teamID", 2);
                            System.out.println("LOOP RESULT " + pl.getJSONObject(9));
                            //System.out.println(TeamID);
                        //}
                    }

                    //System.out.println("Player name " + PlayersNames[0]);


                /*
                JSONObject JSO = new JSONObject(buffer.toString());
                System.out.println("JSON " + JSO);
                playerName = (String) JSO.get("name");
                System.out.println("JSON " + playerName);
                */

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    //Used to get Player names from the restframework
    public static class getPlayersID extends AsyncTask<String, String, JSONArray> {

        public JSONArray p1;
        public ArrayList<String> playerNames = new ArrayList<String>();
        public ArrayList<Integer> playerIDS = new ArrayList<Integer>();

        protected JSONArray doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);
                }

                JSONArray jsonArray = new JSONArray(buffer.toString());
                //playerNames.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    p1 = (JSONArray) jsonObject.get("players");
                }

                //To make sure no duplicates are added to the list
                playerIDS.clear();
                //To get the players IDs from the team
                for (int i = 0; i < p1.length(); i++) {
                    int playerID = (int) p1.get(i);
                    System.out.println("Players id for the team selected " + p1.get(i));
                    playerIDS.add(playerID);
                }

                //storePlayerID(playerIDS);


                URL urlPlayer = new URL("http://142.93.44.141/players");
                connection = (HttpURLConnection) urlPlayer.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream streamPlayer = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(streamPlayer));

                StringBuffer bufferPlayer = new StringBuffer();
                String linePlayer = "";

                while ((linePlayer = reader.readLine()) != null) {
                    bufferPlayer.append(line+"\n");
                    Log.d("Response: ", "> " + line);
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    p1 = (JSONArray) jsonObject.get("players");
                    int pid = (int) jsonObject.get("name");
                    String pname = (String) jsonObject.get("DOB");
                    String pDOB = (String) jsonObject.get("id");

                    for (int j = 0; j < playerIDS.size(); j++) {

                        if(pid == playerIDS.get(i)){

                            System.out.println("Players id for the team selected " + pid);
                            String fullDisplayPlayer = pid + " " + pname + " " + pDOB;
                            System.out.println(fullDisplayPlayer);

                        }
                    }
                }

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

            //System.out.println("This is the result at the bottom " + p1);
            //return p1;
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            //txtJson.setText(result);
            System.out.println("This is the results" + result);
        }
    }
}
