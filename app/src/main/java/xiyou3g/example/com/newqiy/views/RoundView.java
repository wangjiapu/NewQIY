package xiyou3g.example.com.newqiy.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import xiyou3g.example.com.newqiy.R;

/**
 * Created by 蒲家旺 on 2017/5/26.
 * 是用与FloatingActionButton与底部菜单栏融为一体
 */

public class RoundView extends FrameLayout {
    private int roundx,roundy;//圆心
    private int r;//半径
    private Paint mpaint;
    public RoundView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public RoundView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public RoundView(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context c) {
        mpaint=new Paint();//制作笔
        mpaint.setAntiAlias(true);
        mpaint.setColor(c.getResources().getColor(R.color.menu_main_bg));//设置颜色
        mpaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        roundy=getHeight()/2;
        roundx=getWidth()/2;
        r=roundy;
       canvas.drawCircle(roundx,roundy,r,mpaint);
    }
}
