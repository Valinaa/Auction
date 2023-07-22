package cn.valinaa.auction.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-09 18:16
 */
public class RoleFilter extends RolesAuthorizationFilter {
    
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        
        final Subject subject = getSubject(request, response);
        final String[] rolesArray = (String[]) mappedValue;
        
        if (rolesArray == null || rolesArray.length == 0) {
            // 无指定角色时，无需检查，允许访问
            return true;
        }
        
        for (String roleName : rolesArray) {
            if (subject.hasRole(roleName)) {
                return true;
            }
        }
        
        return false;
    }
    
    
}
