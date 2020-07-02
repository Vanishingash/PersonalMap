package com.example.personalmap;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


public class WListView extends  ListView{
    public WListView(Context context) {
        super(context);
    }

    public WListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //根据模式据算每个child的高度和宽度
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
