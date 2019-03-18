package com.example.thomas.ydusjx;

/**
 * Created by Thomas on 03/11/2018.
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

import java.util.ArrayList;

import static android.R.id.list;
import static android.R.layout.simple_list_item_1;


/*
This screen displays  the modules the user has entered
Also has a back button th=o bring the user to the main page
 */
public class PlayerList extends ListActivity {
    Team team;
    ListView mylist;
    MyDbHelper mydb;
    Intent intent;
    Button back, addplayer;
    public static int chk;
    public static String nameselect, Pid;
    public ArrayList<String> PlayersNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerlist);
        System.out.println("Entered playerlist");

        //Gets information passed from the team screen, to see which textview has been clicked
        Bundle bundle = getIntent().getExtras();
        nameselect = bundle.getString("NameSelect");
        Pid = bundle.getString("Pid");
        System.out.println("Pid =  " + Pid);

        //Used to go back to the Team screen
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Team screen");
                Toast.makeText(PlayerList.this, "No player selected",Toast.LENGTH_LONG).show();
                Intent Team = new Intent(PlayerList.this, Team.class);
                //Used to keep the players name as it was if the user decides not to chnage the player when in the screen with the list of layer names
                Team.putExtra("PlayerSelected", nameselect);
                Team.putExtra("NewPlayer", nameselect);
                Team.putExtra("Pid", Pid);
                startActivity(Team);
            }
        });

        //Used to move to the add player screen
        addplayer = (Button)findViewById(R.id.AddPlayer);
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

        /*
        Cursor cursor = mydb.getAllRows();
        if(cursor.getCount() == 0){
            Toast.makeText(PlayerList.this, "No data in the table",Toast.LENGTH_LONG).show();
        }
        else{
            while(cursor.moveToNext()){
                theList.add(cursor.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<String>(this, simple_list_item_1, theList);
                mylist.setAdapter(listAdapter);
            }
        }
        */
        //Reference complete


        for(int i = 0 ; i < PlayersNames.size(); i++){
            theList.add(PlayersNames.get(i));
            ListAdapter listAdapter = new ArrayAdapter<String>(this, simple_list_item_1, theList);
            mylist.setAdapter(listAdapter);
        }
    }

    //This is called when a player from the list is clicked
    //This opens the team screen with the player selected in the position of the textview selected
    protected void onListItemClick(ListView l, View v, int position, long id){
        String selection = l.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), selection, Toast.LENGTH_LONG).show();
        Intent PlayedSelected = new Intent(PlayerList.this, Team.class);
        //Used tp pass a string to NoteList
        //Reference: The following code an Android example https://stackoverflow.com/questions/5343544/send-a-variable-between-classes-through-the-intent
        PlayedSelected.putExtra("PlayerSelected", nameselect);
        PlayedSelected.putExtra("NewPlayer", selection);
        PlayedSelected.putExtra("Pid", Pid);
        //team.setPlayerName();
        //Reference complete
        startActivity(PlayedSelected);

    }
}

