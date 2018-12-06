package xiyou3g.example.com.newqiy.framents;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xiyou3g.example.com.newqiy.Adapter.RecyclerAdapter;
import xiyou3g.example.com.newqiy.R;
import xiyou3g.example.com.newqiy.bean.ClassifyData;
import xiyou3g.example.com.newqiy.bean.DocumentaryData;
import xiyou3g.example.com.newqiy.bean.DongmanData;
import xiyou3g.example.com.newqiy.bean.MovieData;
import xiyou3g.example.com.newqiy.bean.RecommendData;
import xiyou3g.example.com.newqiy.bean.RecycleViewItemData;
import xiyou3g.example.com.newqiy.bean.RollData;
import xiyou3g.example.com.newqiy.bean.TVData;
import xiyou3g.example.com.newqiy.bean.VideoData;
import xiyou3g.example.com.newqiy.bean.ZhongyiData;
import xiyou3g.example.com.newqiy.bean.lists;
import xiyou3g.example.com.newqiy.utils.DataRequest;
import xiyou3g.example.com.newqiy.utils.ParseDataFromHttp;

/**
 * Created by DickJampink on 2017/6/1.
 *
 */

public class RecomFragment extends android.support.v4.app.Fragment {

    public static final int ROLLVIEW = 0;
    public static final int CLASSIFY_TV = 2;
    public static final int CLASSIFY_MOVIE = 1;
    public static final int CLASSIFY_DONMAN = 4;
    public static final int CLASSIFY_ZIXUN = 25;
    public static final int CLASSIFY_STAR = 6;
    public static final int CLASSIFY_Documentary = 3;
    public static final int CLASSIFY_MOVIE_Item = 111;
    public static final int CLASSIFY_MOVIE_Item_noTitle = 1111;
    public static final int CLASSIFY_TV_Item = 222;
    public static final int CLASSIFY_TV_Item_noTitle = 2222;
    public static final int CLASSIFY_DM_Item = 444;
    public static final int CLASSIFY_DM_Item_noTitle = 4444;
    public static final int CLASSIFY_Item_noTitle_5 = 555;
    public static final int CLASSIFY_Item_5 = 5555;
    public static final int CLASSIFY_ZY_Item_noTitle = 6666;
    public static final int CLASSIFY_ZY_Item = 666;
    public static final int DATAOVER = 999;



    //信号量，用于传递信息
    public static final String TAB_PAGE = "tab_page";
    public static final String GET_CONTEXT = "get_context";

    //页数作为 viewpager 和 tablayout 的关联信号量
    private int mPage = 0;

    //fragment 需要的显示的视图
    View getDataview;

    //该页面的recyclerView的适配器
    private RecyclerAdapter adapter;

    //加载视图的进度条
    private ProgressBar progressBar;


    //用newInstance()方法是一种“静态工厂方法",让我们在初始化和设置一个新的fragment的时候省去调用它的构造函数和额外的setter方法。
    // 为你的Fragment提供静态工厂方法是一种好的做法,因为它封装和抽象了在客户端构造对象所需的步骤
    public static RecomFragment newInstance(int what_page) {

        Bundle args = new Bundle();
        args.putInt(TAB_PAGE, what_page);
        RecomFragment fragment = new RecomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(TAB_PAGE);

        Log.e("The mPage is :", "" + mPage);
        adapter = new RecyclerAdapter();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (mPage) {
                    case 1:
                        if (lists.rList.size() > 0 || lists.cList.size() > 0)
                            Init();
                        break;
                    case 2:
                        if (lists.MVlist.size() > 0) {
                            Init();
                        }
                        break;
                    case 3:
                        if (lists.TVlist.size() > 0) {
                            Init();
                        }
                        break;
                    case 4:if (lists.DDlist.size()>0)
                        Init();
                        break;
                    case 5:
                        if (lists.Dmlist.size()>0)
                            Init();
                        break;
                    case 6:
                        if (lists.ZYlist.size()>0)
                            Init();
                        break;
                }
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();
        Handler handler = adapter.getHander();
        if(handler!=null)
            handler.removeCallbacksAndMessages(null);
    }

