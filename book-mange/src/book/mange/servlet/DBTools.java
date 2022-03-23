package book.mange.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;



public class DBTools {
	// MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/book_mange?useUnicode=true&characterEncoding=utf-8";
   private  Logger logger=LoggerFactory.getLogger(getClass()); 
    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
   
       
   
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "root";
    
    static Connection conn = null;
    private static void  connect() {
    	
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            if(conn == null) {
            	System.out.println("链接数据库失败");
            	
            }
            System.out.println("链接数据库成功");
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        
    }
    /*
     * 检查账号密码是否正确
     */
    
    public static boolean checkPassWord(String userName,String pwd) {
    	connect();
    	Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	String sql = "SELECT username, password FROM users ";
        ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);
			  while(rs.next()){
	                // 通过字段检索
	                String name  = rs.getString("username");
	                String password = rs.getString("password");
	             
	                if(userName.equals(name)&&pwd.equals(password)) {
	                	return true;
	                }
	                
	                
	                // 输出数据
	                System.out.print("用户名称: " + name);
	                System.out.print(", 登录密码: " + password);

	                System.out.print("\n");
	            }

			 System.out.println(rs);
			  // 完成后关闭
	        rs.close();
	        stmt.close();
	        conn.close();
	       
	       
	   
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
      
      
        return false;
    }
    
    
    /*
       * 查询数据的图书列表
     */
    public static List selectBooks() {
    	System.out.println("图书列表查询");
    	connect();
    	Statement stmt = null;
    	List<Map> list=new ArrayList();
    	
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	String sql = "SELECT * FROM books ";
        ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);
		//	logger.info(JSONObject.toJSONString(rs));
			  while(rs.next()){
				 Map map=new HashMap();
	                // 通过字段检索
				  String id  = rs.getString("id");
	                String name  = rs.getString("name");
	                String brief = rs.getString("brief");
	                String author = rs.getString("author");
	                String num = rs.getString("num");
	                String year = rs.getString("year");
	                
	                map.put("id",id);
	                map.put("name",name);
	                map.put("brief",brief);
	                map.put("author",author);
	                map.put("num",num);
	                map.put("year",year);
	                list.add(map);
	            }
         
			 System.out.println(rs);
			  // 完成后关闭
	        rs.close();
	        stmt.close();
	        conn.close();
	       
	       
	   
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
      
      
        return list;
    }
    
    /*
     * 添加图书
     */
    
    
    public  boolean AddBook(JSONObject rs) {
    	connect();
    	Statement stmt = null;
    	
    	//获取对应的数据
    	 String name  = rs.getString("name");
         String brief = rs.getString("author");
         String author = rs.getString("brief");
         String num = rs.getString("num");
         String year = rs.getString("year");
    	
    	
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	String sql = "insert into books(name,author,brief,year,num)  values('"+name+"','"+author+"','"+brief+"','"+year+"','"+num+"')";
       System.out.println("执行的sql语句"+sql);
    	try {
			stmt.execute(sql);
			 stmt.close();
		     conn.close();
		     System.out.println("图书添加完毕");
		     return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    /*
     * 删除图书
     */
    public static boolean DeleteBook(String id) {
    	System.out.println("删除图书开始。。。。");
    	connect();
    	Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	String sql = "delete from books where id = " + id ;
    	System.out.println("删除语句："+sql);
        boolean rs;
		try {
			rs = stmt.execute(sql);
			// 完成后关闭
	        stmt.close();
	        conn.close();
	        
	        return true;
	  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
    }
    
    
    /*
     * 修改图书
     */
    public static boolean UpdateBook(JSONObject rs) {
    	connect();
    	Statement stmt = null;
    	//获取对应的数据
    	String id  = rs.getString("id");
   	    String name  = rs.getString("name");
        String brief = rs.getString("brief");
        String author = rs.getString("author");
        String num = rs.getString("num");
        String year = rs.getString("year");
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	String sql = "update books set name='"+name+"',brief='"+brief+"',author='"+author+"',num='"+num+"',year='"+year +"' where id='"+id + "'";
    	System.out.println("更新语句："+sql);
			
		 try {
			stmt.execute(sql);
			stmt.close();
		    conn.close();
		    System.out.println("图书编辑成功");
		    return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // 完成后关闭
		 return false;
    }
    
    //账号注册
    
    public  boolean register(HttpServletRequest request) {
    	connect();
    	Statement stmt = null;
    	
    	//获取对应的数据
    	 String name  = request.getParameter("username");
         String pwd = request.getParameter("mobile");
         String mobile = request.getParameter("password");
         String Date = new Date().toString();;
         long time = new Date().getTime();
    	
    	
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	String sql = "insert into users(username,admin,password,Login_date,login_time)  values('"+name+"','"+pwd+"','"+mobile+"','"+Date+"','"+time+"')";
       System.out.println("执行的sql语句"+sql);
    	try {
			stmt.execute(sql);
			 stmt.close();
		     conn.close();
		     System.out.println("用户添加完毕");
		     return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    
}


