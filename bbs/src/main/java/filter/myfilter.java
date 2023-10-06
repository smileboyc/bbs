package filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class myfilter
 */

//@WebFilter(filterName = "myfilter1",urlPatterns = "/*")
//@WebFilter(filterName = "myfilter1",urlPatterns = {"/displayAllTz","/xxx"})
@WebFilter(filterName = "myfilter",urlPatterns = "/needFilter/*")

public class myfilter  implements Filter {

    /**
     * Default constructor. 
     */
    public myfilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest request2=(HttpServletRequest)request;
		HttpServletResponse response2=(HttpServletResponse)response;
		System.out.println("过滤器来了");
		response2.setContentType("text/html");
		response2.setCharacterEncoding("utf-8");
		//理论上可以从request中取出上下文，session,cookies,用户表单数据
		HttpSession session2=null;
		if(request2.getSession()!=null)
		{
		  session2=request2.getSession(false);
		}
		//System.out.println(session2.getAttribute("xm"));
		   if(session2.getAttribute("student_num")==null)
		 {  System.out.println("拦截了");
		   PrintWriter out1=response2.getWriter();
		   out1.println("请先登陆，不能跨越访问");
		   out1.flush(); 
		    //response2.sendRedirect("index.html");
		    return;
		  }
		  else
		  {  System.out.println("放行");
			  chain.doFilter(request, response);//放行，继续访问用户原目标资源
		  return;
		  }	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