    private Handler DataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case DATAOVER:
                    Init();
                    RecyclerView recyclerView = (RecyclerView) getDataview.findViewById(R.id.recom_recycler);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getDataview.getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    if (progressBar.getVisibility() == View.VISIBLE) {
                        progressBar.setVisibility(View.GONE);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDataview = inflater.inflate(R.layout.recomfragment, container, false);
        progressBar = (ProgressBar) getDataview.findViewById(R.id.progress_bar);
        Log.w("onCreateView  mpage is", "" + mPage);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (mPage) {
                    case 1:
                        if (lists.rList.size() == 0 || lists.cList.size() == 0) {
                            if (progressBar.getVisibility() == View.GONE) {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                            getRecommendData();
                        }
                        break;
                    case 2:
                        if (lists.MVlist.size()==0){
                            if (progressBar.getVisibility() == View.GONE) {
                                progressBar.setVisibility(View.VISIBLE);
                            }getMovieData();
                        }

                        break;
                    case 3:
                        if (lists.TVlist.size()==0){
                            if (progressBar.getVisibility() == View.GONE) {
                                progressBar.setVisibility(View.VISIBLE);
                            }getTVItem();
                        }

                        break;
                    case 4:
                        if (lists.DDlist.size()==0){
                            if (progressBar.getVisibility() == View.GONE) {
                                progressBar.setVisibility(View.VISIBLE);
                            }getDocumentaryData();
                        }

                        break;
                    case 5:
                        if (lists.Dmlist.size()==0)
                        {
                            if (progressBar.getVisibility() == View.GONE) {
                                progressBar.setVisibility(View.VISIBLE);
                            }getDonmanItem();
                        }

                        break;
                    case 6:
                        if (lists.ZYlist.size()==0)
                        {
                            if (progressBar.getVisibility() == View.GONE) {
                                progressBar.setVisibility(View.VISIBLE);
                            }getZhongyiItem();
                        }

                    default:
                        break;
                }
            }
        });
        RecyclerView recyclerView = (RecyclerView) getDataview.findViewById(R.id.recom_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getDataview.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return getDataview;
    }

    private void Init() {
        //推荐页
        if (mPage == 1) {
            /*创建轮播图，page == 1 是 为了固定轮播图只在推荐页中使用*/
            RecycleViewItemData<RollData> RollItem = new RecycleViewItemData<>();
            List<RollData> mList = lists.getmList();
            RollItem.setDataType(ROLLVIEW);
            RollItem.setT(new RollData(mList));
            adapter.getData().add(RollItem);

            RecycleViewItemData<ClassifyData> ClassifyItem_TV = new RecycleViewItemData<>();
            if (lists.getCData(CLASSIFY_TV) != null)
                ClassifyItem_TV.setT(lists.getCData(CLASSIFY_TV));
            ClassifyItem_TV.setDataType(CLASSIFY_TV);
            adapter.getData().add(ClassifyItem_TV);

            RecycleViewItemData<ClassifyData> ClassifyItem_MOVIE = new RecycleViewItemData<>();
            if (lists.getCData(CLASSIFY_MOVIE) != null)
                ClassifyItem_MOVIE.setT(lists.getCData(CLASSIFY_MOVIE));
            ClassifyItem_MOVIE.setDataType(CLASSIFY_MOVIE);
            adapter.getData().add(ClassifyItem_MOVIE);

            RecycleViewItemData<ClassifyData> ClassifyItem_ZHONGYI = new RecycleViewItemData<>();
            if (lists.getCData(CLASSIFY_STAR) != null)
                ClassifyItem_ZHONGYI.setT(lists.getCData(CLASSIFY_STAR));
            ClassifyItem_ZHONGYI.setDataType(CLASSIFY_STAR);
            adapter.getData().add(ClassifyItem_ZHONGYI);

            RecycleViewItemData<ClassifyData> ClassifyItem_ZIXUN = new RecycleViewItemData<>();
            if (lists.getCData(CLASSIFY_ZIXUN) != null)
                ClassifyItem_ZIXUN.setT(lists.getCData(CLASSIFY_ZIXUN));
            ClassifyItem_ZIXUN.setDataType(CLASSIFY_ZIXUN);
            adapter.getData().add(ClassifyItem_ZIXUN);
        }
        //电影
        if (mPage == 2) {
            RecycleViewItemData<MovieData> ClassifyItem_MOVIE = new RecycleViewItemData<>();
            if (lists.getMVlist(CLASSIFY_MOVIE) != null)
                ClassifyItem_MOVIE.setT(lists.getMVlist(CLASSIFY_MOVIE));
            ClassifyItem_MOVIE.setDataType(CLASSIFY_MOVIE_Item);
            adapter.getData().add(ClassifyItem_MOVIE);

            RecycleViewItemData<MovieData> ClassifyItem_MOVIE_NT = new RecycleViewItemData<>();
            if (lists.getMVlist(CLASSIFY_MOVIE) != null)
                ClassifyItem_MOVIE_NT.setT(lists.getMVlist(CLASSIFY_MOVIE));
            ClassifyItem_MOVIE_NT.setDataType(CLASSIFY_MOVIE_Item_noTitle);
            adapter.getData().add(ClassifyItem_MOVIE_NT);
        }
        //电视剧
        if (mPage == 3) {
            RecycleViewItemData<TVData> ClassifyItem_TV = new RecycleViewItemData<>();
            if (lists.getTVlist(CLASSIFY_TV) != null)
                ClassifyItem_TV.setT(lists.getTVlist(CLASSIFY_TV));
            ClassifyItem_TV.setDataType(CLASSIFY_TV_Item);
            adapter.getData().add(ClassifyItem_TV);

            RecycleViewItemData<TVData> ClassifyItem_TV_NT = new RecycleViewItemData<>();
            if (lists.getTVlist(CLASSIFY_TV) != null)
                ClassifyItem_TV_NT.setT(lists.getTVlist(CLASSIFY_TV));
            ClassifyItem_TV_NT.setDataType(CLASSIFY_TV_Item_noTitle);
            adapter.getData().add(ClassifyItem_TV_NT);

        }
        //纪录片
        if (mPage == 4) {
                RecycleViewItemData<DocumentaryData> ClassifyItem_DD = new RecycleViewItemData<>();
                if (lists.getDDlist(CLASSIFY_Documentary) != null)
                    ClassifyItem_DD.setT(lists.getDDlist(CLASSIFY_Documentary));
                ClassifyItem_DD.setDataType(CLASSIFY_Item_5);
                adapter.getData().add(ClassifyItem_DD);

                RecycleViewItemData<DocumentaryData> ClassifyItem_DD_2 = new RecycleViewItemData<>();
                if (lists.getDDlist(CLASSIFY_Documentary) != null)
                    ClassifyItem_DD_2.setT(lists.getDDlist(CLASSIFY_Documentary));
                ClassifyItem_DD_2.setDataType(CLASSIFY_Item_noTitle_5);
                adapter.getData().add(ClassifyItem_DD_2);
        }
        //动漫
        if (mPage == 5) {
            RecycleViewItemData<DongmanData> ClassifyItem_DM = new RecycleViewItemData<>();
            if (lists.getDmlist(CLASSIFY_DONMAN) != null)
                ClassifyItem_DM.setT(lists.getDmlist(CLASSIFY_DONMAN));
            ClassifyItem_DM.setDataType(CLASSIFY_DM_Item);
            adapter.getData().add(ClassifyItem_DM);

            RecycleViewItemData<DongmanData> ClassifyItem_DM_NT = new RecycleViewItemData<>();
            if (lists.getDmlist(CLASSIFY_DONMAN) != null)
                ClassifyItem_DM_NT.setT(lists.getDmlist(CLASSIFY_DONMAN));
            ClassifyItem_DM_NT.setDataType(CLASSIFY_DM_Item_noTitle);
            adapter.getData().add(ClassifyItem_DM_NT);
        }
        //综艺
        if (mPage == 6) {
            RecycleViewItemData<ZhongyiData> ClassifyItem_ZY = new RecycleViewItemData<>();
            if (lists.getZYlist(CLASSIFY_STAR) != null)
                ClassifyItem_ZY.setT(lists.getZYlist(CLASSIFY_STAR));
            ClassifyItem_ZY.setDataType(CLASSIFY_ZY_Item);
            adapter.getData().add(ClassifyItem_ZY);

            RecycleViewItemData<ZhongyiData> ClassifyItem_ZY_2 = new RecycleViewItemData<>();
            if (lists.getZYlist(CLASSIFY_STAR) != null)
                ClassifyItem_ZY_2.setT(lists.getZYlist(CLASSIFY_STAR));
            ClassifyItem_ZY_2.setDataType(CLASSIFY_ZY_Item_noTitle);
            adapter.getData().add(ClassifyItem_ZY_2);

        }

    }
    //获取推荐页数据
    private void getRecommendData() {
        final DataRequest dataRequest = DataRequest.newInstance();
        //获取推荐页信息
        dataRequest.getRecommendList(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("进入了", "InitRollData  ->onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful())
                    Log.e("进入了", "onResponse but response is not");
                else {
                    Log.e("进入了", "onResponse");
                    final List<RecommendData> list = ParseDataFromHttp.getRcommendDataList(response.body().string());
                    Log.e("RecommendDataList is ::", list.toString());
                    getRollImage(list, dataRequest);

                    getRecomClassifyData(list, dataRequest);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DataHandler.sendEmptyMessage(DATAOVER);
                        }
                    });

                }
            }
        });
    }

    private void getDocumentaryData() {
        final DataRequest dataRequest = DataRequest.newInstance();
        dataRequest.getChannelDataDetails("纪录片", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                List<VideoData> Vlist = ParseDataFromHttp.getChannelVideoList(s);
                Log.e("The 纪录片 is :: ", Vlist.toString());
                final DocumentaryData documentaryData = new DocumentaryData();
                documentaryData.setID("3");
                documentaryData.setTitleBar("纪录片");
                for (int i = 0; i < Vlist.size(); i++) {
                    String pic = Vlist.get(i).getImg();
                    final int finalI = i;
                    dataRequest.getPicHD(pic, new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                       }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                                documentaryData.setImage(finalI, ParseDataFromHttp.getPicFromResponse(response));
                             }
                        });
                    documentaryData.setVideoData(Vlist.get(finalI), finalI);
                    documentaryData.setBigtitle(finalI,Vlist.get(finalI).getTitle());
                    documentaryData.setMinTitle(finalI,Vlist.get(finalI).getShortTitle());
                    documentaryData.setImageMsg(finalI,Vlist.get(finalI).getDataFormat());
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lists.DDlist.add(documentaryData);
                        DataHandler.sendEmptyMessage(DATAOVER);
                    }
                });
            }
        });
    }

    private void getMovieData() {
        final DataRequest dataRequest = DataRequest.newInstance();
        dataRequest.getChannelDataDetails("电影", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                List<VideoData> Vlist = ParseDataFromHttp.getChannelVideoList(s);
                Log.e("The 电影 is :: ", Vlist.toString());
                final MovieData movieData = new MovieData();
                movieData.setID("1");
                movieData.setTitleBar("电影");
                for (int i = 0; i < Vlist.size(); i++) {
                    String pic = Vlist.get(i).getImg();
                    final int finalI = i;
                    if (i != 0) {
                        Log.e("获取", "普通图片");
                        dataRequest.getPic(pic, new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {

                                        movieData.setImage(finalI,ParseDataFromHttp.getPicFromResponse(response));

                            }
                        });
                    }
                    else {
                        Log.e("获取", "高清图片");
                        dataRequest.getPicHD(pic, new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {

                                        movieData.setImage(finalI,ParseDataFromHttp.getPicFromResponse(response));


                            }
                        });
                    }
                    movieData.setVideoData(Vlist.get(finalI), finalI);
                    movieData.setBigtitle(finalI,Vlist.get(finalI).getTitle());
                    movieData.setMinTitle(finalI,Vlist.get(finalI).getShortTitle());
                    movieData.setImageMsg(finalI,Vlist.get(finalI).getSns_score());
                    movieData.setVIP(Vlist.get(finalI).isVip(),finalI);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lists.MVlist.add(movieData);
//                        Init();
                        DataHandler.sendEmptyMessage(DATAOVER);
                    }
                });

            }
        });
    }

    private void getTVItem() {
        final DataRequest dataRequest = DataRequest.newInstance();
        dataRequest.getChannelDataDetails("电视剧", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                List<VideoData> Vlist = ParseDataFromHttp.getChannelVideoList(s);
                Log.e("The 电视剧 is :: ", Vlist.toString());
                final TVData tvData = new TVData();
                tvData.setID("2");
                tvData.setTitleBar("电视剧");
                for (int i = 0; i < Vlist.size(); i++) {
                    String pic = Vlist.get(i).getImg();
                    final int finalI = i;
                    if (i != 0) {
                        Log.e("获取", "普通图片");
                        dataRequest.getPic(pic, new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {

                                tvData.setImage(finalI,ParseDataFromHttp.getPicFromResponse(response));


                            }
                        });
                    }
                    else {
                        Log.e("获取", "高清图片");

                        dataRequest.getPicHD(pic, new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                                tvData.setImage(finalI,ParseDataFromHttp.getPicFromResponse(response));
                            }
                        });
                    }
                    tvData.setVideoData(Vlist.get(finalI),finalI);
                    tvData.setBigtitle(finalI,Vlist.get(finalI).getTitle());
                    tvData.setMinTitle(finalI,Vlist.get(finalI).getShortTitle());
                    if (Vlist.get(finalI).getUpDateNum().equals(Vlist.get(finalI).getTotalNum())) {
                        tvData.setImageMsg(finalI,Vlist.get(finalI).getTotalNum()+"集全");
                    }
                    else
                        tvData.setImageMsg(finalI,"更新至"+Vlist.get(finalI).getUpDateNum()+"集");
                    tvData.setVIP(Vlist.get(finalI).isVip(),finalI);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lists.TVlist.add(tvData);
                        DataHandler.sendEmptyMessage(DATAOVER);
                    }
                });

            }
        });
    }

    private void getDonmanItem() {
        final DataRequest dataRequest = DataRequest.newInstance();
        dataRequest.getChannelDataDetails("动漫", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                final List<VideoData> Vlist = ParseDataFromHttp.getChannelVideoList(s);
                Log.e("The 动漫 is :: ", Vlist.toString());
                final DongmanData dongmanData = new DongmanData();
                dongmanData.setID("4");
                dongmanData.setTitleBar("动漫");
                for (int i = 0; i < Vlist.size(); i++) {
                    String pic = Vlist.get(i).getImg();
                    final int finalI = i;
                    if (i != 0) {
                        Log.e("获取", "普通图片");
                        dataRequest.getPic(pic, new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                                dongmanData.setImage(finalI,ParseDataFromHttp.getPicFromResponse(response));
                            }
                        });
                    }
                    else {
                        Log.e("获取", "高清图片");
                        dataRequest.getPicHD(pic, new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                                dongmanData.setImage(finalI,ParseDataFromHttp.getPicFromResponse(response));

                            }
                        });
                    }
                    dongmanData.setVideoData(Vlist.get(finalI),finalI);
                    dongmanData.setBigtitle(finalI,Vlist.get(finalI).getTitle());
                    dongmanData.setMinTitle(finalI,Vlist.get(finalI).getShortTitle());
                    if (Vlist.get(finalI).getUpDateNum().equals(Vlist.get(finalI).getTotalNum())) {
                        dongmanData.setImageMsg(finalI,Vlist.get(finalI).getTotalNum()+"集全");
                    }
                    else
                        dongmanData.setImageMsg(finalI,"更新至"+Vlist.get(finalI).getUpDateNum()+"集");
                    dongmanData.setVIP(Vlist.get(finalI).isVip(),finalI);
                }
                lists.Dmlist.add(dongmanData);
                DataHandler.sendEmptyMessage(DATAOVER);
            }
        });

    }

    private void getZhongyiItem() {
        final DataRequest dataRequest = DataRequest.newInstance();
        dataRequest.getChannelDataDetails("综艺", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                List<VideoData> Vlist = ParseDataFromHttp.getChannelVideoList(s);
                Log.e("The 综艺 is :: ", Vlist.toString());
                final ZhongyiData zhongyiData = new ZhongyiData();
                zhongyiData.setID("6");
                zhongyiData.setTitleBar("综艺");
                for (int i = 0; i < Vlist.size(); i++) {
                    String pic = Vlist.get(i).getImg();
                    final int finalI = i;
                    dataRequest.getPicHD(pic, new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                            zhongyiData.setImage(finalI, ParseDataFromHttp.getPicFromResponse(response));

                        }
                    });
                    zhongyiData.setVideoData(Vlist.get(finalI), finalI);
                    zhongyiData.setBigtitle(finalI,Vlist.get(finalI).getTitle());
                    zhongyiData.setMinTitle(finalI,Vlist.get(finalI).getShortTitle());
                    zhongyiData.setImageMsg(finalI,Vlist.get(finalI).getDataFormat());
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lists.ZYlist.add(zhongyiData);
                        DataHandler.sendEmptyMessage(DATAOVER);
                    }
                });
            }
        });
    }

    //获取轮播图的图
    private void getRollImage(List<RecommendData> list,DataRequest dataRequest) {
        int Datasize = list.get(0).getVideoDataList().size();
        if (Datasize != 0) {
            for (int i = 0; i < Datasize; i++) {
                final RollData rd = new RollData();
                VideoData mvideoData = list.get(0).getVideoDataList().get(i);
                rd.setVideoData(mvideoData, i);
                rd.setRoll_msg(mvideoData.getTitle());
                dataRequest.getPic(mvideoData.getImg(), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {

                        rd.setRoll_imag( ParseDataFromHttp.getPicFromResponse(response));

                    }
                });
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lists.rList.add(rd);
                    }
                });

            }
        }
    }

    //获取推荐页的各项类型的数据
    private void getRecomClassifyData(List<RecommendData> list,DataRequest dataRequest) {
        Log.e("正在检查VIP权限", "getRecomClassifyData   " + list.size()+"   "+list.get(2).getVideoDataList().size());
        for (int i = 1; i < list.size() ; i++) {
            int Datasize = list.get(i).getVideoDataList().size();
            if (Datasize != 0) {
                final ClassifyData cdata = new ClassifyData();
                cdata.setID(list.get(i).getChannelData().getId());
                cdata.setOptionTitle(list.get(i).getChannelData().getName());
                if (Datasize>8) Datasize = 8;
                for (int j = 0; j < Datasize ; j++) {
                    VideoData mvideoData = list.get(i).getVideoDataList().get(j);
                    cdata.setVideoData(mvideoData, j);
                    cdata.setBigTitle(mvideoData.getShortTitle(),j);
                    cdata.setMinTitle(mvideoData.getTitle(),j);
                    if (mvideoData.isVip())
                        cdata.setTags("VIP", j);
                    else
                        cdata.setTags("", j);
                    if (i == 1 || i == 4)
                        cdata.setImageMsg(mvideoData.getDataFormat(), j);
                    else
                        cdata.setImageMsg(mvideoData.getSns_score(), j);

                    final int finalJ = j;
                    if (i!=1)
                        dataRequest.getPic(mvideoData.getImg(), new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {

                                        cdata.setOptionImage(ParseDataFromHttp.getPicFromResponse(response), finalJ);

                            }
                        });
                    else
                        dataRequest.getPicHD(mvideoData.getImg(), new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                                        cdata.setOptionImage(ParseDataFromHttp.getPicFromResponse(response), finalJ);
                            }
                        });
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lists.cList.add(cdata);
                    }
                });

            }
        }
    }

}
