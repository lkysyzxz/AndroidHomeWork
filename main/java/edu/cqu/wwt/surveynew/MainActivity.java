package edu.cqu.wwt.surveynew;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import edu.cqu.wwt.surveynew.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    //Static Field
    public static String name;
    public static String gender;
    public static String grade;
    public static String major;
    public static String class_;
    public static String member;
    public static String partyMember;
    public static String home;
    public static String[] games;
    public static String teacher;
    public static String student;
    public static String luckyNumber;
    //Other Field


    ActivityMainBinding m_binding;//Button Binding
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //m_btnBegin=(Button)findViewById(R.id.MAIN_BTN_BEGIN);
        //m_btnClose=(Button)findViewById(R.id.MAIN_BTN_CLOSE);
        m_binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        games=new String[2];
        m_binding.MAINBTNBEGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.nextActivity(MainActivity.this,Question_1.class,true);
            }
        });

        m_binding.MAINBTNCLOSE.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    public static void nextActivity(Context context,Class<?> nextActivity,boolean isClick){
        if(!isClick){
            Toast.makeText(context, "Please make a choice", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent=new Intent(context,nextActivity);
            context.startActivity(intent);
        }
    }



}
