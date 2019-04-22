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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.id.list;
import static android.R.layout.simple_list_item_1;


/*
This screen displays  the modules the user has entered
Also has a back button th=o bring the user to the main page
 */
public class FixtureSelection extends ListActivity {
    Team team;
    ListView mylist;
    static ListView teamList;
    TextView Header;
    MyDbHelper mydb;
    Intent intent;
    Button back, AddFixture;
    //public static int chk;
    String TeamName, ScreenCalled;//ScreenCalled is used to see which screen opened FixtureSelection.java
    public static ArrayList<String> fixtures = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixtureselection);
        System.out.println("Entered FixtureSelection");

        /*
        apiCalls.getFixtures getFixtures = new apiCalls.getFixtures();
        getFixtures.execute("http://142.93.44.141/fixtures/", "fixtures");
        */

        Bundle bundle = getIntent().getExtras();
        TeamName = bundle.getString("TeamSelected");
        //ScreenCalled = bundle.getString("ScreenCalled");
        System.out.println("This is the team that was selected " + TeamName);
        //System.out.println("This is the team that was selected " + ScreenCalled);

        //Used to go back to the Team screen
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Team screen");
                //Toast.makeText(FixtureSelection.this, "No player selected",Toast.LENGTH_LONG).show();
                Intent TeamMainScreen = new Intent(FixtureSelection.this, TeamMainScreen.class);
                TeamMainScreen.putExtra("TeamSelected", TeamName);
                startActivity(TeamMainScreen);
            }
        });

        //Used to go back to the Team screen
        AddFixture = (Button)findViewById(R.id.AddFixture);
        AddFixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to AddFixture class");
                //Toast.makeText(FixtureSelection.this, "No player selected",Toast.LENGTH_LONG).show();
                Intent AddFixture = new Intent(FixtureSelection.this, AddFixture.class);
                AddFixture.putExtra("TeamSelected", TeamName);
                startActivity(AddFixture);
            }
        });

        mylist = (ListView) findViewById(list);
        //Reference: The following code an Android example https://stackoverflow.com/questions/45870812/showing-data-in-listview-from-database-in-android-studio
        ArrayList<String> theList = new ArrayList<String>();

        for(int i = 0 ; i < fixtures.size(); i++){
            theList.add(fixtures.get(i));
            ListAdapter listAdapter = new ArrayAdapter<String>(this, simple_list_item_1, theList);
            mylist.setAdapter(listAdapter);
        }
    }

    //This recesives data from apiCalls
    protected static void getFixtures(ArrayList result) {
        System.out.println("In getFixtures");
        fixtures.clear();
        for(int i = 0 ; i < result.size(); i++){
            String name = (String) result.get(i);
            fixtures.add(name);
        }
    }


    //This is called when a player from the list is clicked
    //This opens the team screen with the player selected in the position of the textview selected
    protected void onListItemClick(ListView l, View v, int position, long id){
        String selection = l.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), selection, Toast.LENGTH_LONG).show();
        //Intent TeamSelected = new Intent(TeamSelection.this, TeamMainScreen.class);
        //Used tp pass a string to NoteList
        //Reference: The following code an Android example https://stackoverflow.com/questions/5343544/send-a-variable-between-classes-through-the-intent
        //TeamSelected.putExtra("TeamSelected", selection);

        //team.setPlayerName();
        //Reference complete
        //startActivity(TeamSelected);

    }


}