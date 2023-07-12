package cn.valinaa.auction.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-04 12:12
 */
@Data
@NoArgsConstructor
@Schema(description = "卖家信息", name = "SalerInfo")
public class SalerInfo {

    @Schema(description = "卖家信息id", name = "id")
    private Integer id;
    @Schema(description = "卖家姓名", name = "salerName")
    private String salerName;
    @Schema(description = "商家名称", name = "busineName")
    private String busineName;
    @Schema(description = "商家地址", name = "busineAddress")
    private String busineAddress;
    @Schema(description = "商家电话", name = "businePhone")
    private String salerEmail;
    @Schema(description = "商家联系人", name = "busineContact")
    private String busineContact;
    @Schema(description = "商家账号", name = "account")
    private String account;
    @Schema(description = "申请理由", name = "applyReason")
    private String applyReason;
    @Schema(description = "申请状态", name = "status", defaultValue = "0")
    private Integer status;
    @Schema(description = "申请时间", name = "applyTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime;
}