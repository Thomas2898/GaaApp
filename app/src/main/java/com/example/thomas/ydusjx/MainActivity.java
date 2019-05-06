package com.example.thomas.ydusjx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        System.out.println("The screen height " + height + " width " + width);


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

                //updatePlayerStat();
                //apiCalls.playerStat(9, 10, "Point_Miss");
                //updateApi();
                //addPlayer();
                //updatePlayerStat();
                dateTest();

                //apiPlayer.getAllPlayersStats(10, "Passing", "Player 1");
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


    static public void updateApi() {
        final String API = "http://142.93.44.141/players/11";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(API);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PATCH");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    //jsonParam.put("id", 3);
                    jsonParam.put("name", "Gerry");
                    //jsonParam.put("DOB", "26/03/1992");
                    //jsonParam.remove("teamID");
                    //jsonParam.put("teamID", 10);

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
                            System.out.println("LOOP RESULT " + pl);
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
















    //Used to get a players stats from a specific match
    static public void playerStat(final int playerID, final int fixtureID, final String Stat) {
        final String API = "http://142.93.44.141/stats/";
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

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line+"\n");
                        Log.d("Response: ", "> " + line);
                    }

                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int playerIDData = (int) jsonObject.get("Player_ID");
                        int fixtureIDData = (int) jsonObject.get("Fixture_ID");

                        if(playerID == playerIDData && fixtureID == fixtureIDData) {

                            int StatID = (int) jsonObject.get("id");
                            int Player_ID = (int) jsonObject.get("Player_ID");
                            int Fixture_ID = (int) jsonObject.get("Fixture_ID");
                            int Pass = (int) jsonObject.get("Pass");
                            int Pass_Miss = (int) jsonObject.get("Pass_Miss");
                            int Point = (int) jsonObject.get("Point");
                            int Point_Miss = (int) jsonObject.get("Point_Miss");
                            int Goal = (int) jsonObject.get("Goal");
                            int Goal_Miss = (int) jsonObject.get("Goal_Miss");
                            int Turnover = (int) jsonObject.get("Turnover");
                            int Dispossessed = (int) jsonObject.get("Dispossessed");
                            int Block = (int) jsonObject.get("Block");
                            int Kickout_won = (int) jsonObject.get("Kickout_won");
                            int Kickout_lost = (int) jsonObject.get("Kickout_lost");
                            int Goal_save = (int) jsonObject.get("Goal_save");
                            int Goal_conceded = (int) jsonObject.get("Goal_conceded");
                            int Yellow_card = (int) jsonObject.get("Yellow_card");
                            int Red_card = (int) jsonObject.get("Red_card");
                            int Black_card = (int) jsonObject.get("Black_card");

                            //Used to compare to the stat that is a parameter when this class is called
                            String[] PStats = {"id", "Player_ID", "Fixture_ID", "Pass", "Pass_Miss", "Point", "Point_Miss", "Goal", "Goal_Miss", "Turnover", "Dispossessed", "Block", "Kickout_won", "Kickout_lost", "Goal_save", "Goal_conceded", "Yellow_card", "Red_card", "Black_card"};
                            int[] PStatsVal = {StatID, Player_ID, Fixture_ID, Pass, Pass_Miss, Point, Point_Miss, Goal, Goal_Miss, Turnover, Dispossessed, Block, Kickout_won, Kickout_lost, Goal_save, Goal_conceded, Yellow_card, Red_card, Black_card};

                            //Used to compare the stat that was passed in as a parameter
                            //and increase the stat by one
                            for(int j=0; j<PStats.length; j++) {
                                if(Stat.equals(PStats[j])){
                                    System.out.println("Stat being increased is "  + Stat);
                                    PStatsVal[j] = PStatsVal[j] + 1;
                                    System.out.println("Stat is increased to "  + PStatsVal[j]);
                                    System.out.println("Compare "  + Point);
                                }
                            }

                            System.out.println("Player Pass " + Pass + " Player pass miss " + Pass_Miss);
                            System.out.println("The id of the stat is "  + StatID);
                            String StatChosen = Integer.toString(StatID);
                            //System.out.println(TeamID);
                            connection.disconnect();


                            //updatePlayerStat(StatChosen, PStats, PStatsVal);
                        }
                    }
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }















    //static public void updatePlayerStat(final String StatID, final String[] PStats, final int[] PStatsVal) {
    static public void updatePlayerStat() {

        //final String API = "http://142.93.44.141/stats/" + StatID;
        final String API = "http://142.93.44.141/stats/8";
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
                    /*
                    for(int i=0; i<PStats.length; i++) {
                        jsonParam.put(PStats[i], PStatsVal[i]);
                    }
                    */

                    //jsonParam.put("id", 11);
                    jsonParam.put("Player_ID", 19);
                    jsonParam.put("Fixture_ID", 1);
                    jsonParam.put("Pass", 12);
                    jsonParam.put("Pass_Miss", 2);
                    jsonParam.put("Point", 5);
                    jsonParam.put("Point_Miss", 3);
                    jsonParam.put("Goal", 0);
                    jsonParam.put("Goal_Miss", 1);
                    jsonParam.put("Turnover", 2);
                    jsonParam.put("Dispossessed", 4);
                    jsonParam.put("Block", 5);
                    jsonParam.put("Kickout_won", 0);
                    jsonParam.put("Kickout_lost", 0);
                    jsonParam.put("Goal_save", 0);
                    jsonParam.put("Goal_conceded",0);
                    jsonParam.put("Yellow_card", 1);
                    jsonParam.put("Red_card", 0);
                    jsonParam.put("Black_card", 0);
                    //jsonParam.remove("teamID");
                    //jsonParam.put("teamID", 10);

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


    //Chkclass is used to see which class is calling getPlayersFixtureStats
    static public void dateTest() {
        final String API = "http://142.93.44.141/fixtures/";
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

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line+"\n");
                        Log.d("Response: ", "> " + line);
                    }

                    int chkStat = 0;//Used to clarify if the players stat record exists for the certain fixture
                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Datef = (String) jsonObject.get("Date");
                        if(Datef.equals("2019-05-13")) {
                            String Home_Team = (String) jsonObject.get("Home_Team");
                            String Away_Team = (String) jsonObject.get("Away_Team");
                            String Referee = (String) jsonObject.get("Referee");
                            String Time = (String) jsonObject.get("Time");
                            String Location = (String) jsonObject.get("Location");
                            System.out.println("Fixure Found");
                            System.out.println(Home_Team);
                            System.out.println(Away_Team);
                            System.out.println(Referee);
                            Date c = Calendar.getInstance().getTime();
                            System.out.println("Current time => " + c);
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = df.format(c);
                            System.out.println(formattedDate);
                        }

                        connection.disconnect();


                    }
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


}
