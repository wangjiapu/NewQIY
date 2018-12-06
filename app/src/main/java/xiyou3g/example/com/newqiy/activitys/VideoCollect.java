package xiyou3g.example.com.newqiy.activitys;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import xiyou3g.example.com.newqiy.Adapter.VideoHistoryAdapter;
import xiyou3g.example.com.newqiy.DataBase.DataBaseModify;
import xiyou3g.example.com.newqiy.R;

import static xiyou3g.example.com.newqiy.utils.Content.*;

/**
 * Created by Lance on 2017/6/10.
 */

public class VideoCollect extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private RecyclerView recyclerView;
    private RelativeLayout historyNull;
    private RelativeLayout nologin;

    private LinearLayoutManager linearLayoutManager;
    private VideoHistoryAdapter videoHistoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_collect);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initWight();
    }

    private void initWight() {
        collectVideoDatas.clear();
        back = (ImageView) findViewById(R.id.back);
        recyclerView = (RecyclerView) findViewById(R.id.history_recyclerView);
        historyNull = (RelativeLayout) findViewById(R.id.history_null);
        nologin = (RelativeLayout) findViewById(R.id.nologin);

        back.setOnClickListener(this);

        if(isLoginIn == 0){
            nologin.setVisibility(View.VISIBLE);
            historyNull.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }else{
            nologin.setVisibility(View.GONE);
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("提示：");
            progressDialog.setMessage("正在加载...");
            progressDialog.show();
            collectVideoDatas = DataBaseModify.getVideoCollectList(VideoCollect.this);
            progressDialog.dismiss();

            if(collectVideoDatas.size() == 0){
                historyNull.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }else{
                historyNull.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                videoHistoryAdapter = new VideoHistoryAdapter(this,collectVideoDatas);
                recyclerView.setAdapter(videoHistoryAdapter);
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
