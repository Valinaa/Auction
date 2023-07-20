package cn.valinaa.auction.controller;

import cn.valinaa.auction.bean.Result;
import cn.valinaa.auction.bean.User;
import cn.valinaa.auction.enums.ResultCodeEnum;
import cn.valinaa.auction.service.IUserService;
import cn.valinaa.auction.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "AuthController", description = "权限相关接口")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final JwtUtil jwtUtil;
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    
    @PostMapping("/login")
    @Operation(summary = "login", description = "登录")
    public Result<Map<String,String>> login(@RequestBody User user){
        UsernamePasswordAuthenticationToken authentication=new
                UsernamePasswordAuthenticationToken(
                        user.getUsername(),user.getPassword()
        );
        Authentication authenticate = null;
        try{
            authenticate = authenticationManager.authenticate(authentication);
            log.info("authenticate:{}",authenticate);
        }catch (Exception e){
            log.error("login error:{}",e.getMessage());
        }
        if (authenticate == null) {
            log.error("login error: authenticate is null");
            return Result.failure(ResultCodeEnum.LOGIN_ERROR);
        }
        UserDetails userDetails =userService.loadUserByUsername(user.getUsername());
        String token = jwtUtil.createToken(userDetails);
        return Result.success(new HashMap<>() {{
            put("token", token);
        }});
    }
}
