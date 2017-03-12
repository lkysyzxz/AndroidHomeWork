package edu.cqu.wwt.surveynew;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class Question_4 extends QuestionActivity {

    private Button m_btnNext,m_btnBack;

    private ListView m_majorList;

    private String m_major;

    private String[] m_selections;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_4);
        initView();
        initEvent();
        m_major=null;
        m_selections=getResources().getStringArray(R.array.QUESTION_4_MAJORS);

    }

    @Override
    protected void initView() {
        m_btnBack=(Button)findViewById(R.id.QUE_4_BACK);
        m_btnNext=(Button)findViewById(R.id.QUE_4_NEXT);
        m_majorList=(ListView)findViewById(R.id.QUE_4_LIST);
    }

    @Override
    protected void initEvent() {
        m_majorList.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_major=m_selections[position];
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.QUE_4_NEXT:
                if(m_major==null)
                    MainActivity.nextActivity(this,Question_5.class,false);
                else {
                    MainActivity.major=m_major;
                    MainActivity.nextActivity(this, Question_5.class, true);
                }
                break;
            case R.id.QUE_4_BACK:
                finish();
                break;
        }
    }
}
