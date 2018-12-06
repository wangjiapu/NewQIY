package xiyou3g.example.com.newqiy.activitys;

import android.Manifest;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.BoringLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.qiyi.video.playcore.ErrorCode;
import com.qiyi.video.playcore.IQYPlayerHandlerCallBack;
import com.qiyi.video.playcore.QiyiVideoView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xiyou3g.example.com.newqiy.Adapter.GuessListAdapter;
import xiyou3g.example.com.newqiy.DataBase.DataBaseModify;
import xiyou3g.example.com.newqiy.R;
import xiyou3g.example.com.newqiy.bean.VideoData;
import xiyou3g.example.com.newqiy.utils.DataRequest;
import xiyou3g.example.com.newqiy.utils.ParseDataFromHttp;
import xiyou3g.example.com.newqiy.utils.SpaceItemDecration;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static xiyou3g.example.com.newqiy.utils.Content.*;

/**
 * Created by LZL on 2017/6/5.
 */

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener{
    private PercentRelativeLayout mplayerBottomBar;
    private QiyiVideoView videoView;
    private TextView nowData;
    private SeekBar mSeekBar;
    private TextView mTotalTime;
    private TextView mCurrentTime;
    private ImageView fullScreenButton;
    private ImageView mPlayeButton;
    private RecyclerView mGuessList;
    private TextView mPlayerVideoTitle;
    private TextView mPlayerVideoType;
    private TextView mPlayerVideoCount;
    private TextView mPlayerProgressText;
    private TextView mPlayerBrightnessText;
    private RelativeLayout mPlayerContent;
    private LinearLayout scrollView;
    private ProgressBar progressBar;
    private ProgressBar listLoadingBar;
    private LinearLayout progressLayout;
    private GestureDetector gestureDetector;
    private SpaceItemDecration itemDecration = new SpaceItemDecration(5,0,0,0);

    private ImageView isCollect;
    private int isColleted;

    Bundle bundle = null;
    VideoData videoData;
    DataRequest dataRequest = DataRequest.newInstance();

    private List<VideoData> videoDataLIst;

    final int UPDATE_PROGRESS = 1;
    final int UPDATE_PROGRESS_TIME = 1000; //1s
    final int REFRESH_VIDEO_POSITION = 2;

    boolean fullScreenFlag = false;
    boolean screenChanged = false;
    boolean hasPermission = true;

    String playId = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity_layout);
        bundle = savedInstanceState;
        Intent intent = getIntent();
        videoData =(VideoData) intent.getExtras().getSerializable("videoData");
        if(videoData==null)
            finish();
        else
            playId = videoData.getTvId();
        init(savedInstanceState);
    }

    private void checkPermissionIsGet()
    {
        int result;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            result = checkSelfPermission(Manifest.permission.WRITE_SETTINGS);
            if(result== PackageManager.PERMISSION_DENIED)
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("播放时调整屏幕亮度，需要系统设置权限，为了正常播放请您同意权限请求(没有权限将无法正常播放)。");
                builder.setPositiveButton("确定申请权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            boolean flag = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_SETTINGS);
                        if(flag)
                            requestPermissions(new String[]{Manifest.permission.WRITE_SETTINGS},1);
                        else
                            hasPermission = false;
                    }
                });
                builder.setNegativeButton("取消申请", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hasPermission = false;
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_DENIED)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("非常抱歉，权限申请失败，亮度调节无法正常使用，如需使用，请在系统权限设置中打开权限。");
            builder.setPositiveButton("确定",null);
            builder.create().show();
        }
        else
        {
            hasPermission = true;
        }
    }

    public void init(Bundle savedInstanceState)
    {
        initViewAndSetLisenter();
        setCallBackOnPlayer();
        if(savedInstanceState==null)
        {
            videoView.setPlayData(playId);
        }
        else
        {
            Log.e("savedInstaceState","\tnot null");
            playId = savedInstanceState.getString("videoId");
            videoView.setPlayData(playId);
            fullScreenFlag = savedInstanceState.getBoolean("fullScreen",false);
            screenChanged = savedInstanceState.getBoolean("screenChanged");
        }
        fullScreenSettings();
        if(!fullScreenFlag)
            loadGuessList();
        mPlayerHandler.postDelayed(hiddenPlayerBottomBar,2000);
        checkPermissionIsGet();
    }
    public void loadGuessList()
    {
        listLoadingBar.setVisibility(View.VISIBLE);
        String keyWord = videoData.getShortTitle();
        dataRequest.searchVideoNormally(keyWord, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listLoadingBar.setVisibility(View.INVISIBLE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                videoDataLIst = ParseDataFromHttp.getSearchVideoList(s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(videoDataLIst.size()!=0)
                        {
                            refreshGuessListData();
                        }
                    }
                });
            }
        });
    }

    public void refreshGuessListData()
    {
        GuessListAdapter adapter = new GuessListAdapter(videoDataLIst,this);
        mGuessList.setLayoutManager(new LinearLayoutManager(this));
        mGuessList.setAdapter(adapter);
        mGuessList.removeItemDecoration(null);
        mGuessList.removeItemDecoration(itemDecration);
        mGuessList.addItemDecoration(itemDecration);
        listLoadingBar.setVisibility(View.INVISIBLE);
    }


    public void initViewAndSetLisenter()
    {
        listLoadingBar = (ProgressBar)findViewById(R.id.myPlayer_guessList_LoadingBar);
        mPlayerBrightnessText = (TextView)findViewById(R.id.myPlayer_brightness);
        mPlayerProgressText = (TextView)findViewById(R.id.myPlayer_progress_text);
        progressLayout = (LinearLayout)findViewById(R.id.myPlayer_progress_layout);
        progressBar = (ProgressBar)findViewById(R.id.myPlayer_progressBar);
        scrollView = (LinearLayout) findViewById(R.id.mplayer_recommendListLayout);
        mPlayerContent = (RelativeLayout)findViewById(R.id.mplayer_content);
        mPlayerVideoTitle = (TextView)findViewById(R.id.video_title);
        mPlayerVideoType = (TextView)findViewById(R.id.video_type_data);
        mPlayerVideoCount = (TextView)findViewById(R.id.play_count_data);
        mGuessList = (RecyclerView)findViewById(R.id.player_guess_list);
        mplayerBottomBar = (PercentRelativeLayout)findViewById(R.id.myPlayer_bottomBar);
        mPlayeButton = (ImageView)findViewById(R.id.myPlayer_play_button);
        //nowData = (TextView)findViewById(R.id.now_data);
        fullScreenButton = (ImageView)findViewById(R.id.myPlayer_fullScreenButton);
        mTotalTime = (TextView)findViewById(R.id.totlaTimeText);
        mCurrentTime = (TextView)findViewById(R.id.currentTimeText);
        mSeekBar = (SeekBar)findViewById(R.id.myPlayer_SeekBar);
        videoView = (QiyiVideoView)findViewById(R.id.myPlayer);

        isCollect = (ImageView) findViewById(R.id.isCollect);

        Log.e("isLogined",isLoginIn+"");
        if(isLoginIn == 1){
            isColleted = DataBaseModify.isVideoCollected(this,videoData.getTitle());
            Log.e("iscollect",isColleted+"");
            if(isColleted == 1){
                isCollect.setImageResource(R.drawable.collected);
            }else{
                isCollect.setImageResource(R.drawable.collectno);
            }
            isCollect.setOnClickListener(this);
        }else{
            isCollect.setOnClickListener(null);
        }

        videoView.setOnClickListener(this);
        fullScreenButton.setOnClickListener(this);
        mPlayeButton.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private  int mProgress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                {
                    mProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                videoView.seekTo(mProgress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSeekBar.setProgress(mProgress);
                videoView.seekTo(mProgress);

            }
        });

        createGestureDetectorListener();
        videoView.setOnTouchListener(onTouchListener);
        //progressBar.setVisibility(View.INVISIBLE);
        progressLayout.setVisibility(View.INVISIBLE);
        mPlayerVideoCount.setText(videoData.getPlayCountText());
        mPlayerVideoTitle.setText(videoData.getTitle());
        String type = videoData.getpType();
        if(type.equals("2"))
            mPlayerVideoType.setText("电视剧");
        else if(type.equals("3"))
            mPlayerVideoType.setText("综艺节目");
        else
            mPlayerVideoType.setText("单视频专辑");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(videoView!=null)
        {
            videoView.start();
        }
        mPlayerHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS,UPDATE_PROGRESS_TIME);
        /*if(videoView.isPlaying())
            mPlayeButton.setImageResource(R.drawable.pause);
        else
            mPlayeButton.setImageResource(R.drawable.play);*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(videoView!=null)
        {
            videoView.pause();
            //mPlayeButton.setImageResource(R.drawable.play);
        }
        mPlayerHandler.removeMessages(UPDATE_PROGRESS);
    }

    public void setCallBackOnPlayer()
    {
        videoView.setPlayerCallBack(mPlayerCallBack);
    }
    //播放器回调接口CallBack
    IQYPlayerHandlerCallBack mPlayerCallBack = new IQYPlayerHandlerCallBack() {
        @Override
        public void OnSeekSuccess(long l) {
            Log.e("OnSeekSuccess!!",Long.toString(l));
        }

        @Override
        public void OnWaiting(boolean b) {
            Log.e("OnWaiting!!",Boolean.toString(b));
            final boolean wait = b;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(wait)
                        progressLayout.setVisibility(View.VISIBLE);
                        //progressBar.setVisibility(View.VISIBLE);
                    else
                        progressLayout.setVisibility(View.INVISIBLE);
                        //progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }

        @Override
        public void OnError(ErrorCode errorCode) {
            Log.e("OnError!!",errorCode.name());
        }

        @Override
        public void OnPlayerStateChanged(int i) {
            Log.e("OnPlayerStateChanged!!",Long.toString(i));

            switch (i)
            {
                case 16:
                {
                    //Log.e("bundle",(bundle==null)+""+bundle.getBoolean("screenChanged"));
                    if(bundle!=null&&screenChanged)
                    {
                        Log.e("16","16");
                        int currentPosition = bundle.getInt("currentDuration");
                        videoView.seekTo(currentPosition);
                        int progressMax = videoView.getDuration();
                        int nowTime = videoView.getCurrentPosition();
                        mSeekBar.setMax(progressMax);
                        mSeekBar.setProgress(nowTime);
                    }
                    else
                    {
                        videoView.seekTo(0);
                    }
                    break;
                }
            }
        }
    };

    private Handler mPlayerHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case UPDATE_PROGRESS:
                {
                    int duration = videoView.getDuration();
                    int nowProgress = videoView.getCurrentPosition();
                    if(duration>0)
                    {
                        mSeekBar.setMax(duration);
                        mSeekBar.setProgress(nowProgress);
                        mTotalTime.setText(ms2hms(duration));
                        mCurrentTime.setText(ms2hms(nowProgress));
                    }
                    mPlayerHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS,UPDATE_PROGRESS_TIME);
                    break;
                }
            }
        }
    };

    public void changeToFullScreen(){
        screenChanged = true;
        fullScreenFlag = !fullScreenFlag;
        if(videoView != null){
            if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }else{
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }
    public void fullScreenSettings()
    {
        ActionBar actionBar = getSupportActionBar();
        if(!fullScreenFlag)
        {
            //fullScreenFlag = false;
            if(actionBar!=null)
                actionBar.show();
        }
        else
        {
            //fullScreenFlag = true;
            scrollView.setVisibility(View.GONE);
            if(actionBar!=null)
                actionBar.hide();
            //videoView.setVideoViewSize(getResources().getDisplayMetrics().widthPixels,getResources().getDisplayMetrics().heightPixels,true);
            mPlayerContent.setLayoutParams(new RelativeLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels,getResources().getDisplayMetrics().heightPixels));
            mPlayerContent.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    |View.SYSTEM_UI_FLAG_FULLSCREEN
                    //|View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            videoView.setVideoViewSize(screenWidth, screenHeight, true);
        }else{
            videoView.setVideoViewSize(screenWidth, (int) (screenWidth * 9.0 / 16));
        }
    }


    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.myPlayer_play_button:
            {
                if(videoView.isPlaying())
                {
                    videoView.pause();
                    mPlayeButton.setImageResource(R.drawable.play);
                }
                else
                {
                    videoView.start();
                    mPlayeButton.setImageResource(R.drawable.pause);
                }
                break;
            }
            case R.id.myPlayer_fullScreenButton:
            {
                changeToFullScreen();
                break;
            }
            case R.id.myPlayer:
            {
                mplayerBottomBar.setVisibility(View.VISIBLE);
                mPlayerHandler.postDelayed(hiddenPlayerBottomBar,2000);

                //nowData.setText(builder.toString());
                break;
            }
            case R.id.myPlayer_bottomBar:
            {

                break;
            }
            case R.id.isCollect:
                 if(isColleted == 0){
                     isCollect.setImageResource(R.drawable.collected);
                     DataBaseModify.addVideoCollect(this,videoData);
                 }else {
                     isCollect.setImageResource(R.drawable.collectno);
                     DataBaseModify.deleteVideoFromCollected(this,videoData.getTitle());
                 }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.release();
        videoView = null;
        mPlayerHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int duration = videoView.getDuration();
        int currentDuration = videoView.getCurrentPosition();
        String videoId = playId;
        outState.putString("videoId",videoId);
        outState.putInt("duration",duration);
        outState.putInt("currentDuration",currentDuration);
        outState.putBoolean("fullScreen",fullScreenFlag);
        outState.putBoolean("screenChanged",screenChanged);
    }

    private String ms2hms(int millis) {
        String result = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
        return result;
    }



    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    };

    public void createGestureDetectorListener()
    {
        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.e("onDown","onDown....");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.e("showPress","showPress....");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e("onSingleTapUp","onSingleTapUp");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                String s = "distance x:"+distanceX+"\tdistance y:"+distanceY+"\t"+e1.getX()+"\t"+e2.getX()+"\t"+e1.getY()+"\t"+e2.getY();
                Log.e("onScroll :\t",s);

                float x = e2.getX() - e1.getX();
                float y = e2.getY() - e1.getY();
                mplayerBottomBar.setVisibility(View.VISIBLE);
                mPlayerHandler.removeCallbacks(hiddenPlayerBottomBar);
                if(Math.abs(x)>Math.abs(y))
                {

                    mPlayerProgressText.setVisibility(View.VISIBLE);
                    mPlayerHandler.removeCallbacks(progressTextHiddenRun);

                    if(x>50)
                    {
                        int duration = videoView.getDuration();
                        int currentDuration = videoView.getCurrentPosition();
                        double percent = (x/500)*0.01;
                        int moreDuration = (int)(duration*percent);
                        if(currentDuration+moreDuration>duration)
                            currentDuration = duration;
                        else
                            currentDuration+=moreDuration;
                        mPlayerProgressText.setText(ms2hms(currentDuration));
                        mSeekBar.setProgress(currentDuration);
                        videoView.seekTo(currentDuration);
                        //Log.e("move Right ",Float.toString(x));
                    }
                    else if(x<-50)
                    {
                        int duration = videoView.getDuration();
                        int currentDuration = videoView.getCurrentPosition();
                        double percent = (Math.abs(x)/500)*0.01;
                        int lessDuration = (int)(duration*percent);
                        if(currentDuration-lessDuration<0)
                            currentDuration = 0;
                        else
                            currentDuration-=lessDuration;
                        mPlayerProgressText.setText(ms2hms(currentDuration));
                        mSeekBar.setProgress(currentDuration);
                        videoView.seekTo(currentDuration);
                    }
                    mPlayerHandler.postDelayed(progressTextHiddenRun,2000);
                    mPlayerHandler.postDelayed(hiddenPlayerBottomBar,2000);
                }
                else
                {
                    if(hasPermission)
                    {
                        mPlayerHandler.removeCallbacks(brightnessTextHiddenRun);
                        mPlayerBrightnessText.setVisibility(View.VISIBLE);
                    }
                    Log.e("Brightness:","\t"+getAppBrightness());
                    if(y>50&&hasPermission)
                    {
                        int brightness = getAppBrightness();
                        double percent = (y/300)*0.01;
                        int lessLight = (int)(255*percent);
                        brightness-=lessLight;
                        if(brightness>0)
                            setAppBrightness(brightness);
                        else
                        {
                            brightness = 0;
                            setAppBrightness(brightness);
                        }
                        int progress  = (int)(((brightness)/255.0)*100.0);
                        mPlayerBrightnessText.setText("亮度："+progress+"%");
                        Log.e("light","\t"+lessLight);
                        //Log.e("move Down ",Float.toString(y));
                    }
                    else if(y<-50&&hasPermission)
                    {
                        int brightness = getAppBrightness();
                        double percent = (Math.abs(y)/300)*0.01;
                        int moreLight = (int)(255*percent);
                        brightness+=moreLight;
                        if(brightness<=255)
                            setAppBrightness(brightness);
                        else
                        {
                            brightness = 255;
                            setAppBrightness(brightness);
                        }
                        Log.e("light","\t"+moreLight);
                        int progress  = (int)((brightness/255.0)*100.0);
                        mPlayerBrightnessText.setText("亮度："+progress+"%");
                        //Log.e("move Up ",Float.toString(y));
                    }
                    if(hasPermission)
                        mPlayerHandler.postDelayed(brightnessTextHiddenRun,2000);
                }

                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.e("LongPress","press....");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float x = e2.getX() - e1.getX();
                float y = e2.getY() - e1.getY();
                if(Math.abs(x)>Math.abs(y))
                {
                    if(x>50)
                    {
                        Log.e("move Right ",Float.toString(x));
                    }
                    else if(x<-50)
                    {
                        Log.e("move Left ",Float.toString(x));
                    }
                }
                else
                {
                    if(y>50)
                    {
                        Log.e("move Down ",Float.toString(y));
                    }
                    else if(y<-50)
                    {
                        Log.e("move Up ",Float.toString(y));
                    }
                }
                return false;
            }
        });
    }

    Runnable progressTextHiddenRun = new Runnable() {
        @Override
        public void run() {
            mPlayerProgressText.setVisibility(View.INVISIBLE);
        }
    };
    Runnable brightnessTextHiddenRun = new Runnable() {
        @Override
        public void run() {
            mPlayerBrightnessText.setVisibility(View.INVISIBLE);
        }
    };

    Runnable hiddenPlayerBottomBar = new Runnable() {
        @Override
        public void run() {
            mplayerBottomBar.setVisibility(View.INVISIBLE);
        }
    };

    private int getAppBrightness()
    {
        int brightness = 0;
        try
        {
            brightness = Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS);
        }catch (Settings.SettingNotFoundException e)
        {
            e.printStackTrace();
        }
        return brightness;
    }
    private void setAppBrightness(int brightness)
    {
        Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,brightness);
    }

    public void changeVideo(VideoData videoData)
    {
        screenChanged = false;
        this.videoData = videoData;
        playId = videoData.getTvId();
        progressLayout.setVisibility(View.INVISIBLE);
        mPlayerVideoCount.setText(videoData.getPlayCountText());
        mPlayerVideoTitle.setText(videoData.getTitle());
        String type = videoData.getpType();
        if(type.equals("2"))
            mPlayerVideoType.setText("电视剧");
        else if(type.equals("3"))
            mPlayerVideoType.setText("综艺节目");
        else
            mPlayerVideoType.setText("单视频专辑");
        videoView.setPlayData(playId);
        videoView.seekTo(0);
        mGuessList.setAdapter(null);
        loadGuessList();
    }

}
