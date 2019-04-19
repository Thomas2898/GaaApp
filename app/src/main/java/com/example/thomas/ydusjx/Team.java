package com.example.thomas.ydusjx;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.R.id.list;
import static android.R.layout.simple_list_item_1;
/**
 * Created by Thomas on 27/10/2018.
 */

//This is the code that creates the screen where stats are recorded
public class Team extends Activity {
    Button back,test1;
    TextView name1, name2, name3, name4, name5, name6, playerChosen, name7, name8, name9, name10, name11, name12, name13, name14, name15;
    ImageView img, img2;
    MyDbHelper mydb;
    public static ArrayList<Integer> PlayersX = new ArrayList<Integer>();
    public static ArrayList<Integer> PlayersY = new ArrayList<Integer>();
    public static ArrayList<Integer> PlayersHeight = new ArrayList<Integer>();
    public static ArrayList<Integer> PlayersWidth = new ArrayList<Integer>();
    public static String str, FixtureID, TeamName;
    public static String str2;
    public static String Pid;
    public static String test = "Name1";
    public static String id1, id2, id3, id4, id5, id6;
    public static String player1, player2, player3, player4, player5, player6, player7, player8, player9, player10, player11, player12, player13, player14, player15;//Player1, player2 are used to store the names of the players in the textview
    public static int chk;
    //public static ArrayList<String> PlayersNamesString = new ArrayList<String>();
    String[] PlayersNames= {player1, player2, player3};
    //TextView[] TextNames= {name1, name2, name3};
    TextView[] textViewNames = new TextView[3];

