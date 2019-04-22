package com.example.thomas.ydusjx;

/**
 * Created by Thomas on 20/04/2019.
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
public class DisplayPlayerStats extends  Activity {
    Team team;
    TextView Player, Pass, PassValue, Point, PointValue, Goal, GoalValue, Turn, Turnover, Dis, Dispossessed;
    TextView Blk, Block, Kickout, KickoutValue, Sav, Saves, YC, YellowCard, RC, RedCard, BC, BlackCard;
    Intent intent;
    Button back, Search;
    public static int PID, FID;//Player id and fixture id
    public static String nameselect, Pid, FixtureID, TeamName;
    public static ArrayList<String> players = new ArrayList<String>();
    public static ArrayList<String> fixtures = new ArrayList<String>();
    public static ArrayList<Integer> playersStats = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayplayerstats);
        System.out.println("Entered statsDisplay");

        /*
        apiCalls.getFixtures getFixtures = new apiCalls.getFixtures();
        getFixtures.execute("http://142.93.44.141/fixtures/", "fixtures");
        */

        //Used to get what team was chosen
        Bundle bundle = getIntent().getExtras();
        //TeamName = bundle.getString("TeamSelected");
        //System.out.println("This is the team that was selected " + TeamName);
        FixtureID = bundle.getString("FixtureSelected");
        TeamName = bundle.getString("TeamSelected");
        System.out.println("Team name in PlayerList is " + TeamName);
        System.out.println("Fixture id =  " + FixtureID);
        FID = Integer.parseInt(FixtureID);

        //System.out.println("Player chosen " + PID + " Fixture chosen " + FID);

        //Used to go back to the Team screen
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Team screen");
                //Toast.makeText(FixtureSelection.this, "No player selected",Toast.LENGTH_LONG).show();
                Intent Team = new Intent(DisplayPlayerStats.this, Team.class);
                Team.putExtra("FixtureID", FixtureID);
                Team.putExtra("TeamSelected", TeamName);
                startActivity(Team);
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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String selection = (String) parent.getItemAtPosition(position);
                System.out.println("Player that was selected is " + selection);
                String[] getPID = selection.split(" ");//Used to extract the players id from the string
                System.out.println("First element from splitting string " + getPID[0]);
                PID = Integer.parseInt(getPID[0]);
                System.out.println("Player chosen " + PID + " Fixture chosen " + FID);
                apiCalls.getPlayersFixtureStats(PID, FID, 2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //Used to go back to the Team screen
        Search = (Button)findViewById(R.id.Search);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Team screen");
                //Toast.makeText(FixtureSelection.this, "No player selected",Toast.LENGTH_LONG).show();
                if(playersStats.isEmpty()){
                    System.out.println("ArrayList empty");
                }
                else{
                    setStats();
                }
            }
        });

        Pass = (TextView) findViewById(R.id.Pass);
        PassValue = (TextView) findViewById(R.id.PassValue);

        Point = (TextView) findViewById(R.id.Point);
        PointValue = (TextView) findViewById(R.id.PointValue);

        Goal = (TextView) findViewById(R.id.Goal);
        GoalValue = (TextView) findViewById(R.id.GoalValue);

        Turn = (TextView) findViewById(R.id.Turn);
        Turnover = (TextView) findViewById(R.id.Turnover);

        Dis = (TextView) findViewById(R.id.Dis);
        Dispossessed = (TextView) findViewById(R.id.Dispossessed);

        Blk = (TextView) findViewById(R.id.Blk);
        Block = (TextView) findViewById(R.id.Block);

        Kickout = (TextView) findViewById(R.id.Kickout);
        KickoutValue = (TextView) findViewById(R.id.KickoutValue);

        Sav = (TextView) findViewById(R.id.Sav);
        Saves = (TextView) findViewById(R.id.Saves);

        YC = (TextView) findViewById(R.id.YC);
        YellowCard = (TextView) findViewById(R.id.YellowCard);

        RC = (TextView) findViewById(R.id.RC);
        RedCard = (TextView) findViewById(R.id.RedCard);

        BC = (TextView) findViewById(R.id.BC);
        BlackCard = (TextView) findViewById(R.id.BlackCard);
    }

    //This recesives data from apiCalls.java to pass the players into the arraylist players
    protected static void getPlayers(ArrayList result) {
        System.out.println("In getPlayers");
        players.clear();
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

    protected void setStats() {
        PassValue = (TextView) findViewById(R.id.PassValue);
        PointValue = (TextView) findViewById(R.id.PointValue);
        GoalValue = (TextView) findViewById(R.id.GoalValue);
        Turnover = (TextView) findViewById(R.id.Turnover);
        Dispossessed = (TextView) findViewById(R.id.Dispossessed);
        Block = (TextView) findViewById(R.id.Block);
        KickoutValue = (TextView) findViewById(R.id.KickoutValue);
        Saves = (TextView) findViewById(R.id.Saves);
        YellowCard = (TextView) findViewById(R.id.YellowCard);
        RedCard = (TextView) findViewById(R.id.RedCard);
        BlackCard = (TextView) findViewById(R.id.BlackCard);


        int OverallPass = playersStats.get(3) + playersStats.get(4);
        int OverallPoint = playersStats.get(5) + playersStats.get(6);
        int OverallGoal = playersStats.get(7) + playersStats.get(8);
        int OverallKickout = playersStats.get(12) + playersStats.get(13);
        int OverallSaves = playersStats.get(14) + playersStats.get(15);

        PassValue.setText(playersStats.get(3) + "/" + OverallPass);
        PointValue.setText(playersStats.get(5) + "/" + OverallPoint);
        GoalValue.setText(playersStats.get(7) + "/" + OverallGoal);
        Turnover.setText(playersStats.get(9).toString());
        Dispossessed.setText(playersStats.get(10).toString());
        Block.setText(playersStats.get(11).toString());
        KickoutValue.setText(playersStats.get(12) + "/" + OverallKickout);
        Saves.setText(playersStats.get(14) + "/" + OverallSaves);
        YellowCard.setText(playersStats.get(15).toString());
        RedCard.setText(playersStats.get(16).toString());
        BlackCard.setText(playersStats.get(17).toString());
    }

}

