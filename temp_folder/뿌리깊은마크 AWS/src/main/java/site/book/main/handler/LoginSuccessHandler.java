package site.book.main.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import site.book.user.dto.UserDTO;
import site.book.user.service.UserService;

/**
 * @Class : LoginSuccessHandler.java
 * @Date : 2018. 6. 12.
 * @Author : 김태웅
 */

// Spring Security에서 제공해주는 AuthenticationSuccessHandler를 상속 받아 onAuthenticationSuccess method를 오버라이딩
// LoginSuccessHandler란 로그인 성공시 사용자에게 응답하기 전에 처리되는 Filter의 역할
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// Handler는 Filter로 작동함으로서 HttpServletRequest, HttpServletResponse 모두 사용
		// Authentication을 통해 사용자의 정보를 가져올 수 있음
		
		// authentication.getName(): userid
		request.setAttribute("userid", authentication.getName());
		request.setAttribute("msg", "success");
		
		// 로그인 성공한 회원이 가지고 있는 권한(역할)을 체크하는 부분
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean authorized = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		// 만약 권한 중 ROLE_ADMIN을 가지고 있다면, ADMIN을 담고 아니라면 USER를 담는 부분
		if(authorized) {
			request.setAttribute("ROLE", "ADMIN");
		}else {
			request.setAttribute("ROLE", "USER");
		}
		
		// 비동기 처리에 필요한 부분으로 동기가 아닌 해당 주소로 forward 방식을 통해 처리가 가능
		request.getRequestDispatcher("/joinus/login.do").forward(request, response);
	}
}
