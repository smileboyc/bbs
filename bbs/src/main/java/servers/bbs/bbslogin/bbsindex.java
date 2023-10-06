package servers.bbs.bbslogin;

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

import Tools.DataTools;
import Tools.mysqlconnect;

/**
 * Servlet implementation class bbsindex
 */
@WebServlet("/bbsindex")
public class bbsindex extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bbsindex() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	DataTools.setCode(request,response);
	//String sql0="SELECT DISTINCT bbs.id AS id,bbs.title AS 标题,CONCAT(CAST(COUNT(bbsrep.tzid) AS CHAR),'/',CAST(bbs.clickNo AS CHAR)) AS 点回,CONCAT('<a href=display.html?id=',CAST(bbs.id AS CHAR),'><img src=''/loadFile/bbs/tz/33.png'' width=''20px''/></a>') AS 查看 FROM bbs LEFT JOIN bbsrep ON bbs.id=bbsrep.tzid GROUP BY bbs.id";
	String sql0="SELECT DISTINCT bbs.id AS id,bbs.title AS 标题,CONCAT(CAST(COUNT(bbsrep.tzid) AS CHAR),'/',CAST(bbs.clickNo AS CHAR)) AS 点回,pic AS 图片,auth as 作者,fbDate as 日期 FROM bbs LEFT JOIN bbsrep ON bbs.id=bbsrep.tzid GROUP BY bbs.id order by rand() limit 5" ;
	ResultSet resultSet = null;
	try {
		resultSet = mysqlconnect.selectSql(sql0);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		JSONArray jsonArray=new JSONArray();
		try {
			jsonArray=mysqlconnect.resultString3(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out1=response.getWriter();

		out1.print(jsonArray);
		out1.close();
		//System.out.println(jsonArray);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
