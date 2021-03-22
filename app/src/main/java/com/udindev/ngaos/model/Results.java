package com.udindev.ngaos.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {
    @SerializedName("datetime")
    private List<Datetime> datetime = null;

    public List<Datetime> getDatetime() {
        return datetime;
    }

    public void setDatetime(List<Datetime> datetime) {
        this.datetime = datetime;
    }
}
