package cn.valinaa.auction.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-09 16:15
 */
@Schema(name = "Identity", description = "身份")
@Data
@NoArgsConstructor
public class Identity {
    @Schema(name = "id", description = "身份id")
    private Integer id;
    @Schema(name = "identityName", description = "身份名称")
    private String identityName;
    @Schema(name = "dec", description = "身份描述")
    private String dec;
}
