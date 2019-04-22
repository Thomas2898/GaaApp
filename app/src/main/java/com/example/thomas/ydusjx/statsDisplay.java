package com.example.thomas.ydusjx;

/**
 * Created by Thomas on 19/04/2019.
 */

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.id.list;
import static android.R.layout.simple_list_item_1;


/*
This screen displays  the modules the user has entered
Also has a back button th=o bring the user to the main page
 */
public class statsDisplay extends  Activity {
    Team team;
    TextView Player, Fixture;
    Intent intent;
    Button back;
    String TeamName;
    public static int PID, FID;//Player id and fixture id
    public static ArrayList<String> players = new ArrayList<String>();
    public static ArrayList<String> fixtures = new ArrayList<String>();
    public static ArrayList<Integer> playersStats = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statsdisplay);
        System.out.println("Entered statsDisplay");

        /*
        apiCalls.getFixtures getFixtures = new apiCalls.getFixtures();
        getFixtures.execute("http://142.93.44.141/fixtures/", "fixtures");
        */

        //Used to get what team was chosen
        Bundle bundle = getIntent().getExtras();
        TeamName = bundle.getString("TeamSelected");
        System.out.println("This is the team that was selected " + TeamName);

        System.out.println("Player chosen " + PID + " Fixture chosen " + FID);

        //Used to go back to the Team screen
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Team screen");
                //Toast.makeText(FixtureSelection.this, "No player selected",Toast.LENGTH_LONG).show();
                Intent TeamMainScreen = new Intent(statsDisplay.this, TeamMainScreen.class);
                TeamMainScreen.putExtra("TeamSelected", TeamName);
                startActivity(TeamMainScreen);
            }
        });

        Player = (TextView) findViewById(R.id.Player);

        //Where i learned how to do the drop down menu
        //http://www.ahotbrew.com/android-dropdown-spinner-example/
        Spinner playerDropDown = (Spinner) findViewById(R.id.playerDropDown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, players);
        playerDropDown.setAdapter(adapter);
        playerDropDown.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String selection = (String) parent.getItemAtPosition(position);
                System.out.println("Player that was selected is " + selection);
                if(selection.equals("All")){
                    System.out.println("All chosen");
                }
                else{
                    String[] getPID = selection.split(" ");//Used to extract the players id from the string
                    System.out.println("First element from splitting string " + getPID[0]);
                    PID = Integer.parseInt(getPID[0]);
                    if(FID != 0) {
                        //apiCalls.getPlayersFixtureStats(PID, FID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Fixture = (TextView) findViewById(R.id.Fixture);

        Spinner fixtureDropDown = (Spinner) findViewById(R.id.fixtureDropDown);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, fixtures);
        fixtureDropDown.setAdapter(adapter2);
        fixtureDropDown.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String selection = (String) parent.getItemAtPosition(position);
                System.out.println("Fixture that was selected is " + selection);
                if(selection.equals("All")){
                    System.out.println("All chosen");
                }else {
                    String[] getFID = selection.split(" ");//Used to extract the players id from the string
                    System.out.println("The id of fixture selected " + getFID[0]);
                    FID = Integer.parseInt(getFID[0]);
                    if(PID != 0) {
                        //apiCalls.getPlayersFixtureStats(PID, FID);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    //This recesives data from apiCalls.java to pass the players into the arraylist players
    protected static void getPlayers(ArrayList result) {
        System.out.println("In getPlayers");
        players.clear();
        players.add("All");
        for(int i = 0 ; i < result.size(); i++){
            //Split up string to only display the user id and user name
            //String name = (String) result.get(i);
            String PlayerDetails = (String) result.get(i);
            String[] getPlayerDetails = PlayerDetails.split(", ");//Used to extract the players id from the string
            //System.out.println("First element from splitting string " + getPID[0]);
            //final int TeamID = Integer.parseInt(getPID[0]);
            String name = getPlayerDetails[0] + " " + getPlayerDetails[1];
            players.add(name);
        }
    }

    //This recesives data from apiCalls.java to pass the fixtures into the arraylist fixtures
    protected static void getFixtures(ArrayList result) {
        System.out.println("In getPlayers");
        fixtures.clear();
        fixtures.add("All");
        for(int i = 0 ; i < result.size(); i++){
            String name = (String) result.get(i);
            fixtures.add(name);
        }
    }

    //This receives data from apiCalls.java
    protected static void getPlayersStats(int result[], int check) {
        //check is used to see if rhe user exists, if = 1 then the payers stat exists for the given fixture
        if(check == 1) {
            System.out.println(result[0]);
            System.out.println("In getPlayersStats");
            int OverallPass = result[3] + result[4];
            float PercPass = ((float) result[3]/OverallPass * 100/1);
            System.out.println("Pass " + result[3] + "/" + OverallPass + " (" + PercPass + ")");
            playersStats.clear();
            for (int i = 0; i < result.length; i++) {
                playersStats.add(result[i]);
            }
        }
        else{
            System.out.println("Player Doesnt exist");
        }
    }
}
