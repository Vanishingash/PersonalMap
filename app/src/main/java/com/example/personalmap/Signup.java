package com.example.personalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;


public class Signup extends AppCompatActivity {

    static int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");
        Button registerButton = (Button) this.findViewById(R.id.button3);
        ClickListener cl = new ClickListener();
        registerButton.setOnClickListener(cl);
    }

    class ClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button3:
                    processRegister();
                    break;
                case R.id.button4:
                    Intent intent = new Intent(Signup.this,Login.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

    public void processRegister() {
        EditText usernameText = (EditText) this.findViewById(R.id.editText);
        EditText passwordText = (EditText) this.findViewById(R.id.editText2);
        EditText mailText = (EditText) this.findViewById(R.id.editText3);
        RadioGroup sexGroup = (RadioGroup) this.findViewById(R.id.radiogroup);


        boolean flag = true;

        String username = usernameText.getText().toString();
        flag = checkIsInput(username,"用户名未输入");
        if (!flag) return;

        if(IsRepeated(username)){
            Toast.makeText(Signup.this, "该用户名已被使用！",Toast.LENGTH_LONG ).show();
            return;
        }

        String password = passwordText.getText().toString();
        flag = checkIsInput(password,"密码未输入");
        if (!flag) return;

        String mail = mailText.getText().toString();
        flag = checkIsInput(mail,"请输入您的邮箱");
        if (!flag) return;

        String sex = "";
        RadioButton checkedRadio = (RadioButton) this.findViewById(sexGroup.getCheckedRadioButtonId());
        if (checkedRadio==null) {
            Toast.makeText(Signup.this, "请选择您的性别",Toast.LENGTH_LONG ).show();
            return;
        }
        sex = checkedRadio.getText().toString();

        flag = checkIsInput(sex,"请输入您的性别");
        if (!flag) return;

        User user = new User();
        user.setName(username);
        user.setPwd(password);
        user.setSex(sex);
        user.setEmail(mail);
        user.setUdes("初来乍到~");
        user.save(new SaveListener() {
            @Override
            public void done(Object o, BmobException e) {
                if(e==null){
                    Toast.makeText(Signup.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signup.this,Login.class);
                    startActivity(intent);
                }else{
                    Log.e("注册失败", "原因: ",e );
                }
            }
        });
    }

    private boolean checkIsInput(String value,String warning) {
        if (value==null||value.equals("")) {
            Toast.makeText(Signup.this, warning,Toast.LENGTH_SHORT ).show();
            return false;
        }
        return true;
    }

    public void qwe_click(View v){
        Intent intent = new Intent(Signup.this,Login.class);
        startActivity(intent);
    }

    private boolean IsRepeated(String value){
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("name",value);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(list.size()==0){
                    i = 1;
                }
            }
        });
        if(i==0){
            return false;
        }else
        {
            return true;
        }
    }
}
