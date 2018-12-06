package xiyou3g.example.com.newqiy.Adapter;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xiyou3g.example.com.newqiy.DataBase.DataBaseModify;
import xiyou3g.example.com.newqiy.R;
import xiyou3g.example.com.newqiy.activitys.PlayerActivity;
import xiyou3g.example.com.newqiy.bean.ClassifyData;
import xiyou3g.example.com.newqiy.bean.DocumentaryData;
import xiyou3g.example.com.newqiy.bean.DongmanData;
import xiyou3g.example.com.newqiy.bean.MovieData;
import xiyou3g.example.com.newqiy.bean.RecycleViewItemData;
import xiyou3g.example.com.newqiy.bean.RollData;
import xiyou3g.example.com.newqiy.bean.TVData;
import xiyou3g.example.com.newqiy.bean.ZhongyiData;
import xiyou3g.example.com.newqiy.framents.RecomFragment;
import xiyou3g.example.com.newqiy.utils.OpenPlayIntent;
import xiyou3g.example.com.newqiy.views.CycleViewPager;

/**
 * Created by DickJampink on 2017/6/3.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CycleViewPager mCycleViewPager;

    private List<RollData> mList;

    private List<RecycleViewItemData> mData;

    public List<RecycleViewItemData> getData() {
        return mData;
    }

    public RecyclerAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        switch (viewType) {
            case RecomFragment.ROLLVIEW:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.roll_imager, parent, false);
                return new RollViewHolder(mView);

            case RecomFragment.CLASSIFY_TV:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_6, parent, false);
                return new ClassifyViewHolder_6(mView);

            case RecomFragment.CLASSIFY_MOVIE:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_6, parent, false);
                return new ClassifyViewHolder_6(mView);

            case RecomFragment.CLASSIFY_ZIXUN:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_1, parent, false);
                return new ClassifyViewHolder(mView);

            case RecomFragment.CLASSIFY_STAR:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_6, parent, false);
                return new ClassifyViewHolder_6(mView);

            case RecomFragment.CLASSIFY_MOVIE_Item:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_4, parent, false);
                return new ClassifyViewHolder_4(mView);

            case RecomFragment.CLASSIFY_TV_Item:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_4, parent, false);
                return new ClassifyViewHolder_4(mView);

            case RecomFragment.CLASSIFY_DM_Item:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_4, parent, false);
                return new ClassifyViewHolder_4(mView);

            case RecomFragment.CLASSIFY_MOVIE_Item_noTitle:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_notitle_6, parent, false);
                return new ClassifyViewHolder_noTitle_6(mView);

            case RecomFragment.CLASSIFY_TV_Item_noTitle:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_notitle_6, parent, false);
                return new ClassifyViewHolder_noTitle_6(mView);

            case RecomFragment.CLASSIFY_DM_Item_noTitle:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_notitle_6, parent, false);
                return new ClassifyViewHolder_noTitle_6(mView);

            case RecomFragment.CLASSIFY_Item_noTitle_5:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_notitle_5, parent, false);
                return new ClassifyViewHolder_noTitle_5(mView);

            case RecomFragment.CLASSIFY_ZY_Item_noTitle:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_notitle_5, parent, false);
                return new ClassifyViewHolder_noTitle_5(mView);

            case RecomFragment.CLASSIFY_Item_5:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_5, parent, false);
                return new ClassifyViewHolder_5(mView);

            case RecomFragment.CLASSIFY_ZY_Item:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_item_5, parent, false);
                return new ClassifyViewHolder_5(mView);

            default:
                break;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RollViewHolder) {
            RollData RD = (RollData) mData.get(position).getT();
            if (RD != null)
                mList = RD.getmRollList();
            initView();
        }


        if (holder instanceof ClassifyViewHolder) {
            ClassifyData CD = (ClassifyData) mData.get(position).getT();
            setClassifyItem(holder, CD);
            //创建点击事件：
            setClick(holder, mData.get(position));
        }

        if (holder instanceof ClassifyViewHolder_6) {
            ClassifyData CD = (ClassifyData) mData.get(position).getT();
            setClassifyItem(holder, CD);
            //创建点击事件：
            setClick(holder, mData.get(position));
        }

        if (holder instanceof ClassifyViewHolder_4) {
            switch (mData.get(position).getDataType()) {
                case RecomFragment.CLASSIFY_MOVIE_Item:
                    MovieData MD = (MovieData) mData.get(position).getT();
                    if (MD.getMinTitle(0) != null) {
                    setMovieItem(holder, MD);
                    //创建点击事件：
                    setClick(holder, mData.get(position));
                }
                    break;
                case RecomFragment.CLASSIFY_TV_Item:
                    TVData TD = (TVData) mData.get(position).getT();
                    if (TD.getMinTitle(0) != null) {
                        setTVItem(holder, TD);
                        //创建点击事件：
                        setClick(holder, mData.get(position));
                    }
                    break;
                case RecomFragment.CLASSIFY_DM_Item:
                    DongmanData Dm = (DongmanData) mData.get(position).getT();
                    if (Dm.getMinTitle(0) != null) {
                        setDmItem(holder, Dm);
                        //创建点击事件：
                        setClick(holder, mData.get(position));
                    }
                    break;
                default:
                    break;
            }

        }

        if (holder instanceof ClassifyViewHolder_5) {
            switch (mData.get(position).getDataType()) {
                case RecomFragment.CLASSIFY_Item_5:
                    DocumentaryData DD = (DocumentaryData) mData.get(position).getT();
                    setDocumentaryItem(holder, DD);
                    //创建点击事件：
                    setClick(holder, mData.get(position));
                    break;
                case RecomFragment.CLASSIFY_ZY_Item:
                    ZhongyiData ZD = (ZhongyiData) mData.get(position).getT();
                    setZYItem(holder, ZD);
                    //创建点击事件：
                    setClick(holder, mData.get(position));
                    break;
                default:
                    break;
            }
        }


        if (holder instanceof ClassifyViewHolder_noTitle_6) {
            switch (mData.get(position).getDataType()) {
                case RecomFragment.CLASSIFY_MOVIE_Item_noTitle:
                    MovieData MD = (MovieData) mData.get(position).getT();
                    if (MD.getMinTitle(0) != null) {
                        setMovieItem(holder, MD);
                        //创建点击事件：
                        setClick(holder, mData.get(position));
                    }
                    break;
                case RecomFragment.CLASSIFY_TV_Item_noTitle:
                    TVData TD = (TVData) mData.get(position).getT();
                    if (TD.getMinTitle(0) != null) {
                        setTVItem(holder, TD);
                        //创建点击事件：
                        setClick(holder, mData.get(position));
                    }
                    break;
                case RecomFragment.CLASSIFY_DM_Item_noTitle:
                    DongmanData Dm = (DongmanData) mData.get(position).getT();
                    if (Dm.getMinTitle(0) != null) {
                        setDmItem(holder, Dm);
                        //创建点击事件：
                        setClick(holder, mData.get(position));
                    }
                    break;
                default:
                    break;
            }
        }
        if (holder instanceof ClassifyViewHolder_noTitle_5) {
            switch (mData.get(position).getDataType()) {
                case RecomFragment.CLASSIFY_Item_noTitle_5:
                    DocumentaryData DD = (DocumentaryData) mData.get(position).getT();
                    setDocumentaryItem(holder, DD);
                    //创建点击事件：
                    setClick(holder, mData.get(position));
                    break;
                case RecomFragment.CLASSIFY_ZY_Item_noTitle:
                    ZhongyiData ZD = (ZhongyiData) mData.get(position).getT();
                    setZYItem(holder, ZD);
                    //创建点击事件：
                    setClick(holder, mData.get(position));
                    break;
                default:
                    break;
            }
        }

    }

    public android.os.Handler getHander() {
        if (mCycleViewPager != null) return mCycleViewPager.getHandler();
        return null;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getDataType();
    }

    /**
    *
    * 下列是各种显示布局的初始化
    */

    private class RollViewHolder extends RecyclerView.ViewHolder {

        RollViewHolder(View itemView) {
            super(itemView);
            mCycleViewPager = (CycleViewPager) itemView.findViewById(R.id.cycle_view);
            RollData RD = (RollData) mData.get(RecomFragment.ROLLVIEW).getT();
            if (RD != null)
                mList = RD.getmRollList();

        }

    }

    private class ClassifyViewHolder extends RecyclerView.ViewHolder {

        TextView TitleBar;
        View ViewParent;
        ImageView TitleStartImage;
        View[] IncludeVIP = new View[4];
        TextView[] BigTitle = new TextView[4];
        TextView[] MinTitle = new TextView[4];
        TextView[] PagerText = new TextView[4];
        ImageView[] ClassifyImage = new ImageView[4];

        List<LinearLayout> linearLayout = new ArrayList<>();

        int[] BTID = {R.id.big_title1, R.id.big_title2, R.id.big_title3, R.id.big_title4};
        int[] MTID = {R.id.min_title1, R.id.min_title2, R.id.min_title3, R.id.min_title4};
        int[] PTID = {R.id.classify_1_pagertext, R.id.classify_2_pagertext, R.id.classify_3_pagertext, R.id.classify_4_pagertext};
        int[] CIID = {R.id.classify_1_Image, R.id.classify_2_Image, R.id.classify_3_Image, R.id.classify_4_Image};
        int[] LLY = {R.id.pager_title, R.id.classify_1, R.id.classify_2, R.id.classify_3, R.id.classify_4};
        int[] VIP = {R.id.vip_1, R.id.vip_2, R.id.vip_3, R.id.vip_4};

        ClassifyViewHolder(View itemView) {
            super(itemView);

            TitleBar = (TextView) itemView.findViewById(R.id.title_bar);
            ViewParent = itemView;
            TitleStartImage = (ImageView) itemView.findViewById(R.id.textStartImage1);

            for (int i = 0; i < 4; i++) {

                IncludeVIP[i] = itemView.findViewById(VIP[i]);
                BigTitle[i] = (TextView) itemView.findViewById(BTID[i]);
                MinTitle[i] = (TextView) itemView.findViewById(MTID[i]);
                PagerText[i] = (TextView) itemView.findViewById(PTID[i]);
                ClassifyImage[i] = (ImageView) itemView.findViewById(CIID[i]);
                linearLayout.add((LinearLayout) itemView.findViewById(LLY[i]));
            }
            linearLayout.add((LinearLayout) itemView.findViewById(LLY[4]));
        }
    }

    private class ClassifyViewHolder_6 extends RecyclerView.ViewHolder {

        TextView TitleBar;
        ImageView TitleStartImage;
        View ViewParent;
        View[] IncludeVIP = new View[6];
        TextView[] BigTitle = new TextView[6];
        TextView[] PagerText = new TextView[6];
        ImageView[] ClassifyImage = new ImageView[6];
        LinearLayout linearLayout;
        List<RelativeLayout> relativeLayouts = new ArrayList<>();

        int[] BTID = {R.id.itemtext1, R.id.itemtext2, R.id.itemtext3, R.id.itemtext4, R.id.itemtext5, R.id.itemtext6};
        int[] PTID = {R.id.image_msg1, R.id.image_msg2, R.id.image_msg3, R.id.image_msg4, R.id.image_msg5, R.id.image_msg6};
        int[] CIID = {R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5, R.id.image6};
        int[] RLY = {R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6};
        int[] VIP = {R.id.vip1, R.id.vip2, R.id.vip3, R.id.vip4, R.id.vip5, R.id.vip6};

        ClassifyViewHolder_6(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.Item_title);
            TitleBar = (TextView) itemView.findViewById(R.id.title_bar);
            TitleStartImage = (ImageView) itemView.findViewById(R.id.textStartImage6);
            ViewParent = itemView;

            for (int i = 0; i < 6; i++) {

                IncludeVIP[i] = itemView.findViewById(VIP[i]);
                BigTitle[i] = (TextView) itemView.findViewById(BTID[i]);
                PagerText[i] = (TextView) itemView.findViewById(PTID[i]);
                ClassifyImage[i] = (ImageView) itemView.findViewById(CIID[i]);
                relativeLayouts.add((RelativeLayout) itemView.findViewById(RLY[i]));
            }
        }
    }

    private class ClassifyViewHolder_noTitle_6 extends RecyclerView.ViewHolder {

        View[] IncludeVIP = new View[6];
        TextView[] BigTitle = new TextView[6];
        TextView[] PagerText = new TextView[6];
        ImageView[] ClassifyImage = new ImageView[6];
        List<RelativeLayout> relativeLayouts = new ArrayList<>();

        int[] BTID = {R.id.noTitleitemtext1, R.id.noTitleitemtext2, R.id.noTitleitemtext3,
                R.id.noTitleitemtext4, R.id.noTitleitemtext5, R.id.noTitleitemtext6};
        int[] PTID = {R.id.noTitleimage_msg1, R.id.noTitleimage_msg2, R.id.noTitleimage_msg3,
                R.id.noTitleimage_msg4, R.id.noTitleimage_msg5, R.id.noTitleimage_msg6};
        int[] CIID = {R.id.noTitleimage1, R.id.noTitleimage2, R.id.noTitleimage3,
                R.id.noTitleimage4, R.id.noTitleimage5, R.id.noTitleimage6};
        int[] RLY = {R.id.noTitleitem1, R.id.noTitleitem2, R.id.noTitleitem3,
                R.id.noTitleitem4, R.id.noTitleitem5, R.id.noTitleitem6};
        int[] VIP = {R.id.noTitlevip1, R.id.noTitlevip2, R.id.noTitlevip3,
                R.id.noTitlevip4, R.id.noTitlevip5, R.id.noTitlevip6};

        ClassifyViewHolder_noTitle_6(View itemView) {
            super(itemView);


            for (int i = 0; i < 6; i++) {
                IncludeVIP[i] = itemView.findViewById(VIP[i]);
                BigTitle[i] = (TextView) itemView.findViewById(BTID[i]);
                PagerText[i] = (TextView) itemView.findViewById(PTID[i]);
                ClassifyImage[i] = (ImageView) itemView.findViewById(CIID[i]);
                relativeLayouts.add((RelativeLayout) itemView.findViewById(RLY[i]));
            }
        }
    }

    private class ClassifyViewHolder_noTitle_5 extends RecyclerView.ViewHolder {

        TextView[] BigTitle = new TextView[5];
        TextView[] PagerText = new TextView[5];
        TextView[] MinTitle = new TextView[5];
        ImageView[] ClassifyImage = new ImageView[5];
        RelativeLayout relativeLayout;
        List<LinearLayout> linearLayouts = new ArrayList<>();

        int[] BTID = {R.id.wImageMsg, R.id.wbig_title1, R.id.wbig_title2, R.id.wbig_title3, R.id.wbig_title4};
        int[] MTID = {0, R.id.wmin_title1, R.id.wmin_title2, R.id.wmin_title3, R.id.wmin_title4};
        int[] PTID = {0, R.id.wimage_msg1, R.id.wimage_msg2, R.id.wimage_msg3, R.id.wimage_msg4};
        int[] CIID = {R.id.wImage, R.id.wclassify_1_Image, R.id.wclassify_2_Image, R.id.wclassify_3_Image, R.id.wclassify_4_Image};
        int[] LLY = {0, R.id.wclassify_1, R.id.wclassify_2, R.id.wclassify_3, R.id.wclassify_4};

        ClassifyViewHolder_noTitle_5(View itemView) {
            super(itemView);
            for (int i = 0; i < 5; i++) {
                if (i != 0) {
                    PagerText[i] = (TextView) itemView.findViewById(PTID[i]);
                    MinTitle[i] = (TextView) itemView.findViewById(MTID[i]);
                }
                if (i == 0)
                    relativeLayout = (RelativeLayout) itemView.findViewById(R.id.weightImageItem);
                else
                    linearLayouts.add((LinearLayout) itemView.findViewById(LLY[i]));

                BigTitle[i] = (TextView) itemView.findViewById(BTID[i]);
                ClassifyImage[i] = (ImageView) itemView.findViewById(CIID[i]);

            }
        }
    }

    private class ClassifyViewHolder_5 extends RecyclerView.ViewHolder {

        TextView DandZtitle_bar;
        ImageView DandZtitle_Image;
        View ViewParent;
        TextView[] BigTitle = new TextView[5];
        TextView[] PagerText = new TextView[5];
        TextView[] MinTitle = new TextView[5];
        ImageView[] ClassifyImage = new ImageView[5];
        RelativeLayout relativeLayout;
        List<LinearLayout> linearLayouts = new ArrayList<>();

        int[] BTID = {R.id.wImageMsg, R.id.wbig_title1, R.id.wbig_title2,
                R.id.wbig_title3, R.id.wbig_title4};
        int[] MTID = {0, R.id.wmin_title1, R.id.wmin_title2, R.id.wmin_title3, R.id.wmin_title4};
        int[] PTID = {0, R.id.wimage_msg1, R.id.wimage_msg2, R.id.wimage_msg3, R.id.wimage_msg4};
        int[] CIID = {R.id.wImage, R.id.wclassify_1_Image, R.id.wclassify_2_Image,
                R.id.wclassify_3_Image, R.id.wclassify_4_Image};
        int[] LLY = {0, R.id.wclassify_1, R.id.wclassify_2, R.id.wclassify_3, R.id.wclassify_4};

        ClassifyViewHolder_5(View itemView) {
            super(itemView);
            ViewParent = itemView;
            DandZtitle_bar = (TextView) itemView.findViewById(R.id.DandZtitle_bar);
            DandZtitle_Image = (ImageView) itemView.findViewById(R.id.DandZtitle_Image);
            for (int i = 0; i < 5; i++) {
                if (i != 0) {
                    PagerText[i] = (TextView) itemView.findViewById(PTID[i]);
                    MinTitle[i] = (TextView) itemView.findViewById(MTID[i]);
                }
                if (i == 0)
                    relativeLayout = (RelativeLayout) itemView.findViewById(R.id.weightImageItem);
                else
                    linearLayouts.add((LinearLayout) itemView.findViewById(LLY[i]));

                BigTitle[i] = (TextView) itemView.findViewById(BTID[i]);
                ClassifyImage[i] = (ImageView) itemView.findViewById(CIID[i]);
            }
        }
    }

    private class ClassifyViewHolder_4 extends RecyclerView.ViewHolder {

        TextView TitleBar;
        View[] IncludeVIP = new View[4];
        TextView minTitle;
        View ViewParent;
        ImageView TitleStartImage;
        TextView[] BigTitle = new TextView[4];
        TextView[] PagerText = new TextView[4];
        ImageView[] ClassifyImage = new ImageView[4];
        List<RelativeLayout> relativeLayouts = new ArrayList<>();

        int[] BTID = {R.id.bigImageText, R.id.movieitemtext1,
                R.id.movieitemtext2, R.id.movieitemtext3};
        int[] PTID = {R.id.bigImage_msg, R.id.movieimage_msg1,
                R.id.movieimage_msg2, R.id.movieimage_msg3};
        int[] CIID = {R.id.bigImage, R.id.movieimage1, R.id.movieimage2,
                R.id.movieimage3};
        int[] RLY = {R.id.bigitem, R.id.movie1, R.id.movie2, R.id.movie3};
        int[] VIP = {R.id.movievip0, R.id.movievip1, R.id.movievip2,
                R.id.movievip3};

        ClassifyViewHolder_4(View itemView) {
            super(itemView);
            ViewParent = itemView;
            TitleBar = (TextView) itemView.findViewById(R.id.movietitle_bar);
            minTitle = (TextView) itemView.findViewById(R.id.bigImage_minText);
            TitleStartImage = (ImageView) itemView.findViewById(R.id.textStartImage4);

            for (int i = 0; i < 4; i++) {

                IncludeVIP[i] = itemView.findViewById(VIP[i]);
                BigTitle[i] = (TextView) itemView.findViewById(BTID[i]);
                PagerText[i] = (TextView) itemView.findViewById(PTID[i]);
                ClassifyImage[i] = (ImageView) itemView.findViewById(CIID[i]);
                relativeLayouts.add((RelativeLayout) itemView.findViewById(RLY[i]));
            }
        }
    }

    /**
     * 给不同的布局设置点击事件
     */

    private void setClick(RecyclerView.ViewHolder holder, RecycleViewItemData recycleViewItemData) {

        if (holder instanceof ClassifyViewHolder) {
            final ClassifyData CD = (ClassifyData) recycleViewItemData.getT();
            ((ClassifyViewHolder) holder).linearLayout.get(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenPlayIntent.startplay(view.getContext(),CD.getVideoData(0));
                }
            });
            for (int i = 1; i < ((ClassifyViewHolder) holder).linearLayout.size(); i++) {

                final int finalI = i-1;
                ((ClassifyViewHolder) holder).linearLayout.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OpenPlayIntent.startplay(view.getContext(),CD.getVideoData(finalI));
                    }
                });
            }
        } else if (holder instanceof ClassifyViewHolder_6) {
            final ClassifyData CD = (ClassifyData) recycleViewItemData.getT();
            ((ClassifyViewHolder_6) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), CD.getOptionTitle(), Toast.LENGTH_LONG).show();
                }
            });
            for (int i = 0; i < ((ClassifyViewHolder_6) holder).relativeLayouts.size(); i++) {

                //为了使其能正常打印所更改的信息，
                final int finalI = i;
                ((ClassifyViewHolder_6) holder).relativeLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OpenPlayIntent.startplay(view.getContext(),CD.getVideoData(finalI));
                    }
                });
            }

        } else if (holder instanceof ClassifyViewHolder_4) {
            switch (recycleViewItemData.getDataType()) {
                case RecomFragment.CLASSIFY_MOVIE_Item:
                    final MovieData MD = (MovieData)recycleViewItemData.getT();
                    for (int i = 0; i < ((ClassifyViewHolder_4) holder).relativeLayouts.size(); i++) {

                        //为了使其能正常打印所更改的信息，
                        final int finalI1 = i;
                        ((ClassifyViewHolder_4) holder).relativeLayouts.get(i).setOnClickListener(
                                new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPlayIntent.startplay(view.getContext(),MD.getVideoData(finalI1));
                            }
                        });
                    }
                    break;
                case RecomFragment.CLASSIFY_TV_Item:
                    final TVData TD = (TVData) recycleViewItemData.getT();

                    for (int i = 0; i < ((ClassifyViewHolder_4) holder).relativeLayouts.size(); i++) {

                        //为了使其能正常打印所更改的信息，
                        final int finalI1 = i;
                        ((ClassifyViewHolder_4) holder).relativeLayouts.get(i).setOnClickListener(
                                new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                OpenPlayIntent.startplay(view.getContext(),TD.getVideoData(finalI1));
                            }
                        });
                    }
                    break;
                case RecomFragment.CLASSIFY_DM_Item:
                   final DongmanData Dm = (DongmanData) recycleViewItemData.getT();

                    for (int i = 0; i < ((ClassifyViewHolder_4) holder).relativeLayouts.size(); i++) {

                        //为了使其能正常打印所更改的信息，
                        final int finalI1 = i;
                        ((ClassifyViewHolder_4) holder).relativeLayouts.get(i).setOnClickListener(
                                new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                OpenPlayIntent.startplay(view.getContext(),Dm.getVideoData(finalI1));
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        } else if (holder instanceof ClassifyViewHolder_noTitle_6) {
            switch (recycleViewItemData.getDataType()) {
                case RecomFragment.CLASSIFY_MOVIE_Item_noTitle:
                    final MovieData MD = (MovieData)recycleViewItemData.getT();
                    for (int i = 0, j = 4; i < ((ClassifyViewHolder_noTitle_6) holder).relativeLayouts.size(); i++, j++) {
                        //为了使其能正常打印所更改的信息，
                        final int finalI = j;
                        ((ClassifyViewHolder_noTitle_6) holder).relativeLayouts.get(i).setOnClickListener(
                                new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPlayIntent.startplay(view.getContext(),MD.getVideoData(finalI));
                            }
                        });
                    }
                    break;
                case RecomFragment.CLASSIFY_TV_Item_noTitle:
                    final TVData TD = (TVData) recycleViewItemData.getT();

                    for (int i = 0, j = 4; i < ((ClassifyViewHolder_noTitle_6) holder).relativeLayouts.size(); i++, j++) {
                        //为了使其能正常打印所更改的信息，
                        final int finalI = j;
                        ((ClassifyViewHolder_noTitle_6) holder).relativeLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                OpenPlayIntent.startplay(view.getContext(),TD.getVideoData(finalI));
                            }
                        });
                    }
                    break;
                case RecomFragment.CLASSIFY_DM_Item_noTitle:
                    final DongmanData Dm = (DongmanData) recycleViewItemData.getT();

                    for (int i = 0, j = 4; i < ((ClassifyViewHolder_noTitle_6) holder).relativeLayouts.size(); i++, j++) {
                        //为了使其能正常打印所更改的信息，
                        final int finalI = j;
                        ((ClassifyViewHolder_noTitle_6) holder).relativeLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                OpenPlayIntent.startplay(view.getContext(),Dm.getVideoData(finalI));

                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        } else if (holder instanceof ClassifyViewHolder_noTitle_5) {
            switch (recycleViewItemData.getDataType()) {
                case RecomFragment.CLASSIFY_Item_noTitle_5:
                    final DocumentaryData DD = (DocumentaryData) recycleViewItemData.getT();

                    ((ClassifyViewHolder_noTitle_5) holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OpenPlayIntent.startplay(view.getContext(),DD.getVideoData(5));

                        }
                    });

                    for (int i = 0, j = 6; i < ((ClassifyViewHolder_noTitle_5) holder).linearLayouts.size(); i++, j++) {

                        //为了使其能正常打印所更改的信息，
                        final int finalI = j;
                        ((ClassifyViewHolder_noTitle_5) holder).linearLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                OpenPlayIntent.startplay(view.getContext(),DD.getVideoData(finalI));

                            }
                        });
                    }
                    break;
                case RecomFragment.CLASSIFY_ZY_Item_noTitle:
                    final ZhongyiData ZD = (ZhongyiData)recycleViewItemData.getT();

                    ((ClassifyViewHolder_noTitle_5) holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OpenPlayIntent.startplay(view.getContext(),ZD.getVideoData(5));

                        }
                    });

                    for (int i = 0, j = 6; i < ((ClassifyViewHolder_noTitle_5) holder).linearLayouts.size(); i++, j++) {

                        //为了使其能正常打印所更改的信息，
                        final int finalI = j;
                        ((ClassifyViewHolder_noTitle_5) holder).linearLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                OpenPlayIntent.startplay(view.getContext(),ZD.getVideoData(finalI));

                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        } else if (holder instanceof ClassifyViewHolder_5) {
            switch (recycleViewItemData.getDataType()) {
                case RecomFragment.CLASSIFY_Item_5:
                    final DocumentaryData DD = (DocumentaryData) recycleViewItemData.getT();
                    ((ClassifyViewHolder_5) holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OpenPlayIntent.startplay(view.getContext(),DD.getVideoData(0));

                        }
                    });

                    for (int i = 0; i < ((ClassifyViewHolder_5) holder).linearLayouts.size(); i++) {

                        //为了使其能正常打印所更改的信息，
                        final int finalI = i + 1;
                        ((ClassifyViewHolder_5) holder).linearLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                OpenPlayIntent.startplay(view.getContext(),DD.getVideoData(finalI));

                            }
                        });
                    }
                    break;
                case RecomFragment.CLASSIFY_ZY_Item:
                    final ZhongyiData ZD = (ZhongyiData)recycleViewItemData.getT();
                    ((ClassifyViewHolder_5) holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OpenPlayIntent.startplay(view.getContext(),ZD.getVideoData(0));

                        }
                    });

                    for (int i = 0; i < ((ClassifyViewHolder_5) holder).linearLayouts.size(); i++) {

                        //为了使其能正常打印所更改的信息，
                        final int finalI = i + 1;
                        ((ClassifyViewHolder_5) holder).linearLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                OpenPlayIntent.startplay(view.getContext(),ZD.getVideoData(finalI));

                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 初始化View,轮播图的指示器
     */
    private void initView() {
        if (mList.size() != 0) {
            //设置选中和未选中时的图片
            mCycleViewPager.setIndicators(R.drawable.ad_select, R.drawable.ad_unselect);
            //设置轮播间隔时间
            mCycleViewPager.setDelay(3000);
            mCycleViewPager.setData(mList, mAdCycleViewListener);

        }
    }

    /**
     * 轮播图点击监听
     */
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener =
            new CycleViewPager.ImageCycleViewListener() {

                @Override
                public void onImageClick(RollData info, int position, View imageView) {
                    if (mCycleViewPager.isCycle()) {
                        position = position - 1;
                    }
                    OpenPlayIntent.startplay(imageView.getContext(),info.getVideoData(position));
                }
            };

    /**
     * 给对应的布局，设置数据。
     */
    private void setClassifyItem(RecyclerView.ViewHolder holder, ClassifyData classifyData) {
        if (holder instanceof ClassifyViewHolder) {
            if (classifyData.getLogo() != null) {
                Drawable drawable = ContextCompat.getDrawable(((ClassifyViewHolder) holder)
                        .ViewParent.getContext(), classifyData.getLogo());
                Drawable drawableRight = ContextCompat.getDrawable(((ClassifyViewHolder) holder)
                        .ViewParent.getContext(), R.drawable.ic_chevron_right_black_24dp);
                ((ClassifyViewHolder) holder).TitleBar
                        .setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableRight, null);
                ((ClassifyViewHolder) holder).TitleStartImage.setBackground(drawable);
            }
            ((ClassifyViewHolder) holder).TitleBar.setText(classifyData.getOptionTitle());
            for (int i = 0; i < 4; i++) {
                ((ClassifyViewHolder) holder).ClassifyImage[i].setImageBitmap(classifyData.getOptionImage(i));
                ((ClassifyViewHolder) holder).BigTitle[i].setText(classifyData.getBigTitle(i));
                ((ClassifyViewHolder) holder).MinTitle[i].setText(classifyData.getMinTitle(i));
                ((ClassifyViewHolder) holder).PagerText[i].setText(classifyData.getImageMsg(i));
                if (!classifyData.getTags(i).equals("")) {
                    ((ClassifyViewHolder) holder).IncludeVIP[i].setVisibility(View.VISIBLE);
                }
            }
        } else if (holder instanceof ClassifyViewHolder_6) {
            if (classifyData.getLogo() != null) {
                Drawable drawableLeft = ContextCompat.getDrawable(((ClassifyViewHolder_6) holder).
                        ViewParent.getContext(), classifyData.getLogo());
                Drawable drawableRight = ContextCompat.getDrawable(((ClassifyViewHolder_6) holder).
                        ViewParent.getContext(), R.drawable.ic_chevron_right_black_24dp);
                ((ClassifyViewHolder_6) holder).TitleStartImage.setBackground(drawableLeft);
                ((ClassifyViewHolder_6) holder).TitleBar.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableRight, null);
            }
            ((ClassifyViewHolder_6) holder).TitleBar.setText(classifyData.getOptionTitle());
            for (int i = 0; i < 6; i++) {
                ((ClassifyViewHolder_6) holder).ClassifyImage[i].setImageBitmap(classifyData.getOptionImage(i));
                ((ClassifyViewHolder_6) holder).BigTitle[i].setText(classifyData.getMinTitle(i));
                ((ClassifyViewHolder_6) holder).PagerText[i].setText(classifyData.getImageMsg(i));
                if (!classifyData.getTags(i).equals("")) {
                    ((ClassifyViewHolder_6) holder).IncludeVIP[i].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setMovieItem(RecyclerView.ViewHolder holder, MovieData movieData) {
        if (holder instanceof ClassifyViewHolder_4) {
            Drawable d = ContextCompat.getDrawable(((ClassifyViewHolder_4) holder).ViewParent.getContext(), movieData.getLogo());
            ((ClassifyViewHolder_4) holder).TitleStartImage.setBackground(d);
            ((ClassifyViewHolder_4) holder).minTitle.setText(movieData.getMinTitle(0));
            ((ClassifyViewHolder_4) holder).TitleBar.setText(movieData.getTitleBar());
            for (int i = 0; i < 4; i++) {
                ((ClassifyViewHolder_4) holder).ClassifyImage[i].setImageBitmap(movieData.getImage(i));
                ((ClassifyViewHolder_4) holder).BigTitle[i].setText(movieData.getBigtitle(i));
                ((ClassifyViewHolder_4) holder).PagerText[i].setText(movieData.getImageMsg(i));
                if (movieData.getVIP(i)) {
                    ((ClassifyViewHolder_4) holder).IncludeVIP[i].setVisibility(View.VISIBLE);
                }
            }
        }
        if (holder instanceof ClassifyViewHolder_noTitle_6) {
            for (int i = 4, j = 0; i < 10 && j < 6; i++, j++) {
                ((ClassifyViewHolder_noTitle_6) holder).ClassifyImage[j].setImageBitmap(movieData.getImage(i));
                ((ClassifyViewHolder_noTitle_6) holder).BigTitle[j].setText(movieData.getBigtitle(i));
                ((ClassifyViewHolder_noTitle_6) holder).PagerText[j].setText(movieData.getImageMsg(i));
                if (movieData.getVIP(i)) {
                    ((ClassifyViewHolder_noTitle_6) holder).IncludeVIP[j].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setTVItem(RecyclerView.ViewHolder holder, TVData tvData) {
        if (holder instanceof ClassifyViewHolder_4) {
            Drawable d = ContextCompat.getDrawable(((ClassifyViewHolder_4) holder)
                    .ViewParent.getContext(), tvData.getLogo());
            ((ClassifyViewHolder_4) holder).TitleStartImage.setBackground(d);
            ((ClassifyViewHolder_4) holder).minTitle.setText(tvData.getMinTitle(0));
            ((ClassifyViewHolder_4) holder).TitleBar.setText(tvData.getTitleBar());
            for (int i = 0; i < 4; i++) {
                ((ClassifyViewHolder_4) holder).ClassifyImage[i].setImageBitmap(tvData.getImage(i));
                ((ClassifyViewHolder_4) holder).BigTitle[i].setText(tvData.getBigtitle(i));
                ((ClassifyViewHolder_4) holder).PagerText[i].setText(tvData.getImageMsg(i));
                if (tvData.getVIP(i)) {
                    ((ClassifyViewHolder_4) holder).IncludeVIP[i].setVisibility(View.VISIBLE);
                }
            }
        }
        if (holder instanceof ClassifyViewHolder_noTitle_6) {
            for (int i = 4, j = 0; i < 10 && j < 6; i++, j++) {
                ((ClassifyViewHolder_noTitle_6) holder).ClassifyImage[j].setImageBitmap(tvData.getImage(i));
                ((ClassifyViewHolder_noTitle_6) holder).BigTitle[j].setText(tvData.getBigtitle(i));
                ((ClassifyViewHolder_noTitle_6) holder).PagerText[j].setText(tvData.getImageMsg(i));
                if (tvData.getVIP(i)) {
                    ((ClassifyViewHolder_noTitle_6) holder).IncludeVIP[j].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setDmItem(RecyclerView.ViewHolder holder, DongmanData dongmanData) {
        if (holder instanceof ClassifyViewHolder_4) {
            Drawable d = ContextCompat.getDrawable(((ClassifyViewHolder_4) holder)
                    .ViewParent.getContext(), dongmanData.getLogo());
            ((ClassifyViewHolder_4) holder).TitleStartImage.setBackground(d);
            ((ClassifyViewHolder_4) holder).minTitle.setText(dongmanData.getMinTitle(0));
            ((ClassifyViewHolder_4) holder).TitleBar.setText(dongmanData.getTitleBar());
            for (int i = 0; i < 4; i++) {
                ((ClassifyViewHolder_4) holder).ClassifyImage[i].setImageBitmap(dongmanData.getImage(i));
                ((ClassifyViewHolder_4) holder).BigTitle[i].setText(dongmanData.getBigtitle(i));
                ((ClassifyViewHolder_4) holder).PagerText[i].setText(dongmanData.getImageMsg(i));
                if (dongmanData.getVIP(i)) {
                    ((ClassifyViewHolder_4) holder).IncludeVIP[i].setVisibility(View.VISIBLE);
                }
            }
        }
        if (holder instanceof ClassifyViewHolder_noTitle_6) {
            for (int i = 4, j = 0; i < 10 && j < 6; i++, j++) {
                ((ClassifyViewHolder_noTitle_6) holder).ClassifyImage[j].setImageBitmap(dongmanData.getImage(i));
                ((ClassifyViewHolder_noTitle_6) holder).BigTitle[j].setText(dongmanData.getBigtitle(i));
                ((ClassifyViewHolder_noTitle_6) holder).PagerText[j].setText(dongmanData.getImageMsg(i));
                if (dongmanData.getVIP(i)) {
                    ((ClassifyViewHolder_noTitle_6) holder).IncludeVIP[j].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setDocumentaryItem(RecyclerView.ViewHolder holder, DocumentaryData documentaryData) {
        if (holder instanceof ClassifyViewHolder_noTitle_5) {
            for (int i = 5, j = 0; i < 10; i++, j++) {

                if (i != 5) {
                    ((ClassifyViewHolder_noTitle_5) holder).MinTitle[j].setText(documentaryData.getMinTitle(i));
                    ((ClassifyViewHolder_noTitle_5) holder).PagerText[j].setText(documentaryData.getImageMsg(i));
                }
                ((ClassifyViewHolder_noTitle_5) holder).BigTitle[j].setText(documentaryData.getBigtitle(i));
                ((ClassifyViewHolder_noTitle_5) holder).ClassifyImage[j].setImageBitmap(documentaryData.getImage(i));
            }
        }
        if (holder instanceof ClassifyViewHolder_5) {
            Drawable draw = ContextCompat.getDrawable(((ClassifyViewHolder_5) holder)
                    .ViewParent.getContext(), documentaryData.getLogo());
            ((ClassifyViewHolder_5) holder).DandZtitle_Image.setBackground(draw);
            ((ClassifyViewHolder_5) holder).DandZtitle_bar.setText(documentaryData.getTitleBar());
            for (int i = 0; i < 5; i++) {
                if (i != 0) {
                    ((ClassifyViewHolder_5) holder).MinTitle[i].setText(documentaryData.getMinTitle(i));
                    ((ClassifyViewHolder_5) holder).PagerText[i].setText(documentaryData.getImageMsg(i));
                }
                ((ClassifyViewHolder_5) holder).BigTitle[i].setText(documentaryData.getBigtitle(i));
                ((ClassifyViewHolder_5) holder).ClassifyImage[i].setImageBitmap(documentaryData.getImage(i));
            }
        }

    }

    private void setZYItem(RecyclerView.ViewHolder holder, ZhongyiData zhongyiData) {
       if (holder instanceof ClassifyViewHolder_noTitle_5) {
            for (int i = 5, j = 0; i < 10; i++, j++) {

                if (i != 5) {
                    ((ClassifyViewHolder_noTitle_5) holder).MinTitle[j].setText(zhongyiData.getMinTitle(i));
                    ((ClassifyViewHolder_noTitle_5) holder).PagerText[j].setText(zhongyiData.getImageMsg(i));
                }
                ((ClassifyViewHolder_noTitle_5) holder).BigTitle[j].setText(zhongyiData.getBigtitle(i));
                ((ClassifyViewHolder_noTitle_5) holder).ClassifyImage[j].setImageBitmap(zhongyiData.getImage(i));
            }
        }
        if (holder instanceof ClassifyViewHolder_5) {
            Drawable draw = ContextCompat.getDrawable(((ClassifyViewHolder_5) holder)
                    .ViewParent.getContext(), zhongyiData.getLogo());
            ((ClassifyViewHolder_5) holder).DandZtitle_Image.setBackground(draw);
            ((ClassifyViewHolder_5) holder).DandZtitle_bar.setText(zhongyiData.getTitleBar());
            for (int i = 0; i < 5; i++) {
                if (i != 0) {
                    ((ClassifyViewHolder_5) holder).MinTitle[i].setText(zhongyiData.getMinTitle(i));
                    ((ClassifyViewHolder_5) holder).PagerText[i].setText(zhongyiData.getImageMsg(i));
                }
                ((ClassifyViewHolder_5) holder).BigTitle[i].setText(zhongyiData.getBigtitle(i));
                ((ClassifyViewHolder_5) holder).ClassifyImage[i].setImageBitmap(zhongyiData.getImage(i));
            }
        }

    }

}
