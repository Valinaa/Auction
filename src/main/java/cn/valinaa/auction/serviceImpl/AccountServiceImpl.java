package cn.valinaa.auction.serviceImpl;

import cn.valinaa.auction.bean.Account;
import cn.valinaa.auction.bean.AccountInfo;
import cn.valinaa.auction.bean.Result;
import cn.valinaa.auction.enums.ResultCodeEnum;
import cn.valinaa.auction.mapper.AccountMapper;
import cn.valinaa.auction.service.AccountService;
import com.alibaba.fastjson2.JSONObject;
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
        Integer b = accountMapper.getAccountIdByAccount(account.getAccount());
        if(!StringUtils.hasLength(account.getAccount()) || !StringUtils.hasLength(account.getPassword()) || b != null){
            return Result.failure(ResultCodeEnum.REPEAT_SUBMIT);
        }
        account.setRegTime(LocalDateTime.now());
        accountMapper.registerAccount(account);
        accountMapper.registerAccountInfo(account.getId());
        return Result.success();
    }

    @Override
    public Object getAccountInfo(Account account) {
        if(!StringUtils.hasLength(account.getAccount())){
            return Result.failure(ResultCodeEnum.NO_SUCH_RECORD);
        }
        AccountInfo info = accountMapper.getAccountInfoByAccount(account);
        if(info != null){
            return Result.success(info);
        }
        return Result.failure(ResultCodeEnum.DATA_ERROR);
    }
    
    @Override
    public Object getAccountByAccountId(Integer accountId){
        Account a;
        try {
            a = accountMapper.getAccountByAccountId(accountId);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.FAIL);
        }
        return Result.success(a);
    }
    @Override
    public Object getAccountInfoByAid(Integer aid){
        try{
            return Result.success(accountMapper.getAccountInfoByAid(aid));
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.DATA_ERROR);
        }
    }
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Object updateAccountInfo(AccountInfo accountInfo) {
        int f1 = accountMapper.updateAccountInfo(accountInfo);
        int f2 = 1;
        if(!"null".equals(accountInfo.getName())){
            f2 = accountMapper.updateAccountName(accountInfo);
        }
        if(f1 ==  0|| f2 == 0){
            return Result.failure(ResultCodeEnum.DATA_ERROR);
        }
        return Result.success();
    }

    @Override
    public Object updateAccountPsw(String map) {
        JSONObject obj = JSONObject.parseObject(map);
        String password = (String)obj.get("password");
        String oldPassword = (String)obj.get("oldPassword");
        Integer id = (Integer) obj.get("id");
        if(StringUtils.isEmpty(map) || StringUtils.isEmpty(password) || id == null || id <= 0){
            return Result.failure(ResultCodeEnum.NO_SUCH_RECORD);
        }
        int f = accountMapper.updateAccountPsw(oldPassword, password, id);
        if(f == 0){
           return Result.failure(ResultCodeEnum.FAIL);
        }
        return Result.success();
    }
}
