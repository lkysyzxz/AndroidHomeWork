package edu.cqu.wwt.surveynew;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 吴文韬 on 2017/3/12.
 */

public abstract class QuestionActivity extends AppCompatActivity {

    protected abstract void initView();
    protected abstract void initEvent();

    public abstract void onClick(View view);
}
