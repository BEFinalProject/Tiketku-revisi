package com.example.tiketku_finalproject.Response;

import org.springframework.stereotype.Component;

@Component
public class RegisterResponse {
    public <T> CommonRegisterResponse<T> succsesResponse(String msg){
        CommonRegisterResponse commonResponse = new CommonRegisterResponse<>();
        commonResponse.setStatus("200");
        commonResponse.setMsg(msg);

        return commonResponse;
    }

    public <T> CommonRegisterResponse<T> failedResponse(String msg){
        CommonRegisterResponse commonResponse = new CommonRegisterResponse<>();
        commonResponse.setStatus("500");
        commonResponse.setMsg(msg);

        return commonResponse;
    }
}
