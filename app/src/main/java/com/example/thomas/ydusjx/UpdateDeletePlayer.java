package com.example.thomas.ydusjx;

/**
 * Created by Thomas on 21/04/2019.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//Used to update and delete a chosen Player
public class UpdateDeletePlayer extends Activity {
    EditText nameInput, DOBInput;//Name and DOB input
    Button btnUpdatePlayer, btnDeletePlayer;
    Button back;
    TextView header;//Text on top of screen
    String TeamName;
    static int TeamID;
    public static String nameselect, Pid;
    private String PlayerChosen;
    public static ArrayList<String> playersDetails = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatedeleteplayer);

        //Gets information from playerlist screen
        Bundle bundle = getIntent().getExtras();
        TeamName = bundle.getString("TeamSelected");
        PlayerChosen = bundle.getString("PlayerSelected");
        System.out.println("This is the team that was selected " + TeamName);
        System.out.println("This is the player that was selected " + PlayerChosen);

        String[] getPID = PlayerChosen.split(", ");//Used to extract the players id from the string
        System.out.println("First element from splitting string " + getPID[0]);
        final int PlayerID = Integer.parseInt(getPID[0]);
        //Used to get data for the edittext box with the player the user selected to update/delete
        String PlayerName = getPID[1];
        String DOB = getPID[2];


        //Used to move back to the playerlistmain or to the playerlist screen
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Main screen");
                Intent PlayerSelection = new Intent(UpdateDeletePlayer.this, PlayerSelection.class);
                PlayerSelection.putExtra("TeamSelected", TeamName);
                apiCalls.getPlayers getPlayers = new apiCalls.getPlayers();
                getPlayers.execute("http://142.93.44.141/teams", TeamName);
                startActivity(PlayerSelection);
            }
        });

        header = (TextView)findViewById(R.id.header);
        nameInput = (EditText)findViewById(R.id.userInput);
        DOBInput = (EditText)findViewById(R.id.userInput2);
        btnUpdatePlayer = (Button)findViewById(R.id.UpdatePlayer);
        btnDeletePlayer = (Button)findViewById(R.id.DeletePlayer);

        //Used to set the data for the edittext box with the player the user selected to update/delete
        nameInput.setText(PlayerName);
        DOBInput.setText(DOB);

        //Used tp pass the new players data to a class in apiCalls to add to the framework
        btnUpdatePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player_name = nameInput.getText().toString();
                String DOB = DOBInput.getText().toString();

                //Used to check if the user has entered nothing
                if(player_name.isEmpty())
                {
                    Toast.makeText(UpdateDeletePlayer.this, "Please enter a name",Toast.LENGTH_LONG).show();
                }
                else if(DOB.isEmpty()){
                    Toast.makeText(UpdateDeletePlayer.this, "Please enter a date of birth",Toast.LENGTH_LONG).show();
                }
                else{
                    System.out.println("This is the player that was selected " + player_name);
                    System.out.println("This is the DOB that was selected " + DOB);
                    System.out.println("This is the teams ID that was selected " + TeamID);
                    apiCalls.updatePlayer(PlayerID, player_name, DOB);
                    apiCalls.getPlayers getPlayers = new apiCalls.getPlayers();
                    getPlayers.execute("http://142.93.44.141/teams", TeamName);
                }
            }
        });

        btnDeletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Delete player");
                apiCalls.deleteObject(PlayerID, "players");
                apiCalls.getPlayers getPlayers = new apiCalls.getPlayers();
                getPlayers.execute("http://142.93.44.141/teams", TeamName);
            }
        });
    }

    //Used to receive the selected teams id
    protected static void receiveID(int TID) {
        System.out.println("In receiveID");
        TeamID = TID;
    }

}


