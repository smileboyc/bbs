package servers.bbs.bbsTools;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class getSession
 */
@WebServlet("/getSession")
public class getSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getSession() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	   request.setCharacterEncoding("UTF-8");
	   response.setContentType("text/html;Charset=UTF-8");
	   HttpSession session1=request.getSession();
	   if(session1!=null) {
       String student_num=session1.getAttribute("student_num").toString();   
       String student_perm=session1.getAttribute("student_perm").toString();
       String student_name=session1.getAttribute("student_name").toString();
       PrintWriter out1=response.getWriter();  out1.print(student_name);
       out1.flush();
       out1.close();}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
