package cn.valinaa.auction.controller;

import com.alibaba.fastjson.JSONObject;
import cn.valinaa.auction.bean.Account;
import cn.valinaa.auction.bean.AccountInfo;
import cn.valinaa.auction.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Valinaa
Controller
 * @Description:
 * @Date: 2023-07-12 18:42
 */
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody Account account){

        JSONObject res = new JSONObject();

        // 登录认证
        UsernamePasswordToken token = new UsernamePasswordToken(account.getAccount(), account.getPassword());
        // 获取 subject 对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            res.put("msg", "ok");
            Account realAccount = (Account)subject.getPrincipal();
            realAccount.setPassword("null");
            res.put("account", realAccount);
        }catch (Exception e){
            res.put("msg", "f");
            res.put("error", e.getMessage());
        }
        return res;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@RequestBody Account account){
        Object res = accountService.register(account);
        return res;
    }


    @RequestMapping(value = "getAccountInfo", method = RequestMethod.POST)
    public Object getAccountInfo(@RequestBody Account account){
        Object res =  accountService.getAccountInfo(account);
        return res;
    }


    @RequestMapping(value = "updateAccountInfo", method = RequestMethod.POST)
    public Object updateAccountInfo(@RequestBody AccountInfo accountInfo){
        Object res =  accountService.updateAccountInfo(accountInfo);
        return res;
    }


    @RequestMapping(value = "updateAccountPsw", method = RequestMethod.POST)
    public Object updateAccountPsw(@RequestBody String map){
        Object res =  accountService.updateAccountPsw(map);
        return res;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object updateAccountPsw(){
        JSONObject res= new JSONObject();
        res.put("msg", "ok");
        Subject lvSubject=SecurityUtils.getSubject();
        lvSubject.logout();
        return res;
    }


}
