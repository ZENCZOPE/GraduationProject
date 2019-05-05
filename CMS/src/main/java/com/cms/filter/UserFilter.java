package com.cms.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class UserFilter implements Filter {
		  private static final Logger log =Logger.getLogger(UserFilter.class);

		  protected FilterConfig filterConfig = null;

		  private List<String> noCheckUrlList = new ArrayList();

		  public void init(FilterConfig filterconfig)
		    throws ServletException{
		    this.filterConfig = filterconfig;
		    String notCheckURLListStr = this.filterConfig.getInitParameter("notCheckURL");
		    if (notCheckURLListStr != null) {
		      StringTokenizer st = new StringTokenizer(notCheckURLListStr, ";");
		      this.noCheckUrlList.clear();
		      while (st.hasMoreTokens())
		        this.noCheckUrlList.add(st.nextToken());}
		    }
		  

	@Override
	public void destroy() {
		this.filterConfig = null;
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		long startTime = 0L;
	    if (log.isDebugEnabled()) {
	      startTime = new Date().getTime();
	      log.debug("调用【doFilter】方法开始");
	    }
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		if (checkRequestURIIntNotFilterList(request))
	    {
	     
	      } else {
	    
		Cookie[] cookies=request.getCookies();
		if (null==cookies) {
			chain.doFilter(arg0, arg1);
			if (log.isDebugEnabled()) {
			      long useredTime = new Date().getTime() - startTime;
			      log.debug("调用【doFilter】方法结束, 耗时【" + useredTime + "】ms");
			    }
			return;
		}
		if(cookies.length!=4){
			response.sendRedirect("/CMS/login/initLogin.do");
			//request.getRequestDispatcher("").forward(arg0, arg1);
			//throw new ServletException("cookie问题，请重新登陆！");
			return ;
		}}
		chain.doFilter(arg0, arg1);
		if (log.isDebugEnabled()) {
		      long useredTime = new Date().getTime() - startTime;
		      log.debug("调用【doFilter】方法结束, 耗时【" + useredTime + "】ms");
		    }
	}
	
	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request)
	  {
	    String uri = request.getRequestURI();

	    String contextPath = request.getContextPath();
	    uri = uri.replaceFirst(contextPath, "");
	    boolean a=this.noCheckUrlList.contains(uri);
	    return a;
	  }

	

}
