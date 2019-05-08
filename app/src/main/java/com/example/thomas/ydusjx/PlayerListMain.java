package com.example.thomas.ydusjx;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.id.list;
import static android.R.layout.simple_list_item_1;

/**
 * Created by Thomas on 26/11/2018.
 */

public class PlayerListMain extends ListActivity {
    Team team;
    ListView mylist;
    MyDbHelper mydb;
    Intent intent;
    Button back, addplayer;
    public static String nameselect, Pid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerlist);
        System.out.println("Entered playerlistmain");

        //opens the main menu
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to modules");
                Toast.makeText(PlayerListMain.this, "No player selected",Toast.LENGTH_LONG).show();
                Intent Team = new Intent(PlayerListMain.this, MainActivity.class);
                //Used to keep the players name as it was if the user decides not to chnage the player when in the screen with the list of layer names
                startActivity(Team);
            }
        });

        mylist = (ListView) findViewById(list);
        mydb = new MyDbHelper(this);
        //Reference: The following code an Android example https://stackoverflow.com/questions/45870812/showing-data-in-listview-from-database-in-android-studio
        ArrayList<String> theList = new ArrayList<String>();
        Cursor cursor = mydb.getAllRows();
        if(cursor.getCount() == 0){
            Toast.makeText(PlayerListMain.this, "No data in the table",Toast.LENGTH_LONG).show();
        }
        else{
            while(cursor.moveToNext()){
                theList.add(cursor.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<String>(this, simple_list_item_1, theList);
                mylist.setAdapter(listAdapter);
            }
        }
        //Reference complete
    }

    //Called when a player from the list is clicked
    //Opens the PlayerUpdaeDelete screen
    protected void onListItemClick(ListView l, View v, int position, long id){
        String selection = l.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), selection, Toast.LENGTH_LONG).show();
        Intent PlayedSelected = new Intent(PlayerListMain.this, PlayerUpdateDelete.class);
        //Reference: The following code an Android example https://stackoverflow.com/questions/5343544/send-a-variable-between-classes-through-the-intent
        PlayedSelected.putExtra("PlayerSelected", selection);
        //Reference complete
        startActivity(PlayedSelected);

    }
}


