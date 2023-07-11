package cn.valinaa.auction.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import cn.valinaa.auction.bean.Account;
import cn.valinaa.auction.bean.AccountInfo;
import cn.valinaa.auction.mapper.AccountMapper;
import cn.valinaa.auction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author Valinaa
ServiceImpl
 * @Description:
 * @Date: 2023-07-12 18:44
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Object login(Account account) {
        JSONObject res = new JSONObject();
        String password = account.getPassword();
        Account byaccount = accountMapper.getAccountByaccount(account.getAccount());
        if(byaccount != null && !StringUtils.isEmpty(password) && account.getPassword().equals(password)){
            res.put("msg", "ok");
            byaccount.setPassword("null");
            res.put("account", byaccount);
            return res;
        }
        res.put("msg", "f");
        return res;
    }

    @Override
    public Object register(Account account) {
        JSONObject res = new JSONObject();
        Integer b = accountMapper.getAccountIdByAccount(account.getAccount());
        if(StringUtils.isEmpty(account.getAccount()) || StringUtils.isEmpty(account.getPassword()) || b != null){
            res.put("msg","f");
            return res;
        }
        account.setRegTime(LocalDateTime.now());
        accountMapper.registerAccount(account);
        accountMapper.registerAccountInfo(account.getId());
        res.put("msg", "ok");
        return res;
    }

    @Override
    public Object getAccountInfo(Account account) {
        JSONObject res = new JSONObject();
        res.put("msg", "f");
        if(StringUtils.isEmpty(account.getAccount())){
            return res;
        }
        AccountInfo info = accountMapper.getAccountInfoByAccount(account);
        if(info != null){
            res.put("msg", "ok");
            res.put("AccountInfo", info);
            return res;
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Object updateAccountInfo(AccountInfo accountInfo) {
        JSONObject res = new JSONObject();
        res.put("msg", "ok");
        int f1 = accountMapper.updateAccountInfo(accountInfo);
        int f2 = 1;
        if(!"null".equals(accountInfo.getName())){
            f2 = accountMapper.updateAccountName(accountInfo);
        }
        if(f1 ==  0|| f2 == 0){
            res.put("msg", "f");
        }
        return res;
    }

    @Override
    public Object updateAccountPsw(String map) {
        JSONObject res = new JSONObject();
        res.put("msg", "ok");
        JSONObject obj = JSONObject.parseObject(map);
        String password = (String)obj.get("password");
        String oldPassword = (String)obj.get("oldPassword");
        Integer id = (Integer) obj.get("id");
        if(StringUtils.isEmpty(map) || StringUtils.isEmpty(password) || id == null || id <= 0){
            res.put("msg", "f");
            return res;
        }
        int f = accountMapper.updateAccountPsw(oldPassword, password, id);
        if(f == 0){
            res.put("msg", "f");
        }
        return res;
    }
}
