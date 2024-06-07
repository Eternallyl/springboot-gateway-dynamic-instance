package cn.daijc.enums;

/**
 * 请求头信息枚举类型
 *
 * @author 84317
 * @date 2023/06/21
 */
public enum RequestHeaderEnum {

    AUTHORIZATION("Authorization"),
    USER_ID("USER_ID"),
    USER_NAME("USER_NAME"),
    DEBUG("DEBUG"),
    SOURCE("SOURCE"),
    REQUEST_ID("REQUEST_ID"),
    SERVICE_ID("SERVICE_ID"),
    SERVICE_GROUP("service-group"),
    X_SERVICE_GROUP("X-Service-Group");


    private final String header;

    RequestHeaderEnum(String header) {
        this.header = header;
    }


    public String header() {
        return header;
    }
}
