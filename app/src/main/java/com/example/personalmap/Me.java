package com.example.personalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.personalmap.Add.CHOOSE_PHOTO;

public class Me extends AppCompatActivity {
    TextView Nickname;
    ImageView picture;
    ImageView picture1;
    public String Temp;
    HashMap mhashmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");
        Nickname = (TextView) findViewById(R.id.textView45);
        picture = (ImageView)findViewById(R.id.imageView5);
        picture1 = (ImageView)findViewById(R.id.imageView6);
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("name",Login.USER_NAME);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                for(User data : list)
                {
                    String nname = data.getName();
                    Nickname.setText(nname);
                    String ssex = data.getSex();
                    String url = data.getPic();
                    Bitmap bitmap = BitmapFactory.decodeFile(url);
                    picture.setImageBitmap(bitmap);
                    if(ssex.equals("男")){
                        picture1.setImageResource(R.drawable.male);
                    }else
                    {
                        picture1.setImageResource(R.drawable.female);
                    }
                }
            }
        });

    }

    public void setting_click(View v){
        Intent intent = new Intent(Me.this,Settings.class);
        startActivity(intent);
    }

    public void info_click(View v){
        Intent intent = new Intent(Me.this,Information.class);
        startActivity(intent);
    }

    public void help_click(View v){
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        builder.setTitle("您好！");
        builder.setMessage("请联系开发者邮箱：\n1124195372@qq.com\n1559905672@qq.com\n欢迎来信！");
        builder.setPositiveButton("ok", null);
        builder.setCancelable(true);
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void map_click(View v){
        Intent intent = new Intent(Me.this,MainActivity.class);
        startActivity(intent);
    }

    public void foot_click(View v){
        Intent intent = new Intent(Me.this,Log.class);
        startActivity(intent);
    }

    public void delete_click(View v){
        Intent intent = new Intent(Me.this,Delete.class);
        startActivity(intent);
    }

    public void touxiang_click(View v){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT>=19){
                        //4.4以上使用这个方法处理图片
                        handleIMageOnKitKat(data);
                    }else{
                        handleIMageBeforKitKat(data);
                    }
                }
                break;
            default:
                break;
        }super.onActivityResult(requestCode, resultCode, data);
    }
    String imagePath = null;
    @TargetApi(19)
    private void handleIMageOnKitKat(Intent data) {
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this , uri)){
            //如果是document类型的URI，则使用document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID +"=" +id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri ,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果不是document类型的URI，则使用普通方式处理
            imagePath = getImagePath(uri , null);
        }
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }else {
            Toast.makeText(Me.this, "未得到图片", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri , null , selection , null , null);
        if (cursor!=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        Temp = path;
        BmobQuery<User> query=new BmobQuery<User>();
        query.addWhereEqualTo("name", Login.USER_NAME);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                for(User data : list) {
                    mhashmap = new HashMap<>();
                    mhashmap.put("ID", data.getObjectId());
                    String temp = (String) mhashmap.get("ID");
                    User user = new User();
                    user.setObjectId(temp);
                    user.setPic(Temp);
                    user.update(temp, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            Toast.makeText(Me.this, "修改成功", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        return path;
    }

    private void handleIMageBeforKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri , null);
        displayImage(imagePath);
    }

}