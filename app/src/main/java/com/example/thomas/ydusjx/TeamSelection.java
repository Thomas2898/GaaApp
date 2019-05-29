package com.example.thomas.ydusjx;

/**
 * Created by Thomas on 31/03/2019.
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
Screen that displays the teams gotten from the database
 */
public class TeamSelection extends ListActivity {
    Team team;
    ListView mylist;
    static ListView teamList;
    MyDbHelper mydb;
    Intent intent;
    Button back, addplayer;
    public static int chk;
    public static String nameselect, Pid;
    public ArrayList<String> PlayersNames = new ArrayList<String>();
    public static ArrayList<String> teamNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamselection);
        System.out.println("Entered Teamlist");

        apiCalls.getTeams getTeam = new apiCalls.getTeams();
        getTeam.execute("http://142.93.44.141/teams", "teams");
        System.out.println("This is the returned values in TeamSelection " + getTeam);

        //Used to go back to the Team screen
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Team screen");
                Toast.makeText(TeamSelection.this, "No player selected",Toast.LENGTH_LONG).show();
                Intent Main = new Intent(TeamSelection.this, MainActivity.class);
                startActivity(Main);
            }
        });

        /*
        //Used to move to the add player screen
        addplayer = (Button)findViewById(R.id.AddTeam);
        addplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to modules");
                Intent NewPlayer = new Intent(PlayerList.this, AddPlayer.class);
                NewPlayer.putExtra("PlayerSelected", nameselect);
                NewPlayer.putExtra("NewPlayer", nameselect);
                NewPlayer.putExtra("Pid", Pid);
                startActivity(NewPlayer);
            }
        });
        */

        System.out.println("After call");
        mylist = (ListView) findViewById(list);
        mydb = new MyDbHelper(this);
        String[] Test = {"A","B","C","D"};
        //Reference: The following code an Android example https://stackoverflow.com/questions/45870812/showing-data-in-listview-from-database-in-android-studio
        ArrayList<String> theList = new ArrayList<String>();
        //ArrayList<String> PlayersNames = new ArrayList<String>();
        PlayersNames.add("A");
        PlayersNames.add("B");
        PlayersNames.add("C");
        PlayersNames.add("D");

        for(int i = 0 ; i < teamNames.size(); i++){
            theList.add(teamNames.get(i));
            ListAdapter listAdapter = new ArrayAdapter<String>(this, simple_list_item_1, theList);
            mylist.setAdapter(listAdapter);
        }
    }

    //This recesives data from the
    protected static void getTeam(ArrayList result) {
        System.out.println("In getTeam");
        teamNames.clear();
        for(int i = 0 ; i < result.size(); i++){
            String name = (String) result.get(i);
            teamNames.add(name);
        }
    }

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

}


