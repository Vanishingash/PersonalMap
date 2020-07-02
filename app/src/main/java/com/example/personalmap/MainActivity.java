package com.example.personalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class MainActivity extends AppCompatActivity {
    ChinaMapView lView;
    String cityname="";
    ArrayList<String> citys;
    public static  int j=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");

        lView = (ChinaMapView)findViewById(R.id.vp);
        lView.setOnProvinceSelectedListener(new ChinaMapView.OnProvinceSelectedListener() {
            @Override
            public void onprovinceSelected(ChinaMapView.Area pArea) {
                Toast.makeText(MainActivity.this,"您选择了-->"+pArea.name(),Toast.LENGTH_SHORT).show();
                cityname = pArea.name();
            }
        });
        lView.setMapColor(Color.BLACK);

        findViewById(R.id.button_big).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                lView.zoomIn();
            }
        });
        findViewById(R.id.button_small).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                lView.zoomOut();
            }
        });
        findViewById(R.id.fuwei).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                lView.restPosition();
            }
        });
        /*findViewById(R.id.yaunshidaxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                lView.restScale();
            }
        });*/

        /*findViewById(R.id.sichuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                lView.setPaintColor(ChinaMapView.Area.四川, Color.rgb(0x5c,0xad,0xad),true);
            }
        });
        findViewById(R.id.neimeng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                lView.setPaintColor(ChinaMapView.Area.内蒙古, Color.rgb(0x8f,0x45,0x86),true);
            }
        });
        findViewById(R.id.xinjiang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                lView.selectAProvince(ChinaMapView.Area.新疆);
            }
        });*/
    }
    public void add_click(View v){
        if(cityname=="") {
            Toast.makeText(MainActivity.this,"请先选择地区再添加！",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(MainActivity.this, Add.class);
            intent.putExtra("city", cityname);
            startActivity(intent);
        }
    }

    public void foot_click(View v){
        Intent intent = new Intent(MainActivity.this,Log.class);
        startActivity(intent);
    }

    public void me_click(View v){
        Intent intent = new Intent(MainActivity.this,Me.class);
        startActivity(intent);
    }

    public void look_click(View v){
        if(cityname=="") {
            Toast.makeText(MainActivity.this,"请先选择地区再查看！",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(MainActivity.this,Look.class);
            intent.putExtra("city",cityname);
            startActivity(intent);
        }
    }

    public void small_click(View v){
        BmobQuery<Foot> query=new BmobQuery<Foot>();
        query.addWhereEqualTo("uname", Login.USER_NAME);
        query.findObjects(new FindListener<Foot>() {
            @Override
            public void done(List<Foot> list, BmobException e) {
                citys =  new ArrayList<>(list.size()+1);
                for(Foot data : list){
                    citys.add(data.getCity());
                }
                for(int i =0;i<list.size();i++){
                    String name = citys.get(i);
                    lView.setPaintColor(ChinaMapView.Area.valueOf(name),Color.rgb(0,0,128),true);
                }
                citys.clear();
                //System.gc();
            }
        });
    }

    public void removerlist(){
        //citys.remove
    }

}
