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
import com.carcoop.helpme.interfaces.ItemOnclickListerner;

import java.util.ArrayList;

public class VideosUplodedAdapter extends RecyclerView.Adapter<VideosUplodedAdapter.VideosHolder> {

    private Context context;
    private ArrayList<String> videoURIs;

    private int CONTAIN = 1;
    private int LOAD_MORE = 2;

    private ItemOnclickListerner itemOnclickListerner;


    public VideosUplodedAdapter(Context context, ArrayList<String> videoURIs) {
        this.context = context;
        this.videoURIs = videoURIs;
    }

    public void setOnclicklistener(ItemOnclickListerner itemOnclickListerner) {
        this.itemOnclickListerner = itemOnclickListerner;
    }

    @NonNull
    @Override
    public VideosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = null;

        if (viewType == CONTAIN) {
            itemview = LayoutInflater.from(context).inflate(R.layout.videoview_rvadapter_layout, parent, false);
        } else if (LOAD_MORE == viewType) {
            itemview = LayoutInflater.from(context).inflate(R.layout.addmore_layout, parent, false);
        }
        return new VideosHolder(itemview);

    }

    @Override
    public void onBindViewHolder(@NonNull VideosHolder holder, int position) {

        if (position == getItemCount() - 1) {
            addmoreBindviewholder(holder, position);
        } else {
            videoContainBindViewholder(holder, position);
        }

    }


//    public ArrayList<String> getVideoURIs(){
//        return videoURIs;
//    }

    /**
     * add more layout set
     */
    private void addmoreBindviewholder(VideosHolder holder, int position) {

        holder.add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemOnclickListerner.itemOnclick(Constance.VIDEO);

            }
        });
    }

    private void videoContainBindViewholder(VideosHolder holder, int position) {
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
        holder.videocancle_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoURIs.remove(position);
                notifyItemRemoved(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return videoURIs.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == getItemCount() - 1) {
            return LOAD_MORE;
        } else {
            return CONTAIN;
        }
    }

    public void addURI(String videouri) {
        videoURIs.add(videouri);
        notifyItemChanged(videoURIs.size() - 1);
    }

    public ArrayList<String> getVideoURIs() {
        return videoURIs;
    }

    public int getvideosize() {
        return videoURIs.size();
    }


    static class VideosHolder extends RecyclerView.ViewHolder {

        ImageView videoView, add_more;
        ImageView videocancle_IV;

        public VideosHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.Video_id);
            add_more = itemView.findViewById(R.id.add_more);
            videocancle_IV = itemView.findViewById(R.id.videocancle_IV);
        }
    }
}
