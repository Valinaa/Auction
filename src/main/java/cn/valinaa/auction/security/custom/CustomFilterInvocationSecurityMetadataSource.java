package cn.valinaa.auction.security.custom;

import cn.valinaa.manage.entity.RouterMenu;
import cn.valinaa.manage.enums.AuthorityEnum;
import cn.valinaa.manage.service.RouterMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @author Valinaa
 * @Date : 2022/7/22
 * @Description : 获取访问路径所需权限
 */
@RequiredArgsConstructor
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    
    private final RouterMenuService routerMenuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    private static final String AUTHENTICATION = "/loginCheck";
    
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求地址
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        System.out.println("请求地址" + requestUrl);
        // 判断是否为登录请求
        if (AUTHENTICATION.equals(requestUrl) || routerMenuService.list().isEmpty()) {
            System.out.println("metadataSource无需验证！");
            return null;
        } else {
            System.out.println("metadataSource 获取访问路径所需权限");
            List<RouterMenu> allRouter = routerMenuService.list();
            for (RouterMenu router : allRouter) {
                if (antPathMatcher.match(router.getUrl(), requestUrl) && router.getRoles().size() > 0) {
                    List<AuthorityEnum> roles = router.getRoles();
                    int size = roles.size();
                    String[] attributes = new String[size];
                    for (int i = 0; i < size; i++) {
                        attributes[i] = "ROLE_" + roles.get(i).getValue();
                    }
                    return SecurityConfig.createList(attributes);
                }
            }
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
