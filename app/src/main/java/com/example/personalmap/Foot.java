package com.example.personalmap;

import cn.bmob.v3.BmobObject;

import cn.bmob.v3.datatype.BmobFile;

public class Foot extends BmobObject{
    private String city;
    private String scenery;
    private String uname;
    private String des;
    //private BmobFile pics;
    private String pics;
    private String date;


    Foot(){

    }

    public Foot(String A,String B,String C){
        scenery = A;
        des = B;
        date = C;
    }
    public String getPics(){
        return pics;
    }

    public void setPics(String Pics){
        pics = Pics;
    }

    public void setCity(String City){
        city = City;
    }

    public String getCity(){
        return  city;
    }

    public void setDes(String Des){
        des = Des;
    }

    public String getDes(){
        return des;
    }

    public void setScenery(String Scenery){
        scenery = Scenery;
    }

    public String getScenery(){
        return scenery;
    }

    public void setUname(String Uname){
        uname  = Uname;
    }

    public String getUname(){
        return uname;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String Date){
        date = Date;
    }
}
