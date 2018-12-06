package xiyou3g.example.com.newqiy.bean;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by DickJampink on 2017/6/3.
 */

public class RollData {

    private String roll_msg;
    private Bitmap roll_imag;
    private List<RollData> mRollList;
    private VideoData[] videoData = new VideoData[6];

    public RollData() {

    }

    RollData(String roll_msg, Bitmap roll_imag) {
        this.roll_imag =roll_imag;
        this.roll_msg = roll_msg;
    }

    public RollData(List<RollData> mRollList) {
        this.mRollList = mRollList;
    }

    public VideoData getVideoData(int x) {
        if (x >= 0 && x < 5) {
            return videoData[x];
        }
        return null;
    }

    public List<RollData> getmRollList() {
        return mRollList;
    }

    public void setmRollList(List<RollData> mRollList) {
        this.mRollList = mRollList;
    }

    public Bitmap getRoll_imag() {
        return roll_imag;
    }

    public String getRoll_msg() {
        return roll_msg;
    }

    public void setRoll_imag(Bitmap roll_imag) {
        this.roll_imag = roll_imag;
    }

    public void setRoll_msg(String roll_msg) {
        this.roll_msg = roll_msg;
    }

    public void setVideoData(VideoData videoData,int x) {
        this.videoData[x] = videoData;
    }

}
