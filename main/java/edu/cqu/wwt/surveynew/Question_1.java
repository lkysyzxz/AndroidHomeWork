package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Question_1 extends QuestionActivity {

    private Button m_btnNext,m_btnBack;
    private EditText m_editName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_1);
        initView();
        initEvent();
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_1_NEXT:
                MainActivity.name=m_editName.getText().toString();
                if(!MainActivity.name.equals(""))
                    MainActivity.nextActivity(this,Question_2.class,true);
                else
                    MainActivity.nextActivity(this,Question_2.class,false);
                break;
            case R.id.QUE_1_BACK:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_1_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_1_NEXT);
        m_editName=(EditText)findViewById(R.id.QUE_1_EDIT);
    }

    @Override
    protected void initEvent() {

    }
}
