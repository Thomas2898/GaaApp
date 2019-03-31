package com.example.thomas.ydusjx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Thomas on 31/03/2019.
 */

public class TeamMainScreen extends Activity {

    Button fixture, stats;
    String TeamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_main_screen);

        Bundle bundle = getIntent().getExtras();
        TeamName = bundle.getString("TeamSelected");
        System.out.println("This is the team that was selected " + TeamName);



        //Used to open the team screen
        fixture = (Button) findViewById(R.id.FixtureBtn);
        fixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to team list");
                //Intent TeamDisplay = new Intent(TeamMainScreen.this, FixtureList.class);
                //startActivity(TeamDisplay);
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
    }
}
