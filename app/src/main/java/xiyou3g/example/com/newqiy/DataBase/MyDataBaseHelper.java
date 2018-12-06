package xiyou3g.example.com.newqiy.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lance on 2017/6/5.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {

    //创建用户的登录信息的数据库;
    public static final String CREATE_USERDB = "create table User("
            +"id integer primary key autoincrement,"
            +"phone text,"
            +"password text)";

    //创建历史记录的信息的数据库;
    public static final String CREATE_VIDEO_HISTORY = "create table VideoHistory("
            +"id integer primary key autoincrement,"
            +"Uphone text,"
            +"Vid text,"
            +"title text,"
            +"shortTitle text,"
            +"img text,"
            +"sns_score text,"
            +"playCount text,"
            +"playCountText text,"
            +"albumId text,"
            +"tvId text,"
            +"isVip text,"                      //是否是会员视频,这里使用String类型，true为1，false为0；
            +"type text,"
            +"pType text,"
            +"dataTimeStamp text,"
            +"dataFormat text,"
            +"totalNum text,"
            +"upDateNum text,"
            +"currentTime text,"
            +"episode text)";

    //创建我的收藏的数据库;
    public static final String CREATE_VIDEO_COLLECT = "create table VideoCollect("
            +"id integer primary key autoincrement,"
            +"Uphone text,"
            +"Vid text,"
            +"title text,"
            +"shortTitle text,"
            +"img text,"
            +"sns_score text,"
            +"playCount text,"
            +"playCountText text,"
            +"albumId text,"
            +"tvId text,"
            +"isVip text,"                      //是否是会员视频,这里使用String类型，true为1，false为0；
            +"type text,"
            +"pType text,"
            +"dataTimeStamp text,"
            +"dataFormat text,"
            +"totalNum text,"
            +"upDateNum text,"
            +"currentTime text,"
            +"episode text)";

    private Context context;

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERDB);
        db.execSQL(CREATE_VIDEO_HISTORY);
        db.execSQL(CREATE_VIDEO_COLLECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists VideoHistory");
        db.execSQL("drop table if exists VideoCollect");
        onCreate(db);
    }
}
