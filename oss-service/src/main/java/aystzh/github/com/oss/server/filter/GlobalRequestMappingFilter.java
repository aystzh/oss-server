
package aystzh.github.com.oss.server.filter;


import aystzh.github.com.oss.server.common.ThreadLocalHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class GlobalRequestMappingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		ThreadLocalHolder.setRequest(req);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
