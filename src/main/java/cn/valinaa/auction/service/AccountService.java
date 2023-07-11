package cn.valinaa.auction.service;

import cn.valinaa.auction.bean.Account;
import cn.valinaa.auction.bean.AccountInfo;

/**
 * @author Valinaa
Service
 * @Description:
 * @Date: 2023-07-12 18:43
 */
public interface AccountService {

    public Object login(Account account);

    public Object register(Account account);

    public Object getAccountInfo(Account account);


    public Object updateAccountInfo(AccountInfo accountInfo);

    public Object updateAccountPsw(String map);

}
