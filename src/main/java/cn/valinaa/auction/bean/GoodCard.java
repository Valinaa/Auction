package cn.valinaa.auction.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-04 17:37
 */
@Schema(name = "GoodCard", description = "商品卡片")
@Data
@NoArgsConstructor
public class GoodCard {

    @Schema(name = "id", description = "商品id")
    private Integer id;
    @Schema(name = "goodName", description = "商品名称")
    private String goodName;
    @Schema(name = "pic", description = "商品图片")
    private String pic;
    @Schema(name = "salerName", description = "卖家名称")
    private String salerName;
    @Schema(name = "nowPrice", description = "当前价格")
    private Double nowPrice;
    @Schema(name = "aucNum", description = "竞拍人数")
    private Integer aucNum;
}
