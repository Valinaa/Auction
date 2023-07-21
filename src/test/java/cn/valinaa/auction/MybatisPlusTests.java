package cn.valinaa.auction;

import cn.valinaa.auction.bean.User;
import cn.valinaa.auction.mapper.UserMapper;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor
public class MybatisPlusTests {
    
    private final UserMapper userMapper;
    
    @Test
    public void registerTest(){
        User user=new User("usernameo","passwordo","o");
        
    }
}
