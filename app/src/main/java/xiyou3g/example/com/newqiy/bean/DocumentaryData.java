package xiyou3g.example.com.newqiy.bean;

import android.graphics.Bitmap;



import java.util.HashMap;
import java.util.Map;

import xiyou3g.example.com.newqiy.R;

/**
 * Created by Kiboooo on 2017/6/14.
 */

public class DocumentaryData {
    private String ID;
    private String TitleBar;
    private VideoData[] videoData = new VideoData[10];
    private String[] Bigtitle = new String[10];
    private String[] MinTitle= new String[10];
    private Bitmap[] Image = new Bitmap[10];
    private String[] ImageMsg = new String[10];

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

    public Integer getLogo(){
        return ClassifyLogo.get(ID);
    }

    public void setVideoData(VideoData videoData,int x) {
        this.videoData[x] = videoData;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTitleBar(String titleBar) {
        TitleBar = titleBar;
    }

    public void setMinTitle(int x ,String minTitle) {
        MinTitle[x] = minTitle;
    }

    public void setBigtitle(int x,String bigTitle) {
        Bigtitle[x] = bigTitle;
    }

    public void setImage(int x,Bitmap image) {
        Image[x] = image;
    }

    public void setImageMsg(int x,String imageMsg) {
        ImageMsg[x] = imageMsg;
    }

    public String getID() {
        return ID;
    }

    public Bitmap getImage(int x) {
        if (x>Image.length) return null;
        return Image[x];
    }

    public String getBigtitle(int x) {
        if (x>Bigtitle.length) return null;
        return Bigtitle[x];
    }

    public String getImageMsg(int x) {
        if (x>ImageMsg.length) return null;
        return ImageMsg[x];
    }

    public String getMinTitle(int x) {
        if (x>MinTitle.length) return null;
        return MinTitle[x];
    }

    public String getTitleBar() {
        return TitleBar;
    }

    public VideoData getVideoData(int x) {
        if (x>=0 && x<10)
            return videoData[x];
        return null;
    }
}
