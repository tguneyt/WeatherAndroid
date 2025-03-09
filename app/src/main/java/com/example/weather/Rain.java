package com.example.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rain implements Serializable {

    @SerializedName("1h")
    @Expose
    private Double h1;

    public Double getH1() {
        return h1;
    }

    public void setH1(Double h1) {
        this.h1 = h1;
    }
}
