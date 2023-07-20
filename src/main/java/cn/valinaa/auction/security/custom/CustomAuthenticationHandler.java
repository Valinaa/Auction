package cn.valinaa.auction.security.custom;

import cn.valinaa.auction.bean.Result;
import cn.valinaa.auction.enums.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler
        , AuthenticationFailureHandler, LogoutSuccessHandler
        , SessionInformationExpiredStrategy, AccessDeniedHandler
        , AuthenticationEntryPoint {
    public static final String CONTENT_TYPE = "application/json";
    public static final String CHARACTER_ENCODING = "UTF-8";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    /**
     * 认证失败处理
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException      异常
     * @throws ServletException 异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        
        StringBuilder detailMessage =new StringBuilder(authException
                .getClass().getSimpleName() + " --- ");
        if (authException instanceof InsufficientAuthenticationException) {
            detailMessage.append("请登陆后再访问!");
        }else{
            detailMessage.append(authException.getLocalizedMessage());
        }
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(Result.failure(
                detailMessage, ResultCodeEnum.NOT_LOGIN)));
    }
    
    /**
     * 权限不足时的处理
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        StringBuilder detailMessage =new StringBuilder(accessDeniedException
                .getClass().getSimpleName() + " --- ");
        if (accessDeniedException instanceof MissingCsrfTokenException) {
            detailMessage.append("缺少CSRF TOKEN,请从表单或HEADER传入");
        } else if (accessDeniedException instanceof InvalidCsrfTokenException) {
            detailMessage.append("无效的CSRF TOKEN");
        } else {
            detailMessage.append(accessDeniedException.getLocalizedMessage());
        }
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(
                Result.failure(detailMessage, ResultCodeEnum.FORBIDDEN)));
    }
    
    /**
     * 认证失败时的处理
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException authException)
            throws IOException, ServletException {
        StringBuilder detailMessage =new StringBuilder(authException
                .getClass().getSimpleName() + " --- ");
        detailMessage.append(authException.getLocalizedMessage());
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(
                Result.failure(detailMessage, ResultCodeEnum.LOGIN_ERROR)));
    }
    
    /**
     * 认证成功时的处理
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpStatus.OK.value());
        // SecurityContext在设置Authentication的时候并不会自动写入Session，读的时候却会根据Session判断，所以需要手动写入一次，否则下一次刷新时SecurityContext是新创建的实例。
        request.getSession().setAttribute(HttpSessionSecurityContextRepository
                .SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        response.getWriter().write(
                OBJECT_MAPPER.writeValueAsString(
                        Result.build(authentication, HttpStatus.OK.value(),
                                "登陆成功!")
                ));
        //清理使用过的验证码
//        request.getSession().removeAttribute(VERIFY_CODE_KEY);
    }
    
    /**
     * 会话过期处理
     * @throws IOException      异常
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event)
            throws IOException {
        String message = "该账号已从其他设备登陆,如果不是您自己的操作请及时修改密码";
        final HttpServletResponse response = event.getResponse();
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(
                Result.build(event.getSessionInformation(),
                        HttpStatus.UNAUTHORIZED.value(), message)
        ));
    }
    
    /**
     * 登出成功处理
     * @param request        请求
     * @param response       响应
     * @param authentication 认证信息
     * @throws IOException      异常
     * @throws ServletException 异常
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(
                Result.build(authentication, HttpStatus.OK.value(), "登出成功!")
        ));
    }
}

