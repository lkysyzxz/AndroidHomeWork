package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Question_11 extends QuestionActivity{

    private Button m_btnNext,m_btnBack;

    private RadioGroup m_choices;

    private String m_student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_11);
        initView();
        initEvent();
        m_student=null;
    }


   @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_11_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_11_NEXT);
        m_choices=(RadioGroup)findViewById(R.id.QUE_11_CHOICES);
    }

    @Override
    protected void initEvent() {
        m_choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button= (RadioButton) Question_11.this.findViewById(checkedId);
                m_student=button.getText().toString();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_11_NEXT:
                if(m_student==null)
                    MainActivity.nextActivity(this,Question_12.class,false);
                else {
                    MainActivity.student=m_student;
                    MainActivity.nextActivity(this, Question_12.class, true);
                }
                break;
            case R.id.QUE_11_BACK:
                finish();
                break;
        }
    }
}
