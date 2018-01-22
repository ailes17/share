package com.share.view.filters;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
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

import com.share.view.constants.PageNames;
 
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/filtered/*"})
public class AuthFilter implements Filter {
     
	
    public AuthFilter() {
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         try {
 
            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);
            
            if (!req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip JSF resources (CSS/JS/Images/etc)
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                res.setDateHeader("Expires", 0); // Proxies.
            }
            
            String reqURI = req.getRequestURI();
            
            
            if (isUserConnected(ses)) {
            	if (reqURI.contains(PageNames.LOGIN_PAGE) || reqURI.contains(PageNames.NEW_USER_PAGE)) {
            		res.sendRedirect(req.getContextPath() + PageNames.INDEX_PAGE);
            	} else {
            		chain.doFilter(request, response);
            	}
            } else {
            	if (reqURI.contains(PageNames.LOGIN_PAGE) || reqURI.contains(PageNames.NEW_USER_PAGE)) {
            		chain.doFilter(request, response);
            	} else {
            		res.sendRedirect(req.getContextPath() + PageNames.LOGIN_PAGE + PageNames.FACES_REDIRECT);
            	}
            }
            
      }
     catch(Throwable t) {
         System.out.println( t.getMessage());
     }
    } //doFilter
 
    @Override
    public void destroy() {
         
    }
    
    private boolean isUserConnected(final HttpSession ses) {
    	return (ses != null && ses.getAttribute("username") != null) ? true : false;
    }
}