package com.learningman.nagnae.domain.response;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class responseMsg {

    private final String result;
    private final String message;
    private final Object data;

    public static commonMsg success(Object data) {
        return new commonMsg("success", null, data);
    }

    public static commonMsg fail(String message) {
        return new commonMsg("fail", message, null);
    }

}