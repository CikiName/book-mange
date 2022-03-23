package book.mange.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBTools tools;
       
	private Logger logger=LoggerFactory.getLogger(getClass());
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        //��ʼ���������ݿ�
        logger.info("��ʼ�����ݿ�");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		System.out.println("get����");
		JSONObject res = new JSONObject();
		String servletPath = request.getServletPath();
		switch(servletPath) {
			case "/main-servlet":
				res.put("status", "1");
				res.put("msg", "success");
				res.put("list", tools.selectBooks());
				response.getWriter().append(res.toJSONString());
		//		logger.info(JSONObject.toJSONString(response));
				break;
			default:
				break;
		    
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		System.out.println("post����");
		Map map=new HashMap();
		JSONObject res = new JSONObject();

		String servletPath = request.getServletPath();
		System.out.println("ִ�е�·����"+servletPath);
		switch(servletPath) {
			case "/main-servlet/login":
				String username = request.getParameter("username");
			    String password = request.getParameter("password");
			    tools=new DBTools();
			    if(tools.checkPassWord(username,password)) {
			    	res.put("status", "1");
			    	res.put("msg", "success");
			    }else {
			    	res.put("status", "0");
			    	res.put("msg", "fail");
			    }
			    break;
			case "/main-servlet/add":
			    JSONObject addData =new JSONObject();
			    addData.put("name", request.getParameter("name"));
			    addData.put("author", request.getParameter("author"));
			    addData.put("brief", request.getParameter("brief"));
			    addData.put("year", request.getParameter("year"));
			    addData.put("num", request.getParameter("num"));
			    tools=new DBTools();
				if(tools.AddBook(addData)) {
					res.put("status", "1");
					res.put("msg", "��ӳɹ�");
				}else {
					res.put("status", "0");
					res.put("msg", "���ʧ��");
				}
				break;
			case "/main-servlet/edit":
				JSONObject editData =new JSONObject();
				editData.put("id", request.getParameter("id"));
				editData.put("name", request.getParameter("name"));
				editData.put("author", request.getParameter("author"));
			    editData.put("brief", request.getParameter("brief"));
			    editData.put("year", request.getParameter("year"));
			    editData.put("num", request.getParameter("num"));
				tools=new DBTools();
				if(tools.UpdateBook(editData)) {
					res.put("status", "1");
					res.put("msg", "�༭�ɹ�");
				}else {
					res.put("status", "0");
					res.put("msg", "�༭ʧ��");
				}
				break;
				
			case "/main-servlet/del":				
				System.out.println("ɾ��ͼ������");
				String id = request.getParameter("id");
				DBTools	tool2=new DBTools();
				
				boolean flag=tool2.DeleteBook(id);
				if(flag ){
					res.put("status", "1");
					res.put("msg", "ɾ���ɹ�");
				}else {
					res.put("status", "0");
					res.put("msg", "ɾ��ʧ��");
				}
				break;
			
			case "/main-servlet/register":				
				System.out.println("�û���Ϣע��");			
			
				DBTools	tool5=new DBTools();
				
				boolean flag5=tool5.register(request);
				if(flag5 ){
					res.put("status", "1");
					res.put("msg", "ע��ɹ�");
				}else {
					res.put("status", "0");
					res.put("msg", "ע��ʧ��");
				}
				break;		
				
			default:
				break;		    
		}
		
		response.getWriter().append(res.toJSONString());
	}
}
