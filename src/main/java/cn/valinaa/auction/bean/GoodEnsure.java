package cn.valinaa.auction.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-03 16:11
 */
@Schema(name = "GoodEnsure", description = "商品保障")
@Data
@NoArgsConstructor
public class GoodEnsure {

    @Schema(name = "gid", description = "商品id")
    private Integer gid;
    @Schema(name = "packMail", description = "包邮")
    private Integer packMail;
    @Schema(name = "oimei", description = "假一赔十")
    private Integer oimei;
    @Schema(name = "ensure", description = "协议保障")
    private Integer ensure;

    public GoodEnsure(GoodAuction goodAuction) {
        this.gid = goodAuction.getId();
        this.packMail = "on".equals(goodAuction.getPackMail()) ? 1 : 0;
        this.oimei = "on".equals(goodAuction.getOimei()) ? 1 : 0;
        this.ensure = "on".equals(goodAuction.getEnsure()) ? 1 : 0;
    }
}
