package cn.valinaa.auction.security;

import cn.valinaa.manage.dao.UserMapper;
import cn.valinaa.manage.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new UsernameNotFoundException("用户名:" + username + "不存在!");
        }
        return user;
        //new org.springframework.security.core.userdetails.User(username, new BCryptPasswordEncoder().encode(user.getPassword()), authorities)
    }
}
