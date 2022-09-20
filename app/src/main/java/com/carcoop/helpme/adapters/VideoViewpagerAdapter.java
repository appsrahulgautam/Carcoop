package com.carcoop.helpme.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.carcoop.helpme.R;

import java.io.File;
import java.util.ArrayList;

public class VideoViewpagerAdapter extends RecyclerView.Adapter<VideoViewpagerAdapter.VideoHolder> {

    public static final String TAG = "videoViewPager";

    Context context;
    private ArrayList<String> videoURIS;
    private MediaController mediaController;
    private ViewPager2 viewPager;
    private int i;


    public VideoViewpagerAdapter(Context context, ArrayList<String> videoURIS, ViewPager2 videoPager) {
        this.context = context;
        this.videoURIS = videoURIS;
        this.viewPager = videoPager;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(context).inflate(R.layout.activity_attachment_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        Log.e(TAG, "loadVideo: " + videoURIS.get(position) + " some changes 4 " + position);
        holder.vv_full_view.setVideoURI(Uri.parse(videoURIS.get(position)));
//        holder.playvideo_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG, "onClick: playVideo_iv" );
//                loadVideo(holder,position);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return videoURIS.size();
    }

    @Override
    public long getItemId(int position) {
        Log.e(TAG, "getItemId: position-> " + position);
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e(TAG, "getItemViewType:position->  " + position);
        return position;
    }

    public void notified(int position) {
        Log.e("notifieddata", "notified: position " + position + " mediaController-> " + mediaController);

//        if(mediaController!=null){
//            mediaController.hide();
//            mediaController = null;
//        }
    }

    private void loadVideo(VideoHolder holder, int position) {

        holder.progress_show_video.setVisibility(View.VISIBLE);

        File file = new File(videoURIS.get(position));
        Log.e(TAG, "loadVideo: file path-> " + file.getPath());

        if (file != null) {
            mediaController = new MediaController(context);
            mediaController.setAnchorView(holder.vv_full_view);
            holder.vv_full_view.setMediaController(mediaController);
            holder.vv_full_view.setVideoPath(file.getPath());
            holder.vv_full_view.start();
        }

        holder.vv_full_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                holder.progress_show_video.setVisibility(View.GONE);
                holder.playvideo_iv.setVisibility(View.GONE);
                mediaController.show(2000);
            }
        });

        holder.vv_full_view.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                holder.progress_show_video.setVisibility(View.GONE);
                mediaController.hide();
                return true;
            }
        });
    }

    static class VideoHolder extends RecyclerView.ViewHolder {

        VideoView vv_full_view;
        ProgressBar progress_show_video;
        ImageView playvideo_iv;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            Log.e(TAG, "VideoHolder: ");
            vv_full_view = itemView.findViewById(R.id.vv_full_view);
            progress_show_video = itemView.findViewById(R.id.progress_show_video);


        }
    }
}
