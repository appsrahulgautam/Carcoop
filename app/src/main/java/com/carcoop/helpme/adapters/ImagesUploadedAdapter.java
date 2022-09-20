package com.carcoop.helpme.adapters;

import static com.carcoop.helpme.Constance.CLICK_IMAGE_POSITION;
import static com.carcoop.helpme.Constance.IMAGE_ARRAY;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.carcoop.helpme.Activity.Image_fullScreen;
import com.carcoop.helpme.Constance;
import com.carcoop.helpme.R;
import com.carcoop.helpme.interfaces.ItemOnclickListerner;

import java.util.ArrayList;

public class ImagesUploadedAdapter extends RecyclerView.Adapter<ImagesUploadedAdapter.ImagesHolder> {

    private static final String TAG = "ImageUploadedAdapter";
    private Context context;
    private ArrayList<String> imageURIs;
    private int CONTAIN = 1;
    private int LOAD_MORE = 2;
    private ItemOnclickListerner itemOnclickListerner;

    public ImagesUploadedAdapter(Context context, ArrayList<String> imageURIs) {
        this.context = context;
        this.imageURIs = imageURIs;
    }

    public void setItemOnclickListerner(ItemOnclickListerner itemOnclickListerner) {
        this.itemOnclickListerner = itemOnclickListerner;
    }

    @NonNull
    @Override
    public ImagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.e(TAG, "onCreateViewHolder:viewType->  " + viewType);

        View itemview = null;
        if (CONTAIN == viewType) {
            itemview = LayoutInflater.from(context).inflate(R.layout.imageview_rvadapter_layout, parent, false);
        } else if (LOAD_MORE == viewType) {
            itemview = LayoutInflater.from(context).inflate(R.layout.addmore_layout, parent, false);
        }
        return new ImagesHolder(itemview);

    }

    @Override
    public void onBindViewHolder(@NonNull ImagesHolder holder, int position) {

        if (position == getItemCount() - 1) {
            addmoreBindviewholder(holder, position);
        } else {
            imageContainBindViewholder(holder, position);
        }

    }


    /**
     * add more layout set
     */
    private void addmoreBindviewholder(ImagesHolder holder, int position) {
        holder.add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemOnclickListerner.itemOnclick(Constance.IMAGE);
            }
        });


    }


    private void imageContainBindViewholder(ImagesHolder holder, int position) {

        Glide.with(context).load(imageURIs.get(position))
                .placeholder(R.drawable.ic_file)
                .error(R.drawable.ic_crash).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Image_fullScreen.class);
                intent.putExtra(CLICK_IMAGE_POSITION, position);
                intent.putExtra(IMAGE_ARRAY, imageURIs);
                context.startActivity(intent);
            }
        });

        holder.imagecancle_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageURIs.remove(position);
                notifyItemRemoved(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return imageURIs.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {


        Log.e(TAG, "getItemViewType: position-> " + position + " getitemcount-> " + (getItemCount() - 1));
        if (position == getItemCount() - 1) {
            return LOAD_MORE;
        } else {

            return CONTAIN;
        }

    }

    /**
     * add new image uri
     */
    public void addURI(String imageuri) {
        imageURIs.add(imageuri);
        notifyItemInserted(imageURIs.size() - 1);
    }

    public int getsize() {
        return imageURIs.size();
    }

    private void gotoEnd_ofrv() {

    }

    public ArrayList<String> getImageURIs() {
        return imageURIs;
    }


    static class ImagesHolder extends RecyclerView.ViewHolder {
        ImageView imageView, add_more;
        ImageView imagecancle_IV;

        public ImagesHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.myZoomageView);
            add_more = itemView.findViewById(R.id.add_more);
            imagecancle_IV = itemView.findViewById(R.id.imagecancle_IV);
        }
    }
}
