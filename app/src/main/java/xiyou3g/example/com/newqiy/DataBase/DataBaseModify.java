package xiyou3g.example.com.newqiy.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import xiyou3g.example.com.newqiy.bean.VideoData;

import static xiyou3g.example.com.newqiy.utils.Content.*;

/**
 * Created by Lance on 2017/6/9.
 */

public class DataBaseModify {

    static MyDataBaseHelper myDataBaseHelper;
    static SQLiteDatabase db;

    //播放历史的数据库;
    public static void addVideoHistory(Context context, VideoData videoData){
        myDataBaseHelper = new MyDataBaseHelper(context,"NewQIY.db",null,1);
        db = myDataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Uphone",userPhone);
        values.put("Vid",videoData.getId());
        values.put("title",videoData.getTitle());
        values.put("shortTitle",videoData.getShortTitle());
        values.put("img",videoData.getImg());
        values.put("sns_score",videoData.getSns_score());
        values.put("playCount",videoData.getPlayCount());
        values.put("playCountText",videoData.getPlayCountText());
        values.put("albumId",videoData.getAlbumId());
        values.put("tvId",videoData.getTvId());
        if(videoData.isVip()){
            values.put("isVip","1");
        }else{
            values.put("isVip","0");
        }
        values.put("type",videoData.getType());
        values.put("pType",videoData.getpType());
        values.put("dataTimeStamp",videoData.getDataTimeStamp());
        values.put("dataFormat",videoData.getDataFormat());
        values.put("totalNum",videoData.getTotalNum());
        values.put("upDateNum",videoData.getUpDateNum());
        values.put("currentTime",getCurrentTime());
        values.put("episode",videoData.getEpisode());
        db.insert("VideoHistory",null,values);
    }

