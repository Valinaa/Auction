package cn.valinaa.auction.security.custom;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author Valinaa
 * @Date : 2022/7/22
 * @Description : 判断角色是否能够访问请求路径
 */
public class CustomAccessDecisionManager implements AuthorizationManager<Object> {
    @Override
    public void verify(Supplier<Authentication> authentication, Object object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, Object object) {
        return null;
    }
}
