package xiyou3g.example.com.newqiy.bean;

import java.util.List;

/**
 * Created by LZL on 2017/6/5.
 */

public class RecommendData {
    private List<VideoData> videoDataList;
    private ChannelData channelData;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(channelData);
        for(VideoData videoData:videoDataList)
        {
            builder.append(videoData);
        }
        return builder.toString();
    }

    public List<VideoData> getVideoDataList() {
        return videoDataList;
    }

    public void setVideoDataList(List<VideoData> videoDataList) {
        this.videoDataList = videoDataList;
    }

    public ChannelData getChannelData() {
        return channelData;
    }

    public void setChannelData(ChannelData channelData) {
        this.channelData = channelData;
    }
}
