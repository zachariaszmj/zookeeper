package com.example.zookeeperscheduler.exception;

public class BailedRequestException extends RuntimeException {

    private int code;

    public BailedRequestException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}