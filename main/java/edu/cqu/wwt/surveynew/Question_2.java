package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Question_2 extends QuestionActivity {

    private Button m_btnNext,m_btnBack;

    private RadioGroup m_choices;

    private String m_gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_2);
        initView();
        initEvent();
        m_gender=null;
    }

    @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_2_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_2_NEXT);
        m_choices=(RadioGroup)findViewById(R.id.QUE_2_CHOICES);
    }

    @Override
    protected void initEvent() {
        m_choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button= (RadioButton) Question_2.this.findViewById(checkedId);
                m_gender=button.getText().toString();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_2_NEXT:
                if(m_gender==null)
                    MainActivity.nextActivity(this,Question_3.class,false);
                else {
                    MainActivity.gender=m_gender;
                    MainActivity.nextActivity(this, Question_3.class, true);
                }
                break;
            case R.id.QUE_2_BACK:
                finish();
                break;
        }
    }
}
