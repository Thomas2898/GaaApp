package com.example.thomas.ydusjx;

/**
 * Created by Thomas on 19/04/2019.
 */

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

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
    TextView Player, Fixture, Player2, Fixture2, stat, Pass, PassValue, PassValue2, Point, PointValue, PointValue2, Goal, GoalValue, GoalValue2, Turn, Turnover, Turnover2, Dis, Dispossessed, Dispossessed2;;
    TextView Blk, Block, Block2, Kickout, KickoutValue, KickoutValue2, Sav, Saves, Saves2, YC, YellowCard, YellowCard2, RC, RedCard, RedCard2, BC, BlackCard, BlackCard2, Player1Chosen, Player2Chosen;
    Intent intent;
    Button back, search, load;
    String TeamName;
    public static String StatSelected, player1, player2;//player1 and player2 use to distinguish between the player/team chosen first and second
    private float[] yData = {25.3f, 10.6f};
    private String[] xData = {"Thomas", "Thomas"};
    PieChart pieChart;
    public static int PID, FID, PID2, FID2;//Player id and fixture id
    public static String Player1Name, Player2Name;//Player id and fixture id
    public static ArrayList<Integer> player1Stats = new ArrayList<Integer>();
    public static ArrayList<Integer> player2Stats = new ArrayList<Integer>();
    public static ArrayList<String> players = new ArrayList<String>();
    public static ArrayList<String> fixtures = new ArrayList<String>();
    public static ArrayList<Integer> playersStats = new ArrayList<Integer>();
    //public static String[] statsSelect = new String[]{"Passing", "Points", "Goals", "Won possession", "Lost possession", "Red Card"};

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

        //Used to go back to the main Team screen
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

                    //Used to check to see if the user selected All players
                    if (selection.equals("All")) {
                        System.out.println("Team Chosen");
                        player1=selection;
                        //So the methods gettin data for a specific player cannot be called
                        PID = 0;
                        //Used to check if the user has selected a fixture and if they have then it
                        // will call a method that will get statitics
                        if(FID != 0) {
                            //apiCalls.getAllPlayersStats(FID, StatSelected, "Player1");
                        }
                        //Else is triggered when the user selects a player from the list
                    } else {
                        String[] getPID = selection.split(" ");//Used to extract the players id from the string
                        player1 = getPID[1];
                        System.out.println("First element from splitting string " + getPID[0]);
                        //Transforms the players ID from a string into an int
                        PID = Integer.parseInt(getPID[0]);
                        //Used to check if the user has selected a fixture
                        Player1Name = getPID[1];
                        if (FID != 0) {
                            apiCalls.getPlayersStats(PID, FID, "Player1");
                        }
                        else{
                            Toast.makeText(statsDisplay.this, "Please select Fixture", Toast.LENGTH_LONG).show();
                        }
                    }
                //}
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
                    Toast.makeText(statsDisplay.this, "Please select a Fixture", Toast.LENGTH_LONG).show();
                    FID = 0;
                }else {
                    String[] getFID = selection.split(", ");//Used to extract the fixture id from the string
                    System.out.println("The id of fixture selected " + getFID[0]);
                    //Used to transform the ID from String to int
                    FID = Integer.parseInt(getFID[0]);

                    if(player1.equals("All")){
                        //apiCalls.getAllPlayersStats(FID, StatSelected, "Player1");
                    }
                    //Used to check if the user has selected a player from the first player drop down
                    if(PID != 0) {
                        //Calls method in apiCalls to get the certain statistics(Passing, Scoring) for a certain player
                        apiCalls.getPlayersStats(PID, FID, "Player1");
                    }
                    else{
                        Toast.makeText(statsDisplay.this,"Please select a player", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Player2 = (TextView) findViewById(R.id.Player2);

        //Where i learned how to do the drop down menu
        //http://www.ahotbrew.com/android-dropdown-spinner-example/
        Spinner playerDropDown2 = (Spinner) findViewById(R.id.playerDropDown2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, players);
        playerDropDown2.setAdapter(adapter3);
        playerDropDown2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String selection = (String) parent.getItemAtPosition(position);
                System.out.println("Player that was selected is " + selection);

                if(selection.equals("All")){
                    System.out.println("Team chosen");
                    player2=selection;
                    PID2 = 0;
                    if(FID2 != 0) {
                        //apiCalls.getAllPlayersStats(FID2, StatSelected, "Player2");
                    }
                }
                else{
                    String[] getPID = selection.split(" ");//Used to extract the players id from the string
                    player2 = getPID[1];
                    System.out.println("First element from splitting string " + getPID[0]);
                    PID2 = Integer.parseInt(getPID[0]);
                    Player2Name = getPID[1];
                    if(FID2 != 0) {
                        apiCalls.getPlayersStats(PID2, FID2, "Player2");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Fixture2 = findViewById(R.id.Fixture2);

        Spinner fixtureDropDown2 = (Spinner) findViewById(R.id.fixtureDropDown2);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, fixtures);
        fixtureDropDown2.setAdapter(adapter4);
        fixtureDropDown2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String selection = (String) parent.getItemAtPosition(position);
                System.out.println("Fixture that was selected is " + selection);
                if(selection.equals("All")){
                    Toast.makeText(statsDisplay.this, "Please select a Fixture", Toast.LENGTH_LONG).show();
                    System.out.println("All chosen");
                    FID2 = 0;
                }else {
                    String[] getFID = selection.split(", ");//Used to extract the fixtures id from the string
                    System.out.println("The id of fixture selected " + getFID[0]);
                    FID2 = Integer.parseInt(getFID[0]);

                    if(player1.equals("All")){
                        //apiCalls.getAllPlayersStats(FID2, StatSelected, "Player2");
                    }
                    if(PID2 != 0) {
                        apiCalls.getPlayersStats(PID2, FID2, "Player2");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        search = (Button)findViewById(R.id.Search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Team screen");
                //Used to update the Pie Chart
                setStats();
            }
        });

        Player1Chosen = (TextView) findViewById(R.id.Player1Chosen);
        Player2Chosen = (TextView) findViewById(R.id.Player2Chosen);

        Pass = (TextView) findViewById(R.id.Pass);
        PassValue = (TextView) findViewById(R.id.PassValue);
        PassValue2 = (TextView) findViewById(R.id.PassValue2);

        Point = (TextView) findViewById(R.id.Point);
        PointValue = (TextView) findViewById(R.id.PointValue);
        PointValue2 = (TextView) findViewById(R.id.PointValue2);

        Goal = (TextView) findViewById(R.id.Goal);
        GoalValue = (TextView) findViewById(R.id.GoalValue);
        GoalValue2 = (TextView) findViewById(R.id.GoalValue2);

        Turn = (TextView) findViewById(R.id.Turn);
        Turnover = (TextView) findViewById(R.id.Turnover);
        Turnover2 = (TextView) findViewById(R.id.Turnover2);


        Dis = (TextView) findViewById(R.id.Dis);
        Dispossessed = (TextView) findViewById(R.id.Dispossessed);
        Dispossessed2 = (TextView) findViewById(R.id.Dispossessed2);

        Blk = (TextView) findViewById(R.id.Blk);
        Block = (TextView) findViewById(R.id.Block);
        Block2 = (TextView) findViewById(R.id.Block2);

        Kickout = (TextView) findViewById(R.id.Kickout);
        KickoutValue = (TextView) findViewById(R.id.KickoutValue);
        KickoutValue2 = (TextView) findViewById(R.id.KickoutValue2);

        Sav = (TextView) findViewById(R.id.Sav);
        Saves = (TextView) findViewById(R.id.Saves);
        Saves2 = (TextView) findViewById(R.id.Saves2);

        YC = (TextView) findViewById(R.id.YC);
        YellowCard = (TextView) findViewById(R.id.YellowCard);
        YellowCard2 = (TextView) findViewById(R.id.YellowCard2);

        RC = (TextView) findViewById(R.id.RC);
        RedCard = (TextView) findViewById(R.id.RedCard);
        RedCard2 = (TextView) findViewById(R.id.RedCard2);

        BC = (TextView) findViewById(R.id.BC);
        BlackCard = (TextView) findViewById(R.id.BlackCard);
        BlackCard2 = (TextView) findViewById(R.id.BlackCard2);
    }

    //This recesives data from apiCalls.java to pass the players into the arraylist players
    protected static void getPlayers(ArrayList result) {
        System.out.println("In getPlayers");
        players.clear();
        //players.add("Select Player");
        //players.add("Team");
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
        System.out.println("In getFixtures");
        fixtures.clear();
        //fixtures.add("Select Fixture");
        fixtures.add("All");
        for(int i = 0 ; i < result.size(); i++){
            String name = (String) result.get(i);
            fixtures.add(name);
        }
    }

    //This receives data from apiCalls.java
    protected static void getPlayersStats(int result[], String PlayerChk) {
        //check is used to see if rhe user exists, if = 1 then the payers stat exists for the given fixture
        //if(check == 1) {
            System.out.println(result[0]);
            System.out.println("In getPlayersStats");
            int OverallPass = result[3] + result[4];
            float PercPass = ((float) result[3]/OverallPass * 100/1);
            System.out.println("Pass " + result[3] + "/" + OverallPass + " (" + PercPass + ")");

            if(PlayerChk.equals("Player1")) {
                player1Stats.clear();
                for (int i = 0; i < result.length; i++) {
                    player1Stats.add(result[i]);
                }
            }
            if(PlayerChk.equals("Player2")) {
                player2Stats.clear();
                for (int i = 0; i < result.length; i++) {
                    player2Stats.add(result[i]);
                }
            }
        //}
        //else{
            //System.out.println("Player Doesnt exist");
        //}
    }

    protected void setStats() {

        if(player1Stats.isEmpty() || player2Stats.isEmpty()){
            Toast.makeText(statsDisplay.this, "Please select Fixtures/Players", Toast.LENGTH_LONG).show();
        }
        else{

            Player1Chosen.setText(Player1Name);
            Player2Chosen.setText(Player2Name);

            int OverallPass = player1Stats.get(3) + player1Stats.get(4);
            int OverallPoint = player1Stats.get(5) + player1Stats.get(6);
            int OverallGoal = player1Stats.get(7) + player1Stats.get(8);
            int OverallKickout = player1Stats.get(12) + player1Stats.get(13);
            int OverallSaves = player1Stats.get(14) + player1Stats.get(15);

            PassValue.setText(player1Stats.get(3) + "/" + OverallPass);
            PointValue.setText(player1Stats.get(5) + "/" + OverallPoint);
            GoalValue.setText(player1Stats.get(7) + "/" + OverallGoal);
            Turnover.setText(player1Stats.get(9).toString());
            Dispossessed.setText(player1Stats.get(10).toString());
            Block.setText(player1Stats.get(11).toString());
            KickoutValue.setText(player1Stats.get(12) + "/" + OverallKickout);
            Saves.setText(player1Stats.get(14) + "/" + OverallSaves);
            YellowCard.setText(player1Stats.get(15).toString());
            RedCard.setText(player1Stats.get(16).toString());
            BlackCard.setText(player1Stats.get(17).toString());

            OverallPass = player2Stats.get(3) + player2Stats.get(4);
            OverallPoint = player2Stats.get(5) + player2Stats.get(6);
            OverallGoal = player2Stats.get(7) + player2Stats.get(8);
            OverallKickout = player2Stats.get(12) + player2Stats.get(13);
            OverallSaves = player2Stats.get(14) + player2Stats.get(15);

            PassValue2.setText(player2Stats.get(3) + "/" + OverallPass);
            PointValue2.setText(player2Stats.get(5) + "/" + OverallPoint);
            GoalValue2.setText(player2Stats.get(7) + "/" + OverallGoal);
            Turnover2.setText(player2Stats.get(9).toString());
            Dispossessed2.setText(player2Stats.get(10).toString());
            Block2.setText(player2Stats.get(11).toString());
            KickoutValue2.setText(player2Stats.get(12) + "/" + OverallKickout);
            Saves2.setText(player2Stats.get(14) + "/" + OverallSaves);
            YellowCard2.setText(player2Stats.get(15).toString());
            RedCard2.setText(player2Stats.get(16).toString());
            BlackCard2.setText(player2Stats.get(17).toString());
        }
    }
}
