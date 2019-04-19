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
    ListView mylist;
    TextView Player, Fixture;
    static ListView teamList;
    MyDbHelper mydb;
    Intent intent;
    Button back, addplayer;
    public static int chk;
    String TeamName;
    public static ArrayList<String> players = new ArrayList<String>();
    public static ArrayList<String> fixtures = new ArrayList<String>();

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    //This recesives data from apiCalls.java
    protected static void getPlayers(ArrayList result) {
        System.out.println("In getPlayers");
        players.clear();
        players.add("All");
        for(int i = 0 ; i < result.size(); i++){
            String name = (String) result.get(i);
            players.add(name);
        }
    }

    //This recesives data from apiCalls.java
    protected static void getFixtures(ArrayList result) {
        System.out.println("In getPlayers");
        fixtures.clear();
        fixtures.add("All");
        for(int i = 0 ; i < result.size(); i++){
            String name = (String) result.get(i);
            fixtures.add(name);
        }
    }

    /*
    //This is called when a player from the list is clicked
    //This opens the team screen with the player selected in the position of the textview selected
    protected void onListItemClick(ListView l, View v, int position, long id){
        String selection = l.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), selection, Toast.LENGTH_LONG).show();
        Intent TeamSelected = new Intent(TeamSelection.this, TeamMainScreen.class);
        //Used tp pass a string to NoteList
        //Reference: The following code an Android example https://stackoverflow.com/questions/5343544/send-a-variable-between-classes-through-the-intent
        TeamSelected.putExtra("TeamSelected", selection);

        //team.setPlayerName();
        //Reference complete
        startActivity(TeamSelected);

    }
    */

}
