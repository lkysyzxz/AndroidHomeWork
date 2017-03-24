package edu.cqu.wwt.drawerlayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 吴文韬 on 2017/3/22.
 * Modify List:
 * 吴文韬:Write comment    ——2017.3.22
 */

public class SqlHelper {
    private String drive="net.sourceforge.jtds.jdbc.Driver";    //Drive string
    private String connStr;     //connection string
    private String server;      //server IP
    private String dbName;      //dataBase Name
    private String userName;
    private String userPwd;
    private Connection con;     //connection for once query
    private PreparedStatement pstm;     //statement that ready to execute
    private CallableStatement cstm;     //statement for calling procedure
    public SqlHelper(String server,String dbName,
                     String userName,String userPwd){
        this.server=server;
        this.dbName=dbName;
        this.connStr="jdbc:jtds:sqlserver://"+this.server+":1433/"+this.dbName;
        this.userName=userName;
        this.userPwd=userPwd;
        try
        {
            Class.forName(drive);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    /**
     * [弃用的]
     * Function Name:CallProcedure
     * String procName:Procedure name
     * List<String> parmsName:参数名称集合
     * List<Object> parms:参数集合
     * Des:调用存储过程
     */

    public int CallProcedure(String procName,List<String> parmsName,List<Object> parms)
    {
        try
        {
            con = DriverManager.getConnection(this.connStr,this.userName,this.userPwd);
            String sql="{call "+procName;
            if(parmsName!=null&&!parms.equals(""))
            {
                sql+=" (";
                for(int i=0;i<parmsName.size();i++)
                {
                    if(i>0)sql+=",";
                    sql+=parmsName.get(i)+"=\'"+parms.get(i).toString()+"\'";
                }
                sql+=")";
            }
            sql+="}";
            cstm=con.prepareCall(sql);
            if(parmsName!=null&&!parms.equals(""))
            {
                for (int i = 0; i < parmsName.size(); i++)
                {
                    cstm.setObject(parmsName.get(i),parms.get(i));
                }
            }
            return cstm.executeUpdate();
        }catch (Exception e)
        {
            System.out.println(e.getMessage().toString());
            return -1;
        }finally
        {
            try
            {
                cstm.close();
                con.close();
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

    }

    /**
     * Function Name:MakeString
     * String str:待转换的字符串
     * Des:将字符串转换为数据库可以使用的字符串
     */
    public static String MakeString(String str)
    {
        return "\'"+str+"\'";
    }
    /**
     *  Function Name:ExeuteNonQuery
     *  String sql:待执行语句
     *  List<Object> params:参数列表
     *  Return value int:被修改行数
     */
    public int ExecuteNonQuery(String sql, List<Object> params)
    {
        try
        {
            //Create connection
            con= DriverManager.getConnection(this.connStr,this.userName,this.userPwd);
            //Create PrepareStatement
            pstm=con.prepareStatement(sql);
            //Add params
            //If there is no param.Ignore it!
            //Else add it/them.
            if(params !=null && !params.equals(""))
            {
                for(int i=0;i<params.size();i++)
                {
                    pstm.setObject(i+1,params.get(i));
                }
            }
            //Execute statement and return how many lines changed.
            return pstm.executeUpdate();

        }catch(SQLException e)
        {
            e.printStackTrace();
            return -1;
        }finally {
            try{
                pstm.close();;
                con.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     *  Function Name:ExeciteQuery
     *  String sql:待执行语句
     *  List<Object> params:参数列表
     *  Return value String:JSON结果集，需要用户自行解析
     */
    public String ExecuteQuery(String sql,List<Object> params)
    {

        //JSONArray will be returned as result
        JSONArray jsonArray=new JSONArray();
        try
        {
            //Create connection
            con=DriverManager.getConnection(this.connStr,this.userName,this.userPwd);
            //Create prepare statement
            pstm=con.prepareStatement(sql);
            //Add params
            //If there is no param.Ignore it!
            //Else add it/them.
            if(params!=null && !params.equals(""))
            {
                for(int i=0;i<params.size();i++)
                {
                    pstm.setObject(i+1,params.get(i));
                }
            }
            //Execute statement and get result
            ResultSet rs=pstm.executeQuery();
            //Get metadata
            //Metadata contain the column name of table
            ResultSetMetaData rsMetaData=rs.getMetaData();
            //Travers the result set to get data
            //The data will be made to JSONObject
            //JSONObject will be add into JSONArray
            //JSONArray will be returned as String
            while(rs.next())
            {
                JSONObject jsonObject=new JSONObject();
                for(int i=0;i<rsMetaData.getColumnCount();i++)
                {
                    //读取一行数据
                    String columnName=rsMetaData.getColumnLabel(i+1);
                    String value=rs.getString(columnName);
                    jsonObject.put(columnName,value);
                }
                jsonArray.put(jsonObject);
            }
            return jsonArray.toString();
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }finally {
            try
            {
                pstm.close();
                con.close();
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
