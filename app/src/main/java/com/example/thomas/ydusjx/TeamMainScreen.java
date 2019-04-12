package com.example.thomas.ydusjx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Thomas on 31/03/2019.
 */

public class TeamMainScreen extends Activity {

    Button fixture, stats, teamPlayers, back;
    String TeamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_main_screen);

        Bundle bundle = getIntent().getExtras();
        TeamName = bundle.getString("TeamSelected");
        System.out.println("This is the team that was selected " + TeamName);

        //Calls the getFixtures class in apiCalls.java, to get the fixtures relating to the team that was chosen
        //It places these fixtures in an arraylist called fixture in fixtureselection.java
        apiCalls.getFixtures getFixtures = new apiCalls.getFixtures();
        getFixtures.execute("http://142.93.44.141/fixtures", TeamName);

        apiCalls.getPlayers getPlayers = new apiCalls.getPlayers();
        getPlayers.execute("http://142.93.44.141/teams", TeamName);


        //Used to go back to the Team selection
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Team screen");
                Toast.makeText(TeamMainScreen.this, "No player selected",Toast.LENGTH_LONG).show();
                Intent Main = new Intent(TeamMainScreen.this, TeamSelection.class);
                startActivity(Main);
            }
        });

        //Used to open the team screen
        fixture = (Button) findViewById(R.id.FixtureBtn);
        fixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to team list");
                Intent FixtureDisplay = new Intent(TeamMainScreen.this, FixtureSelection.class);
                FixtureDisplay.putExtra("TeamSelected", TeamName);
                startActivity(FixtureDisplay);
            }
        });

        stats = (Button) findViewById(R.id.StatsBtn);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to team list");
                //Intent TeamDisplay = new Intent(MainActivity.this, TeamSelection.class);
                //startActivity(TeamDisplay);
            }
        });

        teamPlayers = (Button) findViewById(R.id.PlayersBtn);
        teamPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to playerlist");
                Intent TeamDisplay = new Intent(TeamMainScreen.this, PlayerSelection.class);
                TeamDisplay.putExtra("TeamSelected", TeamName);
                startActivity(TeamDisplay);
            }
        });
    }
}
