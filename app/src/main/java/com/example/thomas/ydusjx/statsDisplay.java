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
    TextView Player, Fixture, Player2, Fixture2, stat;
    Intent intent;
    Button back, search;
    String TeamName;
    public static String StatSelected, player1, player2;//player1 and player2 use to distinguish between the player/team chosen first and second
    private float[] yData = {25.3f, 10.6f};
    private String[] xData = {"Thomas", "Thomas"};
    PieChart pieChart;
    public static int PID, FID, PID2, FID2;//Player id and fixture id
    public static ArrayList<Integer> player1Stats = new ArrayList<Integer>();
    public static ArrayList<Integer> player2Stats = new ArrayList<Integer>();
    public static ArrayList<String> players = new ArrayList<String>();
    public static ArrayList<String> fixtures = new ArrayList<String>();
    public static ArrayList<Integer> playersStats = new ArrayList<Integer>();
    //public static String[] statsSelect = new String[]{"Passing", "Points", "Goals", "Won possession", "Lost possession", "Red Card"};
    public static ArrayList<String> statsSelect = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statsdisplay);
        System.out.println("Entered statsDisplay");

        /*
        apiCalls.getFixtures getFixtures = new apiCalls.getFixtures();
        getFixtures.execute("http://142.93.44.141/fixtures/", "fixtures");
        */

        //Used to fill up the statistic drop down list
        statsSelect.clear();
        statsSelect.add("Passing");
        statsSelect.add("Points");
        statsSelect.add("Goals");
        statsSelect.add("Won possession");
        statsSelect.add("Lost possession");

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
                            apiCalls.getAllPlayersStats(FID, StatSelected, "Player1");
                        }
                        //Else is triggered when the user selects a player from the list
                    } else {
                        String[] getPID = selection.split(" ");//Used to extract the players id from the string
                        player1 = getPID[1];
                        System.out.println("First element from splitting string " + getPID[0]);
                        //Transforms the players ID from a string into an int
                        PID = Integer.parseInt(getPID[0]);
                        //Used to check if the user has selected a fixture
                        if (FID != 0) {
                            apiCalls.getPlayersStats(PID, FID, StatSelected, "Player1");
                        }
                        else{
                            Toast.makeText(statsDisplay.this, "Please select a Fixture", Toast.LENGTH_LONG).show();
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
                        apiCalls.getAllPlayersStats(FID, StatSelected, "Player1");
                    }
                    //Used to check if the user has selected a player from the first player drop down
                    if(PID != 0) {
                        //Calls method in apiCalls to get the certain statistics(Passing, Scoring) for a certain player
                        apiCalls.getPlayersStats(PID, FID, StatSelected, "Player1");
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
                        apiCalls.getAllPlayersStats(FID2, StatSelected, "Player2");
                    }
                }
                else{
                    String[] getPID = selection.split(" ");//Used to extract the players id from the string
                    player2 = getPID[1];
                    System.out.println("First element from splitting string " + getPID[0]);
                    PID2 = Integer.parseInt(getPID[0]);
                    if(FID2 != 0) {
                        apiCalls.getPlayersStats(PID2, FID2, StatSelected, "Player2");
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
                        apiCalls.getAllPlayersStats(FID2, StatSelected, "Player2");
                    }
                    if(PID2 != 0) {
                        apiCalls.getPlayersStats(PID2, FID2, StatSelected, "Player2");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        stat = (TextView) findViewById(R.id.stat);


        Spinner statDropDown = (Spinner) findViewById(R.id.statDropDown);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, statsSelect);
        statDropDown.setAdapter(adapter5);
        statDropDown.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String selection = (String) parent.getItemAtPosition(position);
                System.out.println("Fixture that was selected is " + selection);
                StatSelected = selection;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //Used to go back to the Team screen
        search = (Button)findViewById(R.id.Search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Team screen");
                //apiCalls.getPlayersStats(9, 10, "Passing");
                //apiCalls.getPlayersStats(9, 10, "Passing", 2);

                //Used to call the methods again to load the two specific players specific statistics
                /*
                if(PID2 != 0 && PID != 0 && FID2 != 0 && FID != 0) {
                    apiCalls.getPlayersStats(PID, FID, StatSelected, "Player1");
                    apiCalls.getPlayersStats(PID2, FID2, StatSelected, "Player2");
                }
                */

                if(PID2 != 0 && FID2 != 0) {
                    apiCalls.getPlayersStats(PID2, FID2, StatSelected, "Player2");
                }

                if(PID != 0 && FID != 0) {
                    apiCalls.getPlayersStats(PID, FID, StatSelected, "Player");
                }

                if(player1.equals("All") || player2.equals("All")){
                    apiCalls.getAllPlayersStats(FID, StatSelected, "Player1");
                    apiCalls.getAllPlayersStats(FID2, StatSelected, "Player2");
                }

                //Used to update the Pie Chart
                if(player1Stats.isEmpty()) {
                    //addDatatoChart();
                }else{
                    addDatatoChart();
                }
                //Toast.makeText(FixtureSelection.this, "No player selected",Toast.LENGTH_LONG).show();
            }
        });

        pieChart = (PieChart) findViewById(R.id.idPieChart);

        //pieChart.setDescription("Sales by employee (In Thousands $)");
        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Stats");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!


        //addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                System.out.println("This is the value of e");
                System.out.println(e);
                System.out.println(e.toString());

                int pos1 = e.toString().indexOf("Entry, x: 0.0 y: ");
                String statClicked = e.toString().substring(pos1 + 17);
                float statClickedFloat = Float.parseFloat(statClicked);
                int statClickedInt = (int) statClickedFloat;
                System.out.println(statClicked);

                if(player1Stats.isEmpty())
                {
                    //Toast.makeText(statsDisplay.this, "Select a player", Toast.LENGTH_LONG).show();
                }
                else {
                    if (player1Stats.get(2) == statClickedInt) {
                        int overall = player1Stats.get(0) + player1Stats.get(1);
                        Toast.makeText(statsDisplay.this, player1 + " " + StatSelected + "= " + player1Stats.get(0) + "/" + overall, Toast.LENGTH_LONG).show();
                    }
                    else{
                        int overall = player2Stats.get(0) + player2Stats.get(1);
                        Toast.makeText(statsDisplay.this, player2 + " " + StatSelected + "= " + player2Stats.get(0) + "/" + overall, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void addDatatoChart() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        System.out.println("In addDataSet");
        System.out.println("This is itttttt " + player1Stats.get(2));

        if(player1Stats.isEmpty()){
            System.out.println("No Player stats");
        }else{
            yEntrys.add(new PieEntry(player1Stats.get(2) , 0));
            yEntrys.add(new PieEntry(player2Stats.get(2) , 1));
        }

        xEntrys.add(player1);
        xEntrys.add(player2);


        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Player/Team Statistics");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
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
        System.out.println("In getPlayers");
        fixtures.clear();
        //fixtures.add("Select Fixture");
        fixtures.add("All");
        for(int i = 0 ; i < result.size(); i++){
            String name = (String) result.get(i);
            fixtures.add(name);
        }
    }

    //Used to receive the selected players stats from apiCalls class getPlayersStats
    protected static void receivePlayerStats(ArrayList<Integer> receivedStat, String PlayerChk) {
        //check is used to see if rhe user exists, if = 1 then the payers stat exists for the given fixture
            System.out.println(receivedStat.get(0));
            System.out.println("In getPlayersStats");
            int OverallPass = receivedStat.get(0) + receivedStat.get(1);
            float PercPass = ((float) receivedStat.get(0)/OverallPass * 100/1);
            System.out.println("Pass " + receivedStat.get(0) + "/" + OverallPass + " (" + PercPass + ")");
            int percentage = (int) PercPass;
            if(PlayerChk.equals("Player1")) {
                player1Stats.clear();
                for (int i = 0; i < receivedStat.size(); i++) {
                    player1Stats.add(receivedStat.get(i));
                }
                player1Stats.add(percentage);
            }
            else{
                player2Stats.clear();
                for (int i = 0; i < receivedStat.size(); i++) {
                    player2Stats.add(receivedStat.get(i));
                }
                player2Stats.add(percentage);
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
