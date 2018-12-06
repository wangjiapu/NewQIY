package xiyou3g.example.com.newqiy.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

import xiyou3g.example.com.newqiy.DataBase.DataBaseModify;
import xiyou3g.example.com.newqiy.activitys.PlayerActivity;
import xiyou3g.example.com.newqiy.bean.VideoData;
import static xiyou3g.example.com.newqiy.utils.Content.*;

/**
 * Created by 蒲家旺 on 2017/6/18.
 */

public class OpenPlayIntent {
    public static void startplay(Context context, VideoData vd) {
        if(isLoginIn == 1){
            DataBaseModify.addVideoHistory(context,vd);
        }
        Intent intent = new Intent(context, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoData",vd);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
