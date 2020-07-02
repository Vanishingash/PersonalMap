package com.example.personalmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TableAdapter extends  BaseAdapter{
    private List<Foot> list;
    private LayoutInflater inflater;

    public TableAdapter(Context context, List<Foot> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (list != null) {
            ret = list.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Foot goods = (Foot) this.getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.q, null);
            viewHolder.goodId = (TextView) convertView.findViewById(R.id.text_id);
            viewHolder.goodName = (TextView) convertView.findViewById(R.id.text_goods_name);
            viewHolder.goodCodeBar = (TextView) convertView.findViewById(R.id.text_codeBar);
            viewHolder.goodNum = (TextView) convertView.findViewById(R.id.text_num);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.goodId.setText(goods.getDes());
        viewHolder.goodId.setTextSize(13);
        viewHolder.goodName.setText(goods.getScenery());
        viewHolder.goodName.setTextSize(13);
        viewHolder.goodCodeBar.setText(goods.getDate());
        viewHolder.goodCodeBar.setTextSize(13);
        //viewHolder.goodNum.setText(goods.getV());
        viewHolder.goodNum.setTextSize(13);

        return convertView;
    }

    public static class ViewHolder {
        public TextView goodId;
        public TextView goodName;
        public TextView goodCodeBar;
        public TextView goodNum;
    }
}
