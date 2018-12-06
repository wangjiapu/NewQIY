package xiyou3g.example.com.newqiy.framents;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xiyou3g.example.com.newqiy.R;
import xiyou3g.example.com.newqiy.activitys.LoginActivity;
import xiyou3g.example.com.newqiy.activitys.MyVIP;
import xiyou3g.example.com.newqiy.activitys.RegisterActivity;
import xiyou3g.example.com.newqiy.activitys.Setteing;
import xiyou3g.example.com.newqiy.activitys.VideoCollect;
import xiyou3g.example.com.newqiy.activitys.VideoHistory;

import static xiyou3g.example.com.newqiy.utils.Content.*;


/**
 * Created by lance on 2017/5/26.
 * 我的管理界面主要实现我的东西管理
 * 最好做到视频推送
 *
 */

public class Me_Frament extends Fragment implements View.OnClickListener{

    private ImageView loginImage;
    private TextView loginIn;
    private TextView middle;
    private TextView register;

    private LinearLayout isOpenVIP;
    private LinearLayout setting;
    private LinearLayout history;
    private LinearLayout collect;
    private LinearLayout help;
    private LinearLayout us;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.melayout,container,false);
        initWight(view);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case LOGININ_SUCCESS:
                        loginIn.setText("已登录");
                        register.setText("");
                        loginImage.setOnClickListener(null);
                        loginIn.setOnClickListener(null);
                        register.setOnClickListener(null);
                        break;
                }
            }
        };
        return view;
    }

    private void initWight(View view) {

        loginImage = (ImageView) view.findViewById(R.id.user_login);
        loginIn = (TextView) view.findViewById(R.id.user_login_in);
        middle = (TextView) view.findViewById(R.id.mid);
        register = (TextView) view.findViewById(R.id.user_register);

        isOpenVIP = (LinearLayout) view.findViewById(R.id.isOpenVIP);
        setting  = (LinearLayout) view.findViewById(R.id.setting);
        history = (LinearLayout) view.findViewById(R.id.history);
        collect = (LinearLayout) view.findViewById(R.id.collect);
        help = (LinearLayout) view.findViewById(R.id.help);
        us = (LinearLayout) view.findViewById(R.id.us);

        isOpenVIP.setOnClickListener(this);
        setting.setOnClickListener(this);
        history.setOnClickListener(this);
        collect.setOnClickListener(this);
        help.setOnClickListener(this);
        us.setOnClickListener(this);

        if(isLoginIn == 1){
            loginIn.setText("已登录");
            middle.setText("");
            register.setText("");
            loginImage.setOnClickListener(null);
            loginIn.setOnClickListener(null);
            register.setOnClickListener(null);
        }else{
            loginIn.setText("登录");
            middle.setText("|");
            register.setText("注册");
            loginImage.setOnClickListener(this);
            loginIn.setOnClickListener(this);
            register.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        switch (v.getId()){
            case R.id.user_login:
            case R.id.user_login_in:
                intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.user_register:
                intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.isOpenVIP:
                intent = new Intent(getContext(), MyVIP.class);
                startActivity(intent);
                break;
            case R.id.setting:
                intent = new Intent(getContext(), Setteing.class);
                startActivity(intent);
                break;
            case R.id.history:
                intent = new Intent(getContext(), VideoHistory.class);
                startActivity(intent);
                break;
            case R.id.collect:
                intent = new Intent(getContext(), VideoCollect.class);
                startActivity(intent);
                break;
            case R.id.help:
                builder.setTitle("帮助反馈：");
                builder.setMessage("To:XXX@xiyou3g.com");
                builder.setPositiveButton("好的",null);
                builder.create().show();
                break;
            case R.id.us:
                builder.setTitle("关于我们：");
                builder.setMessage("From 西邮移动应用开发兴趣小组。");
                builder.setPositiveButton("好的",null);
                builder.create().show();
                break;
        }
    }
}
