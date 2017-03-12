package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Question_5 extends QuestionActivity {
    private Button m_btnNext,m_btnBack;
    private EditText m_editClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_5);
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_5_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_5_NEXT);
        m_editClass=(EditText)findViewById(R.id.QUE_5_EDIT);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_5_NEXT:
                MainActivity.class_=m_editClass.getText().toString();
                if(!MainActivity.class_.equals(""))
                    MainActivity.nextActivity(this,Question_6.class,true);
                else
                    MainActivity.nextActivity(this,Question_6.class,false);
                break;
            case R.id.QUE_5_BACK:
                finish();
                break;
        }
    }
}
