package com.example.project.chatvroom.models;

public class User {

    private String profile_pic;
    private String name;
    private String bms;
    private String security_level;
    private String user_id;

    public User(String profile_pic, String name, String bms, String security_level, String user_id) {
        this.profile_pic = profile_pic;
        this.name = name;
        this.bms = bms;
        this.security_level = security_level;
        this.user_id = user_id;
    }

    public User() {
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBms() {
        return bms;
    }

    public void setBms(String bms) {
        this.bms = bms;
    }

    public String getSecurity_level() {
        return security_level;
    }

    public void setSecurity_level(String security_level) {
        this.security_level = security_level;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "profile_pic='" + profile_pic + '\'' +
                ", name='" + name + '\'' +
                ", bms='" + bms + '\'' +
                ", security_level='" + security_level + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }

}
