package cn.valinaa.auction.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-03 09:07
 */
@Schema(description = "商品拍卖",name = "GoodAuction")
@Data
@NoArgsConstructor
public class GoodAuction {

    @Schema(description = "商品id",name = "id")
    private Integer id;
    @Schema(description = "商品名称",name = "goodName")
    private String goodName;
    @Schema(description = "商品类型",name = "goodType")
    private Integer goodType;
    @Schema(description = "商品起拍价",name = "startPrice")
    private Double startPrice;
    @Schema(description = "商品加价",name = "pricePlus")
    private Double pricePlus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "商品开始时间",name = "startTime")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "商品结束时间",name = "endTime")
    private LocalDateTime endTime;
    @Schema(description = "拍卖人",name = "accountId")
    private Integer accountId;
    @Schema(description = "商品描述",name = "goodsDec")
    private String goodsDec;
    @Schema(description = "商品图片",name = "pic")
    private String pic;
    @Schema(description = "商品状态",name = "status")
    private Integer status;
    @Schema(description = "卖家名称",name = "salerName")
    private String salerName;
    @Schema(description = "商品当前价格",name = "nowPrice")
    private Double nowPrice;
    @Schema(name = "packMail", description = "包邮")
    private String packMail;
    @Schema(name = "oimei", description = "假一赔十")
    private String oimei;
    @Schema(description = "ensure",name = "协议保障")
    private String ensure;
    @Override
    public String toString() {
        return "GoodAuction{" +
                "id=" + id +
                ", goodName='" + goodName + '\'' +
                ", goodType='" + goodType + '\'' +
                ", startPrice=" + startPrice +
                ", pricePlus=" + pricePlus +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", accountId=" + accountId +
                ", goodsDec='" + goodsDec + '\'' +
                ", pic='" + pic + '\'' +
                ", status=" + status +
                ", salerName='" + salerName + '\'' +
                ", nowPrice=" + nowPrice +
                ", packMail='" + packMail + '\'' +
                ", oimei='" + oimei + '\'' +
                ", ensure='" + ensure + '\'' +
                '}';
    }
}
