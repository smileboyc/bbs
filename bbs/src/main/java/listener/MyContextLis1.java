package listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class MyContextLis1
 *
 */
@WebListener
public class MyContextLis1 implements ServletContextListener, ServletContextAttributeListener {

    /**
     * Default constructor. 
     */
    public MyContextLis1() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	
         // TODO Auto-generated method stub
    	  // web应用停止时，容器调用本方法，把context中的计数器写到文件中去。这个要发生在服务器重启动后才会实现的。中间数计算访问量不会保存
   	 ServletContext context1=arg0.getServletContext();//首先把servletContext对象获得
        context1.log(context1.getServletContextName()+"应用结束，服务器要关闭了。");
        String s1=context1.getAttribute("no1").toString();
   	 Integer n1=Integer.parseInt(s1);
        //n1++ 的事在其它web访问时实现了的
        try {
   	 BufferedWriter write1=new BufferedWriter(new FileWriter("C:\\Users\\25498\\eclipse-workspace\\studentManege\\src\\main\\webapp\\DB\\count.txt"));
   	 write1.write(n1.toString().trim());
   	 write1.close();
        } catch(IOException e) {e.printStackTrace();
			// TODO: handle exception
		}

    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	//当web应用初始化时，自动调用这个方法，以后少操很多心哟
    	//为什么能达到网站访问数计数目的？细想一下
    	
       ServletContext context1=arg0.getServletContext();//首先把servletContext对象获得
       context1.log(context1.getServletContextName()+"应用开始初始化。");
       try { //准备读文本文件数据出来
    	 BufferedReader reader1=new  BufferedReader(new  FileReader("C:\\Users\\25498\\eclipse-workspace\\studentManege\\src\\main\\webapp\\DB\\count.txt"));
    	//这个文件路径最好换成相对路径，或使用系统ｐａｔｈ
    	 String s1=reader1.readLine();
    	 reader1.close();    	   	 
    	 Integer n1=Integer.parseInt(s1);   
    	 context1.setAttribute("no1", n1);//把计数器保存以web应用中去，以后在网页中可调用  	
    	  
       }catch (IOException e) { e.printStackTrace();}
		// TODO: handle exception
	}    	
    }
	

