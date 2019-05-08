package com.example.thomas.ydusjx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import static android.R.layout.simple_list_item_1;

/**
 * Created by Thomas on 26/01/2019.
 */

public class apiCalls extends Activity {
    public JSONArray getPlayers;

    //////////////////////////////////////////////////////////////////////////////
    //These ere used to influence my code in creatin connections between the app and the rest framework
    //This is where i learned how to connect the app to data from rest framework using AsyncTask
    //https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
    ////This is where i learned how to connect the app to data from rest framework using threads
    //https://stackoverflow.com/questions/42767249/android-post-request-with-json
    ///////////////////////////////////////////////////////////////////////////////

    public JSONArray callApi() {
        final String API = "http://142.93.44.141/teams/";
        //HttpURLConnection connection = null;
        //BufferedReader reader = null;
        Thread thread = new Thread(String.valueOf(new Callable() {

            @Override
            public JSONArray call() {
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
                        buffer.append(line + "\n");
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
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //int teamId = (int) jsonObject.get("teamID");
                        //jsonObject.put("teamID", 2);
                        //System.out.println("LOOP RESULT t " + teamId);
                        //if(teamId == 1) {
                        //String jsonObjectAsString = (String) jsonObject.get("players");
                        getPlayers = (JSONArray) jsonObject.get("players");

                        //int TeamID = (int) jsonObject.get("teamID");
                        //PlayersNames[i] = jsonObjectAsString;
                        //PlayersNames.add(jsonObjectAsString);
                        //PlayerList.PlayersNames.add("A");
                        //jsonObject.put("teamID", 2);
                        //System.out.println("LOOP RESULT " + getPlayers);
                        //System.out.println(TeamID);
                        //}
                    }
                    //System.out.println("LOOP RESULT " + getPlayers);

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
                //System.out.println("LOOP RESULT " + getPlayers);
                return getPlayers;
            }
        }));
        thread.start();
        //System.out.println("LOOP RESULT " + getPlayers);
        return getPlayers;
    }


    //Used to get Teams names from the restframework
    public static class getTeams extends AsyncTask<String, String, JSONArray> {

        public JSONArray p1;
        public ArrayList<String> teamNames = new ArrayList<String>();
        /*
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog pd = new ProgressDialog(apiCalls.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }
        */
        protected JSONArray doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                String url2 = params[0];
                String search = params[1];
                String fullUrl = url2 + search;
                //URL url = new URL(fullUrl);
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

                //String[] Players = {Team.player1,Team.player2,Team.player3};
                JSONArray jsonArray = new JSONArray(buffer.toString());
                if(params[1] == "teams") {
                    teamNames.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = (String) jsonObject.get("name");
                        teamNames.add(name);
                        JSONArray pl = new JSONArray(buffer.toString());

                        p1 = (JSONArray) jsonObject.get("players");
                        System.out.println("LOOP RESULT " + pl);

                    }
                    TeamSelection.getTeam(teamNames);
                }

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
            //txtJson.setText(result);
            System.out.println("This is the results" + result);
        }
    }



    //Used to get all the Fixtures relating to the team the user chose
    public static class getFixtures extends AsyncTask<String, String, JSONArray> {

        public JSONArray p1;
        public ArrayList<String> fixtureNames = new ArrayList<String>();

        protected JSONArray doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                System.out.println("Inside getFixtures");
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

                //String[] Players = {Team.player1,Team.player2,Team.player3};
                JSONArray jsonArray = new JSONArray(buffer.toString());
                //if(params[1] == "fixtures") {
                    fixtureNames.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        int StatID = (int) jsonObject.get("id");
                        String StatChosen = Integer.toString(StatID);
                        String AwayTeam = (String) jsonObject.get("Away_Team");
                        String HomeTeam = (String) jsonObject.get("Home_Team");
                        String Referee = (String) jsonObject.get("Referee");
                        String Date = (String) jsonObject.get("Date");
                        String Time = (String) jsonObject.get("Time");
                        String Location = (String) jsonObject.get("Location");
                        //String fullDisplay = StatChosen + " " + HomeTeam + " vs " + AwayTeam + " Date " + Date;
                        String fullDisplay = StatChosen + ", " + HomeTeam + ", " + AwayTeam + ", " + Referee + ", " + Date + ", " + Time + ", " + Location;
                        System.out.println(fullDisplay);
                        System.out.println("This is the param " + params[1]);
                        System.out.println("This is the homeTeam " + HomeTeam);
                        //fixtureNames.add(fullDisplay);

                        //Used to get the fixtures that contain the users team as the home team
                        if(params[1].equals(HomeTeam)) {
                            fixtureNames.add(fullDisplay);
                        }
                    }
                    //Fills up arraylists across the app
                    FixtureSelection.getFixtures(fixtureNames);
                    statsDisplay.getFixtures(fixtureNames);
                    StatFixtureClass.getStatFixtures(fixtureNames);
                //}

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

    //Used to get Player names from the restframework and pass the names to
    public static class getPlayers extends AsyncTask<String, String, JSONArray> {

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

                //String IDString = (String) Integer.toString(pid);
                String PlayerIDS = "";
                //To make sure no duplicates are added to the list
                playerIDS.clear();
                //To get the players IDs from the team
                for (int i = 0; i < p1.length(); i++) {
                    int playerID = (int) p1.get(i);
                    String IDString = (String) Integer.toString(playerID);
                    PlayerIDS = PlayerIDS + IDString + " ";
                    System.out.println("Players id for the team selected " + p1.get(i));
                    playerIDS.add(playerID);
                }


                storePID(PlayerIDS);
                //storePlayerID(playerIDS);

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










    //This receives data from the getPlayers class of the ids in string format
    //the strings are seperated by spaces in the string so taking them out of the
    //string is easier
    //This calls loadPlayerID
    protected static void storePID(String result) {
        System.out.println("StorePID received " + result);

        loadPlayers loadPlayerID = new loadPlayers();
        loadPlayerID.execute("http://142.93.44.141/players/", result);
    }

    //This loads the players names along with their IDs t arraylists through out the app. Used to fill lists up
    public static class loadPlayers extends AsyncTask<String, String, JSONArray> {

        public JSONArray p1;
        public ArrayList<String> playerNames = new ArrayList<String>();
        //public ArrayList<Integer> playerIDS = new ArrayList<Integer>();

        protected JSONArray doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                System.out.println("Entered load players");
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

                //Split the string which contains all the IDs of players from the team the user chose
                String[] PlayerIDs = params[1].split(" ");
                System.out.println("First element from splitting string " + PlayerIDs[0]);

                //Data received from framework
                JSONArray jsonArray = new JSONArray(buffer.toString());

                //Used to go through all the data received from the framework
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //p1 = (JSONArray) jsonObject.get("players");
                    int pid = (int) jsonObject.get("id");
                    String pname = (String) jsonObject.get("name");
                    String pDOB = (String) jsonObject.get("DOB");

                    //Used to turn the players id from /players into a string so it can be compared to players ids received from storePID
                    String IDString = (String) Integer.toString(pid);
                    //Goes through all the IDs receieved from storePID
                    for (int j = 0; j < PlayerIDs.length; j++) {

                        //Used to find the users ids in /players taken from /team
                        if(IDString.equals(PlayerIDs[j])){
                            System.out.println("Players id for the team selected " + IDString);
                            //Makes keeping track of some players ID easy
                            String fullDisplayPlayer = IDString + ", " + pname + ", " + pDOB;
                            System.out.println(fullDisplayPlayer);
                            playerNames.add(fullDisplayPlayer);

                        }
                    }
                }

                //Used to fill up arraylists through out the app
                PlayerSelection.getPlayers(playerNames);
                statsDisplay.getPlayers(playerNames);
                PlayerList.getPlayersNames(playerNames);
                DisplayPlayerStats.getPlayers(playerNames);

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

                    int chkStat = 0;//Used to clarify if the players stat record exists for the certain fixture
                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int playerIDData = (int) jsonObject.get("Player_ID");
                        int fixtureIDData = (int) jsonObject.get("Fixture_ID");

                        if(playerID == playerIDData && fixtureID == fixtureIDData) {
                            chkStat = 1;

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

                                System.out.println("Stat =  "  + PStats[j] + " value = " + PStatsVal[j]);
                                if(Stat.equals(PStats[j])){
                                    System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                                    System.out.println("Stat being increased is "  + Stat + " value = " + PStatsVal[j]);
                                    System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                                    PStatsVal[j] = PStatsVal[j] + 1;
                                    System.out.println("Compare "  + Point);
                                }
                            }

                            System.out.println("Player Pass " + Pass + " Player pass miss " + Pass_Miss);
                            System.out.println("The id of the stat is "  + StatID);
                            String StatChosen = Integer.toString(StatID);
                            //System.out.println(TeamID);
                            connection.disconnect();
                            //Passes all the stats names and values with the stat that will be updated tp updatePlayerStat()
                            updatePlayerStat(StatChosen, PStats, PStatsVal);
                        }
                    }
                    //Used to create a stat record for a player who does not have one for a certain fixture
                    if(chkStat == 0){
                        System.out.println("Doesnt have one");
                        addPlayerStat(playerID, fixtureID, Stat);

                    }
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    //This is called when a player receives thier first statistic for a given fixture
    public static void addPlayerStat(final int playerID, final int fixtureID, final String Stat) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://142.93.44.141/stats/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("Player_ID", playerID);
                    jsonParam.put("Fixture_ID", fixtureID);
                    jsonParam.put("Pass", 0);
                    jsonParam.put("Pass_Miss", 0);
                    jsonParam.put("Point", 0);
                    jsonParam.put("Point_Miss", 0);
                    jsonParam.put("Goal", 0);
                    jsonParam.put("Goal_Miss", 0);
                    jsonParam.put("Turnover", 0);
                    jsonParam.put("Dispossessed", 0);
                    jsonParam.put("Block", 0);
                    jsonParam.put("Kickout_won", 0);
                    jsonParam.put("Kickout_lost", 0);
                    jsonParam.put("Goal_save", 0);
                    jsonParam.put("Goal_conceded", 0);
                    jsonParam.put("Yellow_card", 0);
                    jsonParam.put("Red_card", 0);
                    jsonParam.put("Black_card", 0);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                    //Once the entry in the stat table has been created
                    //then it updates the player with the statistic image that was dropped onto the player
                    playerStat(playerID, fixtureID, Stat);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    //Receives data from addPlayerStat() and updates the players statistics
    static public void updatePlayerStat(final String StatID, final String[] PStats, final int[] PStatsVal) {
        final String API = "http://142.93.44.141/stats/" + StatID;
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
                    for(int i=0; i<PStats.length; i++) {
                        jsonParam.put(PStats[i], PStatsVal[i]);
                    }

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



    //Used to get a players stats from a specific match
    //Chkclass is used to see which class is calling getPlayersFixtureStats
    static public void getPlayersFixtureStats(final int playerID, final int fixtureID, final int ChkClass) {
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

                    int chkStat = 0;//Used to clarify if the players stat record exists for the certain fixture
                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int playerIDData = (int) jsonObject.get("Player_ID");
                        int fixtureIDData = (int) jsonObject.get("Fixture_ID");

                        if(playerID == playerIDData && fixtureID == fixtureIDData) {
                            chkStat = 1;

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

                            System.out.println("Player Pass " + Pass + " Player pass miss " + Pass_Miss);
                            System.out.println("The id of the stat is "  + StatID);
                            String StatChosen = Integer.toString(StatID);
                            //System.out.println(TeamID);
                            connection.disconnect();

                            //updatePlayerStat(PStatsVal);
                            if(ChkClass == 2){
                                DisplayPlayerStats.getPlayersStats(PStatsVal, chkStat);
                            }
                            else {
                                //statsDisplay.getPlayersStats(PStatsVal, chkStat);
                            }
                        }
                    }
                    int[] PStatsVal = {1};
                    if(chkStat == 0) {
                        if(ChkClass == 2){
                            DisplayPlayerStats.getPlayersStats(PStatsVal, chkStat);
                        }
                        else {
                            //statsDisplay.getPlayersStats(PStatsVal, chkStat);
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



    // Where i learned how to post to an api https://stackoverflow.com/questions/42767249/android-post-request-with-json
    //Used to add a player to the database
    public static void addPlayer(final String PlayerName, final String DOB, final int TeamID) {
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
                    jsonParam.put("name", PlayerName);
                    jsonParam.put("DOB", DOB);
                    //jsonParam.remove("teamID");
                    jsonParam.put("TeamID", TeamID);

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

    //Used to get the selected Teams id
    static public void getTeamsID(final String Team) {
        final String API = "http://142.93.44.141/teams/";
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

                        String name = (String) jsonObject.get("name");
                        int id = (int) jsonObject.get("id");

                        if(Team.equals(name)) {
                            AddPlayer.receiveID(id);
                            AddFixture.receiveID(id);
                            //TeamMainScreen.receiveID(id);
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

    static public void updatePlayer(final int PlayerID, final String PlayerName, final String PlayerDOB) {
        final String API = "http://142.93.44.141/players/" + PlayerID;
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
                    jsonParam.put("name", PlayerName);
                    jsonParam.put("DOB", PlayerDOB);

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

    //Use to delete fixtures and players
    static public void deleteObject(final int ObjectID, final String table) {
        //final String API = "http://142.93.44.141/players/" + PlayerID;
        final String API = "http://142.93.44.141/" + table + "/" + ObjectID;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(API);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("id", ObjectID);

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

    public static void addFixture(final String HomeTeam, final String AwayTeam, final String Referee, final String Date, final String Time, final String Location) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://142.93.44.141/fixtures/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    //jsonParam.put("id", 3);
                    jsonParam.put("Home_Team", HomeTeam);
                    jsonParam.put("Away_Team", AwayTeam);
                    //jsonParam.remove("teamID");
                    jsonParam.put("Referee", Referee);
                    jsonParam.put("Date", Date);
                    jsonParam.put("Time", Time);
                    jsonParam.put("Location", Location);

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

    public static void updateFixture(final int FixtureID, final String HomeTeam, final String AwayTeam, final String Referee, final String Date, final String Time, final String Location) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://142.93.44.141/fixtures/" + FixtureID);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    //jsonParam.put("id", 3);
                    jsonParam.put("Home_Team", HomeTeam);
                    jsonParam.put("Away_Team", AwayTeam);
                    //jsonParam.remove("teamID");
                    jsonParam.put("Referee", Referee);
                    jsonParam.put("Date", Date);
                    jsonParam.put("Time", Time);
                    jsonParam.put("Location", Location);

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

    //Used to get a players stats from a specific match to be displayed in a pie chart
    //Chkclass is used to see which class is calling getPlayersFixtureStats
    /*
    static public void getPlayersStats(final int playerID, final int fixtureID, final String stat, final String playerChk) {
        final String API = "http://142.93.44.141/stats/";
        final ArrayList<Integer> playerStats = new ArrayList<Integer>();
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

                    playerStats.clear();
                    int chkStat = 0;//Used to clarify if the players stat record exists for the certain fixture
                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int playerIDData = (int) jsonObject.get("Player_ID");
                        int fixtureIDData = (int) jsonObject.get("Fixture_ID");

                        if(playerID == playerIDData && fixtureID == fixtureIDData) {
                            System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                            System.out.println("Finding player stats");
                            chkStat = 1;

                            int StatID = (int) jsonObject.get("id");
                            int Player_ID = (int) jsonObject.get("Player_ID");
                            int Fixture_ID = (int) jsonObject.get("Fixture_ID");

                            if(stat.equals("Passing")) {
                                int Pass = (int) jsonObject.get("Pass");
                                int Pass_Miss = (int) jsonObject.get("Pass_Miss");
                                System.out.println("Players pass" + Pass);
                                System.out.println("Players pass_miss" + Pass_Miss);
                                playerStats.add(Pass);
                                playerStats.add(Pass_Miss);
                            }

                            if(stat.equals("Points")) {
                                int Point = (int) jsonObject.get("Point");
                                int Point_Miss = (int) jsonObject.get("Point_Miss");
                                playerStats.add(Point);
                                playerStats.add(Point_Miss);
                                System.out.println("Players pass" + Point);
                                System.out.println("Players pass_miss" + Point_Miss);
                            }

                            if(stat.equals("Goals")) {
                                int Goal = (int) jsonObject.get("Goal");
                                int Goal_Miss = (int) jsonObject.get("Goal_Miss");
                                System.out.println("Players pass" + Goal);
                                System.out.println("Players pass_miss" + Goal_Miss);
                                playerStats.add(Goal);
                                playerStats.add(Goal_Miss);
                            }
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
                            //String[] PStats = {"id", "Player_ID", "Fixture_ID", "Pass", "Pass_Miss", "Point", "Point_Miss", "Goal", "Goal_Miss", "Turnover", "Dispossessed", "Block", "Kickout_won", "Kickout_lost", "Goal_save", "Goal_conceded", "Yellow_card", "Red_card", "Black_card"};
                            //int[] PStatsVal = {StatID, Player_ID, Fixture_ID, Pass, Pass_Miss, Point, Point_Miss, Goal, Goal_Miss, Turnover, Dispossessed, Block, Kickout_won, Kickout_lost, Goal_save, Goal_conceded, Yellow_card, Red_card, Black_card};

                            //System.out.println("Player Pass " + Pass + " Player pass miss " + Pass_Miss);
                            System.out.println("The id of the stat is "  + StatID);
                            String StatChosen = Integer.toString(StatID);

                            //Used to distinguish which player will be receiving the data
                            //Player1 is the first choice on the stat screen and
                            //Player2 is the second choice on the stat screen
                            if(playerChk.equals("Player1")) {
                                statsDisplay.receivePlayerStats(playerStats, "Player1");
                            }
                            else{
                                statsDisplay.receivePlayerStats(playerStats, "Player2");
                            }
                            //System.out.println(TeamID);
                            connection.disconnect();
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
    */

    //Used to get all players stats from a specific match to be displayed in a pie chart
    //Chkclass is used to see which class is calling getPlayersFixtureStats
    /*
    static public void getAllPlayersStats(final int fixtureID, final String stat, final String playerChk) {
        final String API = "http://142.93.44.141/stats/";
        final ArrayList<Integer> playerStats = new ArrayList<Integer>();
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

                    playerStats.clear();
                    int Team = 0;
                    int TeamMiss = 0;
                    int counter = 0;
                    int chkStat = 0;//Used to clarify if the players stat record exists for the certain fixture
                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int playerIDData = (int) jsonObject.get("Player_ID");
                        int fixtureIDData = (int) jsonObject.get("Fixture_ID");

                        if(fixtureID == fixtureIDData) {
                            chkStat = 1;
                            counter++;

                            int StatID = (int) jsonObject.get("id");
                            int Player_ID = (int) jsonObject.get("Player_ID");
                            int Fixture_ID = (int) jsonObject.get("Fixture_ID");

                            if(stat.equals("Passing")) {
                                int Pass = (int) jsonObject.get("Pass");
                                int Pass_Miss = (int) jsonObject.get("Pass_Miss");
                                Team = Pass + Team;
                                TeamMiss = Pass_Miss + TeamMiss;
                                //playerStats.add(Pass);
                                //playerStats.add(Pass_Miss);
                            }

                            if(stat.equals("Points")) {
                                int Point = (int) jsonObject.get("Point");
                                int Point_Miss = (int) jsonObject.get("Point_Miss");
                                //playerStats.add(Point);
                                //playerStats.add(Point_Miss);
                                Team = Point + Team;
                                TeamMiss = Point_Miss + TeamMiss;
                            }

                            if(stat.equals("Goals")) {
                                int Goal = (int) jsonObject.get("Goal");
                                int Goal_Miss = (int) jsonObject.get("Goal_Miss");
                                //playerStats.add(Goal);
                                //playerStats.add(Goal_Miss);
                                Team = Goal + Team;
                                TeamMiss = Goal_Miss + TeamMiss;
                            }

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

                            //System.out.println(TeamID);
                            connection.disconnect();
                        }


                    }

                    playerStats.add(Team);
                    playerStats.add(TeamMiss);
                    //System.out.println("Player Pass " + Pass + " Player pass miss " + Pass_Miss);
                    System.out.println("Passes completed " + playerStats.get(0) + " Pasesses Failed " + playerStats.get(1));
                    System.out.println("6 =========== " + counter);

                    //Used to distinguish which player will be receiving the data
                    //Player1 is the first choice on the stat screen and
                    //Player2 is the second choice on the stat screen
                    if(playerChk.equals("Player1")) {
                        statsDisplay.receivePlayerStats(playerStats, "Player1");
                    }
                    else{
                        statsDisplay.receivePlayerStats(playerStats, "Player2");
                    }

                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    */
    static public void getPlayersStats(final int playerID, final int fixtureID, final String player) {
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

                    int chkStat = 0;//Used to clarify if the players stat record exists for the certain fixture
                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int playerIDData = (int) jsonObject.get("Player_ID");
                        int fixtureIDData = (int) jsonObject.get("Fixture_ID");
                        //System.out.println("Getting " + player + " stats");

                        if(playerID == playerIDData && fixtureID == fixtureIDData) {
                            chkStat = 1;

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

                            System.out.println("Player Pass " + Pass + " Player pass miss " + Pass_Miss);
                            System.out.println("The id of the stat is "  + StatID);
                            String StatChosen = Integer.toString(StatID);
                            //System.out.println(TeamID);
                            connection.disconnect();
                            if(player.equals("Player1")){
                                System.out.println("Passing Player 1 stats");
                                statsDisplay.getPlayersStats(PStatsVal, player);
                            }

                            if(player.equals("Player2")){
                                System.out.println("Passing Player 2 stats");
                                statsDisplay.getPlayersStats(PStatsVal, player);
                            }
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


    static public void getAllPlayersStats(final int fixtureID, final String playerChk) {
        final String API = "http://142.93.44.141/stats/";
        final ArrayList<Integer> playerStats = new ArrayList<Integer>();
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

                    playerStats.clear();
                    int[] PStatsCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                    int[] PStatsVal = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

                    int chkStat = 0;//Used to clarify if the players stat record exists for the certain fixture
                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int playerIDData = (int) jsonObject.get("Player_ID");
                        int fixtureIDData = (int) jsonObject.get("Fixture_ID");

                        if(fixtureID == fixtureIDData) {
                            chkStat = 1;

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

                            PStatsCount[0] = StatID;
                            PStatsCount[1] = Player_ID;
                            PStatsCount[2] = Fixture_ID;
                            PStatsCount[3] = Pass + PStatsCount[3];
                            PStatsCount[4] = Pass_Miss + PStatsCount[4];
                            PStatsCount[5] = Point + PStatsCount[5];
                            PStatsCount[6] = Point_Miss + PStatsCount[6];
                            PStatsCount[7] = Goal + PStatsCount[7];
                            PStatsCount[8] = Goal_Miss + PStatsCount[8];
                            PStatsCount[9] = Turnover + PStatsCount[9];
                            PStatsCount[10] = Dispossessed + PStatsCount[10];
                            PStatsCount[11] = Block + PStatsCount[11];
                            PStatsCount[12] = Kickout_won + PStatsCount[12];
                            PStatsCount[13] = Kickout_lost + PStatsCount[13];
                            PStatsCount[14] = Goal_save + PStatsCount[14];
                            PStatsCount[15] = Goal_conceded + PStatsCount[15];
                            PStatsCount[16] = Yellow_card + PStatsCount[16];
                            PStatsCount[17] = Red_card + PStatsCount[17];
                            PStatsCount[18] = Black_card + PStatsCount[18];


                            //int[] PStatsVal = {StatID, Player_ID, Fixture_ID, Pass, Pass_Miss, Point, Point_Miss, Goal, Goal_Miss, Turnover, Dispossessed, Block, Kickout_won, Kickout_lost, Goal_save, Goal_conceded, Yellow_card, Red_card, Black_card};

                            //System.out.println(TeamID);
                            connection.disconnect();
                        }

                    }

                    for(int i = 0 ; i < PStatsVal.length; i++){
                        System.out.println(PStatsCount[i]);
                        PStatsVal[i] = PStatsCount[i];
                    }

                    if(playerChk.equals("Player1")){
                        System.out.println("Passing Player 1 stats");
                        statsDisplay.getPlayersStats(PStatsVal, playerChk);
                    }

                    if(playerChk.equals("Player2")){
                        System.out.println("Passing Player 2 stats");
                        statsDisplay.getPlayersStats(PStatsVal, playerChk);
                    }
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    static public void getStatScreensFixtures(final String DateChosen, final String TeamChosen) {
        final String API = "http://142.93.44.141/fixtures/";
        final ArrayList<String> fixtureDetails = new ArrayList<String>();
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

                    fixtureDetails.clear();
                    int chkStat = 0;//Used to clarify if the players stat record exists for the certain fixture
                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String Date = (String) jsonObject.get("Date");

                        String[] getYear = Date.split("-");//Used to extract the players id from the string
                        String year = getYear[0];

                        if(year.equals(DateChosen)) {
                            int StatID = (int) jsonObject.get("id");
                            String StatChosen = Integer.toString(StatID);
                            String AwayTeam = (String) jsonObject.get("Away_Team");
                            String HomeTeam = (String) jsonObject.get("Home_Team");
                            String Referee = (String) jsonObject.get("Referee");
                            String Time = (String) jsonObject.get("Time");
                            String Location = (String) jsonObject.get("Location");
                            //String fullDisplay = StatChosen + " " + HomeTeam + " vs " + AwayTeam + " Date " + Date;
                            String fullDisplay = StatChosen + ", " + HomeTeam + ", " + AwayTeam + ", " + Referee + ", " + Date + ", " + Time + ", " + Location;
                            System.out.println(fullDisplay);
                            System.out.println("This is the homeTeam " + HomeTeam);
                            //fixtureNames.add(fullDisplay);

                            //Used to get the fixtures that contain the users team as the home team
                            if(TeamChosen.equals(HomeTeam)) {
                                fixtureDetails.add(fullDisplay);
                            }
                            //connection.disconnect();
                        }
                        connection.disconnect();

                    }
                    statsDisplay.getFixtures(fixtureDetails);
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}




