package servers.bbs.insert;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import Tools.bbsTools;
import Tools.mysqlconnect;

/**
 * Servlet implementation class fbTz
 */
@WebServlet("/fbTz")
@MultipartConfig
public class fbTz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fbTz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session1=request.getSession();	        	      
	    String student_name=session1.getAttribute("student_name").toString();
		String student_phone=session1.getAttribute("student_phone").toString();
		String student_num=session1.getAttribute("student_num").toString();
	    LocalDateTime nowTime=LocalDateTime.now();
   	    String pattern="%tY-%<tm-%<td %<tT";
   	    String endTime=String.format(pattern, nowTime);
   	    
		String sqlString="INSERT INTO  bbs (content,title,pic,auth,fbDate,clickNo,fbid,repNo,fbpic)VALUES (";
		try {
		sqlString+=	bbsTools.insert("tz", request);
		} catch (ClassNotFoundException | ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlString+=",'"+student_name+"','"+endTime+"', '"+"0','"+student_num+"','0','"+student_phone+"')";	    
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
