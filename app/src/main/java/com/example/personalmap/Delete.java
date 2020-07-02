package com.example.personalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Delete extends AppCompatActivity {
    private LinearLayout linearlayout;
    HashMap mHashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Bmob.initialize(this,"8b2ab8bbc4d795ae0f8ef4d3e8148041");

        linearlayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearlayout.removeAllViews();

        BmobQuery<Foot> query=new BmobQuery<Foot>();
        query.addWhereEqualTo("uname", Login.USER_NAME);
        query.findObjects(new FindListener<Foot>() {
            @Override
            public void done(List<Foot> list, BmobException e) {
                Collections.reverse(list);
                for(Foot data : list){
                    LinearLayout onelayout = new LinearLayout(Delete.this);
                    onelayout.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout twolayout = new LinearLayout(Delete.this);
                    twolayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout threelayout = new LinearLayout(Delete.this);
                    threelayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout fourlayout = new LinearLayout(Delete.this);
                    fourlayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout fivelayout = new LinearLayout(Delete.this);
                    fivelayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout sixlayout = new LinearLayout(Delete.this);
                    sixlayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout sevenlayout = new LinearLayout(Delete.this);
                    sevenlayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout eightlayout = new LinearLayout(Delete.this);
                    eightlayout.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80);
                    LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,700);
                    LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,40);
                    LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 60);
                    LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 150);
                    LinearLayout.LayoutParams lp6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 160);
                    lp4.gravity = Gravity.RIGHT;
                    lp5.gravity = Gravity.RIGHT;

                    onelayout.setLayoutParams(lp2);
                    twolayout.setLayoutParams(lp);
                    threelayout.setLayoutParams(lp4);
                    fourlayout.setLayoutParams(lp);
                    fivelayout.setLayoutParams(lp);
                    sixlayout.setLayoutParams(lp6);
                    sevenlayout.setLayoutParams(lp3);
                    eightlayout.setLayoutParams(lp5);

                    onelayout.setBackgroundResource(R.drawable.background1);
                    sevenlayout.setBackgroundResource(R.drawable.background2);

                    TextView text1 = new TextView(Delete.this);
                    text1.setTextSize(22);
                    text1.setTextColor(Color.parseColor("#000000"));
                    text1.setText("发布者：");
                    TextView text2 = new TextView(Delete.this);
                    text2.setTextSize(22);
                    text2.setTextColor(Color.parseColor("#000000"));
                    text2.setText("//发布者");
                    TextView text3 = new TextView(Delete.this);
                    text3.setTextSize(20);
                    text3.setText("//时间");
                    TextView text4 = new TextView(Delete.this);
                    text4.setTextSize(22);
                    text4.setTextColor(Color.parseColor("#000000"));
                    text4.setText("//地区");
                    TextView text5 = new TextView(Delete.this);
                    text5.setTextSize(22);
                    text5.setTextColor(Color.parseColor("#000000"));
                    text5.setText("—");
                    TextView text6 = new TextView(Delete.this);
                    text6.setTextSize(22);
                    text6.setTextColor(Color.parseColor("#000000"));
                    text6.setText("//景点");
                    TextView text7 = new TextView(Delete.this);
                    text7.setTextSize(22);
                    text7.setTextColor(Color.parseColor("#000000"));
                    text7.setText("他说：");
                    TextView text8 = new TextView(Delete.this);
                    text8.setTextSize(22);
                    text8.setTextColor(Color.parseColor("#000000"));
                    text8.setText("//描述");
                    Button button1 = new Button(Delete.this);
                    button1.setWidth(200);
                    button1.setText("删除");
                    button1.setTextSize(20);
                    button1.setTextColor(Color.parseColor("#000000"));

                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String username = text2.getText().toString();
                            String scenery = text6.getText().toString();
                            BmobQuery<Foot> query = new BmobQuery<Foot>();
                            query.addWhereEqualTo("uname", username);
                            query.addWhereEqualTo("scenery",scenery);
                            query.findObjects(new FindListener<Foot>() {
                                @Override
                                public void done(List<Foot> list, BmobException e) {
                                    for (Foot data : list) {
                                                Foot foot = new Foot();
                                                String ID = data.getObjectId();
                                                foot.setObjectId(ID);
                                                foot.delete(new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if(e == null) {
                                                            Toast.makeText(Delete.this, "删除成功", Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(Delete.this,Delete.class);
                                                            startActivity(intent);
                                                        }
                                                        else{
                                                            Toast.makeText(Delete.this, "删除失败", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                        }
                    });

                    twolayout.addView(text1);
                    twolayout.addView(text2);
                    threelayout.addView(text3);
                    fourlayout.addView(text4);
                    fourlayout.addView(text5);
                    fourlayout.addView(text6);
                    fivelayout.addView(text7);
                    sixlayout.addView(text8);
                    eightlayout.addView(button1);

                    onelayout.addView(twolayout);
                    onelayout.addView(threelayout);
                    onelayout.addView(fourlayout);
                    onelayout.addView(fivelayout);
                    onelayout.addView(sixlayout);
                    onelayout.addView(eightlayout);

                    linearlayout.addView(sevenlayout);
                    linearlayout.addView(onelayout);

                    mHashMap = new HashMap<>();
                    mHashMap.put("USERNAME",data.getUname());
                    String temp = (String) mHashMap.get("USERNAME");
                    text2.setText(temp);
                    mHashMap.put("TIME",data.getDate());
                    String temp1 = (String) mHashMap.get("TIME");
                    text3.setText(temp1);
                    mHashMap.put("RIGION",data.getCity());
                    String temp2 = (String) mHashMap.get("RIGION");
                    text4.setText(temp2);
                    mHashMap.put("SCENERY",data.getScenery());
                    String temp3 = (String) mHashMap.get("SCENERY");
                    text6.setText(temp3);
                    mHashMap.put("DESCN",data.getDes());
                    String temp4 = (String) mHashMap.get("DESCN");
                    text8.setText(temp4);
                }
            }
        });
    }

    public void back_click(View v){
        Intent intent = new Intent(Delete.this, Me.class);
        startActivity(intent);
    }
}
