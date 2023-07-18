package cn.valinaa.auction.security;

import cn.valinaa.auction.bean.Account;
import cn.valinaa.auction.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Valinaa
 * @Date : 2022/7/21
 * @Description : 自定义生产用户实体
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final AccountMapper accountMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountMapper.getAccountByaccount(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名:" + username + "不存在!");
        }
        return user;
        //new org.springframework.security.core.userdetails.User(username, new BCryptPasswordEncoder().encode(user.getPassword()), authorities)
    }
}
