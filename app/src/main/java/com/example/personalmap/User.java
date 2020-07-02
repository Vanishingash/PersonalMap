package com.example.personalmap;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;



public class User extends BmobObject{

    private String pic;//头像
    private String sex;
    private String udes;
    private String email;
    private String name;
    private String pwd;


    public void setName(String Name){
        name=Name;
    }

    public String getName(){
        return name;
    }

    public void setPwd(String Pwd){
        pwd=Pwd;
    }

    public String getPwd(){
        return pwd;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUdes() {
        return udes;
    }

    public void setUdes(String udes) {
        this.udes = udes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(){

    }

}
