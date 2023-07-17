package cn.valinaa.auction.security.custom;

import cn.valinaa.manage.entity.Result;
import cn.valinaa.manage.enums.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Valinaa
 * @Date : 2022/7/23
 * @Description : 自定义未登录返回内容
 */

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(Result.failure(ResultCodeEnum.USER_NOT_LOGGED_IN, "Come From CustomAuthenticationEntryPoint")));
        out.flush();
        out.close();
    }
}
