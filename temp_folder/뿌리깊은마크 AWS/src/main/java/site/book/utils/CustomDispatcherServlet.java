package site.book.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;



@SuppressWarnings("serial")
public class CustomDispatcherServlet extends DispatcherServlet{
	private static final Logger logger = LoggerFactory.getLogger(CustomDispatcherServlet.class);
	
	private boolean checkAccessTime(HttpServletRequest request) {
		HttpSession session = request.getSession();
		JSONObject json = null;
		long ms = System.currentTimeMillis();
		int cnt = 0;
		if( session.getAttribute("accessTime") == null ) {
			json = new JSONObject();
		} else {
			json = (JSONObject) session.getAttribute("accessTime");
			if( (ms - json.getLong("ms") ) < 1000 ) {
				cnt = json.getInt("cnt")+1;
			}
		}
		json.put("ms", ms);
		json.put("cnt", cnt);
		session.setAttribute("accessTime", json);
		
		if( cnt == 15 ) {
			logger.warn("반복호출 발생 ip:{}, host:{}", request.getRemoteAddr(), request.getRemoteHost());
		} else if( cnt > 15 ) {
			if( cnt == 100 ) {
				logger.error("반복호출 발생 ip:{}, host:{}", request.getRemoteAddr(), request.getRemoteHost());
			}
			return false;
		}
		return true;
	}
	
	@Override
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if( checkAccessTime(request) ) {
			super.doDispatch(request, response);			
		}
	}
}
