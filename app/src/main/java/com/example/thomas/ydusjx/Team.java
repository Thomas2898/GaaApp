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
import android.widget.Toast;

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
    Button back,stats, iconBtn;
    TextView name1, name2, name3, name4, name5, name6, playerChosen, name7, name8, name9, name10, name11, name12, name13, name14, name15, Test, Test2;
    ImageView img, img2, img3, img4, img5, img6, img7, img8, img9, AltImg, AltImg2, AltImg3, AltImg4, AltImg5, AltImg6, Altimg7, Altimg8, Altimg9, Altimg10;
    static String IconChosen;
    MyDbHelper mydb;
    public Toast toastobject;
    public static ArrayList<Integer> PlayersX = new ArrayList<Integer>();
    public static ArrayList<Integer> PlayersY = new ArrayList<Integer>();
    public static ArrayList<Integer> PlayersHeight = new ArrayList<Integer>();
    public static ArrayList<Integer> PlayersWidth = new ArrayList<Integer>();
    public static String str, FixtureID, TeamName;
    public static String str2;
    public static String Pid;
    public static String test = "Name1";
    public static String id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15;
    public static String player1, player2, player3, player4, player5, player6, player7, player8, player9, player10, player11, player12, player13, player14, player15;//Player1, player2 are used to store the names of the players in the textview
    public static int ImageChk;
    //public static ArrayList<String> PlayersNamesString = new ArrayList<String>();
    String[] PlayersNames= {player1, player2, player3, player4, player5, player6, player7, player8, player9, player10,player11,player12,player13,player14,player15};
    //TextView[] TextNames= {name1, name2, name3};
    TextView[] textViewNames = new TextView[15];
    private static final int SHORT_DELAY = 1000;

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
        ImageChk = 0;

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

        stats = (Button) findViewById(R.id.Statistcs);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to main DisplayPlayerStats class");
                Intent displayPlayerStats = new Intent(Team.this, DisplayPlayerStats.class);
                displayPlayerStats.putExtra("TeamSelected", TeamName);
                displayPlayerStats.putExtra("FixtureSelected", FixtureID);
                startActivity(displayPlayerStats);
            }
        });

        name1 = (TextView) findViewById(R.id.Name1);
        name2 = (TextView) findViewById(R.id.Name2);
        name3 = (TextView) findViewById(R.id.Name3);
        name4 = (TextView) findViewById(R.id.Name4);
        name5 = (TextView) findViewById(R.id.Name5);
        name6 = (TextView) findViewById(R.id.Name6);
        name7 = (TextView) findViewById(R.id.Name7);
        name8 = (TextView) findViewById(R.id.Name8);
        name9 = (TextView) findViewById(R.id.Name9);
        name10 = (TextView) findViewById(R.id.Name10);

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
                PlayersX.add(3,x);
                PlayersY.add(3,y);
                PlayersWidth.add(3, width);
                PlayersHeight.add(3,height);
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
                PlayersX.add(4,x);
                PlayersY.add(4,y);
                PlayersWidth.add(4, width);
                PlayersHeight.add(4,height);
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

        /*
        name5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int x = name5.getLeft();
                int y = name5.getTop();
                int width = name5.getWidth();
                int height = name5.getHeight();
                PlayersX.add(4,x);
                PlayersY.add(4,y);
                PlayersWidth.add(4, width);
                PlayersHeight.add(4,height);
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
        */

        name6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = name6.getLeft();
                int y = name6.getTop();
                int width = name6.getWidth();
                int height = name6.getHeight();

                PlayersX.add(5,x);
                PlayersY.add(5,y);
                PlayersWidth.add(5, width);
                PlayersHeight.add(5,height);

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

        name7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = name7.getLeft();
                int y = name7.getTop();
                int width = name7.getWidth();
                int height = name7.getHeight();

                PlayersX.add(6,x);
                PlayersY.add(6,y);
                PlayersWidth.add(6, width);
                PlayersHeight.add(6,height);

                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id7 = "ID7";
                playerChosen = (TextView) findViewById(R.id.Name7);
                String a = name7.getText().toString();
                System.out.println(a);
                getPlayerName(a, id7);
            }
        });

        name8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = name8.getLeft();
                int y = name8.getTop();
                int width = name8.getWidth();
                int height = name8.getHeight();

                PlayersX.add(7,x);
                PlayersY.add(7,y);
                PlayersWidth.add(7, width);
                PlayersHeight.add(7,height);

                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id8 = "ID8";
                playerChosen = (TextView) findViewById(R.id.Name8);
                String a = name8.getText().toString();
                System.out.println(a);
                getPlayerName(a, id8);
            }
        });

        name9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = name9.getLeft();
                int y = name9.getTop();
                int width = name9.getWidth();
                int height = name9.getHeight();

                PlayersX.add(8,x);
                PlayersY.add(8,y);
                PlayersWidth.add(8, width);
                PlayersHeight.add(8,height);

                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id9 = "ID9";
                playerChosen = (TextView) findViewById(R.id.Name9);
                String a = name9.getText().toString();
                System.out.println(a);
                getPlayerName(a, id9);
            }
        });

        name10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = name10.getLeft();
                int y = name10.getTop();
                int width = name10.getWidth();
                int height = name10.getHeight();

                PlayersX.add(9,x);
                PlayersY.add(9,y);
                PlayersWidth.add(9, width);
                PlayersHeight.add(9,height);

                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id10 = "ID10";
                playerChosen = (TextView) findViewById(R.id.Name10);
                String a = name10.getText().toString();
                System.out.println(a);
                getPlayerName(a, id10);
            }
        });

        name11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = name11.getLeft();
                int y = name11.getTop();
                int width = name11.getWidth();
                int height = name11.getHeight();

                PlayersX.add(10,x);
                PlayersY.add(10,y);
                PlayersWidth.add(10, width);
                PlayersHeight.add(10,height);

                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id11 = "ID11";
                playerChosen = (TextView) findViewById(R.id.Name11);
                String a = name11.getText().toString();
                System.out.println(a);
                getPlayerName(a, id11);
            }
        });

        name12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = name12.getLeft();
                int y = name12.getTop();
                int width = name12.getWidth();
                int height = name12.getHeight();

                PlayersX.add(11,x);
                PlayersY.add(11,y);
                PlayersWidth.add(11, width);
                PlayersHeight.add(11,height);

                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id12 = "ID12";
                playerChosen = (TextView) findViewById(R.id.Name12);
                String a = name12.getText().toString();
                System.out.println(a);
                getPlayerName(a, id12);
            }
        });

        name13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = name13.getLeft();
                int y = name13.getTop();
                int width = name13.getWidth();
                int height = name13.getHeight();

                PlayersX.add(12,x);
                PlayersY.add(12,y);
                PlayersWidth.add(12, width);
                PlayersHeight.add(12,height);

                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id13 = "ID13";
                playerChosen = (TextView) findViewById(R.id.Name13);
                String a = name13.getText().toString();
                System.out.println(a);
                getPlayerName(a, id13);
            }
        });

        name14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = name14.getLeft();
                int y = name14.getTop();
                int width = name14.getWidth();
                int height = name14.getHeight();

                PlayersX.add(13,x);
                PlayersY.add(13,y);
                PlayersWidth.add(13, width);
                PlayersHeight.add(13,height);

                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id14 = "ID14";
                playerChosen = (TextView) findViewById(R.id.Name14);
                String a = name14.getText().toString();
                System.out.println(a);
                getPlayerName(a, id14);
            }
        });

        name15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = name15.getLeft();
                int y = name15.getTop();
                int width = name15.getWidth();
                int height = name15.getHeight();

                PlayersX.add(14,x);
                PlayersY.add(14,y);
                PlayersWidth.add(14, width);
                PlayersHeight.add(14,height);

                // x = 880 y = 144
                // width = 200 height = 81
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name3 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id15 = "ID15";
                playerChosen = (TextView) findViewById(R.id.Name15);
                String a = name15.getText().toString();
                System.out.println(a);
                getPlayerName(a, id15);
            }
        });

        Test = (TextView) findViewById(R.id.Test);
        Test2 = (TextView) findViewById(R.id.Test2);

        AltImg=(ImageView)findViewById(R.id.AltimageView);
        AltImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View AltImg, MotionEvent Altevent) {
                if (Altevent.getAction() == MotionEvent.ACTION_DOWN) {
                    System.out.println("111111111111111111111111111111111111111111");
                    System.out.println("Altimage 1 is being dragged");
                    IconChosen = "Goal_save";
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(AltImg);

                    AltImg.startDrag(clipdataTouch, MyshadowBuilder, AltImg, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                }
                else {
                    return false;
                }
            }
        });

        img=(ImageView)findViewById(R.id.imageView);
        //img.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                img.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View img, MotionEvent event) {
                        //IconChosen = "Pass";
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            System.out.println("111111111111111111111111111111111111111111");
                            System.out.println("Image 1 is being dragged");
                            IconChosen = "Pass";
                            //toastObject.cancel();
                            Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                            //toastobject = Toast.makeText(Team.this, "This message will disappear when toast.close(); is called", Toast.LENGTH_SHORT);
                            ClipData clipdataTouch = ClipData.newPlainText("", "");
                            View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(img);

                            img.startDrag(clipdataTouch, MyshadowBuilder, img, 0);
                            //Makes the icon disappear
                            //img.setVisibility(View.VISIBLE);
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                //IconChosen = "Pass";


                //Used to detect one of the images being dragged
                img.setOnDragListener(new View.OnDragListener() {
                    final int Imagex = img.getLeft();
                    final int Imagey = img.getTop();
                    final int Imagewidth = img.getWidth();
                    final int Imageheight = img.getHeight();
                    @Override
                    public boolean onDrag(View img, DragEvent event) {
                        //IconChosen = "Pass";
                        System.out.println("New Coordinates");
                        System.out.println("Pass");
                        //System.out.println("X ====== " + Imagex);
                        //System.out.println("Y ====== " + Imagey);
                        //System.out.println("W ====== " + Imagewidth);
                        //System.out.println("H ====== " + Imageheight);
                        switch(event.getAction()) {
                            case DragEvent.ACTION_DRAG_STARTED:
                                layoutParams = (RelativeLayout.LayoutParams)img.getLayoutParams();
                                //v.setVisibility(View.VISIBLE);
                                System.out.println("Action drag started");
                                int xValue = (int) event.getX();
                                int yValue = (int) event.getY();
                                //System.out.println("This is the x value in ACTION_DRAG_Started " + xValue);
                                //System.out.println("This is the y value in ACTION_DRAG_Started " + yValue);
                                break;

                            case DragEvent.ACTION_DRAG_ENTERED:
                                System.out.println("Action drag entered");
                                int xValue2 = (int) event.getX();
                                int yValue2 = (int) event.getY();
                                //System.out.println("This is the x value in ACTION_DRAG_ENTERED " + xValue2);
                                //System.out.println("This is the y value in ACTION_DRAG_ENTERED " + yValue2);
                                break;

                            case DragEvent.ACTION_DRAG_EXITED :
                                System.out.println("Action drag exited");
                                xValue= (int) event.getX();
                                yValue = (int) event.getY();
                                layoutParams.leftMargin = xValue;
                                layoutParams.topMargin = yValue;
                                //img.setLayoutParams(layoutParams);
                                break;

                            case DragEvent.ACTION_DRAG_LOCATION  :
                                System.out.println("Action drag location");
                                int xValue3 = (int) event.getX();
                                int yValue3 = (int) event.getY();
                                //System.out.println("This is the x value in ACTION_DRAG_LOCATION " + xValue3);
                                //System.out.println("This is the y value in ACTION_DRAG_LOCATION " + yValue3);
                                break;

                            //Used to get location of when the icon/image was released
                            case DragEvent.ACTION_DRAG_ENDED   :
                                //IconChosen = "Pass";
                                System.out.println("Action drag ended");
                                //image.setVisibility(View.VISIBLE);
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
                                        System.out.println("Used Image 1");
                                        //System.out.println("The coordinates");
                                        //System.out.println("X ====== " + PlayersX.get(i));
                                        //System.out.println("Y ====== " + PlayersY.get(i));
                                        //System.out.println("W ====== " + PlayersWidth.get(i));
                                        //System.out.println("H ====== " + PlayersHeight.get(i));
                                        if(PlayersNames[i] != null) {
                                            String[] PlayerID = PlayersNames[i].split(" ");

                                            System.out.println("Icon that was used " + IconChosen);
                                            //String input = img.getText().toString();
                                            //IconChosen = "";
                                            //System.out.println("The split " + PlayerID[0]);
                                            //Used to turn the string into an int so it can be passed to a class
                                            int Player = Integer.parseInt(PlayerID[0]);
                                            //System.out.println("The int " + Player);
                                            //System.out.println("The fixture " + FixID);
                                            int Fixture = Integer.parseInt(FixtureID);
                                            System.out.println("Player has been updated1");
                                            //Used to call a class in apiCalls that updates a stat using a player id and fixture id and the name of the stat
                                            Toast.makeText(Team.this, PlayerID[1] + " updated using " + IconChosen, Toast.LENGTH_SHORT).show();
                                            apiCalls.playerStat(Player, Fixture, IconChosen);
                                        }
                                        else
                                        {
                                            Toast.makeText(Team.this, "Please select a player", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                                break;

                            case DragEvent.ACTION_DROP:
                                System.out.println("Action drop");
                                break;
                            default: break;
                        }
                        return true;
                    }
                });

        AltImg2=(ImageView)findViewById(R.id.AltimageView2);
        AltImg2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View AltImg2, MotionEvent Altevent2) {
                //IconChosen = "Pass_Miss";
                if (Altevent2.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Goal_save";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(AltImg2);
                    AltImg2.startDrag(clipdataTouch, MyshadowBuilder, AltImg2, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        img2=(ImageView)findViewById(R.id.imageView2);
        img2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View img2, MotionEvent event2) {
                //IconChosen = "Pass_Miss";
                if (event2.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Pass_Miss";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(img2);
                    img2.startDrag(clipdataTouch, MyshadowBuilder, img2, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        AltImg3=(ImageView)findViewById(R.id.AltimageView3);
        AltImg3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View AltImg3, MotionEvent Altevent3) {
                if (Altevent3.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Goal_conceded";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(AltImg3);

                    AltImg3.startDrag(clipdataTouch, MyshadowBuilder, AltImg3, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        img3=(ImageView)findViewById(R.id.imageView3);
        img3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View img3, MotionEvent event3) {
                if (event3.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Point";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(img3);

                    img3.startDrag(clipdataTouch, MyshadowBuilder, img3, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        AltImg4=(ImageView)findViewById(R.id.AltimageView4);
        AltImg4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View AltImg4, MotionEvent Altevent4) {
                if (Altevent4.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Kickout_won";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(AltImg4);

                    AltImg4.startDrag(clipdataTouch, MyshadowBuilder, AltImg4, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        img4=(ImageView)findViewById(R.id.imageView4);
        img4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View img4, MotionEvent event4) {
                if (event4.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Point_Miss";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(img4);

                    img4.startDrag(clipdataTouch, MyshadowBuilder, img4, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        AltImg5=(ImageView)findViewById(R.id.AltimageView5);
        AltImg5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View AltImg5, MotionEvent Altevent5) {
                if (Altevent5.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Kickout_lost";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(AltImg5);

                    AltImg5.startDrag(clipdataTouch, MyshadowBuilder, AltImg5, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        img5=(ImageView)findViewById(R.id.imageView5);
        img5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View img5, MotionEvent event5) {
                if (event5.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Goal";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(img5);

                    img5.startDrag(clipdataTouch, MyshadowBuilder, img5, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        AltImg6=(ImageView)findViewById(R.id.AltimageView6);
        AltImg6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View AltImg6, MotionEvent Altevent6) {
                if (Altevent6.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Red_card";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(AltImg6);

                    AltImg6.startDrag(clipdataTouch, MyshadowBuilder, AltImg6, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        img6=(ImageView)findViewById(R.id.imageView6);
        img6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View img6, MotionEvent event6) {
                if (event6.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Goal_Miss";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(img6);

                    img6.startDrag(clipdataTouch, MyshadowBuilder, img6, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        Altimg7=(ImageView)findViewById(R.id.AltimageView7);
        Altimg7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View Altimg7, MotionEvent Altevent7) {
                if (Altevent7.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Black_card";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(Altimg7);
                    Altimg7.startDrag(clipdataTouch, MyshadowBuilder, Altimg7, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        img7=(ImageView)findViewById(R.id.imageView7);
        img7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View img7, MotionEvent event7) {
                if (event7.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Block";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(img7);

                    img7.startDrag(clipdataTouch, MyshadowBuilder, img7, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        Altimg8=(ImageView)findViewById(R.id.AltimageView8);
        Altimg8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View Altimg8, MotionEvent Altevent8) {
                if (Altevent8.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Turnover";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(Altimg8);

                    Altimg8.startDrag(clipdataTouch, MyshadowBuilder, Altimg8, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        img8=(ImageView)findViewById(R.id.imageView8);
        img8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View img8, MotionEvent event8) {
                if (event8.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Dispossessed";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(img8);

                    img8.startDrag(clipdataTouch, MyshadowBuilder, img8, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        img9=(ImageView)findViewById(R.id.imageView9);
        img9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View img9, MotionEvent event9) {
                if (event9.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Yellow_card";
                    Toast.makeText(Team.this, "Icon = " + IconChosen, Toast.LENGTH_SHORT).show();
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(img9);

                    img9.startDrag(clipdataTouch, MyshadowBuilder, img9, 0);
                    //Makes the icon disappear
                    //img.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        iconBtn = (Button) findViewById(R.id.IconButton);
        iconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Changing the icons");
                System.out.println("This is the chosen icon = " + IconChosen);
                if(ImageChk == 0) {
                    ImageChk = 1;
                    //img.setVisibility(View.INVISIBLE);
                    img2.setVisibility(View.INVISIBLE);
                    img3.setVisibility(View.INVISIBLE);
                    img4.setVisibility(View.INVISIBLE);
                    img5.setVisibility(View.INVISIBLE);
                    img6.setVisibility(View.INVISIBLE);
                    img7.setVisibility(View.INVISIBLE);
                    img8.setVisibility(View.INVISIBLE);
                    //name1.setVisibility(View.INVISIBLE);
                    iconBtn.setText("Possession Icons");
                    System.out.println("Attacking Icon");
                }
                else{
                    ImageChk = 0;
                    //img.setVisibility(View.VISIBLE);
                    img2.setVisibility(View.VISIBLE);
                    img3.setVisibility(View.VISIBLE);
                    img4.setVisibility(View.VISIBLE);
                    img5.setVisibility(View.VISIBLE);
                    img6.setVisibility(View.VISIBLE);
                    img7.setVisibility(View.VISIBLE);
                    img8.setVisibility(View.VISIBLE);
                    iconBtn.setText("Disspossesed Icons");
                    System.out.println("Defensive Icon");
                }

            }
        });
        /*
        //When the button is clicked this image will be infront og the image occupying image3(Point)
        AltImg=(ImageView)findViewById(R.id.AltimageView);
        AltImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View AltImg, MotionEvent aAltevent) {
                if (aAltevent.getAction() == MotionEvent.ACTION_DOWN) {
                    IconChosen = "Turnover";
                    ClipData clipdataTouch = ClipData.newPlainText("", "");
                    View.DragShadowBuilder MyshadowBuilder = new View.DragShadowBuilder(AltImg);

                    AltImg.startDrag(clipdataTouch, MyshadowBuilder, AltImg, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });
        */

        onWindowFocusChanged(true);

        //Used to get different Icons to drag and drop
        Test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //img=(ImageView)findViewById(R.id.imageView);
                //String input = img.getText().toString();
                System.out.println("This is the chosen icon = " + IconChosen);
                if(ImageChk == 0) {
                    ImageChk = 1;
                    img2.setVisibility(View.INVISIBLE);
                    img3.setVisibility(View.INVISIBLE);
                    img4.setVisibility(View.INVISIBLE);
                    img5.setVisibility(View.INVISIBLE);
                    //name1.setVisibility(View.INVISIBLE);
                    Test2.setText("Attacking Icon");
                    System.out.println("Attacking Icon");
                }
                else{
                    ImageChk = 0;
                    img2.setVisibility(View.VISIBLE);
                    img3.setVisibility(View.VISIBLE);
                    img4.setVisibility(View.VISIBLE);
                    img5.setVisibility(View.VISIBLE);
                    Test2.setText("Defensive Icon");
                    System.out.println("Defensive Icon");
                }
            }
        });
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
        name7 = (TextView) findViewById(R.id.Name7);
        name8 = (TextView) findViewById(R.id.Name8);
        name9 = (TextView) findViewById(R.id.Name9);
        name10 = (TextView) findViewById(R.id.Name10);
        name11 = (TextView) findViewById(R.id.Name11);
        name12 = (TextView) findViewById(R.id.Name12);
        name13 = (TextView) findViewById(R.id.Name13);
        name14 = (TextView) findViewById(R.id.Name14);
        name15 = (TextView) findViewById(R.id.Name15);

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
            PlayersNames[3] = str2;
            name4.setText(str2);
        }

        if(Pid.equals("ID5")) {
            player5 = str2;
            PlayersNames[4] = str2;
            name5.setText(str2);
        }

        if(Pid.equals("ID6")) {
            player6 = str2;
            PlayersNames[5] = str2;
            name6.setText(str2);
        }

        if(Pid.equals("ID7")) {
            player7 = str2;
            PlayersNames[6] = str2;
            name7.setText(str2);
        }

        if(Pid.equals("ID8")) {
            player8 = str2;
            PlayersNames[7] = str2;
            name8.setText(str2);
        }

        if(Pid.equals("ID9")) {
            player9 = str2;
            PlayersNames[8] = str2;
            name9.setText(str2);
        }

        if(Pid.equals("ID10")) {
            player10 = str2;
            PlayersNames[9] = str2;
            name10.setText(str2);
        }

        if(Pid.equals("ID11")) {
            player11 = str2;
            PlayersNames[10] = str2;
            name11.setText(str2);
        }

        if(Pid.equals("ID12")) {
            player12 = str2;
            PlayersNames[11] = str2;
            name12.setText(str2);
        }

        if(Pid.equals("ID13")) {
            player13 = str2;
            PlayersNames[12] = str2;
            name13.setText(str2);
        }

        if(Pid.equals("ID14")) {
            player14 = str2;
            PlayersNames[13] = str2;
            name14.setText(str2);
        }

        if(Pid.equals("ID15")) {
            player15 = str2;
            PlayersNames[14] = str2;
            name15.setText(str2);
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
        name7 = (TextView) findViewById(R.id.Name7);
        name8 = (TextView) findViewById(R.id.Name8);
        name9 = (TextView) findViewById(R.id.Name9);
        name10 = (TextView) findViewById(R.id.Name10);
        name11 = (TextView) findViewById(R.id.Name11);
        name12 = (TextView) findViewById(R.id.Name12);
        name13 = (TextView) findViewById(R.id.Name13);
        name14 = (TextView) findViewById(R.id.Name14);
        name15 = (TextView) findViewById(R.id.Name15);

        textViewNames[0]=name1;
        textViewNames[1]=name2;
        textViewNames[2]=name3;
        textViewNames[3]=name4;
        textViewNames[4]=name5;
        textViewNames[5]=name6;
        textViewNames[6]=name7;
        textViewNames[7]=name8;
        textViewNames[8]=name9;
        textViewNames[9]=name10;
        textViewNames[10]=name11;
        textViewNames[11]=name12;
        textViewNames[12]=name13;
        textViewNames[13]=name14;
        textViewNames[14]=name15;
        //textViewNames[3]=name4;
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
        String[] Players = {player1,player2,player3,player4,player5,player6,player7,player8,player9,player10,player11,player12,player13,player14,player15};
        //Used to know which textview is which
        TextView[] Names = {name1,name2,name3,name4,name5,name6,name7,name8,name9,name10,name11,name12,name13,name14,name15};

        //Used to set the textviews to the players names that were already chosen
        for(int i = 0 ; i < Players.length; i++){
            if( Players[i] != null) {
                Names[i].setText(Players[i]);
            }
        }
    }
}