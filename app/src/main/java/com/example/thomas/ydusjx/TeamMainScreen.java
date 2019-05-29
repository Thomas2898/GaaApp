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

//Main navigation point of the app
public class TeamMainScreen extends Activity {

    Button fixture, stats, teamPlayers, back, DisplayStatsBtn;
    String ScreenCalled;
    String TeamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_main_screen);

        System.out.println("####Entered TeamMainScreen####");
        System.out.println("Team palyer 1 ===== " + Team.player1);

        Bundle bundle = getIntent().getExtras();
        TeamName = bundle.getString("TeamSelected");
        System.out.println("This is the team that was selected " + TeamName);

        apiCalls.getTeamsID(TeamName);
        //Calls the getFixtures class in apiCalls.java, to get the fixtures relating to the team that was chosen
        //It places these fixtures in an arraylist called fixture in fixtureselection.java
        apiCalls.getFixtures getFixtures = new apiCalls.getFixtures();
        getFixtures.execute("http://142.93.44.141/fixtures", TeamName);

        apiCalls.getPlayers getPlayers = new apiCalls.getPlayers();
        getPlayers.execute("http://142.93.44.141/teams", TeamName);

        System.out.println("Team palyer 1 ===== " + Team.player1);


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

        //Used to open the a screen where the user can view, add, update and delete fixtures
        fixture = (Button) findViewById(R.id.FixtureBtn);
        fixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to team list");
                Intent FixtureDisplay = new Intent(TeamMainScreen.this, FixtureSelection.class);
                ScreenCalled = "TeamMainScreen";
                //FixtureDisplay.putExtra("ScreenCalled", ScreenCalled);
                FixtureDisplay.putExtra("TeamSelected", TeamName);
                startActivity(FixtureDisplay);
            }
        });

        //Used top open a fixture selection, then when a fixture is selected
        //a screen where the user can record stats is open
        stats = (Button) findViewById(R.id.StatsBtn);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to team list");
                Intent StatFixture = new Intent(TeamMainScreen.this, StatFixtureClass.class);
                ScreenCalled = "Stat";
                //StatFixture.putExtra("ScreenCalled", ScreenCalled);
                StatFixture.putExtra("TeamSelected", TeamName);
                //StatFixture.putExtra("Pid", Pid);
                startActivity(StatFixture);
            }
        });

        //Used to open the a screen where the user can view, add, update and delete players
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

        //Used to open the a screen where the user can view, add, update and delete players
        DisplayStatsBtn = (Button) findViewById(R.id.DisplayStatsBtn);
        DisplayStatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to playerlist");
                Intent DisplayStats = new Intent(TeamMainScreen.this, statsDisplay.class);
                DisplayStats.putExtra("TeamSelected", TeamName);
                startActivity(DisplayStats);
            }
        });
    }

}
