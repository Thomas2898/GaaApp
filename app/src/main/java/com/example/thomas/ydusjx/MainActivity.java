package com.example.thomas.ydusjx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    MyDbHelper mydb;
    Button btnTeam;
    Button btnPlayer;
    Button btnPlayers;
    public static int chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new MyDbHelper(this);
        System.out.println("WORKS");
        //mydb.insertModule("3", "C");

        //Used to open the module list screen
        btnTeam = (Button) findViewById(R.id.TeamBtn);
        btnTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to modules");
                chk = 5;
                String TeamName = "PlayerName";
                //Learned how to do intents from the notes on webcourses
                Intent TeamDisplay = new Intent(MainActivity.this, Team.class);
                TeamDisplay.putExtra("PlayerSelected", TeamName);
                startActivity(TeamDisplay);
            }
        });

        btnPlayers= (Button) findViewById(R.id.PlayersBtn);
        btnPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to modules");
                //Learned how to do intents from the notes on webcourses
                Intent PlayList = new Intent(MainActivity.this, PlayerListMain.class);
                startActivity(PlayList);
            }
        });
    }
}