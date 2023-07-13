package cn.ecust.security;

import cn.ecust.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Valinaa
 * @Date 2022/7/2
 * @Description 提供自定义User对象
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        cn.ecust.entity.User user = userMapper.selectUserByAdmin(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名:" + username + "不存在!");
        }
        List<String> roles = userMapper.selectRoleByAdmin(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new User(user.getAdmin(), new BCryptPasswordEncoder().encode(user.getPassword()), authorities);
    }
}
