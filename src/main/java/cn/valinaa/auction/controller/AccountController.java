package cn.valinaa.auction.controller;

import cn.valinaa.auction.bean.Account;
import cn.valinaa.auction.bean.AccountInfo;
import cn.valinaa.auction.bean.Result;
import cn.valinaa.auction.enums.ResultCodeEnum;
import cn.valinaa.auction.service.AccountService;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Valinaa
Controller
 * @Description:
 * @Date: 2023-07-12 18:42
 */

@Tag(description = "账号相关接口", name = "AccountController")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/login")
    @Operation(summary = "登录接口", description = "login")
    public Result<Map<String,Account>> login(@RequestBody Account account){

        Map<String,Account> res = new HashMap<>();

        // 登录认证
        UsernamePasswordToken token = new UsernamePasswordToken(account.getAccount(), account.getPassword());
        // 获取 subject 对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            Account realAccount = (Account)subject.getPrincipal();
            realAccount.setPassword("null");
            res.put("account", realAccount);
            return Result.success(res);
        }catch (Exception e){
            return Result.failure(ResultCodeEnum.LOGIN_ERROR);
        }
    }

    @PostMapping(value = "/register")
    @Operation(summary = "注册接口", description = "register")
    public Object register(@RequestBody Account account){
        return accountService.register(account);
    }


    @PostMapping(value = "getAccountInfo")
    @Operation(summary = "获取账号信息", description = "getAccountInfo")
    public Object getAccountInfo(@RequestBody Account account){
        return accountService.getAccountInfo(account);
    }


    @PostMapping(value = "updateAccountInfo")
    @Operation(summary = "更新账号信息", description = "updateAccountInfo")
    public Object updateAccountInfo(@RequestBody AccountInfo accountInfo){
        return accountService.updateAccountInfo(accountInfo);
    }


    @PostMapping(value = "updateAccountPsw")
    @Operation(summary = "更新账号密码", description = "updateAccountPsw")
    public Object updateAccountPsw(@RequestBody String map){
        return accountService.updateAccountPsw(map);
    }


    @GetMapping(value = "/logout")
    @Operation(summary = "注销接口", description = "logout")
    public Object updateAccountPsw(){
        JSONObject res= new JSONObject();
        res.put("msg", "ok");
        Subject lvSubject=SecurityUtils.getSubject();
        lvSubject.logout();
        return res;
    }


}
