package cn.valinaa.auction.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleEnum implements IEnum<Integer> {
    ADMIN(1,"管理员"),
    USER(2,"普通用户"),
    VIP_USER(3,"VIP用户"),
    ANONYMOUS(4,"匿名用户");

    @JsonValue
    private final Integer value;
    @Getter
    private final String name;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
