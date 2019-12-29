package com.shinemo.mpush.bean;

public class User {

    private int id;

    private String userid;

    private String img;

    private String nickname;

    private int app_id;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id= id;
    }

    public String getUserid(){
        return userid;
    }

    public void setUserid(String userid){
        this.userid = userid;
    }

    public String getImg(){
        return img;
    }

    public void setImg(String img){
        this.img = img;
    }

    public String getNickname(){
        return this.nickname;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public int getApp_id(){
        return this.app_id;
    }

    public void setApp_id(int app_id){
        this.app_id = app_id;
    }

    public String toString(){
        String res;
        res = "id:      "+this.getId()
                + "\nuserid:"+this.getUserid()
                + "\nnickname:"+this.getNickname()
                + "\nimg:"+this.getImg()
                + ":\napp_id:"+this.getApp_id();
        return  res;
    }
}
