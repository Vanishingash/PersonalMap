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
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;



import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class Add extends AppCompatActivity {


    public static final int CHOOSE_PHOTO = 3;
    private TextView textcity;
    private ImageView picture;
    private EditText scenery;
    private EditText description;
    private Uri imageUri;
    public String temp;
    Button goods_add , selectPhoto;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");

        findView();
        initListener();

        Intent intent = getIntent();
        String Cityname = intent.getStringExtra("city");
        textcity.setText(Cityname);

    }

    private void findView() {
        goods_add = (Button) findViewById(R.id.button2);
        picture = (ImageView) findViewById(R.id.imageView4);
        selectPhoto = (Button) findViewById(R.id.button);
        textcity=(TextView) findViewById(R.id.textView17);
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
            Toast.makeText(Add.this, "未得到图片", Toast.LENGTH_SHORT).show();
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
        temp = path;
        return path;
    }

    private void handleIMageBeforKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri , null);
        displayImage(imagePath);
    }

    public  void initListener() {
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);

            }
        });
        goods_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAdd();
            }
        });
    }



    public void processAdd(){
        scenery = (EditText)findViewById(R.id.editText5);
        description = (EditText)findViewById(R.id.editText6);

        boolean flag = true;

        Intent intent = getIntent();
        String Cityname = intent.getStringExtra("city");
        flag = checkIsInput(Cityname,"未选择城市");
        if (!flag) return;

        String Scenery = scenery.getText().toString();
        flag = checkIsInput(Scenery,"景区名称未输入");
        if (!flag) return;

        String Des = description.getText().toString();
        flag = checkIsInput(Des,"描述未输入");
        if (!flag) return;

        Time t = new Time();
        t.setToNow();
        String year = String.valueOf(t.year);
        String month = String.valueOf(t.month+1);
        String day = String.valueOf(t.monthDay);

        Foot foot = new Foot();
        foot.setUname(Login.USER_NAME);
        foot.setDes(Des);
        foot.setCity(Cityname);
        foot.setScenery(Scenery);
        foot.setPics(temp);
        foot.setDate(year+"年"+month+"月"+day+"日");
        foot.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null)
                {
                    Toast.makeText(Add.this,"添加成功",Toast.LENGTH_SHORT).show();
                    Add.this.finish();
                }else
                {
                    Log.e("添加失败", "原因: ",e );
                }
            }
        });
    }


    public void fanhui_click(View v){
        Intent intent = new Intent(Add.this,MainActivity.class);
        startActivity(intent);
    }

    private boolean checkIsInput(String value,String warning) {
        if (value==null||value.equals("")) {
            Toast.makeText(Add.this, warning,Toast.LENGTH_SHORT ).show();
            return false;
        }
        return true;
    }

}
