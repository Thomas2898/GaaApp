package com.example.thomas.ydusjx;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by Thomas on 26/11/2018.
 */

public class PlayerUpdateDelete extends Activity {
    MyDbHelper mydb;
    EditText mninput;//Module name input
    Button btnUpdatePlayer, btnDeletePlayer;
    Button back;
    TextView header;//Text on top of screen
    public static String nameselect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_update_delete);
        mydb = new MyDbHelper(this);

        Bundle bundle = getIntent().getExtras();
        nameselect = bundle.getString("PlayerSelected");

        //Used to move back to the main screen
        back = (Button)findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to Main screen");
                Intent PlayerList = new Intent(PlayerUpdateDelete.this, PlayerListMain.class);
                startActivity(PlayerList);
            }
        });

        header = (TextView)findViewById(R.id.header);
        mninput = (EditText)findViewById(R.id.userInput);
        btnUpdatePlayer = (Button)findViewById(R.id.UpdatePlayer);
        //Adds module to the module table
        btnUpdatePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player_name = mninput.getText().toString();

                //Used to check if the user has entered nothing
                if(player_name.isEmpty())
                {
                    Toast.makeText(PlayerUpdateDelete.this, "Please enter a name",Toast.LENGTH_LONG).show();
                }
                else {
                    System.out.println("Entered else");
                    mydb.updatePlayer(player_name, nameselect);
                }
            }
        });

        btnDeletePlayer = (Button)findViewById(R.id.DeletePlayer);
        //Deletes players from the player table
        btnDeletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calls a fucntion in the database class to delete the player who was selected
                mydb.deletePlayer(nameselect);
                //Toast.makeText(PlayerUpdateDelete.this, "Delete button clicked " + nameselect,Toast.LENGTH_LONG).show();
            }
        });
    }
}


