package cn.valinaa.auction.bean;

import cn.valinaa.auction.enums.AuthorityEnum;
import cn.valinaa.auction.enums.RoleEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-12 18:41
 */

@Schema(description = "Security实体类",name = "User")
@Data
@NoArgsConstructor
@TableName("user")
public class User implements UserDetails, Serializable {

        @TableId(value = "id",type= IdType.AUTO)
        @Schema(description = "账号id",name = "id")
        private Integer id;

        @TableField("username")
        @Schema(description = "用户名",name = "username")
        private String username;

        @TableField("name")
        @Schema(description = "姓名",name = "name")
        private String name;

        @TableField("password")
        @Schema(description = "密码",name = "password")
        private String password;

        @TableField("role")
        @Schema(description = "角色",name = "role")
        private RoleEnum role;

        @TableField("age")
        @Schema(description = "年龄",name = "age")
        private Integer age;

        @TableField("location")
        @Schema(description = "地址",name = "location")
        private String location;

        @TableField("phone")
        @Schema(description = "电话",name = "phone")
        private String phone;

        @TableField("email")
        @Schema(description = "邮箱",name = "email")
        private String email;

        @TableField("personal_sign")
        @Schema(description = "个性签名",name = "personalSign")
        private String personalSign;

        @TableField("hobbies")
        @Schema(description = "爱好",name = "hobbies")
        private String hobbies;

        @TableField("tag")
        @Schema(description = "个人标签",name = "tag")
        private String tag;

        @TableField("authorities")
        @Schema(description = "权限列表",name = "authorities")
        private List<SimpleGrantedAuthority> authorities;

        @Version
        @TableField(value = "version",fill = FieldFill.INSERT)
        @Schema(description = "版本号",name = "version")
        private Integer version;

        @TableField(value = "is_deleted",fill = FieldFill.INSERT)
        @TableLogic(value = "0",delval = "1")
        @Schema(description = "是否删除",name = "isDeleted")
        private Integer isDeleted;

        @Schema(description = "用户创建时间",name = "createTime")
        @TableField(fill = FieldFill.INSERT)
        private LocalDateTime createTime;

        @Schema(description = "用户更新时间",name = "updateTime")
        @TableField(fill = FieldFill.INSERT_UPDATE)
        private LocalDateTime updateTime;

        public void setAuthorities(AuthorityEnum authorityEnum){
            authorityEnum.getAuthorities().forEach(
                    i-> this.authorities.add(new SimpleGrantedAuthority(i))
            );
        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.authorities;
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public String getUsername() {
            return this.username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return this.isDeleted==0;
        }

        //默认使用恒等去判断是否是同一个对象，因为登录的同一个用户，如果再次登录就会封装
        //一个新的对象，这样会导致登录的用户永远不会相等，所以需要重写equals方法
        @Override
        public boolean equals(Object obj) {
            //会话并发生效，使用username判断是否是同一个用户
            if (obj instanceof User){
                //字符串的equals方法是已经重写过的
                return ((User) obj).getUsername().equals(this.username);
            }else {
                return false;
            }
        }
        
        User(String username,String password){
            this.username=username;
            this.password=password;
        }
}
