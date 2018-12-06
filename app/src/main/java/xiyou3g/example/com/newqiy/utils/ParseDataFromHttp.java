package xiyou3g.example.com.newqiy.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Response;
import xiyou3g.example.com.newqiy.bean.ChannelData;
import xiyou3g.example.com.newqiy.bean.RecommendData;
import xiyou3g.example.com.newqiy.bean.VideoData;

/**
 * Created by LZL on 2017/6/1.
 */
public class ParseDataFromHttp {
    public static Bitmap getPicFromResponse(Response response)
    {
        InputStream inputStream = response.body().byteStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    public static List<VideoData> getSearchVideoList(String res)
    {
        List<VideoData> list = new LinkedList<>();
        try
        {
            JSONObject object = new JSONObject(res);
            if(object.getInt("code")!=100000)
                return list;
            JSONArray array = object.getJSONArray("data");
            list = getVideoList(array);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public static List<RecommendData> getRcommendDataList(String res)
    {
        List<RecommendData> list = new LinkedList<>();
        try
        {
            JSONObject object = new JSONObject(res);
            if(object.getInt("code")!=0)
                return list;
            JSONArray array = object.getJSONArray("data");
            ChannelData channelData = new ChannelData();
            channelData.setName(array.getJSONObject(0).getString("title"));
            List<VideoData> list1 = getVideoList(array.getJSONObject(0).getJSONArray("video_list"));
            RecommendData data = new RecommendData();
            data.setChannelData(channelData);
            data.setVideoDataList(list1);
            list.add(data);
            for(int i = 1;i<array.length();i++)
            {
                RecommendData recommendData = new RecommendData();
                List<VideoData> videoDatas;
                ChannelData data1 = new ChannelData();
                JSONObject jsonObject = array.getJSONObject(i);
                data1.setDesc(jsonObject.getString("title"));
                data1.setName(jsonObject.getString("channel_name"));
                data1.setId(jsonObject.getString("channel_id"));
                videoDatas = getVideoList(jsonObject.getJSONArray("video_list"));
                recommendData.setChannelData(data1);
                recommendData.setVideoDataList(videoDatas);
                list.add(recommendData);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    private static List<VideoData> getVideoList(JSONArray array)
    {
        List<VideoData> list = new LinkedList<>();
        for(int i = 0;i<array.length();i++)
        {
            try{
                VideoData videoData = new VideoData();
                JSONObject jsonObject = array.getJSONObject(i);
                videoData.setId(jsonObject.getString("id"));
                videoData.setTitle(jsonObject.getString("title"));
                videoData.setShortTitle(jsonObject.getString("short_title"));
                videoData.setImg(jsonObject.getString("img"));
                if (!jsonObject.isNull("sns_score"))
                    videoData.setSns_score(jsonObject.getString("sns_score"));
                videoData.setPlayCount(jsonObject.getString("play_count"));
                videoData.setPlayCountText(jsonObject.getString("play_count_text"));
                videoData.setAlbumId(jsonObject.getString("a_id"));
                videoData.setTvId(jsonObject.getString("tv_id"));
                String vip = jsonObject.getString("is_vip");
                boolean is_vip = false;
                if(vip.equals("1"))
                    is_vip = true;
                videoData.setVip(is_vip);
                videoData.setType(jsonObject.getString("type"));
                videoData.setpType(jsonObject.getString("p_type"));
                videoData.setDataTimeStamp(jsonObject.getString("date_timestamp"));
                videoData.setDataFormat(jsonObject.getString("date_format"));
                videoData.setTotalNum(jsonObject.getString("total_num"));
                videoData.setUpDateNum(jsonObject.getString("update_num"));
                list.add(videoData);
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<VideoData> getChannelVideoList(String res)
    {
        List<VideoData> list = new LinkedList<>();
        try
        {
            JSONObject object = new JSONObject(res);
            if (object.getInt("code")!=100000)
                return list;
            object = object.getJSONObject("data");
            JSONArray array = object.getJSONArray("video_list");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                VideoData videoData = new VideoData();
                videoData.setId(jsonObject.getString("id"));
                videoData.setTitle(jsonObject.getString("title"));
                videoData.setShortTitle(jsonObject.getString("short_title"));
                videoData.setImg(jsonObject.getString("img"));
                videoData.setSns_score(jsonObject.getString("sns_score"));
                videoData.setPlayCount(jsonObject.getString("play_count"));
                videoData.setPlayCountText(jsonObject.getString("play_count_text"));
                videoData.setAlbumId(jsonObject.getString("a_id"));
                videoData.setTvId(jsonObject.getString("tv_id"));
                String vip = jsonObject.getString("is_vip");
                boolean is_vip = false;
                if(vip.equals("1"))
                    is_vip = true;
                videoData.setVip(is_vip);
                videoData.setType(jsonObject.getString("type"));
                videoData.setpType(jsonObject.getString("p_type"));
                videoData.setDataTimeStamp(jsonObject.getString("date_timestamp"));
                videoData.setDataFormat(jsonObject.getString("date_format"));
                videoData.setTotalNum(jsonObject.getString("total_num"));
                videoData.setUpDateNum(jsonObject.getString("update_num"));
                list.add(videoData);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        finally {
            return list;
        }
    }
    public static List<ChannelData> getChannelList(String res)
    {
        List<ChannelData> list = new LinkedList<>();
        try
        {
            JSONObject object = new JSONObject(res);
            if (object.getInt("code")!=100000)
                return list;
            JSONArray array = object.getJSONArray("data");
            for(int i = 0;i<array.length();i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                ChannelData channelData = new ChannelData();
                channelData.setId(jsonObject.getString("id"));
                channelData.setName(jsonObject.getString("name"));
                channelData.setDesc(jsonObject.getString("desc"));
                list.add(channelData);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        finally {
            return list;
        }
    }
}
