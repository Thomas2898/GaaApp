package com.example.thomas.ydusjx;

/**
 * Created by Thomas on 01/04/2019.
 */

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.id.list;
import static android.R.layout.simple_list_item_1;


/*
This screen displays the players relating to the team chosen
it is activate by the players button in TeamMainScreen
 */
public class PlayerSelection extends ListActivity {
    Team team;
    ListView mylist;
    static ListView teamList;
    MyDbHelper mydb;
    Intent intent;
    Button back, addplayer;
    public static int chk;
    String TeamName;
    public static ArrayList<String> players = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerselection);
        System.out.println("Entered FixtureSelection");

        /*
        apiCalls.getFixtures getFixtures = new apiCalls.getFixtures();
        getFixtures.execute("http://142.93.44.141/fixtures/", "fixtures");
        */

        //Used to get what team was chosen
        Bundle bundle = getIntent().getExtras();
        TeamName = bundle.getString("TeamSelected");
        System.out.println("This is the team that was selected " + TeamName);

        //Used to go back to the Team screen
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Team screen");
                //Toast.makeText(FixtureSelection.this, "No player selected",Toast.LENGTH_LONG).show();
                Intent TeamMainScreen = new Intent(PlayerSelection.this, TeamMainScreen.class);
                TeamMainScreen.putExtra("TeamSelected", TeamName);
                startActivity(TeamMainScreen);
            }
        });

        //Used to open addPlayer screen
        addplayer = (Button)findViewById(R.id.AddPlayer);
        addplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to AddPlayer class");
                //Toast.makeText(FixtureSelection.this, "No player selected",Toast.LENGTH_LONG).show();
                Intent TeamMainScreen = new Intent(PlayerSelection.this, AddPlayer.class);
                TeamMainScreen.putExtra("TeamSelected", TeamName);
                startActivity(TeamMainScreen);
            }
        });

        mylist = (ListView) findViewById(list);
        //Reference: The following code an Android example https://stackoverflow.com/questions/45870812/showing-data-in-listview-from-database-in-android-studio
        ArrayList<String> theList = new ArrayList<String>();

        for(int i = 0 ; i < players.size(); i++){
            theList.add(players.get(i));
            ListAdapter listAdapter = new ArrayAdapter<String>(this, simple_list_item_1, theList);
            mylist.setAdapter(listAdapter);
        }
    }

    //This recesives data from apiCalls.java
    protected static void getPlayers(ArrayList result) {
        System.out.println("In getPlayers");
        players.clear();
        for(int i = 0 ; i < result.size(); i++){
            String name = (String) result.get(i);

            String PlayerDetails = (String) result.get(i);
            String[] getPlayerDetails = PlayerDetails.split(",");//Used to extract the players id from the string
            System.out.println("First element from splitting string " + getPlayerDetails[1]);
            //final int TeamID = Integer.parseInt(getPID[0]);
            //String name = getPlayerDetails[0] + " " + getPlayerDetails[1] + " " + getPlayerDetails[2];
            players.add(name);
        }
    }

    //This is called when a player from the list is clicked
    //This opens the team screen with the player selected in the position of the textview selected
    protected void onListItemClick(ListView l, View v, int position, long id){
        String selection = l.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), selection, Toast.LENGTH_LONG).show();
        Intent PlayerSelected = new Intent(PlayerSelection.this, UpdateDeletePlayer.class);
        //Used tp pass a string to NoteList
        //Reference: The following code an Android example https://stackoverflow.com/questions/5343544/send-a-variable-between-classes-through-the-intent
        PlayerSelected.putExtra("TeamSelected", TeamName);
        PlayerSelected.putExtra("PlayerSelected", selection);
        System.out.println("Fixture that is selected " + selection);

        //team.setPlayerName();
        //Reference complete
        startActivity(PlayerSelected);
    }


}
