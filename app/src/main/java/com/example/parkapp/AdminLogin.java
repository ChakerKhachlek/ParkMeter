package com.example.parkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.parkapp.utilities.Message;

import java.io.IOException;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener {

    int attempts;
    final MediaPlayer mp = new MediaPlayer();
    TextView inputCode;
    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    Button buttonResetInput,buttonOk,buttonCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        attempts=0;

        btn0=(Button) findViewById(R.id.button0);
        btn0.setOnClickListener(this);

        btn1=(Button) findViewById(R.id.button1);
        btn1.setOnClickListener(this);

        btn2=(Button) findViewById(R.id.button2);
        btn2.setOnClickListener(this);

        btn3=(Button) findViewById(R.id.button3);
        btn3.setOnClickListener(this);

        btn4=(Button) findViewById(R.id.button4);
        btn4.setOnClickListener(this);

        btn5=(Button) findViewById(R.id.button5);
        btn5.setOnClickListener(this);

        btn6=(Button) findViewById(R.id.button6);
        btn6.setOnClickListener(this);

        btn7=(Button) findViewById(R.id.button7);
        btn7.setOnClickListener(this);

        btn8=(Button) findViewById(R.id.button8);
        btn8.setOnClickListener(this);

        btn9=(Button) findViewById(R.id.button9);
        btn9.setOnClickListener(this);

        buttonResetInput=(Button) findViewById(R.id.buttonResetInput);
        buttonResetInput.setOnClickListener(this);

        buttonCancel=(Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(this);

        buttonOk=(Button) findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(this);

        inputCode=(TextView)findViewById(R.id.inputCode);
        inputCode.setText("");


    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        switch(b.getId()){
            case R.id.button0:
                inputCode.setText(inputCode.getText().toString().concat("0"));
                //playing button click sound  effect
                playSoundEffect("buttonclick.mp3");

                break;

            case R.id.button1:
                inputCode.setText(inputCode.getText().toString().concat("1"));
                //playing button click sound  effect
                playSoundEffect("buttonclick.mp3");

                break;

            case R.id.button2:
                inputCode.setText(inputCode.getText().toString().concat("2"));
                //playing button click sound  effect
                playSoundEffect("buttonclick.mp3");

                break;
            case R.id.button3:
                inputCode.setText(inputCode.getText().toString().concat("3"));
                //playing button click sound  effect
                playSoundEffect("buttonclick.mp3");

                break;
            case R.id.button4:
                inputCode.setText(inputCode.getText().toString().concat("4"));
                //playing button click sound  effect
                playSoundEffect("buttonclick.mp3");

                break;
            case R.id.button5:
                inputCode.setText(inputCode.getText().toString().concat("5"));
                //playing button click sound  effect
                playSoundEffect("buttonclick.mp3");

                break;
            case R.id.button6:
                inputCode.setText(inputCode.getText().toString().concat("6"));
                //playing button click sound  effect
                playSoundEffect("buttonclick.mp3");

                break;
            case R.id.button7:
                inputCode.setText(inputCode.getText().toString().concat("7"));
                //playing button click sound  effect
                playSoundEffect("buttonclick.mp3");

                break;
            case R.id.button8:
                inputCode.setText(inputCode.getText().toString().concat("8"));
                //playing button click sound  effect
                playSoundEffect("buttonclick.mp3");

                break;

            case R.id.button9:
                inputCode.setText(inputCode.getText().toString().concat("9"));
                //playing button click sound  effect

                playSoundEffect("buttonclick.mp3");

                break;

            case R.id.buttonResetInput:
                inputCode.setText("");
                break;
            case R.id.buttonOk:
                attempts=attempts+1;
                if(inputCode.getText().toString().equals("24211273") ){
                    Message.shortMessage(getApplication(),"Welcome Technician");
                    playSoundEffect("electrical_door_open.mp3");


                    Intent adminIntent=new Intent(AdminLogin.this,AdminManager.class);
                    startActivity(adminIntent);
                    finish();

                }else if(attempts < 3 && !(inputCode.getText().toString().equals("24211273"))){
                    playSoundEffect("wrong_effect.mp3");

                    Message.shortMessage(getApplication(),"Wrong Security Code "+(3-attempts)+" attempts left");

                }else if(attempts >= 3 && !(inputCode.getText().toString().equals("24211273"))){
                    Message.shortMessage(getApplication(),"Attempts Passed 3 , Security block .");


                    //disabling all buttons

                    btn0.setEnabled(false);
                    btn0.setBackgroundColor(Color.RED);
                    btn1.setEnabled(false);
                    btn1.setBackgroundColor(Color.RED);
                    btn2.setEnabled(false);
                    btn2.setBackgroundColor(Color.RED);
                    btn3.setEnabled(false);
                    btn3.setBackgroundColor(Color.RED);
                    btn4.setEnabled(false);
                    btn4.setBackgroundColor(Color.RED);
                    btn5.setEnabled(false);
                    btn5.setBackgroundColor(Color.RED);
                    btn6.setEnabled(false);
                    btn6.setBackgroundColor(Color.RED);
                    btn7.setEnabled(false);
                    btn7.setBackgroundColor(Color.RED);
                    btn8.setEnabled(false);
                    btn8.setBackgroundColor(Color.RED);
                    btn9.setEnabled(false);
                    btn9.setBackgroundColor(Color.RED);


                    buttonResetInput.setEnabled(false);
                    buttonResetInput.setBackgroundColor(Color.RED);

                    buttonOk.setEnabled(false);
                    buttonOk.setBackgroundColor(Color.RED);

                    buttonCancel.setEnabled(false);
                    buttonCancel.setBackgroundColor(Color.RED);

                    //playing emergency alarm sound  effect
                    playSoundEffect("emergency_alarm.mp3");

                }
                break;

            case R.id.buttonCancel:
                Intent intentConfig=new Intent(AdminLogin.this,MainActivity.class);
                startActivity(intentConfig);
                finish();
                break;

        }
    }

    public void playSoundEffect(String fileUrl){
        try {
            mp.reset();
            AssetFileDescriptor afd;
            afd = getAssets().openFd(fileUrl);
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}