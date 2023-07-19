package cn.valinaa.auction.service.impl;

import cn.valinaa.auction.bean.User;
import cn.valinaa.auction.enums.AuthorityEnum;
import cn.valinaa.auction.mapper.UserMapper;
import cn.valinaa.auction.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements IUserService, UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        if (user == null) {
            throw new UsernameNotFoundException("不存在username为" + username + "的用户!");
        }
        if(user.getAuthorities().isEmpty()){
            switch (user.getRole()){
                case ADMIN -> user.setAuthorities(AuthorityEnum.ROLE_ADMIN);
                case VIP_USER -> user.setAuthorities(AuthorityEnum.ROLE_VIP_USER);
                case USER -> user.setAuthorities(AuthorityEnum.ROLE_USER);
                case ANONYMOUS -> user.setAuthorities(AuthorityEnum.ROLE_ANONYMOUS);
            }
        }
        return user;
    }
}
