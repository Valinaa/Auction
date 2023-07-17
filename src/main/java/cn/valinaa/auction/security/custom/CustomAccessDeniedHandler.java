package cn.valinaa.auction.security.custom;

import cn.valinaa.manage.entity.Result;
import cn.valinaa.manage.enums.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Valinaa
 * @Date : 2022/7/22
 * @Description : 自定义403响应内容
 */

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(Result.failure(
                ResultCodeEnum.USER_ACCOUNT_FORBIDDEN,
                "Come From CustomAccessDeniedHandler")
        ));
        out.flush();
        out.close();
    }
}
