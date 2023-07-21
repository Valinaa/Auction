package cn.valinaa.auction.enums;

import lombok.Getter;
/**
 * 统一返回结果状态信息类
 *
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),

    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),

    NO_SUCH_RECORD(40001,"没有这个记录"),
    DUPLICATE_USERNAME(40002,"用户名已存在！"),
    NOT_LOGIN(208, "未登陆"),
    LOGIN_ERROR(207, "登陆失败"),
    FORBIDDEN(403, "没有权限"),

    GET_FAILED(2001, "信息获取失败"),
    ORDER_PRICE_ERROR(210, "订单商品价格变化"),
    ORDER_STOCK_FALL(204, "订单库存锁定失败"),
    CREATE_ORDER_FAIL(210, "创建订单失败"),

    URL_ENCODE_ERROR( 216, "URL编码失败"),
    ILLEGAL_CALLBACK_REQUEST_ERROR( 217, "非法回调请求"),
    FETCH_ACCESS_TOKEN_FAILED( 218, "获取accessToken失败"),
    FETCH_USERINFO_ERROR( 219, "获取用户信息失败"),
    ;

    private final Integer code;

    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
