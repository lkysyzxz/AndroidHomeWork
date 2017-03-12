package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Question_6 extends QuestionActivity{
    private Button m_btnNext,m_btnBack;

    private RadioGroup m_choices;

    private String m_member;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_6);
        initView();
        initEvent();
        m_member=null;
    }

    @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_6_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_6_NEXT);
        m_choices=(RadioGroup)findViewById(R.id.QUE_6_CHOICES);
    }

    @Override
    protected void initEvent() {
        m_choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button= (RadioButton) Question_6.this.findViewById(checkedId);
                m_member=button.getText().toString();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_6_NEXT:
                if(m_member==null)
                    MainActivity.nextActivity(this,Question_7.class,false);
                else {
                    MainActivity.member=m_member;
                    MainActivity.nextActivity(this, Question_7.class, true);
                }
                break;
            case R.id.QUE_6_BACK:
                finish();
                break;
        }
    }
}
