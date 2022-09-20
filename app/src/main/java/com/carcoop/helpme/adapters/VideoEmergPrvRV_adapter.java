package com.carcoop.helpme.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.carcoop.helpme.Activity.AttachmentVideoActivity;
import com.carcoop.helpme.Constance;
import com.carcoop.helpme.R;

import java.util.ArrayList;

public class VideoEmergPrvRV_adapter extends RecyclerView.Adapter<VideoEmergPrvRV_adapter.VideoHolder> {


    private Context context;
    private ArrayList<String> videoURIs;

    public VideoEmergPrvRV_adapter(Context context, ArrayList<String> videoURIs) {
        this.context = context;
        this.videoURIs = videoURIs;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.videoview_rvadapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        Glide.with(context).load(videoURIs.get(position))
                .placeholder(R.drawable.ic_file)
                .error(R.drawable.avatar).into(holder.videoView);

        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, Video_fullScreen.class);
//                intent.putExtra(Constance.CLICK_VIDEO_POSITION,position);
//                intent.putExtra(Constance.VIDEO_ARRAY,videoURIs);
//                context.startActivity(intent);
                Intent intent = new Intent(context, AttachmentVideoActivity.class);
                intent.putExtra(Constance.VIDEO_NAME, videoURIs.get(position));
                context.startActivity(intent);
            }
        });

        holder.videocancle_IV.setVisibility(View.GONE);
//        holder.videocancle_IV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoURIs.remove(position);
//                notifyItemChanged(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return videoURIs.size();
    }

    static class VideoHolder extends RecyclerView.ViewHolder {
        ImageView videoView;
        ImageView videocancle_IV;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.Video_id);
            videocancle_IV = itemView.findViewById(R.id.videocancle_IV);
        }
    }
}
