package com.example.thomas.ydusjx;

/**
 * Created by Thomas on 22/04/2019.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//This is the code for the screen that is used to add a fixture
public class AddFixture extends Activity {
    EditText HomeTeamInput, AwayTeamInput, RefereeInput, DateInput, TimeInput, LocationInput;
    Button btnAddFixture;
    Button back;
    TextView header;//Text on top of screen
    String TeamName;
    static int TeamID;
    public static String nameselect, Pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfixture);

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
                Intent PlayerSelection = new Intent(AddFixture.this, FixtureSelection.class);
                PlayerSelection.putExtra("TeamSelected", TeamName);
                //apiCalls.getPlayers getPlayers = new apiCalls.getPlayers();
                //getPlayers.execute("http://142.93.44.141/teams", TeamName);
                startActivity(PlayerSelection);
            }
        });

        header = (TextView)findViewById(R.id.header);
        //HomeTeamInput = (EditText)findViewById(R.id.HomeInput);
        AwayTeamInput = (EditText)findViewById(R.id.AwayInput);
        RefereeInput = (EditText)findViewById(R.id.RefereeInput);
        DateInput = (EditText)findViewById(R.id.DateInput);
        TimeInput = (EditText)findViewById(R.id.TimeInput);
        LocationInput = (EditText)findViewById(R.id.LocationInput);
        btnAddFixture = (Button)findViewById(R.id.AddFixture);

        //Used tp pass the new players data to a class in apiCalls to add to the framework
        btnAddFixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String home_team = HomeTeamInput.getText().toString();
                String away_team = AwayTeamInput.getText().toString();
                String referee = RefereeInput.getText().toString();
                String date = DateInput.getText().toString();
                String time = TimeInput.getText().toString();
                String location = LocationInput.getText().toString();

                System.out.println("This is the valueeeeeeeeeeeeee");
                System.out.println(away_team.isEmpty());

                //Used to check if the user has entered nothing
                if(away_team.isEmpty() || referee.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty())
                {
                    Toast.makeText(AddFixture.this, "Please fill in the details",Toast.LENGTH_LONG).show();
                }
                else{
                    //System.out.println("This is the player that was selected " + player_name);
                    //System.out.println("This is the DOB that was selected " + DOB);
                    //System.out.println("This is the teams ID that was selected " + TeamID);
                    apiCalls.addFixture(TeamName, away_team, referee, date, time, location);

                    apiCalls.getFixtures getFixtures = new apiCalls.getFixtures();
                    getFixtures.execute("http://142.93.44.141/fixtures", TeamName);
                    //Intent PlayerSelection = new Intent(AddFixture.this, FixtureSelection.class);
                    //PlayerSelection.putExtra("TeamSelected", TeamName);
                    //startActivity(PlayerSelection);
                    AwayTeamInput.getText().clear();
                    RefereeInput.getText().clear();
                    DateInput.getText().clear();
                    TimeInput.getText().clear();
                    LocationInput.getText().clear();
                    Toast.makeText(AddFixture.this, "Fixture added",Toast.LENGTH_LONG).show();
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

