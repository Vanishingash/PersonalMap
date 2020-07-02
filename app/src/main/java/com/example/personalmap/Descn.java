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

public class Descn extends AppCompatActivity {

    EditText Descn;
    HashMap mhashmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descn);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");
        Descn = (EditText)findViewById(R.id.editText9);
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("name",Login.USER_NAME);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                for(User data : list)
                {
                    String Desc = data.getUdes();
                    Descn.setText(Desc);
                }
            }
        });

    }

    public void rdescn_click(View v){
        String descn = Descn.getText().toString();
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
                    user.setUdes(descn);
                    user.update(temp, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            Toast.makeText(Descn.this, "修改成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Descn.this,Information.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    public void aa_click(View v){
        Intent intent = new Intent(Descn.this, Information.class);
        startActivity(intent);
    }
}
