package xiyou3g.example.com.newqiy.bean;

import android.graphics.Bitmap;


import java.util.HashMap;
import java.util.Map;

import xiyou3g.example.com.newqiy.R;

/**
 * Created by DickJampink on 2017/6/3.
 */

public class ClassifyData {
    private String ID;
    private String OptionTitle;
    private String[] BigTitle;
    private String[] MinTitle;
    private String[] ImageMsg;
    private String[] Tags;
    private Bitmap[] Image;
    private VideoData[] videoData = new VideoData[8];
    private Map<String, Integer> ClassifyLogo = new HashMap<String, Integer>() {
        {
            put("25", R.drawable.zx);

            put("1", R.drawable.dy);

            put("2", R.drawable.ds);

            put("6", R.drawable.zy);

            put("4", R.drawable.dm);

            put("3", R.drawable.jl);

        }
    };

    public ClassifyData() {
        BigTitle = new String[8];
        MinTitle = new String[8];
        ImageMsg = new String[8];
        Tags = new String[8];
        Image = new Bitmap[8];
    }

    public ClassifyData(String id, String ot, String[] bt, String[] mt, String[] T, Bitmap[] oi) {
        ID = id;
        OptionTitle = ot;
        BigTitle = bt;
        MinTitle = mt;
        Tags = T;
        Image = oi;
    }

    public VideoData getVideoData(int x) {
        if (x >= 0 && x < 8) {
            return videoData[x];
        }
        return null;
    }

    public Integer getLogo(){
        return ClassifyLogo.get(ID);
    }

    public String getID() {
        return ID;
    }

    public Bitmap[] getOptionImage() {
        return Image;
    }

    public Bitmap getOptionImage(int x) {
        return Image[x];
    }

    public String getOptionTitle() {
        return OptionTitle;
    }

    public String[] getBigTitle() {
        return BigTitle;
    }

    public String getBigTitle(int x) {
        return BigTitle[x];
    }

    public String[] getImageMsg() {
        return ImageMsg;
    }

    public String getImageMsg(int x) {
        return ImageMsg[x];
    }

    public String[] getMinTitle() {
        return MinTitle;
    }

    public String getMinTitle(int x) {
        return MinTitle[x];
    }

    public String[] getTags() {
        return Tags;
    }

    public String getTags(int x) {
        return Tags[x];
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setOptionImage(Bitmap[] optionImage) {
        Image = optionImage;
    }

    public void setOptionImage(Bitmap optionImage, int x) {
        Image[x] = optionImage;
    }

    public void setBigTitle(String[] bigTitle) {
        BigTitle = bigTitle;
    }

    public void setBigTitle(String bigTitle, int x) {
        BigTitle[x] = bigTitle;
    }

    public void setImageMsg(String[] imageMsg) {
        ImageMsg = imageMsg;
    }

    public void setImageMsg(String imageMsg, int x) {
        ImageMsg[x] = imageMsg;
    }

    public void setMinTitle(String[] minTitle) {
        MinTitle = minTitle;
    }

    public void setMinTitle(String minTitle, int x) {
        MinTitle[x] = minTitle;
    }

    public void setOptionTitle(String optionTitle) {
        OptionTitle = "  "+optionTitle;
    }

    public void setTags(String[] tags) {
        Tags = tags;
    }

    public void setTags(String tags, int x) {
            Tags[x] = tags;
    }

    public void setVideoData(VideoData videoData,int x) {
        this.videoData[x] = videoData;
    }
}
