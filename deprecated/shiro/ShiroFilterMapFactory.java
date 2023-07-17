package deprecated.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-09 15:09
 */
public class ShiroFilterMapFactory {

    public static Map<String, String> shiroFilterMap() {
//		设置路径映射，注意这里要用LinkedHashMap 保证有序
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/SaleGoods.html", "roles[admin,saler,vipSaler]");

        //对所有用户认证
//        filterChainDefinitionMap.put("/static/**", "anon");
//        filterChainDefinitionMap.put("/login.html", "anon");
//        filterChainDefinitionMap.put("/", "anon");
//        filterChainDefinitionMap.put("/index.html", "anon");
//        filterChainDefinitionMap.put("/logout", "logout");

        //对所有页面进行认证
//        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }


}
