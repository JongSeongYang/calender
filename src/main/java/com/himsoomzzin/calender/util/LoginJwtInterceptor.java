package com.himsoomzzin.calender.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginJwtInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginJwtInterceptor.class);

    // 요청을 컨트롤러에 보내기 전 작업
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        logger.info("LoginJwtInterceptor - {}", "호출완료");
        // token 추출
        String authorizationHeader = request.getHeader("JWT");
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        // token 유효성 검사
        if (authorizationHeader != null && jwtTokenProvider.checkExpToken(authorizationHeader)) {
            logger.info("로그인");
            return true;
        } else {
            ObjectMapper mapper = new ObjectMapper();
            /*
            수정
             */
//            val message = Auth.AuthFailResponse.builder()
//                    .message("Authorized Failed.")
//                    .build();
//            response.setContentType("application/json");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write(mapper.writeValueAsString(message));
            logger.info("로그인 x");
            return false;
        }
    }
}
