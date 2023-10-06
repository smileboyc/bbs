package servers.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Tools.mysqlconnect;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String student_num=request.getParameter("student_num");
		String student_pwd=request.getParameter("student_pwd");		
		String sqlString="select student_perm,student_pwd from student1 where student_num=?";
		//查询返回的结果集
		ResultSet rs1=null;
		int perm1=-1;//假设权限为-1，表示非法用户
   	     //perm1为-1非法用户，perm=1,2,3表示数据库合法用户；0用户名对，登录密码不对
		try {
			//使用预编译
			rs1=mysqlconnect.PstProc(sqlString, student_num);
			if(rs1.next()) {
				if(rs1.getString("student_pwd").equals(student_pwd)) {
					perm1=rs1.getInt("student_perm");
				}
				else {
					perm1=0;//用户名正确的，密码不对
				}
			}
			//登录成功
			PrintWriter out1=response.getWriter();
			out1.print(perm1);	
			//转到登录页面
			//response.sendRedirect("testlogin.html?perm="+perm1);
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
