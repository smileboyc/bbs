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

import com.alibaba.fastjson.JSONArray;

import Tools.bbsTools;

/**
 * Servlet implementation class bbsDisplayTz
 */
@WebServlet("/bbsDisplayTz")
public class bbsDisplayTz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bbsDisplayTz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		bbsTools.setCode(request, response);
		String id0=request.getParameter("tzId");
		String sql0="SELECT DISTINCT bbs.id,bbs.title AS 标题,bbs.content AS 内容,bbs.auth AS 作者,bbs.fbDate AS 发布日期 ,CONCAT(CAST(COUNT(bbsrep.tzid) AS CHAR),'/',CAST(bbs.clickNo AS CHAR)) AS 回复数 ";
		sql0+=",bbs.pic AS 照片,bbs.clickNo as 点赞,bbs.repNO as 评论 FROM bbs LEFT JOIN bbsrep ON bbs.id=bbsrep.tzid WHERE bbs.id="+id0+"  GROUP BY bbs.id" ;
		
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
		doGet(request, response);
	}

}
