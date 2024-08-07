package com.learningman.nagnae.domain.response;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseMsg {

    private final String code;
    private final String message;
    private final Object data;

    public static ResponseMsg success(Object data) {
        return new ResponseMsg("200", "success", data);
    }

    public static ResponseMsg fail(String message) {
        return new ResponseMsg("500", message, null);
    }

}