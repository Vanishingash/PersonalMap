package com.example.personalmap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Settings extends AppCompatActivity {

    HashMap mhashmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");
    }

    public void check_click(View v){
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        builder.setMessage("确认是否注销该账号，注销后无法恢复！");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete_foot();
                BmobQuery<User> query=new BmobQuery<User>();
                query.addWhereEqualTo("name", Login.USER_NAME);
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        for(User data : list)
                        {
                            mhashmap = new HashMap<>();
                            mhashmap.put("ID",data.getObjectId());
                            String temp = (String) mhashmap.get("ID");
                            User user = new User();
                            user.setObjectId(temp);
                            user.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Toast.makeText(Settings.this, "删除成功", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Settings.this,Login.class);
                                        startActivity(intent);
                                    } else{
                                        Toast.makeText(Settings.this, "删除失败", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void return_click(View v){
        Intent intent = new Intent(Settings.this,Me.class);
        startActivity(intent);
    }

    public void help_click(View v){
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        builder.setTitle("帮助");
        builder.setMessage("开发平台：Android Studio \n开发语言：JAVA \n开发日期：2020年6月");
        builder.setPositiveButton("ok", null);
        builder.setCancelable(true);
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void modify_click(View v){
        Intent intent = new Intent(Settings.this,ModifyPwd.class);
        startActivity(intent);
    }

    public void delete_foot(){
        BmobQuery<Foot> query=new BmobQuery<Foot>();
        query.addWhereEqualTo("uname", Login.USER_NAME);
        query.findObjects(new FindListener<Foot>() {
            @Override
            public void done(List<Foot> list, BmobException e) {
                for(Foot data : list){
                    String ID = data.getObjectId();
                    Foot foot = new Foot();
                    foot.setObjectId(ID);
                    foot.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                            } else{
                            }
                        }
                    });
                }
            }
        });
    }
}
