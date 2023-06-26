package com.example.tiketku_finalproject.Response;

import lombok.Getter;
import lombok.Setter;

public class CommonRegisterResponse<T>{
    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private String msg;

    public CommonRegisterResponse() {
    }

    public CommonRegisterResponse(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
