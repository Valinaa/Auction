package cn.valinaa.auction.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
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

@Schema(description = "账号实体类",name = "Account")
@Data
@NoArgsConstructor
public class Account {
    @Schema(description = "账号id",name = "id")
    private Integer id;
    @Schema(description = "账号",name = "account")
    private String account;
    @Schema(description = "姓名",name = "name")
    private String name;
    @Schema(description = "密码",name = "password")
    private String password;
    @Schema(description = "身份",name = "identity")
    private Integer identity;
    @Schema(description = "状态",name = "status")
    private Integer status;
    @Schema(description = "注册时间",name = "regTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regTime;
}
