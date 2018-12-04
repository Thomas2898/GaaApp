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
import android.widget.TextView;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.id.list;
import static android.R.layout.simple_list_item_1;
/**
 * Created by Thomas on 27/10/2018.
 */

public class Team extends Activity {
    Button back;
    TextView name1, name2, playerChosen;
    ImageView img, img2;
    String msg;
    MyDbHelper mydb;
    public static String str;
    public static String str2;
    public static String Pid;
    public static String test = "Name1";
    public static String id1, id2, player1, player2;//Player1, player2 are used to store the names of the players in the textview
    public static int chk;

    private android.widget.LinearLayout.LayoutParams layoutParams;

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
                System.out.println("Codrinates x = " + x + " y = " + y);
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
                //Assigns the textview an id to make it unique and identifiable
                id2 = "ID2";
                playerChosen = (TextView) findViewById(R.id.Name2);
                String a = name2.getText().toString();
                System.out.println(a);
                getPlayerName(a, id2);
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
        if(Pid.equals("ID1")) {
            name1 = (TextView) findViewById(R.id.Name1);
            player1 = str2;
            name1.setText(str2);
        }
        if(Pid.equals("ID2")) {
            name2 = (TextView) findViewById(R.id.Name2);
            player2 = str2;
            name2.setText(str2);
        }

        //Used to reload the team when a new player is chosen
        loadTeam();
    }

    //When the team page is open this is called to load the players into their positions
    //Players that have been picked will be stored in strings such as player1 and player2
    public void loadTeam(){
        System.out.println("Entered load time");
        name1 = (TextView) findViewById(R.id.Name1);
        name2 = (TextView) findViewById(R.id.Name2);
        //name1.setText(player1);

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
                        layoutParams = (LinearLayout.LayoutParams)v.getLayoutParams();
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
                        System.out.println("This is the x value in ACTION_DRAG_ENDED");
                        System.out.println(xValue);
                        //Used to test the images coordinates
                        if(xValue >= 200 && xValue <= 300){
                            System.out.println("Pass added");
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
}
