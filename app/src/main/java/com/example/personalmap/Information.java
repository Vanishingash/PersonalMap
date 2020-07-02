package com.example.personalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Information extends AppCompatActivity {
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");
        picture = (ImageView)findViewById(R.id.imageView7);
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("name",Login.USER_NAME);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                for(User data : list)
                {
                    String url = data.getPic();
                    Bitmap bitmap = BitmapFactory.decodeFile(url);
                    picture.setImageBitmap(bitmap);
                }
            }
        });
    }

    HashMap mhashmap;
    public void sex_click(View v){
        final String[] gender = new String[]{"男","女"};
        android.app.AlertDialog.Builder builder1=new android.app.AlertDialog.Builder(Information.this);
        builder1.setTitle("请选择你的性别");
        builder1.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                            user.setSex(gender[which]);
                            user.update(temp, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    Toast.makeText(Information.this, "修改成功", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }
                });
            }
        });
        builder1.show();
    }

    public void descn_click(View v){
        Intent intent = new Intent(Information.this,Descn.class);
        startActivity(intent);
    }

    public void mail_click(View v){
        Intent intent = new Intent(Information.this,Mailbox.class);
        startActivity(intent);
    }

    public void nick_click(View v){
        Intent intent = new Intent(Information.this,Nickname.class);
        startActivity(intent);
    }

    public void re_se_click(View v){
        Intent intent = new Intent(Information.this,Me.class);
        startActivity(intent);
    }

    public void exit_click(View v){
        Intent intent = new Intent(Information.this,Login.class);
        startActivity(intent);
    }
}
