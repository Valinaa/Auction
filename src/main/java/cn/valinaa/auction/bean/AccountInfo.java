package cn.valinaa.auction.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-02 08:17
 */
@Schema(description = "账号信息",name = "AccountInfo")
@Data
@NoArgsConstructor
public class AccountInfo {

    @Schema(description = "账号id",name = "aid")
    private Integer aid;
    @Schema(description = "账号",name = "account")
    private String account;
    @Schema(description = "姓名",name = "name")
    private String name;
    @Schema(description = "身份",name = "identity")
    private Integer identity;
    @Schema(description = "性别",name = "sex")
    private String sex;
    @Schema(description = "地址",name = "location")
    private String location;
    @Schema(description = "电话",name = "phone")
    private String phone;
    @Schema(description = "邮箱",name = "email")
    private String email;
    @Schema(description = "个性签名",name = "personalSign")
    private String personalSign;
    @Schema(description = "爱好",name = "love")
    private String love;
}
