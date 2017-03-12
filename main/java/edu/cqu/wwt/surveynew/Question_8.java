package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Question_8 extends QuestionActivity {
    private Button m_btnNext,m_btnBack;
    private EditText m_editName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_8);
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_8_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_8_NEXT);
        m_editName=(EditText)findViewById(R.id.QUE_8_EDIT);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_8_NEXT:
                MainActivity.home=m_editName.getText().toString();
                if(!MainActivity.home.equals(""))
                    MainActivity.nextActivity(this,Question_9.class,true);
                else
                    MainActivity.nextActivity(this,Question_9.class,false);
                break;
            case R.id.QUE_8_BACK:
                finish();
                break;
        }
    }
}
