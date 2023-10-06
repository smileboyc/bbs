package Tools;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.*;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import servers.course.course;

public class DataTools {
  static String savePath1="C:/loadFile/";
			//反射生成模型类
				public static void RequestToModel(String tableName,Object c0, HttpServletRequest request) throws ServletException, IOException, ClassNotFoundException
		{request.setCharacterEncoding("UTF-8");
		  Collection<Part> parts=request.getParts();  
		  String savePath="C:/loadFile/";
		  savePath=savePath+tableName; //使用/，减少转义符号	 
		 String fileNameString=tableName+"/"; //防止在model减少一个\，再存入数据库就少了\,路径非法  
		 String fieldFilename0=""; 
		 boolean file0=false; //判断是否有文件上传	 
		 Enumeration<String> values0=request.getParameterNames();//把所有的元素先在part循环之前取出来，不能放在for(Part part:parts)循环内部去了，否则多次重复	 
		for(Part part:parts) {
		if(part.getContentType()==null)//非文件数据
		{ 
			while (values0.hasMoreElements()) {
		    String str0 = (String) values0.nextElement();//取前端控件name值
		if(request.getParameterValues(str0).length>1)//判断是否有几个name相同的控件，request.getParameterValues(str0)返回是一个数组
		{ String ah0=""; 
		 for(int i=0;i<request.getParameterValues(str0).length;i++)
		 { ah0=ah0+request.getParameterValues(str0)[i]+"-";
		 }
		 ah0=ah0.substring(0,ah0.length()-1);  	
		  
		try {
			Field f0 = null;//反射使用的对象
			f0 = c0.getClass().getDeclaredField(str0);
			f0.setAccessible(true);//使当前属性可以在外部操作
			f0.set(c0,ah0);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		      		
		}
		else { 	 
			try {//name不同的控件
				Field f0 = null;
				f0 = c0.getClass().getDeclaredField(str0);
				f0.setAccessible(true);
				f0.set(c0,request.getParameter(str0));
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	 
		  } 
		}
		}
		else {//文件部分	 
		  String filename=part.getSubmittedFileName();	  
		  String header=part.getHeader("Content-Disposition");
		  int start=header.lastIndexOf("=");
		  filename=header.substring(start+1);
		  if(filename.length()>2) 
		  {file0=true; 
		  filename=filename.substring(filename.lastIndexOf('\\')+2,filename.length()-1);
		 // filename=filename.substring(0,filename.length()-1);
		  java.util.Date d0=new java.util.Date();
		  String temp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(d0 );
		  filename=temp+"."+filename; 
		 part.write(savePath+"/"+filename);//保存到指定文件夹
		  fileNameString=fileNameString+filename+"*";
		  fieldFilename0=part.getName();
		  }	  
		}
		}
		  if(file0) 
		{ if(fileNameString.lastIndexOf('*')>1)
		fileNameString=fileNameString.substring(0,fileNameString.length()-1);
		
		try {
			Field f0 = null;		
			f0 = c0.getClass().getDeclaredField(fieldFilename0);
			f0.setAccessible(true);
			f0.set(c0,fileNameString);
			
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }
		}
		
		    //传入数据库中的表名，拼出SQL串
				public static String  insertDb2(String tableName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
			{	request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;Charset=UTF-8");		
			
				String sqlString="insert into ";				
				Collection<Part> parts=request.getParts();				
				String fieldStr0="";//累加保存数据列名串				
				String valuesStr0="";//累加保存数据值串				
				String savePath="C:\\loadFile"; //文件路径			 
				 String fileNameString="";//用于存放一个或多个文件在数据库中的文件名字
				 String fieldFilename0="";//存放到数据库文件列名
				 
				 boolean file0=false;//默认无文件上传
				 Enumeration<String> values0=request.getParameterNames();
				 for(Part part:parts) {
					 
			  if(part.getContentType()==null)//处理普通类型数据
			  {
			   		while (values0.hasMoreElements()) {
			    	String str0 = (String) values0.nextElement();
				if(request.getParameterValues(str0).length>1)
					//表示同一个name有多个选项值，使用数组接收
				{   String ah0="";//累加数组值
				 for(int i=0;i<request.getParameterValues(str0).length;i++)
				 { ah0=ah0+request.getParameterValues(str0)[i]+"-";
				 }
				 ah0=ah0.substring(0,ah0.length()-1);//去掉最后多余的｜
				 ah0="'"+ah0+"',";				 
				 fieldStr0=fieldStr0+str0+","; //凑数据操作串				 
				 valuesStr0=valuesStr0+ah0;     		
				}
			else { 
				fieldStr0=fieldStr0+str0+",";
				valuesStr0=valuesStr0+"'"+request.getParameter(str0)+"',";
				  }
				}
			  }
			  else {//处理文件部分				 
				  String filename=part.getSubmittedFileName();
				  String header=part.getHeader("Content-Disposition");
				  int start=header.lastIndexOf("=");
				  filename=header.substring(start+1);
				  if(filename.length()>2) 
				  {file0=true;//说明至少有一个文件上传了
					  filename=filename.substring(filename.lastIndexOf('\\')+2,filename.length()-1);
				  String temp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date() );
				  filename=temp+"."+filename;
			     part.write(savePath+"\\"+filename);
			   //课后要求：把获取文件名和设置保存路径放到工具类中模块化
				//插入数据库命令串
			     fileNameString=fileNameString+filename+"*";
			      fieldFilename0=part.getName();
			     //取出文件名的name，注意，在HTML要标准化把多个文件的name统一，否则取出与数据库列名不一致
			    // System.out.println(fieldFilename0);
				  }	  
			}  
			 }
			if(file0)//说明有文件上传，至少一个文件
			{fieldStr0=fieldStr0+fieldFilename0+",";
			
			if(fileNameString.lastIndexOf('*')>1)
				fileNameString=fileNameString.substring(0,fileNameString.length()-1);
			valuesStr0=valuesStr0+"'"+fileNameString+"',";
			}
			
			fieldStr0=fieldStr0.substring(0,fieldStr0.length()-1);
			//去掉值串最后一逗号
			valuesStr0=valuesStr0.substring(0, valuesStr0.length()-1);

			 sqlString=sqlString+tableName+"("+fieldStr0+") values("+valuesStr0+")";
			   return sqlString;
			}
            				
			//访问对象，拼SQL串
 				public static String insertDb3(String db, Object c0) {
				 String sqlString="insert into "+db+"(" ;
				 Field[] fields=c0.getClass().getDeclaredFields();
				 String fieldstr="";//属性名串
				 String valStr="";//属性值串
				 try {
					 for(Field f0:fields)
					 {f0.setAccessible(true);
					 String name0=f0.getName();	//获得当前循环的属性名			 		
					 String value0=f0.get(c0).toString();//获得当前循环的属性值
					 fieldstr=fieldstr+name0+",";
					 valStr=valStr+"'"+ value0+"',";
					 }
					 //去除最后逗号
					 fieldstr=fieldstr.substring(0, fieldstr.length()-1);
					 valStr=valStr.substring(0, valStr.length()-1);
					 //SQL串
					 fieldstr=fieldstr+") values(";
					 sqlString=sqlString+fieldstr+valStr+")";						 	 
				 } catch (IllegalArgumentException e) {
				 	// TODO Auto-generated catch block
				 	e.printStackTrace();
				 } 
				  catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 return sqlString;

				}
			
			//访问对象，拼SQL串(insertDb3的修改版)
				public static <T> String insertDb6(String db, T t) {
				 String sqlString="insert into "+db+"(" ;
                 Class<?> cl=t.getClass();
				 Field[] fields=cl.getDeclaredFields();
				 String fieldstr="";//属性名串
				 String valStr="";//属性值串
				 try {
					 for(Field f0:fields)
					 {f0.setAccessible(true);
					 String name0=f0.getName();	//获得当前循环的属性名			 		
					 String value0=f0.get(t).toString();//获得当前循环的属性值
					 fieldstr=fieldstr+name0+",";
					 valStr=valStr+"'"+ value0+"',";
					 }
					 //去除最后逗号
					 fieldstr=fieldstr.substring(0, fieldstr.length()-1);
					 valStr=valStr.substring(0, valStr.length()-1);
					 //SQL串
					 fieldstr=fieldstr+") values(";
					 sqlString=sqlString+fieldstr+valStr+")";						 	 
				 } catch (IllegalArgumentException e) {
				 	// TODO Auto-generated catch block
				 	e.printStackTrace();
				 } 
				  catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 return sqlString;

				}
			
			//拼查询串，传入表名 可实现多条件查询
			   public static String selectDb1(String tableName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
				   Enumeration<String> values0=request.getParameterNames();
				   String sqlString="select * from "+tableName+" where ";
				   while (values0.hasMoreElements()) {					   
					    String str0 = (String) values0.nextElement();//获取前端控件的name
					    if(request.getParameterValues(str0).length>1) {
					    	//表示同一个name有多个选项值，使用数组接收	
					    if(!request.getParameterValues(str0)[0].equals("")&&!request.getParameterValues(str0)[1].equals(""))
					    sqlString=sqlString+str0+">='"+request.getParameterValues(str0)[0]+"'and "+str0+"<='"+request.getParameterValues(str0)[1]+"' and ";					    	  
					    }
					    else {
						    if(!request.getParameter(str0).equals(""))
							    sqlString=sqlString+str0+"='"+request.getParameter(str0)+"'and ";		
						}

				   }
				   sqlString=sqlString.substring(0,sqlString.length()-4);
				   return sqlString;
			   }
				
			//时间格式化工具
				public static  java.sql.Date dateTrans(String sr0) {
					 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					    java.util.Date sr1 = null;
						try {
							sr1 = sdf.parse(sr0);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					return	new java.sql.Date(sr1.getTime());
				}
				
			//将java对象转换为JSON对象，并放到JSON数组中
				public static void modelToJsonArray(Object c0, JSONArray jsArray0)
				{
				//JSONObject js0=new JSONObject();乱序
				JSONObject js0 = new JSONObject(new LinkedHashMap<String, Object>());//有序
				Field[] fields=c0.getClass().getDeclaredFields();
				for(Field f0:fields)
				{f0.setAccessible(true);
				String name0=f0.getName();
				String value0="";
				 try {
					value0=f0.get(c0).toString();
					 
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 js0.put(name0, value0);
				 	 
				}
				jsArray0.add(js0);	
				  
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
				
			//参数依次为request，response，tableName（表名）获取到插入的SQL语句（自己写的）
				public String InsertTable(HttpServletRequest request, HttpServletResponse response,String tableName)throws ServletException, IOException {
						  		  request.setCharacterEncoding("UTF-8");
						          response.setContentType("text/html;Charset=UTF-8");			          
						          String sqlString="insert into ";
						          String fieldStr0="";//属性名串
						          String valuesStr0="";//值串
						          boolean file0=false;//判断是否有文件
						          String savePath = "C:\\loadFile";//存放位置
						          String  fileNameString="";//文件名
						          String fieldFilename0="";//前台name
						        Collection<Part> parts = request.getParts();
						        			        
						        Enumeration<String> values0=request.getParameterNames();									
								
						  		for(Part p0:parts)  //测试一下各部分的类型
						  		{				  			
						  			if(p0.getContentType()==null) {
						  				while (values0.hasMoreElements()) {							
											String str0 = (String) values0.nextElement();							
											if(request.getParameterValues(str0).length>1){//表示同一个name有多个选项值，使用数组接收
											String ah0="";					
											 for(int i=0;i<request.getParameterValues(str0).length;i++){ 
											   ah0=ah0+request.getParameterValues(str0)[i]+"-";
											 }
											 //去掉爱好最后多余的｜
											 ah0=ah0.substring(0,ah0.length()-1);	 
											 //凑数据操作串
											 fieldStr0=fieldStr0+str0+",";
											 valuesStr0=valuesStr0+"'"+ah0+"',";	 		     		
											}
											else {//不是集合的一般数据项
											//凑成数据库操作的insert插入串
												fieldStr0=fieldStr0+str0+",";
												valuesStr0=valuesStr0+"'"+request.getParameter(str0)+"',";			
											}			
											}
						  			}
						  			else{			  			    			
						  				//文件处理					  			  
						  			  String header=p0.getHeader("Content-Disposition");
						  			  int start=header.lastIndexOf("=");//等号位置	  			
						  			  String  filename=header.substring(start+1);
						  			  if(filename.length()>2)  {
						  				  file0=true;//说明至少有一个文件上传了
							  			  if(filename.indexOf(":")==-1)//form-data; name="photo"; filename="C:\Users\25498\Desktop\web后端\web后端资料"			  	
							  			    filename=p0.getSubmittedFileName();			  			  
							  			else 
							  			  filename=filename.substring(filename.lastIndexOf('\\')+1,filename.length()-1);	
							  			  
						  			      String temp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date() );
						  			      filename=temp+"."+filename;			  			 
						  		     p0.write(savePath+"\\"+filename);
						  		   //课后要求：把获取文件名和设置保存路径放到工具类中模块化
						  			//插入数据库命令串
						  		     fileNameString=fileNameString+filename+"*";
						  		      fieldFilename0=p0.getName();
						  		     //取出文件名的name，注意，在HTML要标准化把多个文件的name统一，否则取出与数据库列名不一致			  		    		  		      
						  			  }	  					  		
						  		}  	  		 
								}		  		
					  			if(file0)//说明有文件上传，至少一个文件
						  		{fieldStr0=fieldStr0+fieldFilename0+",";
						  		if(fileNameString.lastIndexOf('*')>1)
						  			fileNameString=fileNameString.substring(0,fileNameString.length()-1);
						  		    valuesStr0=valuesStr0+"'"+fileNameString+"',";
						  		}
					  		//去最后一个逗号
					  	     fieldStr0= fieldStr0.substring(0,fieldStr0.length()-1);
					  		 valuesStr0=  valuesStr0.substring(0,valuesStr0.length()-1);
						  	sqlString=sqlString+tableName+"("+fieldStr0+") values("+valuesStr0+")";	
						return sqlString;
					}
								
			//使用文件工厂
				public static void RequestToModel2(String tableName,Object c0, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException
				{request.setCharacterEncoding("UTF-8");
				 String savePath="C:/loadFile/"+tableName;
				 String fileNameString=savePath+"/"; //使用/,减少转义符带来的麻烦 
				 DiskFileItemFactory factory1=new DiskFileItemFactory();//建立磁盘文件工厂。类来源于：commons-fileupload-1.4.jar
				  factory1.setSizeThreshold(1024*1024*20);//设置内存中用字节数
				 
				  if(ServletFileUpload.isMultipartContent(request))//判断是不是mime方式过来的
				{ //如果是MIME才能进行文件上传，如果不是则跳到本程序最后的else按getparmeter()方法接收参数要简单得多。这里的办法不管是不是文件还是一般数据都可以接收，但处理过程太复杂。
				ServletFileUpload uploadf1=new ServletFileUpload(factory1); //创建文件工厂
				uploadf1.setHeaderEncoding("utf-8");//只能解决文件名或路径中汉字乱码	 
				java.util.List<FileItem> fileItemList;	//创建一个链表存放文件。
					try {
						fileItemList = uploadf1.parseRequest(request);		
						System.out.println(fileItemList.size());
						for(FileItem fileItem :fileItemList )
						 {if(fileItem.isFormField())  //表示是普通参数，不是文件
						 { 
							 System.out.println(fileItem.getFieldName());
						 Field f0 = null;
						f0 = c0.getClass().getDeclaredField(fileItem.getFieldName());
						 f0.setAccessible(true);
						f0.set(c0,fileItem.getString("utf-8"));   
						 }
						 else //不是fileItem.isFormField()),那下一项就是文件流了（以上程序结构要注意，在表单中要把文件放在最后一项，要不然你就在上面循环调用处理文件保存 独立函数
						 { 	 
							System.out.println(fileItem.getFieldName());
							String   fname1=fileItem.getName();
							// System.out.println(fname1);
							 //去掉前面地路径，只要后面干净文件名。
							 int po=fname1.lastIndexOf("\\");
							 if(po!=-1)//说明找到了右斜杠\
							 { fname1=fname1.substring(po+1);}
							// System.out.println(fname1);
							 System.out.println(fileItem.getSize());
							 if(fileItem.getSize()>1024*1024*20) //文件太大了，拒绝上传
							 {return;  }
							 if(fname1==null||fname1.equals("")||fileItem.getSize()==0)
							 {//那说明没有上传文件过来，这样处理 好处是，用户可传可不传文件，无所谓
								 				 
							 }
							 else {
									java.util.Date d0=new java.util.Date();
									  String temp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(d0 );
									  fname1=temp+"."+fname1; 
								System.out.println(fname1);
								File savefile1=new File(fileNameString,fname1);
								try {
									fileItem.write(savefile1);//大家自己解决已有文件同名问题，可以使用系统时间数字，冲突可能性几乎没有
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						 }
						 }
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
				 
				}
								
				public static void RequestToModel3(String tableName,Object c0, HttpServletRequest request) throws ServletException, IOException, ClassNotFoundException
				{request.setCharacterEncoding("UTF-8");	
				  Collection<Part> parts=request.getParts();
				  String  savePath="C:/loadFile/";
				  savePath=savePath+tableName;
				   //savePath=savePath+"/"+tableName; 
				 // System.out.println(parts.size());
				 String fileNameString=""; //使用/,减少转义符带来的麻烦 
				 String fieldFilename0=""; 
				 boolean file0=false; 
				 Enumeration<String> values0=request.getParameterNames();//把所有的元素先在part循环之前取出来，不能放在for(Part part:parts)循环内部去了，否则多次重复
				for(Part part:parts) {
				if(part.getContentType()==null)//非文件数据
				{ while (values0.hasMoreElements()) {
				String str0 = (String) values0.nextElement();
				System.out.println(str0);
				if(request.getParameterValues(str0).length>1)
				{ String ah0=""; 
				 for(int i=0;i<request.getParameterValues(str0).length;i++)
				 { ah0=ah0+request.getParameterValues(str0)[i]+"-";
				 }
				 ah0=ah0.substring(0,ah0.length()-1);  	
				  Field f0 = null;
				try {
					f0 = c0.getClass().getDeclaredField(str0);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  f0.setAccessible(true);
				  try {
					f0.set(c0,ah0);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				      		
				}
				else { 
					  Field f0 = null;
					try {f0 = c0.getClass().getDeclaredField(str0);
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  f0.setAccessible(true);
					  try {	f0.set(c0,request.getParameter(str0));
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	 
				  } 
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
				  //filename=filename.substring(0,filename.length()-1);
				filename=filename.substring(1,filename.length()-1);
				
				java.util.Date d0=new java.util.Date();
				  String temp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(d0 );
				  filename=temp+"."+filename; 
				  //System.out.println(filename);
				  //System.out.println(savePath+"/"+filename);
			     part.write(savePath1+"/"+filename);
				  fileNameString=fileNameString+tableName+"/"+filename+"*";
				  fieldFilename0=part.getName();
				  }	  
				}
				}
				  if(file0) 
				{ if(fileNameString.lastIndexOf('*')>1)
				fileNameString=fileNameString.substring(0,fileNameString.length()-1);
				Field f0 = null;
				try {
					f0 = c0.getClass().getDeclaredField(fieldFilename0);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				f0.setAccessible(true);
				try {f0.set(c0,fileNameString);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 }
				}
				
			//输入类名映射通用化 参数：文件夹名、动态加载的类、请求头
				public static <T> void RequestToModel4(String tableName,T t, HttpServletRequest request) throws ServletException, IOException, ClassNotFoundException
				{				
			     Class<?> clz0=t.getClass(); //动态加载类
				 request.setCharacterEncoding("UTF-8");
				  Collection<Part> parts=request.getParts();
				  String  savePath=savePath1+tableName;				 
				 String fileNameString=""; //使用/,减少转义符带来的麻烦 
				 String fieldFilename0=""; 
				 boolean file0=false; 
				 Enumeration<String> values0=request.getParameterNames();//把所有的元素先在part循环之前取出来，不能放在for(Part part:parts)循环内部去了，否则多次重复
				for(Part part:parts) {
				if(part.getContentType()==null)//非文件数据
				{ while (values0.hasMoreElements()) {
				String str0 = (String) values0.nextElement();
				if(request.getParameterValues(str0).length>1)
				{ String ah0=""; 
				 for(int i=0;i<request.getParameterValues(str0).length;i++)
				 { ah0=ah0+request.getParameterValues(str0)[i]+"-";
				 }
				 ah0=ah0.substring(0,ah0.length()-1);  	
				  Field f0 = null;
				try {
					f0 = clz0.getDeclaredField(str0);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  f0.setAccessible(true);
				  try {
					f0.set(t,ah0);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				      		
				}
				else { 
					  Field f0 = null;
					try {f0 = clz0.getDeclaredField(str0);
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  f0.setAccessible(true);
					  try {	f0.set(t,request.getParameter(str0));
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	 
				  } 
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
				  //filename=filename.substring(0,filename.length()-1);
				filename=filename.substring(1,filename.length()-1);
				
				java.util.Date d0=new java.util.Date();
				  String temp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(d0 );
				  filename=temp+"."+filename; 
				  //System.out.println(filename);
				  //System.out.println(savePath+"/"+filename);
			     part.write(savePath+"/"+filename);
				  fileNameString=fileNameString+tableName+"/"+filename+"*";
				  fieldFilename0=part.getName();
				  }	  
				}
				}
				  if(file0) 
				{ if(fileNameString.lastIndexOf('*')>1)
				fileNameString=fileNameString.substring(0,fileNameString.length()-1);
				Field f0 = null;
				try {
					f0 = clz0.getDeclaredField(fieldFilename0);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				f0.setAccessible(true);
				try {f0.set(t,fileNameString);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 }  
	      	   }
			
							
				
			//输入类名映射通用化 参数：文件夹名、动态加载的类、请求头(适用于update)
				public static <T> String RequestToModel6(String tableName,T t, HttpServletRequest request) throws ServletException, IOException, ClassNotFoundException
					{ String updateKeyString = null;		
				     Class<?> clz0=t.getClass(); //动态加载类
					 request.setCharacterEncoding("UTF-8");
					  Collection<Part> parts=request.getParts();
					  String  savePath=savePath1+tableName;				 
					 String fileNameString=""; //使用/,减少转义符带来的麻烦 
					 String fieldFilename0=""; 
					 boolean file0=false; 
					 Enumeration<String> values0=request.getParameterNames();//把所有的元素先在part循环之前取出来，不能放在for(Part part:parts)循环内部去了，否则多次重复
					for(Part part:parts) {
					if(part.getContentType()==null)//非文件数据
					{ while (values0.hasMoreElements()) {
							
					String str0 = (String) values0.nextElement();
					if(!str0.equals("updateKey")){
					if(request.getParameterValues(str0).length>1)
					{ String ah0=""; 
					 for(int i=0;i<request.getParameterValues(str0).length;i++)
					 { ah0=ah0+request.getParameterValues(str0)[i]+"-";
					 }
					 ah0=ah0.substring(0,ah0.length()-1);  	
					  Field f0 = null;
					try {
						f0 = clz0.getDeclaredField(str0);
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  f0.setAccessible(true);
					  try {
						f0.set(t,ah0);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					      		
					}
					else {
						
						  Field f0 = null;
						try {f0 = clz0.getDeclaredField(str0);
						} catch (NoSuchFieldException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						  f0.setAccessible(true);
						  try {	f0.set(t,request.getParameter(str0));
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	 
					  } 
					}
					else {
						updateKeyString=request.getParameter(str0);
					}
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
					  //filename=filename.substring(0,filename.length()-1);
					filename=filename.substring(1,filename.length()-1);
					
					java.util.Date d0=new java.util.Date();
					  String temp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(d0 );
					  filename=temp+"."+filename; 
					  //System.out.println(filename);
					  //System.out.println(savePath+"/"+filename);
				     part.write(savePath+"/"+filename);
					  fileNameString=fileNameString+tableName+"/"+filename+"*";
					  fieldFilename0=part.getName();
					  }	  
					}
					}
					  if(file0) 
					{ if(fileNameString.lastIndexOf('*')>1)
					fileNameString=fileNameString.substring(0,fileNameString.length()-1);
					Field f0 = null;
					try {
						f0 = clz0.getDeclaredField(fieldFilename0);
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					f0.setAccessible(true);
					try {f0.set(t,fileNameString);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 }  
					
					return updateKeyString;
		      	   }
				
			//遍历Java对象，在后台输出Java对象信息
				public void traversal (Object c0) {
					Field[] fields=c0.getClass().getDeclaredFields();
					for(Field f0:fields)
					{f0.setAccessible(true);
					String name0=f0.getName();
					String value0="";
					try {
						 value0=f0.get(c0).toString();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					 
					}		
			
			//使用文件工厂
				public static void RequestToModel5(String tableName,Object c0, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException
				{request.setCharacterEncoding("UTF-8");
				 String savePath="C:/loadFile/"+tableName;
				 String fileNameString=savePath+"/"; //使用/,减少转义符带来的麻烦 
				 String sqlfieldFilename0=""; //存放在数据库中的名称
				 DiskFileItemFactory factory1=new DiskFileItemFactory();//建立磁盘文件工厂。类来源于：commons-fileupload-1.4.jar
				  factory1.setSizeThreshold(1024*1024*20);//设置内存中用字节数
				 
				  if(ServletFileUpload.isMultipartContent(request))//判断是不是mime方式过来的
				{ //如果是MIME才能进行文件上传，如果不是则跳到本程序最后的else按getparmeter()方法接收参数要简单得多。这里的办法不管是不是文件还是一般数据都可以接收，但处理过程太复杂。
				ServletFileUpload uploadf1=new ServletFileUpload(factory1); //创建文件工厂
				uploadf1.setHeaderEncoding("utf-8");//只能解决文件名或路径中汉字乱码	 
				java.util.List<FileItem> fileItemList;	//创建一个链表存放文件。
							
					try {
						fileItemList = uploadf1.parseRequest(request);		
						System.out.println(fileItemList.size());					
						for(int i=0;i<fileItemList.size();) {
							FileItem fileItem=fileItemList.get(i);
							if(fileItem.isFormField())  //表示是普通参数，不是文件
							{	String ah0=fileItemList.get(i).getString("utf-8");
								for(int j=i+1;j<fileItemList.size();j++) {
									if(fileItemList.get(i).getFieldName().equals(fileItemList.get(j).getFieldName())) {
										ah0=ah0+'-'+fileItemList.get(j).getString("utf-8");
									}
									else {
										Field f0 = null;
										f0 = c0.getClass().getDeclaredField(fileItemList.get(i).getFieldName());
										 f0.setAccessible(true);
										f0.set(c0,ah0);  
										i=j;
										break;
									}
								}
							}
							else {
								 String  fname1=fileItemList.get(i).getName();
								// System.out.println(fname1);
								 //去掉前面地路径，只要后面干净文件名。
								 int po=fname1.lastIndexOf("\\");
								 if(po!=-1)//说明找到了右斜杠\
								 { fname1=fname1.substring(po+1);}
								 if(fileItem.getSize()>1024*1024*20) //文件太大了，拒绝上传
								 {return;  }
								 if(fname1==null||fname1.equals("")||fileItem.getSize()==0)
								 {//那说明没有上传文件过来，这样处理 好处是，用户可传可不传文件，无所谓								 				 
								 }
								 else {
										java.util.Date d0=new java.util.Date();
									    String temp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(d0 );
									    fname1=temp+"."+fname1; 
									    sqlfieldFilename0=sqlfieldFilename0+tableName+"/"+fname1+"*";
										File savefile1=new File(fileNameString,fname1);
										fileItem.write(savefile1);//大家自己解决已有文件同名问题，可以使用系统时间数字，冲突可能性几乎没有
								}	
								 i++;
							}
						}
						 Field f0 = null;
						 f0 = c0.getClass().getDeclaredField(fileItemList.get(fileItemList.size()-1).getFieldName());
					     f0.setAccessible(true);
						 f0.set(c0,sqlfieldFilename0.substring(0,sqlfieldFilename0.length()-1)); 															
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
		}

			//访问对象，生成预编译的SQL串	
				
			//访问对象，拼插入SQL串(用于插入时的预编译) 参数：数据库表名、动态加载的对象
				public static <T> String insertDb5(String db, T t) {
				 String sqlString="insert into "+db+"(" ;
				 Class<?> c0=t.getClass();
				 Field[] fields=c0.getDeclaredFields();
				 String fieldstr="";//属性名串
				 String valStr="";//属性值串
				 try {
					 for(Field f0:fields)
					 {f0.setAccessible(true);
					 String name0=f0.getName();	//获得当前循环的属性名			 							 
					 fieldstr=fieldstr+name0+",";
					 valStr=valStr+"?,";
					 }
					 //去除最后逗号
					 fieldstr=fieldstr.substring(0, fieldstr.length()-1);
					 valStr=valStr.substring(0, valStr.length()-1);
					 //SQL串
					 fieldstr=fieldstr+") values(";
					 sqlString=sqlString+fieldstr+valStr+")";						 	 
				 } catch (IllegalArgumentException e) {
				 	// TODO Auto-generated catch block
				 	e.printStackTrace();
				 }
				 return sqlString;

				}
			
			//访问对象，拼修改SQL串(用于插入时的预编译) 参数：数据库表名、动态加载的对象
				//UPDATE student3 SET studentNum='10000007' WHERE studentNum='10000001'
				public static <T> String insertDb7(String db, T t,String updateKey) throws IllegalAccessException {
				 String sqlString="update "+db+" set " ;
				 Class<?> c0=t.getClass();
				 Field[] fields=c0.getDeclaredFields();
				 try {
					 for(Field f0:fields)
					 {f0.setAccessible(true);
					 String name0=f0.getName();	//获得当前循环的属性名
					 String value0=f0.get(t).toString();//获得当前循环的属性值
					 sqlString=sqlString+name0+" = ? , ";
					 }
					 Field f0=fields[0];
					 f0.setAccessible(true);
					 sqlString=sqlString.substring(0,sqlString.length()-2);
					 sqlString=sqlString+" where "+updateKey;		
				 } catch (IllegalArgumentException e) {
				 	// TODO Auto-generated catch block
				 	e.printStackTrace();
				 }
				 return sqlString;

				}
				
			//访问对象，拼SQL串 传入值为Class类型
				public static String insertDb4(String db, Object c0) {
				 String sqlString="insert into "+db+"(" ;
				 Field[] fields=c0.getClass().getDeclaredFields();
				 String fieldstr="";//属性名串
				 String valStr="";//属性值串
				 try {
					 for(Field f0:fields)
					 {f0.setAccessible(true);
					 String name0=f0.getName();	//获得当前循环的属性名			 		

					 
					 fieldstr=fieldstr+name0+",";
					 valStr=valStr+"?,";
					 }
					 //去除最后逗号
					 fieldstr=fieldstr.substring(0, fieldstr.length()-1);
					 valStr=valStr.substring(0, valStr.length()-1);
					 //SQL串
					 fieldstr=fieldstr+") values(";
					 sqlString=sqlString+fieldstr+valStr+")";						 	 
				 } catch (IllegalArgumentException e) {
				 	// TODO Auto-generated catch block
				 	e.printStackTrace();
				 }
				 return sqlString;

				}
}
