package cn.valinaa.auction.shiro;

import cn.valinaa.auction.bean.Account;
import cn.valinaa.auction.bean.Identity;
import cn.valinaa.auction.mapper.AccountMapper;
import cn.valinaa.auction.mapper.IdentityPermissonMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-09 15:02
 */
@Service
public class MyShiroRealm extends AuthorizingRealm {
    
    @Autowired
    private IdentityPermissonMapper ipm;
    
    @Autowired
    private AccountMapper accountMapper;
    
    
    /**
     * 获取用户角色和权限
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        
        if (principalCollection == null) {
            throw new AuthenticationException("principalCollection should not be null");
        }
        Account account = (Account) SecurityUtils.getSubject().getPrincipal();
        System.out.println("用户 --> " + account.getAccount() + "获取权限中");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        
        // 获取角色集
        List<Identity> identityList = ipm.getIdentityList(account.getAccount());
        for (Identity i : identityList) {
            // 为这个账号添加身份
            simpleAuthorizationInfo.addRole(i.getIdentityName());
            
        }
        System.out.println("角色为 == " + simpleAuthorizationInfo.getRoles());
        
        // 清空授权缓存
//        super.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
        
        return simpleAuthorizationInfo;
    }
    
    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        
        // 获取前端的账号密码
        String account = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        
        // 数据库查询
        Account realAccount = accountMapper.getAccountByaccount(account);
        if (realAccount == null) {
            throw new UnknownAccountException("用户名密码错误！ 或者账号被锁定了，");
        }
        if (!realAccount.getPassword().equals(password)) {
            throw new IncorrectCredentialsException("账号或密码错误");
        }
        
        
        /**
         *  进行认证
         */
        
        return new SimpleAuthenticationInfo(realAccount, realAccount.getPassword(), getName());
    }
    
    
}
