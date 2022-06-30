

package aystzh.github.com.oss.server.common;

import javax.servlet.http.HttpServletRequest;


public class ThreadLocalHolder {
	
	private static final ThreadLocal<HttpServletRequest> th_request=new ThreadLocal<HttpServletRequest>();


	
	public static HttpServletRequest getRequest(){
		return th_request.get();
	}
	
	public static void setRequest(HttpServletRequest request){
		th_request.set(request);
	}
	


}
