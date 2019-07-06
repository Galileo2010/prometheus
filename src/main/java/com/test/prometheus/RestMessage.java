package com.test.prometheus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "返回响应数据")
public class RestMessage {

    @ApiModelProperty(value = "错误信息")
    private String message;
    @ApiModelProperty(value = "状态码")
    private String code;
    @ApiModelProperty(value = "返回的数据")
    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}