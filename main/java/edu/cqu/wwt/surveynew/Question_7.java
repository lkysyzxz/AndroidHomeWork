package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Question_7 extends QuestionActivity {
    private Button m_btnNext,m_btnBack;

    private RadioGroup m_choices;

    private String m_partyMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_7);
        initView();
        initEvent();
        m_partyMember=null;
    }

    @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_7_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_7_NEXT);
        m_choices=(RadioGroup)findViewById(R.id.QUE_7_CHOICES);
    }

    @Override
    protected void initEvent() {
        m_choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button= (RadioButton) Question_7.this.findViewById(checkedId);
                m_partyMember=button.getText().toString();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_7_NEXT:
                if(m_partyMember==null)
                    MainActivity.nextActivity(this,Question_8.class,false);
                else {
                    MainActivity.partyMember=m_partyMember;
                    MainActivity.nextActivity(this, Question_8.class, true);
                }
                break;
            case R.id.QUE_7_BACK:
                finish();
                break;
        }
    }
}
