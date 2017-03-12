package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class Question_9 extends QuestionActivity {

    private Button m_btnNext;
    private Button m_btnBack;
    private CheckBox m_overWatch;
    private CheckBox m_lol;
    private String[] select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_9);
        initView();
        initEvent();
        select=new String[2];
    }

    @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_9_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_9_NEXT);
        m_overWatch=(CheckBox)findViewById(R.id.QUE_9_CHOICE_1);
        m_lol=(CheckBox)findViewById(R.id.QUE_9_CHOICE_2);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_9_NEXT:
                if(!m_overWatch.isChecked()&&!m_lol.isChecked())
                    MainActivity.nextActivity(this,Question_10.class,false);

                if(m_overWatch.isChecked())select[0]="Overwatch";
                if(m_lol.isChecked())select[1]="LOL";
                MainActivity.games=select;
                MainActivity.nextActivity(this,Question_10.class,true);
                break;
            case R.id.QUE_9_BACK:
                finish();
                break;
        }
    }
}
