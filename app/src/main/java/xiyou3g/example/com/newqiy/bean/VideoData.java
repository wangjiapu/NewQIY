package xiyou3g.example.com.newqiy.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by LZL on 2017/6/1.
 */
public class VideoData implements Serializable {
    private String id;
    private String title;
    private String shortTitle;
    private String img;
    private String sns_score;
    private String playCount;
    private String playCountText;
    private String albumId;
    private String tvId;
    private boolean isVip;
    private String type;
    private String pType;
    private String dataTimeStamp;
    private String dataFormat;
    private String totalNum;
    private String upDateNum;
    private String currentTime;
    private String episode;
    private Bitmap imageBitmap;

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    @Override
    public String toString() {
        return id+"\t"+title+"\t"+shortTitle+"\n";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSns_score() {
        return sns_score;
    }

    public void setSns_score(String sns_score) {
        this.sns_score = sns_score;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getPlayCountText() {
        return playCountText;
    }

    public void setPlayCountText(String playCountText) {
        this.playCountText = playCountText;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getTvId() {
        return tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getDataTimeStamp() {
        return dataTimeStamp;
    }

    public void setDataTimeStamp(String dataTimeStamp) {
        this.dataTimeStamp = dataTimeStamp;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getUpDateNum() {
        return upDateNum;
    }

    public void setUpDateNum(String upDateNum) {
        this.upDateNum = upDateNum;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }
}
