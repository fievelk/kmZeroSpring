package it.univaq.mwt.j2ee.kmZero.common.spring.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CookieInterceptor extends HandlerInterceptorAdapter{

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
			throws Exception {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		WebAuthenticationDetails wad = (WebAuthenticationDetails) a.getDetails();
		boolean exist = false;
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null){
			for (Cookie cookie : cookies){
				if (cookie.getName().equals("kmzero")){
					exist = true;
				}
			}
		} else {
			exist = true;
		}
		
		if (!exist){
			Cookie c = new Cookie("kmzero", wad.getSessionId());
			c.setHttpOnly(true);
			response.addCookie(c);
		}
		
		return true;
	}
	
}
