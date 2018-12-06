package xiyou3g.example.com.newqiy.utils;

import android.graphics.Bitmap;

import okhttp3.*;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by LZL on 2017/5/31.
 */
public class DataRequest {
    //URL
    private static String url = "http://iface.qiyi.com";
    private static String channelListURL = url + "/openapi/realtime/channel";
    private static String recommendURL = url + "/openapi/realtime/recommend";
    private static String searchURL = url + "/openapi/realtime/search";
    //static
    private static DataRequest dataRequest;


    private OkHttpClient okHttpClient;
    private Request.Builder requestBuilder;

    private DataRequest() {
        okHttpClient = new OkHttpClient();
        //requestBuilder = new Request.Builder();

    }

    public static DataRequest newInstance() {
        if (dataRequest == null) {
            dataRequest = new DataRequest();
        }
        return dataRequest;
    }

    public void searchVideoNormally(String keyword, Callback callback) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("key", keyword);
        map.put("from", "mobile_list");
        map.put("version", "1.0");
        map.putAll(getPublicParams());
        requestBuilder = new Request.Builder();
        requestBuilder.url(searchURL + createParamas(map))
                .get();
        Call call = okHttpClient.newCall(requestBuilder.build());
        call.enqueue(callback);
    }

    public void getPic(String imgUrl, Callback callback) {
        requestBuilder = new Request.Builder();
        requestBuilder.get()
                .url(imgUrl + "?sign=iqiyi");
        Call call = okHttpClient.newCall(requestBuilder.build());
        call.enqueue(callback);
    }

    public void getPicHD(String imgUrl, Callback callback)
    {
        String newUrl = imgUrl.substring(0, imgUrl.length() - 4) + "_480_360.jpg";
        getPic(newUrl,callback);
    }


    public void getRecommendList(Callback callback)
    {
        requestBuilder = new Request.Builder();
        requestBuilder.url(recommendURL+createParamas(getPublicParams()))
                .get();
        Call call = okHttpClient.newCall(requestBuilder.build());
        call.enqueue(callback);
    }

    public void getChannelList(Callback callback)
    {
        Map<String,String> params = new LinkedHashMap<>();
        params.put("type","list");
        params.put("version","1.0");
        params.putAll(getPublicParams());
        requestBuilder = new Request.Builder();
        requestBuilder.url(channelListURL+createParamas(params))
                .get();
        Request request = requestBuilder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void getChannelDataDetails(String channelName,Callback callback)
    {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("type","detail");
        map.put("channel_name",channelName);
        map.put("mode","1");
        map.put("is_purchase","0");
        map.put("version","1.0");
        map.putAll(getPublicParams());
        requestBuilder = new Request.Builder();
        requestBuilder.url(channelListURL+createParamas(map))
                .get();
        Call call = okHttpClient.newCall(requestBuilder.build());
        call.enqueue(callback);
    }

    private void getChannelDetails(Callback callback)
    {
        Map<String,String> params = new LinkedHashMap<>();
        params.put("type","detail");
        params.put("version","1.0");
        params.putAll(getPublicParams());
        requestBuilder = new Request.Builder();
        requestBuilder.url(channelListURL+createParamas(params))
                .get();
        Request request = requestBuilder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    private Map<String,String> getPublicParams()
    {
        String time = Long.toString(System.currentTimeMillis());
        time = time.substring(0,time.length()-3);
        String dev_hw = "{\"cpu\":0,\"gpu\":\"\",\"mem\":\"50.4MB\"}";
        Map<String,String> map = new LinkedHashMap<>();
        map.put("app_k","f0f6c3ee5709615310c0f053dc9c65f2");
        map.put("app_v","8.4");
        map.put("app_t","0");
        map.put("platform_id","10");
        map.put("dev_os","6.0");
        map.put("dev_ua","MI 5");
        map.put("dev_hw",dev_hw);
        map.put("net_sts","1");
        map.put("scrn_sts","1");
        map.put("scrn_res","1334*750");
        map.put("scrn_dpi","153600");
        map.put("qyid","87390BD2-DACE-497B-9CD4-2FD14354B2A4");
        map.put("secure_v","1");
        map.put("secure_p","GPhone");
        map.put("core","1");
        map.put("req_sn",time);
        map.put("req_times","1");
        return map;
    }
    private String createParamas(Map<String,String> params)
    {
        StringBuilder builder = new StringBuilder();
        Set<Map.Entry<String,String>> entrySet = params.entrySet();
        Iterator<Map.Entry<String,String>> entryIterator = entrySet.iterator();
        while(entryIterator.hasNext())
        {
            Map.Entry<String,String> entry = entryIterator.next();
            builder.append(entry.getKey())
                    .append('=')
                    .append(entry.getValue())
                    .append('&');
        }
        return '?'+builder.toString().substring(0,builder.toString().length()-1);
    }
}


