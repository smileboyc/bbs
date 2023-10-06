package servers.bbs.insert;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Tools.mysqlconnect;

/**
 * Servlet implementation class Collect
 */
@WebServlet("/Collect")
public class Collect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Collect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id0=request.getParameter("tzid");
		HttpSession session1=request.getSession();	        	      
		String student_num=session1.getAttribute("student_num").toString();
	    LocalDateTime nowTime=LocalDateTime.now();
   	    String pattern="%tY-%<tm-%<td %<tT";
   	    String endTime=String.format(pattern, nowTime);
   	    String sqlString="INSERT  INTO collect(tzid,student_num,TIME)VALUES('"+id0+" ', '"+student_num+"','"+endTime+"')";   	    
   	    try {
		mysqlconnect.insert1(sqlString);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		PrintWriter out1=response.getWriter();
		out1.print("");
		out1.flush();
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
