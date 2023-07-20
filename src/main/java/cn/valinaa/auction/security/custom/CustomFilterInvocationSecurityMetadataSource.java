package cn.valinaa.auction.security.custom;

import cn.valinaa.auction.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * @author Valinaa
 * @Date : 2022/7/22
 * @Description : 获取访问路径所需权限
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    
    private final IUserService userService;
    private static final String AUTHENTICATION = "/login";
    
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求地址
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        log.info("请求地址" + requestUrl);
        // 判断是否为登录请求
        if (AUTHENTICATION.equals(requestUrl) ) {
            log.info("登录请求，metadataSource放行！");
            return null;
        } else {
            log.info("metadataSource 获取访问路径所需权限");
        }
        // 没有匹配上的资源，都是登录访问
        // return SecurityConfig.createList("ROLE_LOGIN");
        return null;
    }
    
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
