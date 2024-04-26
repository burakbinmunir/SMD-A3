package com.example.myapplication;

public class Password {
    private String appName;
    private String appPassword;
    private String appUserName;

    public Password(String appName, String appUserName, String appPassword) {
        this.appName = appName;
        this.appPassword = appPassword;
        this.appUserName = appUserName;
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
