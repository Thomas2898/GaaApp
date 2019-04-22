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

import java.util.ArrayList;

public class UpdateDeleteFixture extends Activity {
    EditText HomeTeamInput, AwayTeamInput, RefereeInput, DateInput, TimeInput, LocationInput;//Name and DOB input
    Button btnUpdateFixture, btnDeleteFixture;
    Button back;
    TextView header;//Text on top of screen
    String TeamName;
    static int TeamID;
    public static String nameselect, Pid;
    private String FixtureChosen;
    public static ArrayList<String> playersDetails = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatedeletefixture);

        //Gets information from playerlist screen
        Bundle bundle = getIntent().getExtras();
        TeamName = bundle.getString("TeamSelected");
        FixtureChosen = bundle.getString("FixtureSelected");
        System.out.println("This is the team that was selected " + TeamName);
        System.out.println("This is the fixture that was selected " + FixtureChosen);



        final String[] getFixtureDetails = FixtureChosen.split(", ");//Used to extract the players id from the string
        System.out.println("First element from splitting string " + getFixtureDetails[0]);
        final int FixtureID = Integer.parseInt(getFixtureDetails[0]);


        //Used to get data for the edittext box with the player the user selected to update/delete
        final String Home_Team = getFixtureDetails[1];
        String Away_Team = getFixtureDetails[2];
        String Referee = getFixtureDetails[3];
        String Date = getFixtureDetails[4];
        String Time = getFixtureDetails[5];
        String Location = getFixtureDetails[6];

        System.out.println("This is Away_Team " + getFixtureDetails[1]);
        System.out.println("This Referee " + getFixtureDetails[2]);
        System.out.println("This Date " + getFixtureDetails[3]);
        System.out.println("This Time " + getFixtureDetails[4]);
        System.out.println("This Location " + getFixtureDetails[5]);

        //Used to move back to the playerlistmain or to the playerlist screen
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Main screen");
                Intent PlayerSelection = new Intent(UpdateDeleteFixture.this, FixtureSelection.class);
                PlayerSelection.putExtra("TeamSelected", TeamName);
                //apiCalls.getPlayers getPlayers = new apiCalls.getPlayers();
                //getPlayers.execute("http://142.93.44.141/teams", TeamName);
                startActivity(PlayerSelection);
            }
        });

        header = (TextView)findViewById(R.id.header);
        header = (TextView)findViewById(R.id.header);
        //HomeTeamInput = (EditText)findViewById(R.id.HomeInput);
        AwayTeamInput = (EditText)findViewById(R.id.AwayInput);
        RefereeInput = (EditText)findViewById(R.id.RefereeInput);
        DateInput = (EditText)findViewById(R.id.DateInput);
        TimeInput = (EditText)findViewById(R.id.TimeInput);
        LocationInput = (EditText)findViewById(R.id.LocationInput);
        btnUpdateFixture = (Button)findViewById(R.id.UpdateFixture);
        btnDeleteFixture = (Button)findViewById(R.id.DeleteFixture);


        //Used to set the data for the edittext box with the fixture the user selected to update/delete
        AwayTeamInput.setText(Away_Team);
        RefereeInput.setText(Referee);
        DateInput.setText(Date);
        TimeInput.setText(Time);
        LocationInput.setText(Location);


        //Used tp pass the new players data to a class in apiCalls to add to the framework
        btnUpdateFixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String away_team = AwayTeamInput.getText().toString();
                String referee = RefereeInput.getText().toString();
                String date = DateInput.getText().toString();
                String time = TimeInput.getText().toString();
                String location = LocationInput.getText().toString();

                //Used to check if the user has entered nothing
                if(away_team.isEmpty() || referee.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty())
                {
                    Toast.makeText(UpdateDeleteFixture.this, "Please fill in details",Toast.LENGTH_LONG).show();
                }
                else{
                    //System.out.println("This is the fixture id that was selected " + FixtureID);
                    System.out.println("This is the away team that was selected " + away_team);
                    System.out.println("This is the referee that was selected " + referee);
                    System.out.println("This is the date that was selected " + date);
                    System.out.println("This is the time that was selected " + time);
                    System.out.println("This is the referee that was selected " + referee);
                    System.out.println("This is the location that was selected " + location);

                    apiCalls.updateFixture(FixtureID, Home_Team, away_team, referee, date, time, location);
                    //apiCalls.getPlayers getPlayers = new apiCalls.getPlayers();
                    //getPlayers.execute("http://142.93.44.141/teams", TeamName);
                }
            }
        });

        btnDeleteFixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Delete Fixture");
                apiCalls.deleteObject(FixtureID, "fixtures");
                //apiCalls.getPlayers getPlayers = new apiCalls.getPlayers();
                //getPlayers.execute("http://142.93.44.141/fixtures", TeamName);
            }
        });
    }

    //Used to receive the selected teams id
    protected static void receiveID(int TID) {
        System.out.println("In receiveID");
        TeamID = TID;
    }

}


