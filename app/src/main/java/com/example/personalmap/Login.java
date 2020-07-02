package com.example.personalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class Login extends AppCompatActivity {
    public static  String USER_NAME;

    private EditText editText;
    private EditText editText2;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");
        editText=(EditText)findViewById(R.id.editText);
        editText2=(EditText)findViewById(R.id.editText2);
        btn_login=(Button)findViewById(R.id.button3);
        BmobUser user =new BmobUser();

    }

    //登录按钮
    public void login_onclick(View v)
    {
        String username = editText.getText().toString().trim();
        String password = editText2.getText().toString().trim();
        USER_NAME = username;
        if(username.equals("")||password.equals("")){
            Toast.makeText(this, "帐号或密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        BmobQuery<User> query=new BmobQuery<User>();
        query.addWhereEqualTo("name", username);
        query.addWhereEqualTo("pwd", password);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> arg0, BmobException e) {
                // TODO Auto-generated method stub
                if(e==null){
                    String gname=arg0.get(0).getName().toString();
                    String gpassword=arg0.get(0).getPwd().toString();
                    String name=editText.getText().toString();
                    String password=editText2.getText().toString();
                    Toast.makeText(Login.this, "欢迎！"+gname+"！", Toast.LENGTH_LONG).show();
                    if(gname.equals(name)&&gpassword.equals(password))
                    {
                        Intent seccess = new Intent();
                        seccess.setClass(Login.this, MainActivity.class);
                        seccess.putExtra("username",name);
                        startActivity(seccess);
                    }

                }
                else{
                    Toast.makeText(Login.this, "帐号或密码有误", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    //注册新账户
    public void register_onclick(View v)
    {
        Intent intent = new Intent(Login.this,Signup.class);
        startActivity(intent);
    }
    //忘记密码
    public void forget_onclick(View v)
    {
        Intent intent = new Intent(Login.this,Resetpwd.class);
        startActivity(intent);
    }
}
