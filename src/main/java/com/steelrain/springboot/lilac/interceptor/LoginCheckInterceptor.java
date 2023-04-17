package com.steelrain.springboot.lilac.interceptor;

import com.steelrain.springboot.lilac.common.SESSION_KEY;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        if(session.getAttribute(SESSION_KEY.MEMBER_ID) == null){
            response.sendRedirect("/member/login?redirectURL="+request.getRequestURI());
            return false;
        }
        return true;
    }
}
