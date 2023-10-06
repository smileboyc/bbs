package servers.bbs.bbsDisplay;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;

import Tools.bbsTools;

/**
 * Servlet implementation class recordDisplay
 */
@WebServlet("/recordDisplay")
public class recordDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public recordDisplay() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		bbsTools.setCode(request, response);
		HttpSession session1=request.getSession();	        	      
	    String student_num=session1.getAttribute("student_num").toString();
		String sql0="SELECT  title AS 帖子标题,TIME AS 访问时间 FROM   Record LEFT JOIN bbs ON Record.tzid=bbs.id WHERE  student_num='"+student_num+"'";
		
		ResultSet resultSet = null;
		try {
				resultSet = bbsTools.selectSql(sql0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			JSONArray jsonArray=bbsTools.resultToJsonArray(resultSet);
			PrintWriter out1=response.getWriter();
			
			out1.print(jsonArray);
			out1.flush();
			out1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
