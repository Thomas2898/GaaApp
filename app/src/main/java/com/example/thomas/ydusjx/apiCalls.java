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

/**
 * Created by Thomas on 26/01/2019.
 */

public class apiCalls extends Activity {
    ProgressDialog pd;

    /*
    public Cursor getAllRows(){
        //SQLiteDatabase db = this.getWritableDatabase();
        //String query = "SELECT * FROM " + TABLE_PlAYER;
        //Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
        //Reference complete
    }
    */
    //https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
    protected class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(apiCalls.this);
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
                String[] PlayersNames = {"Test1", "Test2"};
                //String[] Players = {Team.player1,Team.player2,Team.player3};
                JSONArray jsonArray = new JSONArray(buffer.toString());
                for(int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int teamId = (int) jsonObject.get("teamID");
                    //System.out.println("LOOP RESULT t " + teamId);
                    if(teamId == 1) {
                        String jsonObjectAsString = (String) jsonObject.get("name");
                        PlayersNames[i] = jsonObjectAsString;
                        //PlayerList.PlayersNames.add("A");
                        System.out.println("LOOP RESULT " + jsonObjectAsString);
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
}
