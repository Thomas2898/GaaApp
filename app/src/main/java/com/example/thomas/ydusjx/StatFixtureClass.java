package com.example.thomas.ydusjx;

/**
 * Created by Thomas on 13/04/2019.
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
This screen is called when the user clicks he Record statistics button.
It is used to allow the user to click which fixture they would like to
record the statistics for
 */
public class StatFixtureClass extends ListActivity {
    Team team;
    ListView mylist;
    static ListView teamList;
    TextView Header;
    MyDbHelper mydb;
    Intent intent;
    Button back, addplayer;
    //public static int chk;
    String TeamName, ScreenCalled;//ScreenCalled is used to see which screen opened FixtureSelection.java
    public static ArrayList<String> Statfixtures = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statfixtureselection);
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
                Intent TeamMainScreen = new Intent(StatFixtureClass.this, TeamMainScreen.class);
                TeamMainScreen.putExtra("TeamSelected", TeamName);
                startActivity(TeamMainScreen);
            }
        });

        Header = (TextView) findViewById(R.id.Header);
        mylist = (ListView) findViewById(list);
        //Reference: The following code an Android example https://stackoverflow.com/questions/45870812/showing-data-in-listview-from-database-in-android-studio
        ArrayList<String> theList = new ArrayList<String>();

        for(int i = 0 ; i < Statfixtures.size(); i++){
            theList.add(Statfixtures.get(i));
            ListAdapter listAdapter = new ArrayAdapter<String>(this, simple_list_item_1, theList);
            mylist.setAdapter(listAdapter);
        }
    }

    //This recesives data from apiCalls
    protected static void getStatFixtures(ArrayList result) {
        System.out.println("In getFixtures");
        Statfixtures.clear();
        for(int i = 0 ; i < result.size(); i++){
            String name = (String) result.get(i);
            Statfixtures.add(name);
        }
    }


    //This is called when a player from the list is clicked
    //This opens the team screen with the player selected in the position of the textview selected
    protected void onListItemClick(ListView l, View v, int position, long id){
        String selection = l.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), selection, Toast.LENGTH_LONG).show();
        String[] FixtureString = selection.split(" ");
        System.out.println("The id of selected fixture is " + FixtureString[0]);
        System.out.println("Moving to team");

        //Used to pass a string to the team screen as the line "str = bundle.getString("PlayerSelected");" would bring back an error as no string was passed
        String PlayerName = "PlayerName";
        Intent TeamDisplay = new Intent(StatFixtureClass.this, Team.class);
        TeamDisplay.putExtra("PlayerSelected", PlayerName);
        TeamDisplay.putExtra("TeamSelected", TeamName);
        TeamDisplay.putExtra("FixtureID", FixtureString[0]);
        startActivity(TeamDisplay);

    }


}