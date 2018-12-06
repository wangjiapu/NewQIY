package xiyou3g.example.com.newqiy.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import xiyou3g.example.com.newqiy.R;
import xiyou3g.example.com.newqiy.bean.VideoData;
import xiyou3g.example.com.newqiy.utils.OpenPlayIntent;

/**
 * Created by Lance on 2017/6/10.
 */

public class VideoHistoryAdapter extends RecyclerView.Adapter<VideoHistoryAdapter.ViewHolder> {

    private List<VideoData> videoDataList;
    private Context context;

    public VideoHistoryAdapter(Context context,List<VideoData> videoDatas){
        videoDataList = videoDatas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_ch_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final VideoData videoData = videoDataList.get(videoDataList.size()-1-position);
        holder.cardView.setCardElevation(10);
        holder.title.setText(videoData.getTitle());
        holder.shortTitle.setText(videoData.getPlayCount()+"次");
        holder.currentTime.setText(videoData.getCurrentTime());
        Glide.with(context).load(videoData.getImg())
                .placeholder(R.drawable.loading)
                .error(R.drawable.timg)
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPlayIntent.startplay(context,videoData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private ImageView imageView;
        private TextView title;
        private TextView shortTitle;
        private TextView currentTime;
        View view;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.history_item);
            imageView = (ImageView) view.findViewById(R.id.history_image);
            title = (TextView) view.findViewById(R.id.history_title);
            shortTitle = (TextView) view.findViewById(R.id.history_short_title);
            currentTime = (TextView) view.findViewById(R.id.currentTime);
            this.view = view;
        }
    }
}
