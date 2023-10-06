package Tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class mysqlconnect {
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
		
		//插入数据库 参数sql
			public static void insert1(String sqlString) throws SQLException{			
				java.sql.Connection conn1=null;//连接对象
				conn1= mysqlconnect.getConnection();	
				java.sql.Statement pst1=null;
				pst1=conn1.createStatement();
				pst1.executeUpdate(sqlString);
				mysqlconnect.close(null, pst1, conn1);
	
			}
		//插入数据库
			public static boolean insert(String tableName,String primaryKey,String insertSqlstring, HttpServletRequest request) throws ServletException, IOException, ClassNotFoundException {
				String  sqlString1="";
				String primaryKeyvalue=request.getParameter(primaryKey);
				//前端控制必须输入学号
				sqlString1="SELECT * FROM "+tableName+" WHERE "+ primaryKey+"='"+primaryKeyvalue+"'";
				System.out.println(sqlString1);
				java.sql.Connection conn1=null;//连接对象
				conn1= mysqlconnect.getConnection();	
				java.sql.Statement pst1=null;
				 try {
					pst1=conn1.createStatement();//实例化一个命令对象statement，用于接收和执行SQL命令
					if(pst1.executeQuery(sqlString1).next()) {					
					    mysqlconnect.close(null, pst1, conn1);
					    return false;
					}
					else {
					pst1.executeUpdate(insertSqlstring);
					mysqlconnect.close(null, pst1, conn1);
					 return true;
					}									
					} catch (SQLException e) {
									// TODO Auto-generated catch block
					e.printStackTrace();
					}
				 return false;
			}
		
		//查询数据，返回一个JSON对象数组
		    public static JSONArray select(String className,String sqlString) {
			//step1:连接数据库
			java.sql.Connection conn1=null;//连接对象
			conn1= mysqlconnect.getConnection();	
			//step2:实例化一个命令对象statement，用于接收和执行SQL命令
			java.sql.Statement pst1=null;	
			ResultSet rSet=null;
			ResultSetMetaData rsmd=null;
			JSONArray jsonArray=null;
			try {	
				pst1=conn1.createStatement();
				rSet=pst1.executeQuery(sqlString);
				rsmd = rSet.getMetaData() ;  
				
				 while (rSet.next()) {
					 Class t=null;
					 try {
					 t = Class.forName(className);
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    int count = rsmd.getColumnCount();
				    for (int i=0; i<count; i++) {
				            // 得到列的名称
				        Field f0 = null;//反射使用的对象			
				            String columnName = rsmd.getColumnName(i + 1);
				        try {
							f0=t.getClass().getDeclaredField(columnName);
							
						} catch (NoSuchFieldException | SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				            // 获取每一行的每一列的值
				            Object columnValue = rSet.getObject(columnName); 
				            f0.setAccessible(true);//使当前属性可以在外部操作
							try {
								f0.set(t,columnValue+"");
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        }
				   DataTools.modelToJsonArray(t, jsonArray);
				    }				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return jsonArray;
		}
						
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
			
		//查询结果处理
			public static String resultString(ResultSet resultSet) throws SQLException {
					//使用rs.getMetaData().getTableName(1))就可以返回表名 
					String string="";
					 ResultSetMetaData rsmd = resultSet.getMetaData() ;   
					 //int rowCount = resultSet.getRow(); //获得ResultSet的总行数 
					 //int columnCount = rsmd.getColumnCount(); //获得ResultSet的总列数 
					 while (resultSet.next()) {
				        int count = rsmd.getColumnCount();
				        for (int i=0; i<count; i++) {
				            // 得到列的名称
				            String columnName = rsmd.getColumnName(i + 1);
				            // 获取每一行的每一列的值
				            Object columnValue = resultSet.getObject(columnName); 
				        string+=columnName + "=" + columnValue + ",\n";
				        }
				        
				    }	 
				return string;
				}
	   	
		//将结果集合中的数据封装到一个list集合中（第一步）
			public static List<Map<String, Object>> transferResultSetToList(ResultSet rs) {
				List<Map<String, Object>> resultLists=new ArrayList<>();
				try {
					//遍历结果集，每一次遍历的结果是list集合中的一个元素
					while (rs.next()) {
						Map<String, Object> map = new HashMap<>();
						//获取数据库元数据
						ResultSetMetaData rsmd = rs.getMetaData();
						//取到数据库表的列数
						int colums=rsmd.getColumnCount();
						//遍历列，每一列是map集合中的一个元素，元素的键是别名，值是列值
						for(int i=1;i<=colums;i++){
							String key=rsmd.getColumnLabel(i);
							Object obj=rs.getObject(i);
							map.put(key, obj);
						}
						//添加到List
						resultLists.add(map);
					} 
				} catch (Exception e) {
				}
				return resultLists;
			}
			
		//将集合中的map键值对封装成对象（第二步）
			public static <T> List<T> listToReturnLists(List<Map<String, Object>> ls, Class<T> clz) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {			
				List<T> results=new ArrayList<>();			
				//遍历结果集合
				for(int i=0;i<ls.size();i++){
					//组装对象
					T t=clz.newInstance();//保证空构造器存在
					//获取maps中的所有对象
					Map<String,Object> rows=ls.get(i);
					//遍历rows
					for(Map.Entry<String, Object> entry:rows.entrySet()){
						//获取别名，类中的属性名
						String columnLable=entry.getKey();
						Object value=entry.getValue();
						//填充值
						//parseIntoObject(t,columnLable,value+"");					
						Field f=null;
						Class<?> clz0=t.getClass();
						f=clz0.getDeclaredField(columnLable);
						f.setAccessible(true);
						f.set(t, value+"");//转换为string
						//如果类存在父子关系，就不通用
						//clz.getDeclaredField(columnLable).set(t, value);
					}
					results.add(t);
				}				
				return results;
			}
			
		//将列值填充到对象的属性中  t 转换对象、columnLable 属性名、 value 对应的属性值（第三步）
			public static <T> void parseIntoObject(T t, String columnLable, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
				//获取属性			
					Field f=null;
					Class<?> clz=t.getClass();
					f=clz.getDeclaredField(columnLable);
					f.setAccessible(true);
					f.set(t, value);
//					for(Class<?> clz=t.getClass();clz!=Object.class;clz=clz.getSuperclass()){
//					f=clz.getDeclaredField(columnLable);
//					f.setAccessible(true);
//					f.set(t, value);	
//					}											
			}
        
		//查询数据库，映射生成模型数组 ---参数：SQL串、Class.forName("model.student1")、查询结果集
			public static <T> List<T> selectToModelList(String sql,Class<T> className,ResultSet resultSet){			
				List<T> resultLists=new ArrayList<>();
				T t = null;
				try {
					t = className.newInstance();
				} catch (InstantiationException | IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//保证空构造器存在
				try {
					//遍历结果集，每一次遍历的结果是list集合中的一个元素
					while (resultSet.next()) {
						Field f=null;
						Class<?> clz0=t.getClass(); //动态加载类
						//获取数据库元数据
						ResultSetMetaData rsmd = resultSet.getMetaData();
						//取到数据库表的列数
						int colums=rsmd.getColumnCount();
						//遍历列，每一列是map集合中的一个元素，元素的键是别名，值是列值
						for(int i=1;i<=colums;i++){
							String key=rsmd.getColumnLabel(i);
							Object value=resultSet.getObject(i);
							f=clz0.getDeclaredField(key);
							f.setAccessible(true);
							f.set(t, value+"");//转换为string，数据库中的日期为data类型
						}
						//添加到List
						resultLists.add(t);
					} 
				} catch (Exception e) {
				}
				return resultLists;
			}			
		
		//查询结果处理，返回JSON对象数组（通过列名）
			public static JSONArray resultString2(ResultSet resultSet) throws SQLException {
				//使用rs.getMetaData().getTableName(1))就可以返回表名 
				JSONArray jsonArray=new JSONArray();//存放查询结果
				String string="";
				 ResultSetMetaData rsmd = resultSet.getMetaData() ;   
				 //int rowCount = resultSet.getRow(); //获得ResultSet的总行数 
				 //int columnCount = rsmd.getColumnCount(); //获得ResultSet的总列数 
				 while (resultSet.next()) {
			        int count = rsmd.getColumnCount();
			        JSONObject jsonObject=new JSONObject(true);
			        for (int i=0; i<count; i++) {
			            // 得到列的名称
			            String columnName = rsmd.getColumnName(i + 1);
			           // String columnName = rsmd.getColumnLabel(i + 1);别名
			            // 获取每一行的每一列的值
			            Object columnValue = resultSet.getObject(columnName); 
			            jsonObject.put(columnName, columnValue);		    
			        }
			        jsonArray.add(jsonObject);			 
			    }	 
			return jsonArray;
			}	
		
		//查询结果处理，返回JSON对象数组（通过别名）
			public static JSONArray resultString3(ResultSet resultSet) throws SQLException {
				//使用rs.getMetaData().getTableName(1))就可以返回表名 
				JSONArray jsonArray=new JSONArray();//存放查询结果
				String string="";
				 ResultSetMetaData rsmd = resultSet.getMetaData() ;   
				 //int rowCount = resultSet.getRow(); //获得ResultSet的总行数 
				 //int columnCount = rsmd.getColumnCount(); //获得ResultSet的总列数 
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
		
		//查询结果处理，返回Java对象数组
			public static <T> List<T> resultToArrayList(ResultSet resultSet ,Class<T> className) throws SQLException {
				//使用rs.getMetaData().getTableName(1))就可以返回表名 
				JSONArray jsonArray=new JSONArray();//存放查询结果
				 ResultSetMetaData rsmd = resultSet.getMetaData() ;   
				 //int rowCount = resultSet.getRow(); //获得ResultSet的总行数 
				 //int columnCount = rsmd.getColumnCount(); //获得ResultSet的总列数 
				 while (resultSet.next()) {
			        int count = rsmd.getColumnCount();
			        JSONObject jsonObject=new JSONObject(true);
			        for (int i=0; i<count; i++) {
			            //得到列的名称
			            String columnName = rsmd.getColumnName(i + 1);
			           // String columnName = rsmd.getColumnLabel(i + 1);别名
			            // 获取每一行的每一列的值
			            Object columnValue = resultSet.getObject(columnName); 
			            jsonObject.put(columnName, columnValue);		    
			        }
			        jsonArray.add(jsonObject);		        
			    }	
				 List<T> list=JSONArray.parseArray(jsonArray.toJSONString(), className);
			return list;
			}	
			
		//向前台直接返回JSON对象数组
			public static void jsonArrayToResponse(String sqlString,HttpServletResponse response,String ...args) throws ServletException, IOException{
				ResultSet rSet = null;
				JSONArray jsonCourseArray = null;
				try {
					rSet = mysqlconnect.selectSql(sqlString);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					jsonCourseArray = mysqlconnect.resultToJsonArray(rSet);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PrintWriter  out1=response.getWriter();
				out1.print(jsonCourseArray);
				out1.flush();
				out1.close();
				
			}
		
		//查询结果处理，返回JSON对象数组（通过标签）
			public static JSONArray resultToJsonArray(ResultSet resultSet) throws SQLException {
				//使用rs.getMetaData().getTableName(1))就可以返回表名 
				JSONArray jsonArray=new JSONArray();//存放查询结果
				
				 ResultSetMetaData rsmd = resultSet.getMetaData() ;   
				 //int rowCount = resultSet.getRow(); //获得ResultSet的总行数 
				 //int columnCount = rsmd.getColumnCount(); //获得ResultSet的总列数 
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

		//老师写的代码，向前台返回JSON对象数组(查询)（生成删除、修改按钮）
			public  static void Myquery2(String sqlString, HttpServletResponse response,int delete,int update,String...args) {
				//要考虑到多个参数问题
				ResultSet rs1=mysqlconnect.PstProc(sqlString, args);
				java.sql.ResultSetMetaData rsmd1=null;
				try {
					rsmd1 = rs1.getMetaData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int colNo=0;
				try {
					colNo = rsmd1.getColumnCount();
					 
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  JSONArray jsonArray1=new JSONArray();
				try {
					while(rs1.next())
						{ JSONObject json1=new JSONObject(new LinkedHashMap<String, Object>());
							for(int col=1;col<=colNo;col++)
							{if(rs1.getString(col)==null)
								json1.put(rsmd1.getColumnName(col), " ");
							else
								json1.put(rsmd1.getColumnName(col), rs1.getString(col)); 
							}
							if(delete==1) {
								json1.put("删除", "<a href=myDelet2.html?tableName="+rsmd1.getTableName(1)+"&key0="+rsmd1.getColumnName(1)+"&value0="+rs1.getString(1)+">删除</a>");
							}
							if(update==1) {						
								json1.put("修改", "<a href=studentUpdate.html?tableName="+rsmd1.getTableName(1)+"&key0="+rsmd1.getColumnName(1)+"&value0="+rs1.getString(1)+">修改</a>");
							}
						
							jsonArray1.add(json1);
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PrintWriter out1=null;
				try {
				out1 = response.getWriter();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out1.print(jsonArray1);
				out1.close();										
			}
		
		//为查询出的的re（生成删除、修改按钮）
			public  static JSONArray readdbutton(ResultSet resultSet,int delete,int update,String htmlString) {
				//要考虑到多个参数问题
				ResultSet rs1=resultSet;
				java.sql.ResultSetMetaData rsmd1=null;
				try {
					rsmd1 = rs1.getMetaData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int colNo=0;
				try {
					colNo = rsmd1.getColumnCount();
					 
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  JSONArray jsonArray1=new JSONArray();
				try {
					while(rs1.next())
						{ JSONObject json1=new JSONObject(new LinkedHashMap<String, Object>());
							for(int col=1;col<=colNo;col++)
							{if(rs1.getString(col)==null)
								json1.put(rsmd1.getColumnName(col), " ");
							else
								json1.put(rsmd1.getColumnName(col), rs1.getString(col)); 
							}
							if(delete==1) {
								json1.put("删除", "<a href=myDelet2.html?tableName="+rsmd1.getTableName(1)+"&key0="+rsmd1.getColumnName(1)+"&value0="+rs1.getString(1)+">删除</a>");
							}
							if(update==1) {						
								json1.put("修改", "<a href="+htmlString+".html?tableName="+rsmd1.getTableName(1)+"&key0="+rsmd1.getColumnName(1)+"&value0="+rs1.getString(1)+">修改</a>");
							}					
							jsonArray1.add(json1);
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return jsonArray1;									
			}
		
			
		//老师写的代码，向前台返回JSON对象数组(查询)
			public  static void Myquery1(String sqlString, HttpServletResponse response,String...args) {
				//要考虑到多个参数问题
				ResultSet rs1=mysqlconnect.PstProc(sqlString, args);
				java.sql.ResultSetMetaData rsmd1=null;
				try {
					rsmd1 = rs1.getMetaData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int colNo=0;
				try {
					colNo = rsmd1.getColumnCount();
					 
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  JSONArray jsonArray1=new JSONArray();
				try {
					while(rs1.next())
						{ JSONObject json1=new JSONObject(new LinkedHashMap<String, Object>());
							for(int col=1;col<=colNo;col++)
							{if(rs1.getString(col)==null)
								json1.put(rsmd1.getColumnName(col), " ");
							else
								json1.put(rsmd1.getColumnName(col), rs1.getString(col)); 
							}
							jsonArray1.add(json1);
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PrintWriter out1=null;
				try {
				out1 = response.getWriter();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out1.print(jsonArray1);
				out1.close();										
			}
					
		//删除	
			public  static void MyDelete1(String sqlString, HttpServletResponse response,String...args) {
				//要考虑到多个参数问题
				java.sql.Connection con1=mysqlconnect.getConnection();					
				java.sql.PreparedStatement pst1=null;								
				try {
					pst1=con1.prepareStatement(sqlString);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}				
				int n0=1;
				System.out.println(args.length);
				for(;n0<=args.length;n0++) {
					try {
						pst1.setString(n0,args[n0-1]);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			    int re = -1;
				try {
					re = pst1.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
				JSONArray jsonArray1=new JSONArray();
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("re", re);
				jsonArray1.add(jsonObject);
				PrintWriter out1=null;
				try {
				out1 = response.getWriter();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out1.print(jsonArray1);
				out1.close();
					
			}	
		
		//预编译
			public static ResultSet PstProc(String sqlstr,String ...args) {
					//连接数据库
					java.sql.Connection con1=mysqlconnect.getConnection();					
					java.sql.PreparedStatement pst1=null;					
					//查询返回的结果集
					ResultSet rs1=null;				
					try {
						pst1=con1.prepareStatement(sqlstr);
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
					for(int number=0;number<args.length;number++)
						try {
							pst1.setString(number+1, args[number]);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					try {
						rs1=pst1.executeQuery();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					return rs1;
				}
		
		//非预编译(老师写的)
			  public  static ResultSet  PstProc(String sqlstr) {//非预编sql命令处理
				  java.sql.Connection con1=mysqlconnect.getConnection();
				  Statement pst1=null;
				try {
					pst1 = con1.createStatement( );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  ResultSet rs1=null;      
				  try {
					rs1=pst1.executeQuery(sqlstr);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			 
			     return rs1;
			  }
		
		//预编译插入数据
			public static boolean PstProcInsert(String sqlstr,Object object) {
				//连接数据库
				java.sql.Connection con1=mysqlconnect.getConnection();				
				java.sql.PreparedStatement pst1=null;
				
				Field fields[]=object.getClass().getDeclaredFields();
				try {
					pst1=con1.prepareStatement(sqlstr);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				for(int number=0;number<fields.length;number++) {
					Field f0=fields[number];
					f0.setAccessible(true);
					try {
						//fieldVal = f0.get(fieldName)+"";
						String value0=f0.get(object).toString();//获得当前循环的属性值
						//System.out.println(value0);
						pst1.setString(number+1, value0);
					} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				int flag=-1;
				try {
					flag = pst1.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//判断是否插入成功
			   if(flag>0)
				return true;
			    return false;
			}
		
		//预编译插入数据（通过动态加载类）参数：SQL串、动态加载的类对象
			public static <T> boolean PstProcInsert1(String sqlstr,T t) {
				//连接数据库
				java.sql.Connection con1=mysqlconnect.getConnection();				
				java.sql.PreparedStatement pst1=null;
				Class<?> cl=t.getClass();
				Field fields[]=cl.getDeclaredFields();
				try {
					pst1=con1.prepareStatement(sqlstr);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				for(int number=0;number<fields.length;number++) {
					Field f0=fields[number];
					f0.setAccessible(true);
					try {
						String value0=f0.get(t).toString();//获得当前循环的属性值
						pst1.setString(number+1, value0);
					} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				int flag=-1;
				try {
					flag = pst1.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//判断是否插入成功
			   if(flag>0)
				return true;
			    return false;
			}
		
		//预编译插入数据,先判断记录是否存在（参数依次：预编译SQL串、表名、主码、对象、请求对象）
			public static boolean PstProcInsert1(String sqlstr, String tableName,String primaryKey, Object object, HttpServletRequest request) throws ServletException, IOException, ClassNotFoundException, SQLException {				
				String sqlString1="";
				String primaryKeyvalue=request.getParameter(primaryKey);
				//前端控制必须输入学号
				sqlString1="SELECT * FROM "+tableName+" WHERE "+ primaryKey+"='"+primaryKeyvalue+"'";
				//连接数据库
				java.sql.Connection con1=mysqlconnect.getConnection();				
				java.sql.PreparedStatement pst1=null;
				java.sql.Statement pst2=null;
				
				pst2=con1.createStatement();//实例化一个命令对象statement，用于接收和执行SQL命令				
				if(pst2.executeQuery(sqlString1).next()) {		
				    mysqlconnect.close(null, pst2, con1);
				    return false;
				}
				else {
					Field fields[]=object.getClass().getDeclaredFields();
					pst1=con1.prepareStatement(sqlstr);
					for(int number=0;number<fields.length;number++) {
						Field f0=fields[number];
						f0.setAccessible(true);
						try {
							String value0=f0.get(object).toString();//获得当前循环的属性值

							pst1.setString(number+1, value0);
						} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					int flag = pst1.executeUpdate();
					mysqlconnect.close(null, pst1, con1);
					if(flag>0)		//判断是否插入成功
					return true;
					return false;
				}																		
			   
			}
		
		//预编译插入数据，调用PstProc函数，判断是否记录存在
			public static boolean PstProcInsert2(String sqlstr,Object object) {
				
				return false;
			}

        //单条件查询，返回结果前端展示
		   public static void oneSelectResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		           DataTools.setCode(request, response);
				   String args=request.getParameter("args").trim();
				   String[] args0=args.split(":");
				   String sqlString="select * from "+args0[0]+" where "+args0[1]+"=?";
				   mysqlconnect.Myquery1(sqlString, response, args0[2]);
			}

	    //单条件查询，返回结果前端展示(是否有删除、修改)
		   public static void oneSelectResponse1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			           DataTools.setCode(request, response);
					   String args=request.getParameter("args").trim();
					   String[] args0=args.split(":");
					   String sqlString="select "+args0[3]+" from "+args0[0]+" where "+args0[1]+"=?";
					   mysqlconnect.Myquery2(sqlString, response,1,1, args0[2]);
				}
			   
	    //单条件删除，返回结果到前端
			public static void oneDeleteResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			           DataTools.setCode(request, response);
					   String args=request.getParameter("args").trim();
					   String[] args0=args.split(":");
					   String sqlString="delete from "+args0[0]+" where "+args0[1]+"=?";
					mysqlconnect.MyDelete1(sqlString, response, args0[2]);//调用单条件删除函数
					
				}
		
		//单条件多条件删除，返回结果到前端
			public static void twoDeleteResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				           DataTools.setCode(request, response);
						   String args=request.getParameter("args").trim();					   
						   String[] args0=args.split(":");
						   String[] args00=args0[1].split("\\|");
						   String[] args01=args0[2].split("\\|");
						   String sqlString="delete from "+args0[0]+" where ";			
						   for(int i=0;i<args00.length;i++) {
							   sqlString+=args00[i]+"=? and ";
							   System.out.println(args00[i]);
						   }
						  sqlString=sqlString.substring(0,sqlString.length()-4);
						  System.out.println(sqlString);

						  mysqlconnect.MyDelete1(sqlString, response, args01);//调用单条件删除函数
					}
			
	    //填充前台的list
			public static void fillList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				 DataTools.setCode(request, response);
				 String args=request.getParameter("args").trim();
				 String[] args0=args.split(":");
				 String sqlString="select DISTINCT "+ args0[1] +" from "+args0[0];
				 if(args0[2].equals("1"))
				 sqlString+=" ORDER BY 1 DESC";
				 mysqlconnect.Myquery1(sqlString, response);	
			}
		
		//添加数据到数据库中（适用于所有模型）,添加是否成功的结果返回给前台 参数依次为：文件夹名称、数据库中的表名、模型类名 、请求头、响应头
			public static <T> void addDataToDb(String folderName,String tableName ,String className,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
				 String rString="";//向前台返回的串，判断是否参加成功
				 String sqlinsertString="";//后台拼接的SQL串
				 @SuppressWarnings("unchecked")
				Class<T> cl=(Class<T>) Class.forName(className);
				 T t = null;//模型对象的创建
				 try {
				   t = cl.newInstance();
				} catch (InstantiationException | IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 //step1:设置前后台编码
				 DataTools.setCode(request,response);			 
				try {
				//step2:映射到模型中、并且将文件保存的相应文件夹中
					DataTools.RequestToModel4(folderName,t,request);
				} catch (ClassNotFoundException | ServletException | IOException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				//step3:遍历模型对象，返回预编译使用的SQL串
				 sqlinsertString=DataTools.insertDb5(tableName,t);	
				//step4:通过预编译，添加数据到数据库中
				 if(mysqlconnect.PstProcInsert1(sqlinsertString,t))
				    rString="注册成功";
				else {
					rString="注册不成功";
				}
				 //step5:返回前台提示信息
				 PrintWriter out1=response.getWriter();
				 out1.println(rString);
				 out1.close();
			}
		
		//删除--访问前台传的form数据(单条件、多条件通用)
			public static int formToDeletesql(String tableName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{					 					 
				 String sqlString="DELETE FROM "+tableName+" WHERE ";  			 
				 Enumeration<String> values0=request.getParameterNames();				
				 ArrayList<String> list = new ArrayList<String>();
				 java.sql.Connection con1=mysqlconnect.getConnection();					 
				 java.sql.PreparedStatement pst1=null;
				 while (values0.hasMoreElements()) {
					 String str0 = (String) values0.nextElement();	
					 if(request.getParameter(str0).length()>=1) {
						 sqlString=sqlString+str0+"=? and ";
						 list.add(request.getParameter(str0));
					 }						  						
				 }
				 sqlString=sqlString.substring(0,sqlString.length()-4);					 											 
				 try {
					pst1=con1.prepareStatement(sqlString);
				} catch (SQLException e) {					
					e.printStackTrace();
				}	
				 for(int num=0;num<list.size();num++ ) {
					 try {
						pst1.setString(num+1, list.get(num));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				int re = -1;
				try {
					re = pst1.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return re;

					
		}	
			
		//修改数据库数据
			public static <T> void updateDb(String folderName,String tableName ,String className,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
				 String rString="";//向前台返回的串，判断是否参加成功
				 String sqlinsertString="";//后台拼接的SQL串
				String updatekeyString=null;
				 @SuppressWarnings("unchecked")
				Class<T> cl=(Class<T>) Class.forName(className);
				 T t = null;//模型对象的创建
				 try {
				   t = cl.newInstance();
				} catch (InstantiationException | IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 //step1:设置前后台编码
				 DataTools.setCode(request,response);			 
				try {
				//step2:映射到模型中、并且将文件保存的相应文件夹中
				 updatekeyString= DataTools.RequestToModel6(folderName,t,request);
				} catch (ClassNotFoundException | ServletException | IOException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				//step3:遍历模型对象，返回预编译使用的SQL串
				 try {
					sqlinsertString=DataTools.insertDb7(tableName,t,updatekeyString);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				//step4:通过预编译，添加数据到数据库中
				 if(mysqlconnect.PstProcInsert1(sqlinsertString,t))
				    rString="修改成功";
				else {
					rString="修改不成功";
				}
				 //step5:返回前台提示信息
				 PrintWriter out1=response.getWriter();
				 out1.println(rString);
				 out1.close();
			}
}
