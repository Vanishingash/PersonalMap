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

public class Nickname extends AppCompatActivity {
    EditText Nickname;
    HashMap mhashmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");
        Nickname = (EditText)findViewById(R.id.editText7);
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("name",Login.USER_NAME);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                for(User data : list)
                {
                    String nname = data.getName();
                    Nickname.setText(nname);
                }
            }
        });
    }

    public void nickname_click(View v){
        String nickname = Nickname.getText().toString();
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
                    user.setName(nickname);
                    user.update(temp, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            Toast.makeText(Nickname.this, "修改成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Nickname.this,Information.class);
                            startActivity(intent);
                        }
                    });
                }

            }
        });
    }

    public void info3_click(View v){
        Intent intent = new Intent(Nickname.this, Information.class);
        startActivity(intent);
    }
}
