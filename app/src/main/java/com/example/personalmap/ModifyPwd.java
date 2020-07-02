package com.example.personalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyPwd extends AppCompatActivity {

    private EditText pwd;
    private EditText pwd1;
    private EditText original;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");
        pwd = (EditText)findViewById(R.id.editText201);
        pwd1 = (EditText)findViewById(R.id.editText301);
        original = (EditText)findViewById(R.id.editText2201);
    }

    public void mts_click(View v){
        Intent intent = new Intent(ModifyPwd.this,Settings.class);
        startActivity(intent);
    }

    public void mpwd_click(View v){
        String password = pwd.getText().toString();
        String repwd = pwd1.getText().toString();
        String original1 = original.getText().toString();

        if(password.equals("")||repwd.equals("")||original1.equals("")){
            Toast.makeText(this, "密码或确认密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        BmobQuery<User> query1 = new BmobQuery<User>();
        query1.addWhereEqualTo("name", Login.USER_NAME);
        query1.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                for(User data : list){
                    String temp = data.getPwd();
                    if(original1.equals(temp)){
                        if(password.equals(repwd)) {
                            BmobQuery<User> query = new BmobQuery<User>();
                            query.addWhereEqualTo("name", Login.USER_NAME);
                            query.findObjects(new FindListener<User>() {
                                @Override
                                public void done(List<User> list, BmobException e) {
                                    for (User data : list) {
                                        String ID = data.getObjectId();
                                        User user = new User();
                                        user.setObjectId(ID);
                                        user.setPwd(password);
                                        user.update(ID, new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                Toast.makeText(ModifyPwd.this, "修改成功", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(ModifyPwd.this, Settings.class);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(ModifyPwd.this,"两次密码不一致",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(ModifyPwd.this, "原始密码错误", Toast.LENGTH_LONG).show();
                        return ;
                    }
                }
            }
        });


    }
}
