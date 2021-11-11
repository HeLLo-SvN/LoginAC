package com.Accenture.utils;

import lombok.Data;

@Data
public class JsonResult {

    private Object data;

    private Integer status;

    private String msg;

    public JsonResult() {
    }

    public JsonResult(Object data, Integer status, String msg) {
        this.data = data;
        this.status = status;
        this.msg = msg;
    }

    public JsonResult(Object data) {
        this.data = data;
        this.status = 200;
        this.msg = "OK";
    }

    public static JsonResult ok(Object data) {
        return new JsonResult(data);
    }

    public static JsonResult ok() {
        return new JsonResult(null);
    }

    public static JsonResult errorMsg(String msg) {
        return new JsonResult(null,500, msg);
    }
}
