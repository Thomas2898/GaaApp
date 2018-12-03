package com.example.thomas.ydusjx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddPlayer extends Activity {
    MyDbHelper mydb;
    EditText mninput;//Module name input
    Button btnAddPlayer;
    Button back;
    TextView header;//Text on top of screen
    public static String nameselect, Pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addplayer);
        mydb = new MyDbHelper(this);

        Bundle bundle = getIntent().getExtras();
        nameselect = bundle.getString("PlayerSelected");
        Pid = bundle.getString("Pid");

        //Used to move back to the main screen
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Main screen");

                //Used to distinguish which screen to go back to
                //As i pass the string PlayerListMin from the screen PlayerListMain so i can identify to go back to this screen
                if(nameselect.equals("PlayerListMain")){
                    Intent PlayerList = new Intent(AddPlayer.this, PlayerListMain.class);
                    startActivity(PlayerList);
                }
                else {
                    Intent PlayerList = new Intent(AddPlayer.this, PlayerList.class);
                    PlayerList.putExtra("PlayerSelected", nameselect);
                    PlayerList.putExtra("NewPlayer", nameselect);
                    PlayerList.putExtra("Pid", Pid);
                    startActivity(PlayerList);
                }
            }
        });

        header = (TextView)findViewById(R.id.header);
        mninput = (EditText)findViewById(R.id.userInput);
        btnAddPlayer = (Button)findViewById(R.id.AddPlayer);
        //Adds module to the module table
        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player_name = mninput.getText().toString();

                //Used to check if the user has entered nothing
                if(player_name.isEmpty())
                {
                    Toast.makeText(AddPlayer.this, "Please enter a player",Toast.LENGTH_LONG).show();
                }
                else {
                    System.out.println("Entered else");
                    mydb.insertPlayer(player_name);
                }
            }
        });
    }
}

