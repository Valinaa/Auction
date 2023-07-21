package cn.valinaa.auction.service;

import cn.valinaa.auction.bean.Result;
import cn.valinaa.auction.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IService<User>, UserDetailsService {
    Result<User> register(User user);
}
