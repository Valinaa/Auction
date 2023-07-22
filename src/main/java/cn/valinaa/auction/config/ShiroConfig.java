package cn.valinaa.auction.config;

import cn.valinaa.auction.shiro.MyShiroRealm;
import cn.valinaa.auction.shiro.RoleFilter;
import cn.valinaa.auction.shiro.ShiroFilterMapFactory;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-09 15:08
 */
@Configuration
public class ShiroConfig {
    
    /**
     * 这是shiro的大管家，相当于mybatis里的SqlSessionFactoryBean
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/NotPermission.html");
        //页面权限控制
        shiroFilterFactoryBean.setFilterChainDefinitionMap(ShiroFilterMapFactory.shiroFilterMap());
        
        // 自定义 roles filter
        // 存放自定义的filter，这里导入的是：javax.servlet.Filter
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        // 配置自定义 or角色 认证
        filtersMap.put("roles", new RoleFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }
    
    /**
     * web应用管理配置
     *
     * @param shiroRealm
     * @param manager
     * @return
     * @ param cacheManager
     */
    @Bean
//    public DefaultWebSecurityManager securityManager(Realm shiroRealm, CacheManager cacheManager, RememberMeManager manager) {
    public DefaultWebSecurityManager securityManager(Realm shiroRealm, RememberMeManager manager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setCacheManager(cacheManager);
        securityManager.setRememberMeManager(manager);//记住Cookie
        securityManager.setRealm(shiroRealm);
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
    
    /**
     * session过期控制
     *
     * @return
     * @author fuce
     * @Date 2019年11月2日 下午12:49:49
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        // 设置session过期时间3600s
        Long timeout = 60L * 1000 * 60;//毫秒级别
        defaultWebSessionManager.setGlobalSessionTimeout(timeout);
        
        return defaultWebSessionManager;
    }
    
    /**
     * 记住我的配置
     *
     * @return
     */
    @Bean
    public RememberMeManager rememberMeManager() {
        Cookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);//通过js脚本将无法读取到cookie信息
        cookie.setMaxAge(60 * 60 * 24);//cookie保存一天
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(cookie);
        return manager;
    }
    /**
     * 缓存配置
     * @return
     */
//    @Bean
//    public CacheManager cacheManager() {
//        MemoryConstrainedCacheManager cacheManager=new MemoryConstrainedCacheManager();//使用内存缓存
//        return cacheManager;
//    }
    
    /**
     * 配置realm，用于认证和授权
     *
     * @param
     * @return
     */
    @Bean
    public AuthorizingRealm shiroRealm() {
        //校验密码用到的算法
//        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return new MyShiroRealm();
    }
    
    
    /**
     * 启用shiro注解
     * 加入注解的使用，不加入这个注解不生效
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    
    
}
