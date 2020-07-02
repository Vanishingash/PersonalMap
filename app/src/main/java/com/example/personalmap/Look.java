package com.example.personalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Look extends AppCompatActivity {

    private ListView tableListView;
    private TextView cityView;
    private TextView print;
    private List<Foot> list;

    private String scenery,des,date;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);
        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");
        ViewGroup tableTitle = (ViewGroup) findViewById(R.id.table_title);
        tableListView = (ListView) findViewById(R.id.list);
        cityView = (TextView)findViewById(R.id.textView101);
        print = (TextView) findViewById(R.id.dayin);
        show();

    }
    private void show(){
        Intent intent = getIntent();
        String cityname = intent.getStringExtra("city");
        cityView.setText(cityname);
        list = new ArrayList<Foot>();
        BmobQuery<Foot> bmobQuery = new BmobQuery<Foot>();
        bmobQuery.addWhereEqualTo("uname", Login.USER_NAME);
        bmobQuery.addWhereEqualTo("city",cityname);
        bmobQuery.findObjects(new FindListener<Foot>() {
            @Override
            public void done(List<Foot> list1, BmobException e) {
                for(Foot data : list1){
                    des=data.getDes();
                    scenery=data.getScenery();
                    date=data.getDate();
                    list.add(new Foot(scenery,des,date));
                }
                TableAdapter adapter = new TableAdapter(Look.this, list);
                tableListView.setAdapter(adapter);
            }
        });
    }

    public void looktomain_click(View v){

        Intent intent = new Intent(Look.this,MainActivity.class);
        startActivity(intent);
    }

    public void looktoedit_click(View v){
        if(flag==1){
            Intent intent = new Intent(Look.this,Edit.class);
            startActivity(intent);
        }else{
            Toast.makeText(Look.this,"请选择一条足迹",Toast.LENGTH_SHORT).show();
        }
    }

    public  void initListener(){
        tableListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
            }
        });
    }
}
