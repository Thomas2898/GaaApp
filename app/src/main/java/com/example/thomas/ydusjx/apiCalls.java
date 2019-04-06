package com.example.thomas.ydusjx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Thomas on 26/01/2019.
 */

public class apiCalls extends Activity {
    public JSONArray getPlayers;

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
                    //MainActivity.displayPlayers(p1);
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



    //Used to get Fixtures names from the restframework
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

                        String AwayTeam = (String) jsonObject.get("Away_Team");
                        String HomeTeam = (String) jsonObject.get("Home_Team");
                        String Date = (String) jsonObject.get("Date");
                        String fullDisplay = HomeTeam + " vs " + AwayTeam + " Date " + Date;
                        System.out.println(fullDisplay);
                        System.out.println("This is the param " + params[1]);
                        System.out.println("This is the homeTeam " + HomeTeam);
                        //fixtureNames.add(fullDisplay);


                        if(params[1].equals(HomeTeam)) {
                            fixtureNames.add(fullDisplay);
                        }
                    }
                    FixtureSelection.getFixtures(fixtureNames);
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

    //Used to get Player names from the restframework
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


                /*
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

    //This recesives data from the
    protected static void storePID(String result) {
        System.out.println("StorePID received " + result);

        loadPlayers loadPlayerID = new loadPlayers();
        loadPlayerID.execute("http://142.93.44.141/players/", result);
    }







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

                //Split the string up
                String[] PlayerIDs = params[1].split(" ");
                System.out.println("First element from splitting string " + PlayerIDs[0]);

                JSONArray jsonArray = new JSONArray(buffer.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //p1 = (JSONArray) jsonObject.get("players");
                    int pid = (int) jsonObject.get("id");
                    String pname = (String) jsonObject.get("name");
                    String pDOB = (String) jsonObject.get("DOB");

                    String IDString = (String) Integer.toString(pid);
                    for (int j = 0; j < PlayerIDs.length; j++) {

                        if(IDString.equals(PlayerIDs[j])){
                            System.out.println("Players id for the team selected " + IDString);
                            String fullDisplayPlayer = IDString + " " + pname + " " + pDOB;
                            System.out.println(fullDisplayPlayer);
                            playerNames.add(fullDisplayPlayer);

                        }
                    }
                }
                PlayerSelection.getPlayers(playerNames);

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