    private static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = (c.get(Calendar.MONTH))+1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute  = c.get(Calendar.MINUTE);
        return year+"-"+month+"-"+date+" "+hour+":"+minute;
    }

    //用户信息的数据库;
    public static void addUserDataBase(Context context,String number,String password){
        myDataBaseHelper = new MyDataBaseHelper(context,"NewQIY.db",null,1);
        db = myDataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone",number);
        values.put("password",password);
        db.insert("User",null,values);
    }

    //得到播放历史的数据；
    public static List<VideoData> getVideoHistoryList(Context context){
        List<VideoData> videoDataList  = new ArrayList<>();

        myDataBaseHelper = new MyDataBaseHelper(context,"NewQIY.db",null,1);
        db = myDataBaseHelper.getWritableDatabase();

        Cursor cursor = db.query("VideoHistory",null,"Uphone = "+ userPhone,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                VideoData videoData = new VideoData();
                videoData.setId(cursor.getString(cursor.getColumnIndex("Vid")));
                videoData.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                videoData.setShortTitle(cursor.getString(cursor.getColumnIndex("shortTitle")));
                videoData.setImg(cursor.getString(cursor.getColumnIndex("img")));
                videoData.setSns_score(cursor.getString(cursor.getColumnIndex("sns_score")));
                videoData.setPlayCount(cursor.getString(cursor.getColumnIndex("playCount")));
                videoData.setPlayCountText(cursor.getString(cursor.getColumnIndex("playCountText")));
                videoData.setAlbumId(cursor.getString(cursor.getColumnIndex("albumId")));
                videoData.setCurrentTime(cursor.getString(cursor.getColumnIndex("currentTime")));
                videoData.setTvId(cursor.getString(cursor.getColumnIndex("tvId")));
                if(cursor.getString(cursor.getColumnIndex("isVip")).equals("1")){
                    videoData.setVip(true);
                }else{
                    videoData.setVip(false);
                }
                videoData.setType(cursor.getString(cursor.getColumnIndex("type")));
                videoData.setpType(cursor.getString(cursor.getColumnIndex("pType")));
                videoData.setDataTimeStamp(cursor.getString(cursor.getColumnIndex("dataTimeStamp")));
                videoData.setDataFormat(cursor.getString(cursor.getColumnIndex("dataFormat")));
                videoData.setTotalNum(cursor.getString(cursor.getColumnIndex("totalNum")));
                videoData.setUpDateNum(cursor.getString(cursor.getColumnIndex("upDateNum")));
                videoData.setEpisode(cursor.getString(cursor.getColumnIndex("episode")));
                videoDataList.add(videoData);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return videoDataList;
    }

    //我的收藏数据库;
    public static void addVideoCollect(Context context,VideoData videoData){
        myDataBaseHelper = new MyDataBaseHelper(context,"NewQIY.db",null,1);
        db = myDataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Uphone",userPhone);
        values.put("Vid",videoData.getId());
        values.put("title",videoData.getTitle());
        values.put("shortTitle",videoData.getShortTitle());
        values.put("img",videoData.getImg());
        values.put("sns_score",videoData.getSns_score());
        values.put("playCount",videoData.getPlayCount());
        values.put("playCountText",videoData.getPlayCountText());
        values.put("albumId",videoData.getAlbumId());
        values.put("tvId",videoData.getTvId());
        if(videoData.isVip()){
            values.put("isVip","1");
        }else{
            values.put("isVip","0");
        }
        values.put("type",videoData.getType());
        values.put("pType",videoData.getpType());
        values.put("dataTimeStamp",videoData.getDataTimeStamp());
        values.put("dataFormat",videoData.getDataFormat());
        values.put("totalNum",videoData.getTotalNum());
        values.put("upDateNum",videoData.getUpDateNum());
        values.put("currentTime",getCurrentTime());
        values.put("episode",videoData.getEpisode());
        db.insert("VideoCollect",null,values);
    }

    //得到我的收藏的数据；
    public static List<VideoData> getVideoCollectList(Context context){
        List<VideoData> videoDataList  = new ArrayList<>();

        myDataBaseHelper = new MyDataBaseHelper(context,"NewQIY.db",null,1);
        db = myDataBaseHelper.getWritableDatabase();

        Cursor cursor = db.query("VideoCollect",null,"Uphone = "+ userPhone,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                VideoData videoData = new VideoData();
                videoData.setId(cursor.getString(cursor.getColumnIndex("Vid")));
                videoData.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                videoData.setShortTitle(cursor.getString(cursor.getColumnIndex("shortTitle")));
                videoData.setImg(cursor.getString(cursor.getColumnIndex("img")));
                videoData.setSns_score(cursor.getString(cursor.getColumnIndex("sns_score")));
                videoData.setPlayCount(cursor.getString(cursor.getColumnIndex("playCount")));
                videoData.setPlayCountText(cursor.getString(cursor.getColumnIndex("playCountText")));
                videoData.setAlbumId(cursor.getString(cursor.getColumnIndex("albumId")));
                videoData.setTvId(cursor.getString(cursor.getColumnIndex("tvId")));
                if(cursor.getString(cursor.getColumnIndex("isVip")).equals("1")){
                    videoData.setVip(true);
                }else{
                    videoData.setVip(false);
                }
                videoData.setType(cursor.getString(cursor.getColumnIndex("type")));
                videoData.setpType(cursor.getString(cursor.getColumnIndex("pType")));
                videoData.setDataTimeStamp(cursor.getString(cursor.getColumnIndex("dataTimeStamp")));
                videoData.setDataFormat(cursor.getString(cursor.getColumnIndex("dataFormat")));
                videoData.setTotalNum(cursor.getString(cursor.getColumnIndex("totalNum")));
                videoData.setCurrentTime(cursor.getString(cursor.getColumnIndex("currentTime")));
                videoData.setUpDateNum(cursor.getString(cursor.getColumnIndex("upDateNum")));
                videoData.setEpisode(cursor.getString(cursor.getColumnIndex("episode")));
                videoDataList.add(videoData);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return videoDataList;
    }

    //查询是否是收藏的视频；
    public static int isVideoCollected(Context context,String title){
        myDataBaseHelper = new MyDataBaseHelper(context,"NewQIY.db",null,1);
        db = myDataBaseHelper.getWritableDatabase();

        Cursor cursor = db.query("VideoCollect",null,"Uphone = "+userPhone,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(cursor.getColumnIndex("title")).equals(title)){
                    return 1;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return 0;
    }

    //移除收藏列表中的某个视频;
    public static void deleteVideoFromCollected(Context context,String title){
        myDataBaseHelper = new MyDataBaseHelper(context,"NewQIY.db",null,1);
        db = myDataBaseHelper.getWritableDatabase();

        db.delete("VideoCollect","Uphone = ? and title = ?",new String[]{userPhone,title});
        Log.e("kkkkkkkkkkkkkkk","successed");
    }

}
