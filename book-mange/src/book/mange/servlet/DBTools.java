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
	// MySQL 8.0 ���°汾 - JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/book_mange?useUnicode=true&characterEncoding=utf-8";
   private  Logger logger=LoggerFactory.getLogger(getClass()); 
    // MySQL 8.0 ���ϰ汾 - JDBC �����������ݿ� URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
   
       
   
    // ���ݿ���û��������룬��Ҫ�����Լ�������
    static final String USER = "root";
    static final String PASS = "root";
    
    static Connection conn = null;
    private static void  connect() {
    	
        try{
            // ע�� JDBC ����
            Class.forName(JDBC_DRIVER);
            
            // ������
            System.out.println("�������ݿ�...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            if(conn == null) {
            	System.out.println("�������ݿ�ʧ��");
            	
            }
            System.out.println("�������ݿ�ɹ�");
        }catch(SQLException se){
            // ���� JDBC ����
            se.printStackTrace();
        }catch(Exception e){
            // ���� Class.forName ����
            e.printStackTrace();
        }
        
    }
    /*
     * ����˺������Ƿ���ȷ
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
	                // ͨ���ֶμ���
	                String name  = rs.getString("username");
	                String password = rs.getString("password");
	             
	                if(userName.equals(name)&&pwd.equals(password)) {
	                	return true;
	                }
	                
	                
	                // �������
	                System.out.print("�û�����: " + name);
	                System.out.print(", ��¼����: " + password);

	                System.out.print("\n");
	            }

			 System.out.println(rs);
			  // ��ɺ�ر�
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
       * ��ѯ���ݵ�ͼ���б�
     */
    public static List selectBooks() {
    	System.out.println("ͼ���б��ѯ");
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
	                // ͨ���ֶμ���
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
			  // ��ɺ�ر�
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
     * ���ͼ��
     */
    
    
    public  boolean AddBook(JSONObject rs) {
    	connect();
    	Statement stmt = null;
    	
    	//��ȡ��Ӧ������
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
       System.out.println("ִ�е�sql���"+sql);
    	try {
			stmt.execute(sql);
			 stmt.close();
		     conn.close();
		     System.out.println("ͼ��������");
		     return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    /*
     * ɾ��ͼ��
     */
    public static boolean DeleteBook(String id) {
    	System.out.println("ɾ��ͼ�鿪ʼ��������");
    	connect();
    	Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	String sql = "delete from books where id = " + id ;
    	System.out.println("ɾ����䣺"+sql);
        boolean rs;
		try {
			rs = stmt.execute(sql);
			// ��ɺ�ر�
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
     * �޸�ͼ��
     */
    public static boolean UpdateBook(JSONObject rs) {
    	connect();
    	Statement stmt = null;
    	//��ȡ��Ӧ������
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
    	System.out.println("������䣺"+sql);
			
		 try {
			stmt.execute(sql);
			stmt.close();
		    conn.close();
		    System.out.println("ͼ��༭�ɹ�");
		    return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // ��ɺ�ر�
		 return false;
    }
    
    //�˺�ע��
    
    public  boolean register(HttpServletRequest request) {
    	connect();
    	Statement stmt = null;
    	
    	//��ȡ��Ӧ������
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
       System.out.println("ִ�е�sql���"+sql);
    	try {
			stmt.execute(sql);
			 stmt.close();
		     conn.close();
		     System.out.println("�û�������");
		     return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    
}


