package listener;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

import Tools.mysqlconnect;

/**
 * Application Lifecycle Listener implementation class MySession1Lis1
 *
 */
@WebListener
public class MySession1Lis1 implements HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener, HttpSessionIdListener {

    /**
     * Default constructor. 
     */
    public MySession1Lis1() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
    	
         // TODO Auto-generated method stub
    	// 发生新用户的进来时发生.把新用户信息放到应用的Context上下文件对象中，以便其它所有网页可以共用
   	   HttpSession session1=arg0.getSession();
   	   session1.setMaxInactiveInterval(600);
   	   
   	   LocalDateTime nowTime=LocalDateTime.now();
   	   String  pattern="%tY-%<tm-%<td %<tT";
   	   String startTime=String.format(pattern, nowTime);
       session1.setAttribute("startTime", startTime);
//   	 ServletContext app1=session1.getServletContext();
//   	 String sessionid=session1.getId(); //取得系统给该用户分得的ID
//   	 if(session1.isNew()) { //如果是第一次新的会话，也就是说本网站应用启动后，此用户是第一个进来
//   		 String username1=session1.getAttribute("student_num").toString();
//   		 System.out.println(username1);
//   		 if(username1==null) {username1="未登陆的游客";}
//   		 Map<String, String> users=(Map<String, String>)app1.getAttribute("online");
//   		 if(users==null) users=new Hashtable<String, String>();
//   		 users.put(sessionid,username1);//把新用户在线信息放到map
//   		 app1.setAttribute("online", users);   		 
//   	 }
//   	 }
    }

    

	/**
     * @see HttpSessionBindingListener#valueBound(HttpSessionBindingEvent)
     */
    public void valueBound(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionIdListener#sessionIdChanged(HttpSessionEvent, String)
     */
    public void sessionIdChanged(HttpSessionEvent arg0, String arg1)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    	// 当用户与服务器断开时触发 
   	    LocalDateTime nowTime=LocalDateTime.now();
   	    String  pattern="%tY-%<tm-%<td %<tT";
   	    String endTime=String.format(pattern, nowTime);

   	    
    	HttpSession session1=arg0.getSession();
    	
    	ServletContext app1=session1.getServletContext();
    	
    	String student_num=session1.getAttribute("student_num").toString(); 
    	String student_name=session1.getAttribute("student_name").toString(); 
   	 	String userIp=session1.getAttribute("userIp").toString(); 
   	 	String startTime=session1.getAttribute("startTime").toString(); 
   	 	
    	String sessionid=session1.getId();
    	
    	Map<String, String>users=(Map<String, String>)app1.getAttribute("online");
    	
    	if(users!=null) users.remove(sessionid);//删除该用户的在线信息
    	app1.setAttribute("online", users);//把删除后的结果重新写回context对象

    	String sqlString="insert into history  (student_num ,student_name,userIp,startTime,endTime)  values('"+student_num+" ', '"+student_name+"','"+userIp+"','"+startTime+"','"+endTime+"')";
    	 try {
			mysqlconnect.insert1(sqlString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }

	/**
     * @see HttpSessionActivationListener#sessionDidActivate(HttpSessionEvent)
     */
    public void sessionDidActivate(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	 HttpSession session1=arg0.getSession();
    	 
      	 ServletContext app1=session1.getServletContext();
      	 
      	 String sessionid=session1.getId(); //取得系统给该用户分得的ID
      	 if(session1.isNew()) { //如果是第一次新的会话，也就是说本网站应用启动后，此用户是第一个进来
      		 if(session1.getAttribute("student_num")!=null) {
      			 
      		 String username1=session1.getAttribute("student_num").toString();
      		 
      		 if(username1==null) {username1="未登陆的游客";}
      		 Map<String, String> users=(Map<String, String>)app1.getAttribute("online");
      		 
      		 if(users==null) users=new Hashtable<String, String>();
      		 
      		 users.put(sessionid,username1);//把新用户在线信息放到map
      		 app1.setAttribute("online", users);   

      		 }	  
      	 }
      	 
      	
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionActivationListener#sessionWillPassivate(HttpSessionEvent)
     */
    public void sessionWillPassivate(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionBindingListener#valueUnbound(HttpSessionBindingEvent)
     */
    public void valueUnbound(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
