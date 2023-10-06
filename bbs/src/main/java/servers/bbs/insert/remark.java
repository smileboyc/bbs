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

import Tools.bbsTools;
import Tools.mysqlconnect;

/**
 * Servlet implementation class remark
 */
@WebServlet("/remark")
public class remark extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public remark() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
//		bbsTools.setCode(request, response);
		String id0=request.getParameter("tzid");
		String contentBox0=request.getParameter("contentBox");
		
		HttpSession session1=request.getSession();	        	      
	    String student_name=session1.getAttribute("student_name").toString();
	    
		String student_phone=session1.getAttribute("student_phone").toString();
		
	    LocalDateTime nowTime=LocalDateTime.now();
   	    String pattern="%tY-%<tm-%<td %<tT";
   	    String endTime=String.format(pattern, nowTime);
   	    String sqlString="insert into bbsrep  (tzid,content,repL,repDate,clickNO,phone)  values('"+id0+" ', '"+contentBox0+"','"+student_name+"','"+endTime+"','1','"+student_phone+"')";   
   	    try {
		mysqlconnect.insert1(sqlString);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		String sqlString0="UPDATE bbs SET  repNO=repNO+1 WHERE id="+id0;   	    
   	    try {
		mysqlconnect.insert1(sqlString0);
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
