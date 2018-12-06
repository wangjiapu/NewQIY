package xiyou3g.example.com.newqiy.utils;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarUtils {

    private static final int FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = 0x80000000;
    private static final int FLAG_TRANSLUCENT_STATUS = 0x04000000;
    private static final int STATUS_BAR_COLOR = 0xFFC0C0C0;

    public static void setImmersionMode(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(STATUS_BAR_COLOR);
        }
    }

    /**
     * 设置全屏
     *
     * @param activity
     * @param b
     */
    public static void setFullScreen(Activity activity, boolean b) {
        if (b) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

}
