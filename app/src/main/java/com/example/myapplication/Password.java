package com.example.myapplication;

import java.lang.ref.PhantomReference;

public class Password {
    private String appName;
    private String appPassword;
    private String appUserName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public Password(int id, String appName, String appUserName, String appPassword) {
        this.appName = appName;
        this.appPassword = appPassword;
        this.appUserName = appUserName;
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPassword() {
        return appPassword;
    }

    public void setAppPassword(String appPassword) {
        this.appPassword = appPassword;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }
}
