package com.example.personalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import cn.bmob.v3.listener.UpdateListener;

public class Resetpwd extends AppCompatActivity {
    private EditText editText;//用户名
    private EditText editText4;//验证码
    private EditText editText2;//密码
    private EditText editText3;//确认密码
    private Button btn_sure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");
        editText=(EditText)findViewById(R.id.editText);
        editText4=(EditText)findViewById(R.id.editText4);
        editText2=(EditText)findViewById(R.id.editText2);
        editText3=(EditText)findViewById(R.id.editText3);
        btn_sure=(Button)findViewById(R.id.button3);
        BmobUser user =new BmobUser();
    }

    public void reset_sure(View v)
    {
        String username = editText.getText().toString();
        String verifycode = editText4.getText().toString();
        String password = editText2.getText().toString();
        String repwd = editText3.getText().toString();
        if(username.equals("")||password.equals("")||repwd.equals("")){
            Toast.makeText(this, "帐号或密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if(verifycode.equals("")){
            Toast.makeText(this,"验证码不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if(verifycode.equals("KDQU")||verifycode.equals("kdqu")){

        }else{
            Toast.makeText(this,"验证码错误",Toast.LENGTH_LONG).show();
            return;
        }
        if(password.equals(repwd)) {
            BmobQuery<User> query = new BmobQuery<User>();
            query.addWhereEqualTo("name", username);
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> arg0, BmobException e) {
                    for (User data : arg0) {
                        if (e == null) {
                            String gname = arg0.get(0).getName().toString();
                            String name = editText.getText().toString();
                            if (gname.equals(name)) {
                                User user = new User();
                                String ID = data.getObjectId();
                                user.setObjectId(ID);
                                user.setPwd(password);
                                user.update(ID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        Toast.makeText(Resetpwd.this, "修改成功", Toast.LENGTH_LONG).show();
                                        Intent seccess = new Intent();
                                        seccess.setClass(Resetpwd.this, Login.class);
                                        startActivity(seccess);
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(Resetpwd.this, "账号不存在", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void return_onclick(View v)
    {
        Intent intent = new Intent(Resetpwd.this,Login.class);
        startActivity(intent);
    }


}

