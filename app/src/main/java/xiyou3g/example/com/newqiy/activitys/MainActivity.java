package xiyou3g.example.com.newqiy.activitys;


import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import xiyou3g.example.com.newqiy.R;
import xiyou3g.example.com.newqiy.framents.Me_Frament;
import xiyou3g.example.com.newqiy.framents.Recom_Frament;
import xiyou3g.example.com.newqiy.utils.StatusBarUtils;
import xiyou3g.example.com.newqiy.utils.Voice_id;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private Fragment mf;//我的frament;
    private Fragment rf;//推荐fragment;

    //布局
    LinearLayout recom;//推荐
    LinearLayout me;//我的
    FloatingActionButton fab;//语音输入按钮
    FrameLayout fl;

    Dialog mDialog = null;

    private MainHandler mHandler = new MainHandler(this);


    static class MainHandler extends Handler {
        WeakReference<MainActivity> mainActivity;

        MainHandler(MainActivity activity) {
            this.mainActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mainActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        searchPlay((String) msg.obj, activity);
                        break;
                }
            }
        }
    }

    /**
     * 广播接收者
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getStringExtra("result");
            if (!action.equals("")) {
                Message message = new Message();
                message.what = 1;
                message.obj = action;
                mHandler.sendMessage(message);
            }
        }
    };

    @Override
    protected void onResume() {
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("NewQIY");
        this.registerReceiver(mBroadcastReceiver, mIntentFilter);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(MainActivity.this, true);
        StatusBarUtils.setImmersionMode(getWindow());
        setContentView(R.layout.activity_main);
        initView();
        initEvents();
        selectImg(0);
    }


    private void initEvents() {
        recom.setOnClickListener(this);
        me.setOnClickListener(this);
        fab.setOnClickListener(this);
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Voice_id vi = Voice_id.getInstance();
                vi.setMcontext(MainActivity.this);
                vi.discern();
                return true;
            }
        });
    }

    private void initView() {

        recom = findViewById(R.id.commend);
        me = findViewById(R.id.me);
        fab = findViewById(R.id.fab);
        fl = findViewById(R.id.round_fragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commend://推荐界面显示
                selectImg(0);
                break;
            case R.id.me://我的界面显示
                selectImg(1);
                break;
            case R.id.fab://点击事件
                mDialog = new Dialog(this, R.style.ActionSearchDialogStyle);
                View view = LayoutInflater.from(this).inflate(R.layout.searchdialog, null);
                final EditText et = (EditText) view.findViewById(R.id.et);
                Button bt = (Button) view.findViewById(R.id.bt);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = et.getText().toString().trim();
                        if (!s.equals("")) {
                            searchPlay(s, MainActivity.this);
                            mDialog.dismiss();
                        }
                    }
                });
                mDialog.setContentView(view);
                Window w = mDialog.getWindow();
                w.setGravity(Gravity.TOP);
                WindowManager.LayoutParams lp = w.getAttributes();
                lp.width = getResources().getDisplayMetrics().widthPixels;
                w.setAttributes(lp);
                mDialog.show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        et.setFocusable(true);
                        et.setFocusableInTouchMode(true);
                        et.requestFocus();
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(et, 0);
                    }
                }, 100);

                break;
        }
    }

    //选择哪个fragment显示
    private void selectImg(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideFragment(ft);
        if (i == 0) {
            if (rf == null) {
                rf = new Recom_Frament();
                ft.add(R.id.round_fragment, rf);
            } else {
                ft.show(rf);
            }
        } else if (i == 1) {
            if (mf == null) {
                mf = new Me_Frament();
                ft.add(R.id.round_fragment, mf);
            } else {
                ft.show(mf);
            }
        }
        ft.commit();
    }

    //隐藏未被选中的fragment;
    private void hideFragment(FragmentTransaction ft) {
        if (mf != null)
            ft.hide(mf);
        if (rf != null)
            ft.hide(rf);
    }


    /**
     * 查找剧目信息,并开启新的活动显示
     *
     * @param s
     */
    private static void searchPlay(String s, Activity activity) {
        Intent search_intent = new Intent(activity, ToFind_Play.class);
        search_intent.putExtra("date", s);
        activity.startActivity(search_intent);
    }
}
