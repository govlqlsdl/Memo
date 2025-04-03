package com.kobi.memo.interceptor;

import java.io.IOException;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PermissionInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(
			HttpServletRequest request
			, HttpServletResponse response
			, Object handler) throws IOException {
		
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		
		// /post/list-view
		String uri = request.getRequestURI();
		
		if(userId == null) {
			// 로그인이 안된 상태에서는 메모와 관련된 페이지 접근을 막는다.
			// /post 로 시작하는 url 접근을 막는다
			if(uri.startsWith("/post")) {
				// 로그인 페이지로 이동시킨다.
				// 로그인 페이지로 리다이렉트 시킨다.
				response.sendRedirect("/user/login-view");
				return false;
			}
		}else {
			// 로그인이 된 상태에서는 사용자와 관련된 페이지 접근을 막는다.
			// /user 로 시작하는 url 접근을 막는다.
			if(uri.startsWith("/user")) {
				// 리스트 페이지로 리다이렉트
				response.sendRedirect("/post/list-view");
				return false;
			}
			
		}
		
		return true;
		
		
	}
	
	

}
