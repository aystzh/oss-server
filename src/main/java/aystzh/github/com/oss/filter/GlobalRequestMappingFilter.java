
package aystzh.github.com.oss.filter;


import aystzh.github.com.oss.common.ThreadLocalHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/***
 * GlobalRequestMappingFilter
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 */
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
