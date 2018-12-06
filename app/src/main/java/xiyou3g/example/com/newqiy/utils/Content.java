package xiyou3g.example.com.newqiy.utils;

import android.app.ProgressDialog;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;

import xiyou3g.example.com.newqiy.bean.VideoData;

/**
 * Created by Lance on 2017/6/5.
 */

public class Content {
    public static Handler handler;
    public static int isLoginIn = 0;
    public static String userPhone = "";

    public static final int LOGININ_SUCCESS = 1;
    public static List<VideoData> historyVideoDatas = new ArrayList<>();
    public static List<VideoData> collectVideoDatas = new ArrayList<>();

    public static ProgressDialog progressDialog;
}
