package cn.valinaa.auction.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-05 13:35
 */
@Schema(description = "拍卖记录",name = "AuctionRecord")
@Data
@NoArgsConstructor
public class AuctionRecord {

    @Schema(description = "拍卖记录id",name = "id")
    private Integer id;
    @Schema(description = "商品名称",name = "goodName")
    private String goodName;
    @Schema(description = "起拍价",name = "startPrice")
    private Double startPrice;
    @Schema(description = "当前价格",name = "nowPrice")
    private Double nowPrice;
    @Schema(description = "我的加价",name = "myPlus")
    private Double myPlus;
    @Schema(description = "账号id",name = "accountId")
    private Integer accountId;
    @Schema(description = "账号名称",name = "accountName")
    private String accountName;
    @Schema(description = "状态",name = "status")
    private Integer status;
    @Schema(description = "结束时间",name = "endTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    @Schema(description = "开始时间",name = "startTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @Schema(description = "卖家id",name = "salerId")
    private Integer salerId;
    @Schema(description = "商品id",name = "gid")
    private Integer gid;
    @Schema(description = "创建时间",name = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