    private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team);
        mydb = new MyDbHelper(this);
        //populateModuleList();
        loadTeam();// Used to load the players into their positions

        //Gets information passed from the main screen and Playerlistscreen
        Bundle bundle = getIntent().getExtras();
        str = bundle.getString("PlayerSelected");
        str2 = bundle.getString("NewPlayer");
        Pid = bundle.getString("Pid");
        FixtureID = bundle.getString("FixtureID");
        TeamName = bundle.getString("TeamSelected");

        System.out.println("This is new player");
        System.out.println(str2 + "its id = " + Pid);
        System.out.println("###### This is the fixture " + FixtureID);

        //Used so the main activity cannot not change any of the players names
        if(str2 != null){
            setPlayerName(Pid, str2);
        }

        back = (Button) findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to main menu");
                Intent StatFixture = new Intent(Team.this, StatFixtureClass.class);
                StatFixture.putExtra("TeamSelected", TeamName);
                startActivity(StatFixture);
            }
        });

        name1 = (TextView) findViewById(R.id.Name1);
        name2 = (TextView) findViewById(R.id.Name2);
        name3 = (TextView) findViewById(R.id.Name3);
        name4 = (TextView) findViewById(R.id.Name4);
        name5 = (TextView) findViewById(R.id.Name5);
        name6 = (TextView) findViewById(R.id.Name6);
        int[] position= new int[2];
        name1.getLocationInWindow(position);
        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        System.out.println("Coordinates x = " + position[0] + " other co = " +  position[1]);

        name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting the coordinates of the textfield
                int x = name1.getLeft();
                int y = name1.getTop();
                int width = name1.getWidth();
                int height = name1.getHeight();

                float yy = name1.getY();

                //int id = name1.getId();
                PlayersX.add(0,x);
                PlayersY.add(0,y);
                PlayersWidth.add(0, width);
                PlayersHeight.add(0,height);
                //getPlayersCoordinates();


                // x = 0 y = 144
                // width = 200 height = 81
                //System.out.println("ID = " + id);//2131427475
                System.out.println("Coordinates x = " + x + " y = " + y + " float y= " + yy);
                System.out.println("Name1 width = " + width + " height = " + height);

                //Assigns the textview an id to make it unique and identifiable
                id1 = "ID1";
                playerChosen = (TextView) findViewById(R.id.Name1);
                System.out.println(name1.getId());
                String a = name1.getText().toString();
                getPlayerName(a, id1);
            }
        });

        System.out.println("After name 1 ######");
        int x = name1.getLeft();
        //int y = name1.getTop();
        float yy = name1.getY();
        int width = name1.getWidth();
        int height = name1.getHeight();
        System.out.println("Coordinates x = " + x + " y = " + yy);
        System.out.println("name1 width = " + width + " height = " + height);

        name2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int x = name2.getLeft();
                int y = name2.getTop();
                int width = name2.getWidth();
                int height = name2.getHeight();

                PlayersX.add(1, x);
                PlayersY.add(1,y);
                PlayersWidth.add(1, width);
                PlayersHeight.add(1,height);
                // x = 440 y = 144
                // width = 140 height = 57
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name2 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id2 = "ID2";
                playerChosen = (TextView) findViewById(R.id.Name2);
                String a = name2.getText().toString();
                System.out.println(a);
                getPlayerName(a, id2);
            }
        });

        name3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int x = name3.getLeft();
                int y = name3.getTop();
                int width = name3.getWidth();
                int height = name3.getHeight();

                PlayersX.add(2,x);
                PlayersY.add(2,y);
                PlayersWidth.add(2, width);
                PlayersHeight.add(2,height);

                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id3 = "ID3";
                playerChosen = (TextView) findViewById(R.id.Name3);
                String a = name3.getText().toString();
                System.out.println(a);
                getPlayerName(a, id3);
            }
        });

        name4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int x = name4.getLeft();
                int y = name4.getTop();
                int width = name4.getWidth();
                int height = name4.getHeight();
                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id4 = "ID4";
                playerChosen = (TextView) findViewById(R.id.Name4);
                String a = name4.getText().toString();
                System.out.println(a);
                getPlayerName(a, id4);
            }
        });

        name5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int x = name5.getLeft();
                int y = name5.getTop();
                int width = name5.getWidth();
                int height = name5.getHeight();
                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id5 = "ID5";
                playerChosen = (TextView) findViewById(R.id.Name5);
                String a = name5.getText().toString();
                System.out.println(a);
                getPlayerName(a, id5);
            }
        });

        name6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int x = name6.getLeft();
                int y = name6.getTop();
                int width = name6.getWidth();
                int height = name6.getHeight();
                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id6 = "ID6";
                playerChosen = (TextView) findViewById(R.id.Name6);
                String a = name6.getText().toString();
                System.out.println(a);
                getPlayerName(a, id6);
            }
        });

        img=(ImageView)findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("This is the fixture in the image call" + FixtureID);
                int x = img.getLeft();
                int y = img.getTop();
                int width = img.getWidth();
                int height = img.getHeight();
                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("img width = " + width + " height = " + height);
                DragDrop(img, "image1", FixtureID);
            }
        });

        img2=(ImageView)findViewById(R.id.imageView2);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = img2.getLeft();
                int y = img2.getTop();
                int width = img2.getWidth();
                int height = img2.getHeight();
                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("img2 width = " + width + " height = " + height);
                DragDrop(img2, "image2", FixtureID);
            }
        });
        //onWindowFocusChanged(true);
    }

    //Used to get new player from the database
    public void getPlayerName(String name, String id){
        System.out.println("This is new playerChosen");
        System.out.println(playerChosen.getText().toString());
        System.out.println(playerChosen);
        Intent PlayList = new Intent(Team.this, PlayerList.class);

        PlayList.putExtra("FixtureID", FixtureID);
        PlayList.putExtra("TeamSelected", TeamName);
        PlayList.putExtra("NameSelect", name);
        PlayList.putExtra("Pid", id);
        startActivity(PlayList);
    }

    //Used to set players names that have been retrieved from the playerlist screen
    public void setPlayerName(String Pid, String str2){
        System.out.println("Entered setPlayerName");
        System.out.println(playerChosen);
        System.out.println(str2);
        name1 = (TextView) findViewById(R.id.Name1);
        name2 = (TextView) findViewById(R.id.Name2);
        name3 = (TextView) findViewById(R.id.Name3);
        name4 = (TextView) findViewById(R.id.Name4);
        name5 = (TextView) findViewById(R.id.Name5);
        name6 = (TextView) findViewById(R.id.Name6);

        if(Pid.equals("ID1")) {
            player1 = str2;
            PlayersNames[0] = str2;
            name1.setText(str2);
        }
        if(Pid.equals("ID2")) {
            player2 = str2;
            PlayersNames[1] = str2;
            name2.setText(str2);
        }

        if(Pid.equals("ID3")) {
            player3 = str2;
            PlayersNames[2] = str2;
            name3.setText(str2);
            //Names[2].setText(Players[2]);
        }

        if(Pid.equals("ID4")) {
            player4 = str2;

            name4.setText(str2);
        }

        if(Pid.equals("ID5")) {
            player5 = str2;
            name5.setText(str2);
        }

        if(Pid.equals("ID6")) {
            player6 = str2;
            name6.setText(str2);
        }


        /*
        String[] Ids = {"ID1","ID2","ID3"};
        String[] Players = {player1,player2,player3};
        TextView[] Names = {name1,name2,name3};

        for(int i = 0 ; i < Ids.length; i++){
            if(Pid.equals(Ids[i])) {
                System.out.println("ENTERED LOOOOP");
                System.out.println("Value ===== " + Players[i]);
                Players[i] = str2;
                System.out.println("Value ===== " + Players[i]);
                System.out.println("Value View ===== " + name1);
                System.out.println("Value View Array ===== " + Names[i]);
                //Names[i].setText(Players[i]);
                textViewNames[i].setText(Players[i]);
            }
        }
        */



        //Used to reload the team when a new player is chosen
        loadTeam();
    }

    ////Used to get the coordinates for the players textviews
    @Override
    public void onWindowFocusChanged (boolean hasFocus) {

        //Clear the lists, to allow the data to be re-loaded in
        PlayersX.clear();
        PlayersY.clear();
        PlayersWidth.clear();
        PlayersHeight.clear();

        //Used to get the coordinates for the players textviews
        for(int i = 0 ; i < PlayersNames.length; i++) {
            final int[] position = new int[2];
            textViewNames[i].getLocationOnScreen(position);
            int x = textViewNames[i].getLeft();
            int y = textViewNames[i].getTop();
            int width = textViewNames[i].getWidth();
            int height = textViewNames[i].getHeight();
            PlayersX.add(i, x);
            PlayersY.add(i, y);
            PlayersWidth.add(i, width);
            PlayersHeight.add(i, height);

            System.out.println("This is the player " + PlayersNames[i]);
            //System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            //System.out.println("Coordinates x = " + position[0] + " other co = " + position[1]);
            //System.out.println("Coordinates x = " + width + " other co = " + height);
            //System.out.println("Coordinates x = " + x + " y = " + y);
        }


    }

    //When the team page is open this is called to load the players into their positions
    //Players that have been picked will be stored in strings such as player1 and player2
    public void loadTeam(){
        name1 = (TextView) findViewById(R.id.Name1);
        name2 = (TextView) findViewById(R.id.Name2);
        name3 = (TextView) findViewById(R.id.Name3);
        name4 = (TextView) findViewById(R.id.Name4);
        name5 = (TextView) findViewById(R.id.Name5);
        name6 = (TextView) findViewById(R.id.Name6);
        textViewNames[0]=name1;
        textViewNames[1]=name2;
        textViewNames[2]=name3;
        //name1.setText(player1);

        /*
        //To make sure no textfield has no value as the textfield will disappear
        if(player1 == null) {
            System.out.println("Player1 is null");
        }
        else{
            name1.setText(player1);
        }

        //To make sure no textfield has no value as the textfield will disappear
        if(player2 == null) {
            System.out.println("Player2 is null");
        }
        else{
            name2.setText(player2);
        }

        if(player3 != null) {
            name3.setText(player3);
        }
        */

        //An array of strings that hold the value of the players name (String player1 = "Tom")
        String[] Players = {player1,player2,player3,player4,player5,player6};
        //Used to know which textview is which
        TextView[] Names = {name1,name2,name3,name4,name5,name6};

        //Used to set the textviews to the players names that were already chosen
        for(int i = 0 ; i < Players.length; i++){
            if( Players[i] != null) {
                Names[i].setText(Players[i]);
            }
        }


    }

    //Called when one of the images is touched
    //Reference: The following code an Android example from https://www.tutorialspoint.com/android/android_drag_and_drop.htm
    public void DragDrop(ImageView image, final String name, final String FixID){
        System.out.println("Inside DragDrop");
        System.out.println(name);
        System.out.println("This is the fixture that was passed in " + FixID);

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("Image has been clicked");
                ClipData.Item itemClip = new ClipData.Item((CharSequence)v.getTag());
                String[] mime = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData datadraged = new ClipData(v.getTag().toString(),mime, itemClip);
                View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(img);

                v.startDrag(datadraged,myShadowBuilder,null,0);
                return true;
            }
        });

        image.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch(event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        layoutParams = (RelativeLayout.LayoutParams)v.getLayoutParams();
                        System.out.println("Action drag started");
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        System.out.println("Action drag entered");
                        int xValue = (int) event.getX();
                        int yValue = (int) event.getY();
                        //System.out.println("This is the x value in ACTION_DRAG_ENTERED " + xValue);
                        //System.out.println("This is the y value in ACTION_DRAG_ENTERED " + yValue);
                        break;

                    case DragEvent.ACTION_DRAG_EXITED :
                        System.out.println("Action drag exited");
                        xValue= (int) event.getX();
                        yValue = (int) event.getY();
                        layoutParams.leftMargin = xValue;
                        layoutParams.topMargin = yValue;
                        v.setLayoutParams(layoutParams);
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION  :
                        System.out.println("Action drag location");
                        xValue = (int) event.getX();
                        yValue = (int) event.getY();
                        break;

                    //Used to get location of when the icon/image was released
                    case DragEvent.ACTION_DRAG_ENDED   :
                        System.out.println("Action drag ended");
                        xValue = (int) event.getX();
                        yValue = (int) event.getY();
                        System.out.println("This is the x value in ACTION_DRAG_ENDED " + xValue);
                        System.out.println("This is the y value in ACTION_DRAG_ENDED " + yValue);

                        //Used to find where the icon was dropped and what player the icon was dropped on
                        for(int i = 0 ; i < PlayersNames.length; i++){
                            int width = PlayersX.get(i) + PlayersWidth.get(i);
                            int height = PlayersY.get(i) + PlayersHeight.get(i);

                            //Name1
                            // x = 0 y = 144
                            // width = 200 height = 81
                            if(xValue >=  PlayersX.get(i) && xValue <= width && yValue >= PlayersY.get(i) && yValue <= height) {
                                System.out.println("The player found " + PlayersNames[i]);
                                System.out.println("The coordinates");
                                System.out.println("X ====== " + PlayersX.get(i));
                                System.out.println("Y ====== " + PlayersY.get(i));
                                System.out.println("W ====== " + PlayersWidth.get(i));
                                System.out.println("H ====== " + PlayersHeight.get(i));
                                String[] PlayerID = PlayersNames[i].split(" ");
                                System.out.println("Icon that was used " + name);
                                System.out.println("The split " + PlayerID[0]);
                                //Used to turn the string into an int so it can be passed to a class
                                int Player = Integer.parseInt(PlayerID[0]);
                                System.out.println("The int " + Player);
                                System.out.println("The fixture " + FixID);
                                int Fixture= Integer.parseInt(FixtureID);
                                //Used to call a class in apiCalls that updates a stat using a player id and fixture id and the name of the stat
                                apiCalls.playerStat(Player, Fixture, "Point_Miss");
                            }
                        }



                        /*
                        //Used to test the images coordinates
                        // x = 0 y = 144
                        // width = 200 height = 81
                        if(xValue >= 0 && xValue <= 200 && yValue >= 144 && yValue <= 225){
                            System.out.println("Player1 identified");
                            System.out.println("Players name is " + player1);

                            //Used to extratc the players id from the string
                            String[] PlayerID = player1.split(" ");
                            System.out.println("Icon that was used " + name);
                            System.out.println("The split " + PlayerID[0]);
                            //Used to turn the string into an int so it can be passed to a class
                            int Player = Integer.parseInt(PlayerID[0]);
                            System.out.println("The int " + Player);
                            System.out.println("The fixture " + FixID);
                            int Fixture= Integer.parseInt(FixtureID);
                            //Used to call a class in apiCalls that updates a stat using a player id and fixture id and the name of the stat
                            apiCalls.playerStat(Player, Fixture, "Point_Miss");
                        }

                        // x = 440 y = 144
                        // width = 140 height = 57
                        if(xValue >= 470 && xValue <= 610 && yValue >= 144 && yValue <= 201){
                            System.out.println("Player2 identified");
                        }
                        */

                        break;

                    case DragEvent.ACTION_DROP:
                        System.out.println("Action drop");
                        break;
                    default: break;
                }
                return true;
            }
        });

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(img);

                    img.startDrag(clipdataTouch, MyshadowBuilder, img, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });
        //End code reference
    }
}
