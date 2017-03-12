package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Question_10 extends QuestionActivity {

    private Button m_btnNext,m_btnBack;

    private RadioGroup m_choices;

    private String m_teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_10);
        initView();
        initEvent();
        m_teacher=null;
    }

    @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_10_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_10_NEXT);
        m_choices=(RadioGroup)findViewById(R.id.QUE_10_CHOICES);
    }

    @Override
    protected void initEvent() {
        m_choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button= (RadioButton) Question_10.this.findViewById(checkedId);
                m_teacher=button.getText().toString();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_10_NEXT:
                if(m_teacher==null)
                    MainActivity.nextActivity(this,Question_11.class,false);
                else {
                    MainActivity.teacher=m_teacher;
                    MainActivity.nextActivity(this,Question_11.class,true);
                }
                break;
            case R.id.QUE_10_BACK:
                finish();
                break;
        }
    }
}
