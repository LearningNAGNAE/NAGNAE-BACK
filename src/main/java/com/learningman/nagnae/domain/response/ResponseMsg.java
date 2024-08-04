package com.learningman.nagnae.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMsg {

    private String code;
    private String message;

}
