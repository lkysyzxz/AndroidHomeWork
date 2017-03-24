package edu.cqu.wwt.drawerlayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ManagerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //SQL
    private SqlHelper sh=null;

    //Control
    private Spinner m_spinnerGrades;

    //Data
    private List<String> m_grades=new ArrayList<String>();

    //[丢弃的]
    //private Thread readGradeThread=new Thread(new Runnable() {

    //@Override

    //public void run() {

    //Message msg=Message.obtain();

    //msg.what=1;//读取年级数据

    //msg.obj=QueryGrades();

    //handler.sendMessage(msg);

    //}

    //});

    private Thread createNewTaskTread=new Thread(new Runnable() {
        @Override
        public void run() {
            Message msg=Message.obtain();
            msg.what=2;
            msg.obj=createNewTask();
            handler.sendMessage(msg);
        }
    });

    private Thread killCurrentTaskThread=new Thread(new Runnable() {
        @Override
        public void run() {
            Message msg=Message.obtain();
            msg.what=3;
            msg.obj=deleteCurrentTask();
            handler.sendMessage(msg);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        //初始化数据库
        sh=Global.getGlobalSqlHelper();
        //设置导航栏
        Toolbar toolbar=(Toolbar)findViewById(R.id.manager_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout manager_drawer=(DrawerLayout)findViewById(R.id.manager_drawer);
        //设置转换按钮
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,manager_drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        manager_drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navView=(NavigationView)findViewById(R.id.manager_nav_view);
        navView.setNavigationItemSelectedListener(this);

        initView();
        initData();
    }

    /**
     * Function Name:deleteCurrentTask
     * Des:删除当前任务
     * @return
     */
    private int deleteCurrentTask()
    {
        String grade=m_spinnerGrades.getSelectedItem().toString();
        grade=SqlHelper.MakeString(grade);
        String sql="delete from CurrentTask where grade=" + grade + ";";
        int res=sh.ExecuteNonQuery(sql,new ArrayList<Object>());
        return res;
    }

    /**
     * Function Name:createNewTask
     * Des:创建一个新任务
     * @return
     */
    private int createNewTask()
    {
        String grade = m_spinnerGrades.getSelectedItem().toString();
        grade = SqlHelper.MakeString(grade);
        String sql = "insert into CurrentTask(dateTime,grade) values(convert(nvarchar(32),getdate(),20)," + grade + ");";
        int res=sh.ExecuteNonQuery(sql,new ArrayList<Object>());
        return res;
    }


    /**
     * FunctionName:initView
     * Des:绑定控件
     */
    private void initView() {
        m_spinnerGrades=(Spinner)findViewById(R.id.manager_spinner_grades);
    }

    /**
     * Function Name:initData
     * Des:初始化数据
     */
    private void initData()
    {
        //readGradeThread.start();
        m_grades=Global.GetGradesData();
        initGrade();
        m_spinnerGrades.setSelection(0, false);
        //Toast.makeText(ManagerActivity.this, "年级数据加载完成", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch(msg.what)
            {
                case 2:
                    //创建新任务
                    int res_2=(int)msg.obj;
                    if(res_2>0)
                    {
                        Toast.makeText(ManagerActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ManagerActivity.this,"创建失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    //删除当前任务
                    int res_3=(int)msg.obj;
                    if(res_3>0)
                    {
                        Toast.makeText(ManagerActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ManagerActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    /**
     * Function Name:initGrade
     * Des:初始化年级Spinner
     */
    private void initGrade()
    {
        ArrayAdapter<String> gradeAdapter=new ArrayAdapter<String>(ManagerActivity.this,android.R.layout.simple_spinner_item,m_grades);
        m_spinnerGrades.setAdapter(gradeAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.menu_login_record:
                finish();
                break;
            case R.id.menu_about:
                Toast.makeText(this,"Created by 寒江雪——20141794",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.menu_login_record:
                finish();
                break;
            case R.id.menu_about:
                Toast.makeText(this,"Created by 寒江雪——20141794",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    /**
     * Function Name:onCreateNewTaskButtonClick
     * Des:响应创建新任务的按钮
     */
    public void onCreateNewTaskButtonClick(View view)
    {
        createNewTaskTread.start();
    }


    /**
     * Function Name:onKillCurrentTaskButtonClick
     * Des:响应结束当前任务的按钮
     */
    public void onKillCurrentTaskButtonClick(View view)
    {
        killCurrentTaskThread.start();
    }
}
