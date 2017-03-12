package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Question_3 extends QuestionActivity {

    private Button m_btnNext,m_btnBack;

    private RadioGroup m_choices;

    private String m_grade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_3);
        initView();
        initEvent();
        m_grade=null;
    }

    @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_3_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_3_NEXT);
        m_choices=(RadioGroup)findViewById(R.id.QUE_3_CHOICES);
    }

    @Override
    protected void initEvent() {
        m_choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button= (RadioButton) Question_3.this.findViewById(checkedId);
                m_grade=button.getText().toString();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_3_NEXT:
                if(m_grade==null)
                    MainActivity.nextActivity(this,Question_4.class,false);
                else {
                    MainActivity.grade=m_grade;
                    MainActivity.nextActivity(this, Question_4.class, true);
                }
                break;
            case R.id.QUE_3_BACK:
                finish();
                break;
        }
    }
}
