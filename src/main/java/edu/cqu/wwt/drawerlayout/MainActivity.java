package edu.cqu.wwt.drawerlayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *  Created by 吴文韬 on 2017/3/22
 *  2017/3/22  Update:界面控件设置完成
 *  2017/3/22  Update:数据读取完成并测试通过
 *  2017/3/23  Update:数据提交完成并测试通过
 *  2017/3/23  Update:完成界面跳转并测试通过
 *  2017/3/23  Update:主界面完成并测试通过，主界面周五前不再改动
 */
public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{


    //SQL
    private SqlHelper sh=null;//创建数据库连接对象

    //Data
    private List<String> m_grades=new ArrayList<String>();      //年级，直接可以从表中读取的数据，只需要一次读取
    private List<String> m_dormitroys=new ArrayList<String>();  //寝室号，随着年级的改变而改变的数据，可能会多次读取，年级的选项改变即改变
    private List<String> m_roomCommanders=new ArrayList<String>();  //寝室长,随着年级的改变而改变的数据，可能会多次读取，年级的选项改变即改变，位置与寝室号一一对应
    private List<String> m_roomUmpires=new ArrayList<String>(); //寝室总人数，随着年级的改变而改变的数据，可能会多次读取，年级的选项改变即改变，位置与寝室号一一对应

    //Control
    private Spinner m_spinnerGrades;
    private Spinner m_spinnerDormitorys;
    private TextView m_roomCommander;
    private TextView m_roomTotalUmpires;
    private EditText m_realUmpires;
    private EditText m_otherInformation;


    //ReadData
    //读取年级数据线程
    Thread readGradesThread=new Thread(new Runnable() {
        @Override
        public void run() {
            Message msg=Message.obtain();
            msg.what=1;//读取年级数据
            msg.obj=QueryGrades();
            handler.sendMessage(msg);
        }
    });

    //读取寝室数据线程
    Thread readDormitoryThread=new Thread(new Runnable() {
        @Override
        public void run() {
            String gradeSelected=m_grades.get(m_spinnerGrades.getSelectedItemPosition());
            Message msg=Message.obtain();
            msg.what=2;
            msg.obj=QueryDormitoryInfo(gradeSelected);
            handler.sendMessage(msg);
        }
    });


    /**
     * Function QueryForGrade
     * Des:查询年级集合，供选择
     * @return JSONArray的String形式,供后续解析
     */
    private String QueryGrades()
    {
        String sql="select grade from Grade";
        String jsonArray=null;
        try
        {
            jsonArray=sh.ExecuteQuery(sql,new ArrayList<Object>());
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return jsonArray;
    }



    /**
     * Function QueryForGrade
     * Des:查询寝室信息，供选择和提交时使用
     * @param grade:以年级为标准查询
     * @return JSONArray的String形式,供后续解析
     */
    private String QueryDormitoryInfo(String grade)
    {
        String sql="select roomName,roomCommander,roomUmpires from DormitoryInformation where grade=\'"+grade+"\'";
        String jsonArray=null;
        try
        {
            jsonArray=sh.ExecuteQuery(sql,new ArrayList<Object>());
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return jsonArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据库连接
        sh=Global.getGlobalSqlHelper();
        //设置导航栏
        Toolbar toolbar=(Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout main_drawer=(DrawerLayout)findViewById(R.id.main_drawer);
        //设置转换按钮
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,main_drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        main_drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navView=(NavigationView)findViewById(R.id.main_nav_view);
        navView.setNavigationItemSelectedListener(this);

        initView();
        initData();

    }

    /**
     * FunctionName:initView
     * Des:绑定控件
     */
    private void initView()
    {
        m_spinnerGrades=(Spinner)findViewById(R.id.main_spinner_grades);
        m_spinnerDormitorys=(Spinner)findViewById(R.id.main_spinner_rooms);
        m_roomCommander= (TextView) findViewById(R.id.main_text_commander);
        m_roomTotalUmpires=(TextView)findViewById(R.id.main_text_total_umpires);
        m_realUmpires=(EditText)findViewById(R.id.main_edit_real_umpires);
        m_otherInformation=(EditText)findViewById(R.id.main_edit_otherinfo);
    }

    /**
     * Function Name:initGrade
     * Des:初始化年级Spinner
     */
    private void initGrade()
    {

        ArrayAdapter<String> gradeAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,m_grades);
        m_spinnerGrades.setAdapter(gradeAdapter);
        m_spinnerGrades.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //年级选项被修改
                //跟新寝室选项
                readDormitoryThread.start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Function Name:initDormitoryInformation
     * Des:初始化寝室信息
     */
    private void initDormitoryInformation()
    {
        ArrayAdapter<String> dormitoryInformationAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m_dormitroys);
        m_spinnerDormitorys.setAdapter(dormitoryInformationAdapter);
        m_spinnerDormitorys.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TO DO::
                m_roomCommander.setText(m_roomCommanders.get(position));
                m_roomTotalUmpires.setText(m_roomUmpires.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    /**
     * Function Name:initData
     * Des:初始化数据
     */
    private void initData()
    {
        //执行线程
        readGradesThread.start();
        //readDormitoryThread.start();
    }

    private void JsonToGrade(String jsonArray)
    {
        Gson gson=new Gson();
        Type type=new TypeToken<List<Grade>>(){}.getType();

        List<Grade> grade=gson.fromJson(jsonArray,type);
        for(Grade g:grade) {
            m_grades.add(g.getGrade());
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch(msg.what)
            {
                case 1:
                    //年级数据读取
                    if(msg.obj!=null&&!msg.obj.toString().equals("")) {
                        String gradesJson = msg.obj.toString();
                        //年级数据读取完成
                        JsonToGrade(gradesJson);
                        initGrade();
                        m_spinnerGrades.setSelection(0, false);
                        //readDormitoryThread.start();
                        //m_grades.add("xxxx");
                        Global.SetGradeData(m_grades);
                        Toast.makeText(MainActivity.this, "年级数据加载完成", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "年级数据加载失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:

                    if(msg.obj!=null&&!msg.obj.toString().equals("")) {
                        m_dormitroys.clear();
                        m_roomCommanders.clear();
                        m_roomUmpires.clear();
                        String dormitoryInformation = msg.obj.toString();
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<DormitoryInformation>>() {
                        }.getType();
                        List<DormitoryInformation> dormitoryInformations = gson.fromJson(dormitoryInformation, type);

                        for (DormitoryInformation dorInfo : dormitoryInformations) {
                            m_dormitroys.add(dorInfo.getDormitory());
                            m_roomCommanders.add(dorInfo.getRoomCommander());
                            m_roomUmpires.add(dorInfo.getRoomUmpires());
                        }
                        initDormitoryInformation();
                        m_spinnerDormitorys.setSelection(0, false);
                        m_roomCommander.setText(m_roomCommanders.get(0));
                        m_roomTotalUmpires.setText(m_roomUmpires.get(0));
                        //寝室信息读取
                        Toast.makeText(MainActivity.this, "寝室信息读取\"完成\"", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(MainActivity.this, "寝室信息读取\"失败\"", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    /**
     * FunctionName:onSubmitClick
     * Des:响应提交按钮
     * @param view
     */
    public void onSubmitClick(View view)
    {
        String grade=m_spinnerGrades.getSelectedItem().toString();
        String dormitory=m_spinnerDormitorys.getSelectedItem().toString();
        String roomCommander=m_roomCommander.getText().toString();
        final String realUmpires=m_realUmpires.getText().toString();
        final String totalUmpires=m_roomTotalUmpires.getText().toString();
        String otherInformation=m_otherInformation.getText().toString();
        final String res="";
        if(realUmpires.equals(""))
        {
            Toast.makeText(this,"请输入在寝人数",Toast.LENGTH_SHORT).show();
            return;
        }
        final int iRealUmpires=Integer.parseInt(realUmpires);
        final int iTotalUmpires=Integer.parseInt(totalUmpires);
        if(iRealUmpires>iTotalUmpires)
        {
            Toast.makeText(this,"在寝人数多于实际人数",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(iRealUmpires<iTotalUmpires&&otherInformation.equals(""))
        {
            Toast.makeText(this,"在寝人数不满，请输入备注信息",Toast.LENGTH_SHORT).show();
            return;
        }

        grade=SqlHelper.MakeString(grade);
        dormitory=SqlHelper.MakeString(dormitory);
        roomCommander=SqlHelper.MakeString(roomCommander);
        otherInformation=SqlHelper.MakeString(otherInformation);
        final String sql="execute proc_insertSafeRecord"+grade+","+dormitory+","+roomCommander+","+realUmpires+","+totalUmpires+","+otherInformation+","+"''";
        Thread commitData=new Thread(new Runnable() {
            @Override
            public void run() {

                //String sql="{call proc_insertSafeRecord(?,?,?,?,?,?,?)}";
                int result = sh.ExecuteNonQuery(sql, new ArrayList<Object>());
                Looper.prepare();
                if (result == -1 || result == 0) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        });
        commitData.start();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     *  FunctionName:onCreateOptionMenu
     *  Menu menu:对应的菜单
     *  Des:创建菜单选项
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * FunctionName:onOptionsItemsSelected
     * MenuItem item:被选择的菜单项
     * Des:响应右边菜单的点击操作
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch (id){
            case R.id.menu_manager:
                if(Status.LOGIN_FLAG)
                {
                    //Start Activity Manager
                    Intent intent=new Intent(this,ManagerActivity.class);
                    startActivity(intent);
                }else
                {
                    //Start Activity Login
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                }
                //Toast.makeText(this,"ManagerRight",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_about:
                Toast.makeText(this,"Created by 寒江雪——20141794",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Function onNavigationItemSelected
     * MenuItem item:侧边导航栏被选择的菜单项
     * Des:响应侧边菜单的点击操作
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id=item.getItemId();
        switch(id){
            case R.id.menu_manager:
                if(Status.LOGIN_FLAG)
                {
                    //Start Activity Manager
                    Intent intent=new Intent(this,ManagerActivity.class);
                    startActivity(intent);
                }else
                {
                    //Start Activity Login
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                }
                //Toast.makeText(this,"ManagerLeft",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_about:
                Toast.makeText(this,"Created by 寒江雪——20141794",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(Status.LOGIN_RETURN)
        {
            Status.LOGIN_RETURN=false;
            if(Status.LOGIN_FLAG)
            {
                //StartActivity ManagerActivity
                Intent intent=new Intent(this,ManagerActivity.class);
                startActivity(intent);
            }
        }
    }
}
