package cn.valinaa.auction.service;

import cn.valinaa.auction.bean.Account;
import cn.valinaa.auction.bean.AccountInfo;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-12 18:43
 */
 public interface AccountService {

    Object login(Account account);

    Object register(Account account);

     Object getAccountInfo(Account account);
     Object getAccountInfoByAid(Integer aid);
    
     Object getAccountByAccountId(Integer accountId);
     Object updateAccountInfo(AccountInfo accountInfo);

     Object updateAccountPsw(String map);

}
