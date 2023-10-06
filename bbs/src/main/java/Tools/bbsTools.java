package Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class bbsTools {
	private static String driver;
	private static String url=null;
	private static String username=null;
	private static String password=null;
	private static Properties pro=new Properties(); //准备读取配置文件，创建配置对象
	//静态初始化
	static {
		FileInputStream inputStream=null;
			try {
			 inputStream=new FileInputStream(new File("C:\\Users\\25498\\eclipse-workspace\\ajax\\jdbc.properties"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			pro.load(inputStream);//从磁盘上加载文件，要根据你配置文件存放具体的文件夹来设置，搞晕了就用绝对路径		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	  
			 //看看能不能把文件中的参数读出来
			 driver=pro.getProperty("driver");
			 url=pro.getProperty("url");
			 username=pro.getProperty("username");
			 password=pro.getProperty("password");		 
			 try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//加载数据库驱动程序
	}
		//下面是供外部获取连接方法
		public static java.sql.Connection getConnection() { //注，这里不能是mysql的，是java.sql的
			java.sql.Connection con1=null; //父类弄错了，是返回不了的
			  
			  try {
				  con1=DriverManager.getConnection(url, username, password);
			  }
			  catch (SQLException e) { System.out.println(e.toString());e.printStackTrace();
				// TODO: handle exception
			}
			  return con1;	
		}
			
		//释放资源
		public static void close(ResultSet rs,Statement st,Connection conn)
			{ if(rs!=null) try {rs.close();}catch (SQLException e) {
				// TODO: handle exception
			}
			 if(st!=null) try {st.close();}catch (SQLException e) {
				// TODO: handle exception
			}
			 if(conn!=null) try {conn.close();}catch (SQLException e) {
				// TODO: handle exception
			}	}
				
		//数据库的查询（普通查询）
		public static ResultSet selectSql(String sql) throws SQLException {		
				//step1:连接数据库
				java.sql.Connection conn1=null;//连接对象
				conn1= mysqlconnect.getConnection();	
				//step2:实例化一个命令对象statement，用于接收和执行SQL命令
				java.sql.Statement pst1=null;
				pst1=conn1.createStatement();
				ResultSet resultSet=pst1.executeQuery(sql);	
				return resultSet;	
			}
		
		//查询结果处理，返回JSON对象数组（通过别名）
		public static JSONArray resultToJsonArray(ResultSet resultSet) throws SQLException {
			JSONArray jsonArray=new JSONArray();//存放查询结果
			 ResultSetMetaData rsmd = resultSet.getMetaData() ;   			 
			 while (resultSet.next()) {
		        int count = rsmd.getColumnCount();
		        JSONObject jsonObject=new JSONObject(true);
		        for (int i=0; i<count; i++) {
		            // 得到列的名称		            
		           String columnName = rsmd.getColumnLabel(i + 1);
		            // 获取每一行的每一列的值
		            Object columnValue = resultSet.getObject(columnName); 
		            jsonObject.put(columnName, columnValue);		    
		        }
		        jsonArray.add(jsonObject);			 
		    }	 
		return jsonArray;
		}
		
		//转换为UTF-8，解决出现中文乱码
		public static void setCode(HttpServletRequest request,HttpServletResponse response) {
			try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json;charset=utf-8");
			//要想后台把数据以json返回去，必须设置上句，否则只能返回文字或HMTL
		}
		
		public static String insert(String tableName, HttpServletRequest request) throws ServletException, IOException, ClassNotFoundException
		{request.setCharacterEncoding("UTF-8");	
		 Collection<Part> parts=request.getParts();
		 String fileNameString=""; //使用/,减少转义符带来的麻烦 
		 String fieldFilename0=""; 
		 String valueString="";
		 boolean file0=false; 
		 Enumeration<String> values0=request.getParameterNames();//把所有的元素先在part循环之前取出来，不能放在for(Part part:parts)循环内部去了，否则多次重复
		for(Part part:parts) {
					
		if(part.getContentType()==null)//非文件数据
		{ while (values0.hasMoreElements()) {
		String str0 = (String) values0.nextElement();
		System.out.println(str0);
		 valueString+="'"+request.getParameter(str0)+"',";
		}
		}	
		else {//文件部分	 
		  String filename=part.getSubmittedFileName();
		  String header=part.getHeader("Content-Disposition");
		  int start=header.lastIndexOf("=");
		  filename=header.substring(start+1);
		  if(filename.length()>2) 
		  {file0=true; 		
		  filename=filename.substring(filename.lastIndexOf('\\')+1);
		  System.out.println(filename);
		  //filename=filename.substring(0,filename.length()-1);
		  filename=filename.substring(1,filename.length()-1);		
		  java.util.Date d0=new java.util.Date();
		  String temp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(d0 );
		  filename=temp+"."+filename; 
		  System.out.println(filename);
	
	      part.write("C:/loadFile/bbs/"+tableName+"/"+filename);
		  fileNameString=fileNameString+tableName+"/"+filename+"*";
		  System.out.println("C:/loadFile/bbs/"+tableName+"/"+filename);
		  fieldFilename0=part.getName();
		  }	  
		  if(file0) 
		{ if(fileNameString.lastIndexOf('*')>1)
		fileNameString=fileNameString.substring(0,fileNameString.length()-1);
		
		 }
		}
	}
		
		valueString+="'"+fileNameString+"',";
		valueString=valueString.substring(0,valueString.length()-1);
		System.out.println(valueString);
		return valueString;
}
}
