package xiyou3g.example.com.newqiy.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LZL on 2017/6/8.
 */

public class SpaceItemDecration extends RecyclerView.ItemDecoration {
    int top;
    int bottom;
    int left;
    int right;
    public SpaceItemDecration(int top,int bottom,int left,int right)
    {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = bottom;
        outRect.top = top;
        outRect.left = left;
        outRect.right = right;
    }

    /*@Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        outRect.bottom = bottom;
        outRect.top = top;
        outRect.left = left;
        outRect.right = right;
    }*/
}
