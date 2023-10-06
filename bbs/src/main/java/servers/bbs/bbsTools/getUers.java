package servers.bbs.bbsTools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class getUers
 */
@WebServlet("/getUers")
public class getUers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getUers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext application=super.getServletContext(); 
		Map<String,String>users=(Map<String,String>)application.getAttribute("online");
		//封装到：
		JSONArray jsonArray1=new JSONArray();
		   for(String id1:users.keySet())
		   {  JSONObject json1=new JSONObject(new LinkedHashMap<String, Object>());
				json1.put(id1, users.get(id1)); 
				jsonArray1.add(json1);
			 	}  
		   	PrintWriter out1=response.getWriter();
		    out1 = response.getWriter();	 
			out1.print(jsonArray1);
			out1.close();	
		 

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
