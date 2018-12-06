package xiyou3g.example.com.newqiy;

import android.app.Application;

import com.iqiyi.player.qyplayer.IQiyiPlayer;
import com.qiyi.video.playcore.QiyiVideoView;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by 蒲家旺 on 2017/6/15.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        QiyiVideoView.init(this);
    }
}
