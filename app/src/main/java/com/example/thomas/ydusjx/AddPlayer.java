package com.example.thomas.ydusjx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPlayer extends Activity {
    EditText nameInput, DOBInput;//Name and DOB input
    Button btnAddPlayer;
    Button back;
    TextView header;//Text on top of screen
    String TeamName;
    static int TeamID;
    public static String nameselect, Pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addplayer);

        //Gets information from playerlist screen
        Bundle bundle = getIntent().getExtras();
        TeamName = bundle.getString("TeamSelected");
        System.out.println("This is the team that was selected " + TeamName);
        /*
        String[] getPID = TeamName.split(" ");//Used to extract the players id from the string
        System.out.println("First element from splitting string " + getPID[0]);
        final int TeamID = Integer.parseInt(getPID[0]);
        */

        //Used to move back to the playerlistmain or to the playerlist screen
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Main screen");
                Intent PlayerSelection = new Intent(AddPlayer.this, PlayerSelection.class);
                PlayerSelection.putExtra("TeamSelected", TeamName);
                apiCalls.getPlayers getPlayers = new apiCalls.getPlayers();
                getPlayers.execute("http://142.93.44.141/teams", TeamName);
                startActivity(PlayerSelection);
            }
        });

        header = (TextView)findViewById(R.id.header);
        nameInput = (EditText)findViewById(R.id.userInput);
        DOBInput = (EditText)findViewById(R.id.userInput2);
        btnAddPlayer = (Button)findViewById(R.id.AddPlayer);

        //Used tp pass the new players data to a class in apiCalls to add to the framework
        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player_name = nameInput.getText().toString();
                String DOB = DOBInput.getText().toString();

                //Used to check if the user has entered nothing
                if(player_name.isEmpty())
                {
                    Toast.makeText(AddPlayer.this, "Please enter a name",Toast.LENGTH_LONG).show();
                }
                else if(DOB.isEmpty()){
                    Toast.makeText(AddPlayer.this, "Please enter a date of birth",Toast.LENGTH_LONG).show();
                }
                else{
                    System.out.println("This is the player that was selected " + player_name);
                    System.out.println("This is the DOB that was selected " + DOB);
                    System.out.println("This is the teams ID that was selected " + TeamID);
                    apiCalls.addPlayer(player_name, DOB, TeamID);
                    nameInput.getText().clear();
                    DOBInput.getText().clear();
                    apiCalls.getPlayers getPlayers = new apiCalls.getPlayers();
                    getPlayers.execute("http://142.93.44.141/teams", TeamName);
                }
            }
        });
    }

    //Used to receive the selected teams id
    protected static void receiveID(int TID) {
        System.out.println("In receiveID");
        TeamID = TID;
    }
}

