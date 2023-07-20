package cn.valinaa.auction.mapper;

import cn.valinaa.auction.bean.Identity;
import cn.valinaa.auction.bean.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IdentityPermissonMapper {


    /**
     *  也许一个用户多个角色
     * @return
     */
    @Select("SELECT i.* from account a " +
            "LEFT JOIN identity i on i.id = a.identity " +
            "where account = #{account}")
    List<Identity> getIdentityList(String account);


    @Select("SELECT p.* from account a " +
            "left join identity_permission ip on a.identity = ip.identity_id " +
            "LEFT JOIN permission p on p.id = ip.permission_id " +
            "WHERE account = #{account}")
    List<Permission> getNotPermissionList(String account);


    @Select("SELECT p.* from identity_permission ip " +
            "LEFT JOIN permission p on ip.permission_id = p.id " +
            "where identity_id = #{iid}")
    List<Permission> getPermissionList(Integer iid);


}
