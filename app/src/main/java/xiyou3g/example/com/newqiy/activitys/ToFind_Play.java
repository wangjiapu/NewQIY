package xiyou3g.example.com.newqiy.activitys;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xiyou3g.example.com.newqiy.R;
import xiyou3g.example.com.newqiy.bean.VideoData;
import xiyou3g.example.com.newqiy.utils.DataRequest;
import xiyou3g.example.com.newqiy.utils.ParseDataFromHttp;
import xiyou3g.example.com.newqiy.Adapter.VideoAdapter;

/**
 * 传入要搜索的剧目名称，在次活动中将其显示出来，并实现模糊查找的效果
 */
public class ToFind_Play extends Activity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private List<VideoData> mlist=new ArrayList<>();
    private VideoAdapter mAdapter;

    private DataRequest dataRequest;
    private static String value;//上一个activity传入的要搜索的字符串
    private static String refresh_str;

    private LinearLayout loading;
    private ImageView loading_image;

    private AnimationDrawable mAnimation;

    private EditText play_et;
    private TextView cancel;

    private Timer mTimer;//计时器
    private Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case 1://刷新界面
                   loading.setVisibility(View.GONE);
                   mAnimation.stop();
                   recyclerView.setVisibility(View.VISIBLE);
                   mAdapter=new VideoAdapter(ToFind_Play.this,mlist,value);
                   recyclerView.setAdapter(mAdapter);
                   mTimer.cancel();//取消计时
                   break;
               case 2:
                   mAnimation.stop();
                   mAdapter=new VideoAdapter(ToFind_Play.this,mlist,refresh_str);
                   recyclerView.setAdapter(mAdapter);
                   mTimer.cancel();
                   break;
           }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        value=getIntent().getStringExtra("date");
        setContentView(R.layout.toshowplay);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initData();
        initView();
        initEvent();

    }



    private void initData() {
        dataRequest=DataRequest.newInstance();
        dataRequest.searchVideoNormally(value, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(ToFind_Play.this,"出错了，请刷新！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                mlist.addAll(ParseDataFromHttp.getSearchVideoList(s));
            }
        });
    }


    private void initView() {
        cancel=(TextView)findViewById(R.id.cancel);
        play_et=(EditText)findViewById(R.id.play_edittext);
        play_et.setText(value);
        loading=(LinearLayout)findViewById(R.id.loading_animation);
        loading_image=(ImageView)findViewById(R.id.loading_image);
        recyclerView=(RecyclerView)findViewById(R.id.videolist);
        startAn();//开启动画
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        startTimer(1);//开启计时器
    }

    private void initEvent() {
        cancel.setOnClickListener(this);
        play_et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER){
                    ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ToFind_Play.this.getCurrentFocus()
                                    .getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    search();
                }
                return false;
            }
        });
    }

    private void search() {
        refresh_str=play_et.getText().toString().trim();
        if(!refresh_str.equals("")){
            startAn();
            startTimer(2);
            refreshData(refresh_str);
        }
    }

    private void refreshData(String str) {
        mlist.clear();
        dataRequest.searchVideoNormally(str, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(ToFind_Play.this,"出错了，请刷新！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                mlist.addAll(ParseDataFromHttp.getSearchVideoList(s));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                finish();
                break;
        }
    }
    private void startAn() {
        if(mAnimation==null){
            loading_image.setBackgroundResource(R.drawable.loadingdialog_animation);
            mAnimation=(AnimationDrawable)loading_image.getBackground();
            loading_image.post(new Runnable() {
                @Override
                public void run() {
                    mAnimation.start();
                }
            });
        }else {
            mAnimation.start();
        }
    }
    private void startTimer(final int flag) {
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!mlist.isEmpty()){
                    Message message=new Message();
                    switch (flag){
                        case 1:
                            message.what=1;
                            mHandler.sendMessage(message);
                            break;
                        case 2:
                            message.what=2;
                            mHandler.sendMessage(message);
                            break;
                    }
                }
            }
        },0,30);
    }
}
