package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static edu.cqu.wwt.surveynew.MainActivity.luckyNumber;

public class Question_12 extends QuestionActivity {

    private Button m_btnNext,m_btnBack;
    private EditText m_editLuckyNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_12);
        initView();
        initEvent();
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_12_NEXT:
                luckyNumber=m_editLuckyNumber.getText().toString();
                if(luckyNumber.equals(""))
                    MainActivity.nextActivity(this,Question_12.class,false);
                else {
                    //Save
                    saveData();
                    MainActivity.nextActivity(this,MainActivity.class,true);
                }
            case R.id.QUE_12_BACK:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_12_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_12_NEXT);
        m_editLuckyNumber=(EditText)findViewById(R.id.QUE_12_EDIT);
    }

    @Override
    protected void initEvent() {

    }

    public void saveData() {
        String content = "";
        String nameField = "Name:" + MainActivity.name + "\n";
        String genderFied = "Gender:" + MainActivity.gender + "\n";
        String gradeField = "Grade:" + MainActivity.grade + "\n";
        String majorField = "Major:" + MainActivity.major + "\n";
        String classField = "Class:" + MainActivity.class_ + "\n";
        String memberField="Member:"+MainActivity.member+"\n";
        String partyMemberField="PartyMember:"+MainActivity.partyMember+"\n";
        String homeField="Home:"+MainActivity.home+"\n";
        String gameField="Game:";
        for(int i=0;i<MainActivity.games.length;i++){
            if(MainActivity.games[i]!=null){
                if(i!=0)
                    gameField+=MainActivity.games[i];
                else
                    gameField+=","+MainActivity.games[i];
            }
        }
        gameField+="\n";
        String teacherField="Teacher:"+MainActivity.teacher+"\n";
        String studentField="Student:"+MainActivity.student+"\n";
        String luckyNumberField="LuckyNumber:"+MainActivity.luckyNumber+"\n";

        content=nameField+genderFied+gradeField+majorField+classField+memberField+partyMemberField+homeField+gameField+teacherField+studentField+luckyNumberField;
        content+="Thank you for submit your information.\n";
        String path=getExternalFilesDir(null).getAbsolutePath().toString();
        File file=new File(path+"/survey.txt");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Toast.makeText(Question_12.this,"Create File Faile",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(file.exists()){
            try {
                FileOutputStream ostream=new FileOutputStream(file);
                ostream.write(content.getBytes());
                ostream.flush();
                Toast.makeText(this,"Data Save",Toast.LENGTH_SHORT).show();
                ostream.close();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }
}
