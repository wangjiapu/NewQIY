package xiyou3g.example.com.newqiy.bean;

/**
 * Created by DickJampink on 2017/6/3.
 * 不同项目内的滚动的RecycleView数据的抽象集合
 * 用于存放给recycleview提供数据的数据类型
 * 存放他们的编号：dataType；
 */

public class RecycleViewItemData<T> {
    private T t;
    private int dataType;


    public RecycleViewItemData() {
    }

    public RecycleViewItemData (T t, int dataType) {
        this.t = t;
        this.dataType = dataType;
    }

    public T getT () {
        return t;
    }

    public void setT (T t) {
        this.t = t;
    }

    public int getDataType () {
        return dataType;
    }

    public void setDataType (int dataType) {
        this.dataType = dataType;
    }
}
