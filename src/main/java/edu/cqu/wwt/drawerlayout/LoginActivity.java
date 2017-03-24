package edu.cqu.wwt.drawerlayout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    private EditText m_account;
    private EditText m_password;
    private String ACCOUNT;
    private String PASSWORD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置导航栏
        Toolbar toolbar=(Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout main_drawer=(DrawerLayout)findViewById(R.id.login_drawer);
        //设置转换按钮
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,main_drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        main_drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navView=(NavigationView)findViewById(R.id.login_nav_view);
        navView.setNavigationItemSelectedListener(this);

        initData();
        initView();
    }


    /**
     * Function Name:initView
     * Des:绑定控件
     */
    private void initView()
    {
        m_account=(EditText)findViewById(R.id.login_edit_account);
        m_password=(EditText)findViewById(R.id.login_edit_password);
    }

    /**
     * Function Name:initData
     * Des:获取基本账号数据
     */
    private void initData()
    {
        ACCOUNT=getString(R.string.login_account);
        PASSWORD=getString(R.string.login_password);
    }

    /**
     * Function Name:judgeInput
     * Des:评判用户输入
     * @return true:评判通过,false:评判失败
     */
    private boolean judgeInput()
    {
        String inputAccount=m_account.getText().toString();
        String inputPassword=m_password.getText().toString();
        return (inputAccount.equals(ACCOUNT)&&inputPassword.equals(PASSWORD));
    }

    /**
     * Function Name:onButtonBackClick
     * Des:响应返回按钮的点击
     */
    public void onButtonBackClick(View view)
    {
        finish();
    }

    /**
     * Function Name:onButtonLoginClick
     * Des:响应登录按钮的点击
     */
    public void onButtonLoginClick(View view)
    {
        //Judge input
        if(judgeInput())
        {
            //Start ManagerActivity
            Status.LOGIN_FLAG=true;
            Status.LOGIN_RETURN=true;
            finish();
        }else{
            m_password.setText("");
            Toast.makeText(this,"账号密码有误",Toast.LENGTH_SHORT).show();
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
            case R.id.menu_login_about:
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
            case R.id.menu_login_about:
                Toast.makeText(this,"Created by 寒江雪——20141794",Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }
}
