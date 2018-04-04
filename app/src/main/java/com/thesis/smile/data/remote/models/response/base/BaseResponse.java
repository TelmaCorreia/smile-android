package com.thesis.smile.data.remote.models.response.base;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("exception")
    private String exception;

    @Expose
    @SerializedName("data")
    private T data;

    public BaseResponse(){}

    public String getCode() {
        return code;
    }

    public String getException() {
        return exception;
    }

    public @Nullable
    T getData(){
        return data;
    }
}

