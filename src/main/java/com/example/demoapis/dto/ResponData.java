package com.example.demoapis.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponData<T> {
    private boolean status;
    private List<String> message = new ArrayList<>();
    private T payload;

    //getter setter
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
