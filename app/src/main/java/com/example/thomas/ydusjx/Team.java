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

public class Team extends Activity {
    Button back,test1;
    TextView name1, name2, name3, name4, name5, name6, playerChosen;
    ImageView img, img2;
    String msg;
    MyDbHelper mydb;
    public static String str;
    public static String str2;
    public static String Pid;
    public static String test = "Name1";
    public static String id1, id2, id3, id4, id5, id6, player1, player2, player3, player4, player5, player6;//Player1, player2 are used to store the names of the players in the textview
    public static int chk;

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
        System.out.println("This is new player");
        System.out.println(str2 + "its id = " + Pid);

        //Used so the main activity cannot not change any of the players names
        if(str2 != null){
            setPlayerName(Pid, str2);
        }

        back = (Button) findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Moving to main menu");
                Intent ModList = new Intent(Team.this, MainActivity.class);
                startActivity(ModList);
            }
        });

        name1 = (TextView) findViewById(R.id.Name1);
        name2 = (TextView) findViewById(R.id.Name2);
        name3 = (TextView) findViewById(R.id.Name3);
        name4 = (TextView) findViewById(R.id.Name4);
        name5 = (TextView) findViewById(R.id.Name5);
        name6 = (TextView) findViewById(R.id.Name6);

        name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting the coordinates of the textfield
                int x = name1.getLeft();
                int y = name1.getTop();
                int width = name1.getWidth();
                int height = name1.getHeight();

                // x = 0 y = 144
                // width = 140 height = 57
                System.out.println("Coordinates x = " + x + " y = " + y);
                System.out.println("Name1 width = " + width + " height = " + height);
                //Assigns the textview an id to make it unique and identifiable
                id1 = "ID1";
                playerChosen = (TextView) findViewById(R.id.Name1);
                System.out.println(name1.getId());
                String a = name1.getText().toString();
                getPlayerName(a, id1);
            }
        });

        name2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int x = name2.getLeft();
                int y = name2.getTop();
                int width = name2.getWidth();
                int height = name2.getHeight();

                // x = 0 y = 144
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

                // x = 0 y = 144
                // width = 140 height = 57
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

        test1= (Button) findViewById(R.id.Test1);
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Test API");

               // try {
                    //MyGETRequest();
                //} catch (IOException e) {
                //    e.printStackTrace();
               // }
                MainActivity.updateApi();

            }
        });

        img=(ImageView)findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DragDrop(img, "image1");
            }
        });

        img2=(ImageView)findViewById(R.id.imageView2);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DragDrop(img2, "image2");
            }
        });
    }

    //Used to get new player from the database
    public void getPlayerName(String name, String id){
        System.out.println("This is new playerChosen");
        System.out.println(playerChosen.getText().toString());
        System.out.println(playerChosen);
        Intent PlayList = new Intent(Team.this, PlayerList.class);
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


        if(Pid.equals("ID1")) {
            player1 = str2;
            name1.setText(str2);
        }
        if(Pid.equals("ID2")) {
            player2 = str2;
            name2.setText(str2);
        }
        if(Pid.equals("ID3")) {
            player3 = str2;
            name3.setText(str2);
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
                Names[i].setText(str2);
            }
        }
        */


        //Used to reload the team when a new player is chosen
        loadTeam();
    }

    //When the team page is open this is called to load the players into their positions
    //Players that have been picked will be stored in strings such as player1 and player2
    public void loadTeam(){
        name1 = (TextView) findViewById(R.id.Name1);
        name2 = (TextView) findViewById(R.id.Name2);
        name3 = (TextView) findViewById(R.id.Name3);
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
        String[] Players = {player1,player2,player3};
        //Used to know which textview is which
        TextView[] Names = {name1,name2,name3};

        //Used to set the textviews to the players names that were already chosen
        for(int i = 0 ; i < Players.length; i++){
            if( Players[i] != null) {
                Names[i].setText(Players[i]);
            }
        }


    }

    //Called when one of the images is touched
    //Reference: The following code an Android example from https://www.tutorialspoint.com/android/android_drag_and_drop.htm
    public void DragDrop(ImageView image, String name){
        System.out.println("Inside DragDrop");
        System.out.println(name);

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


                        //Used to test the images coordinates
                        if(xValue >= 0 && xValue <= 200 && yValue >= 144 && yValue <= 225){
                            System.out.println("Player1 identified");
                            System.out.println("Players name is " + player1);
                        }

                        if(xValue >= 470 && xValue <= 610 && yValue >= 144 && yValue <= 201){
                            System.out.println("Player2 identified");
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

    public static void MyGETRequest() throws IOException {
        System.out.println("Entered MyGETRequest");
        URL urlForGetRequest = new URL("https://jsonplaceholder.typicode.com/posts/1");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } in .close();
            // print result
            System.out.println("JSON String Result " + response.toString());
            //GetAndPost.POSTRequest(response.toString());
        } else {
            System.out.println("GET NOT WORKED");
        }
    }
}
