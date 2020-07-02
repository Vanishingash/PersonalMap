package com.example.personalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.IDNA;
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

public class Mailbox extends AppCompatActivity {

    EditText Mailbox;
    HashMap mhashmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailbox);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");
        Mailbox = (EditText)findViewById(R.id.editText8);
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("name",Login.USER_NAME);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                for(User data : list)
                {
                    String email = data.getEmail();
                    Mailbox.setText(email);
                }
            }
        });
    }

    public void rmail_click(View v){
        String email = Mailbox.getText().toString();
        BmobQuery<User> query=new BmobQuery<User>();
        query.addWhereEqualTo("name", Login.USER_NAME);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                for(User data : list)
                {
                    mhashmap = new HashMap<>();
                    mhashmap.put("EMAIL",data.getObjectId());
                    String temp = (String) mhashmap.get("EMAIL");
                    User user = new User();
                    user.setObjectId(temp);
                    user.setEmail(email);
                    user.update(temp, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            Toast.makeText(Mailbox.this, "修改成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Mailbox.this,Information.class);
                            startActivity(intent);
                        }
                    });
                }

            }
        });
    }

    public void info2_click(View v){
        Intent intent = new Intent(Mailbox.this, Information.class);
        startActivity(intent);
    }
}
