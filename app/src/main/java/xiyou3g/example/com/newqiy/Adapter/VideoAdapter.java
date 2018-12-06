package xiyou3g.example.com.newqiy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xiyou3g.example.com.newqiy.R;
import xiyou3g.example.com.newqiy.activitys.PlayerActivity;
import xiyou3g.example.com.newqiy.bean.VideoData;
import xiyou3g.example.com.newqiy.utils.OpenPlayIntent;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private Context mcontext;
    private List<VideoData> mlist;
    private String keyword;

    public VideoAdapter(Context context, List<VideoData> list,String key){
        this.mcontext=context;
        this.mlist=list;
        this.keyword=key;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder=new MyViewHolder
                (LayoutInflater.from(mcontext).inflate(R.layout.video_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.p_dec.setText(setKeyWordColor(mlist.get(position).getTitle(),keyword));
        holder.p_score.setText("评分："+mlist.get(position).getSns_score());
        holder.p_type.setText("发布时间："+mlist.get(position).getDataFormat());
        Glide.with(mcontext).load(mlist.get(position).getImg())
                .placeholder(R.drawable.loading)
                .error(R.drawable.timg)
                .into(holder.p_image);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPlayIntent.startplay(mcontext,mlist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

   class MyViewHolder extends RecyclerView.ViewHolder {
       View rootView;
       ImageView p_image;
       TextView p_dec,p_score,p_type;
       MyViewHolder(View itemView) {
           super(itemView);
           p_image=(ImageView)itemView.findViewById(R.id.play_image);
           p_dec=(TextView) itemView.findViewById(R.id.play_dec);
           p_score=(TextView)itemView.findViewById(R.id.play_score);
           p_type=(TextView)itemView.findViewById(R.id.play_type);
           rootView = itemView;
       }
   }

   private SpannableString setKeyWordColor(String content,String k){
       SpannableString s=new SpannableString(content);
       Pattern p=Pattern.compile(k);
       Matcher m=p.matcher(s);
       while (m.find()){
           int start=m.start();
           int end=m.end();
           s.setSpan(new ForegroundColorSpan(Color.GREEN)
                   ,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
       }
       return s;
   }
}
