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

        /*
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
        */

        //myApi = new apiCalls(this);
        //Used to open the screen playerlistmain
    }
}
