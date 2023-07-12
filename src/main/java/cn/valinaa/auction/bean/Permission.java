package cn.valinaa.auction.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-09 16:17
 */
@Schema(description = "权限", name = "Permission")
@Data
@NoArgsConstructor
public class Permission {

    @Schema(description = "权限id", name = "id")
    private Integer id;
    @Schema(description = "权限url", name = "url")
    private String url;
    @Schema(description = "权限名称", name = "name")
    private String name;
}
