package com.carcoop.helpme.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.carcoop.helpme.R;

import java.util.ArrayList;

public class ImageViewpagerAdapter2 extends RecyclerView.Adapter<ImageViewpagerAdapter2.ImageHolder> {

    public static final String TAG = "imageviewpagerAdapter2";
    Context context;
    private ArrayList<String> imageURIs;
    private int i;
    private int pp = -1;

    public ImageViewpagerAdapter2(Context context, ArrayList<String> imageURIs) {
        this.context = context;
        this.imageURIs = imageURIs;
    }

    public void initposition(int position) {

        this.i = position;

        Log.e(TAG, "initposition: ");
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: ");
        return new ImageHolder(LayoutInflater.from(context).inflate(R.layout.full_screen_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {


        Log.e(TAG, "onBindViewHolder: ");

        Glide.with(context).load(imageURIs.get(position))
                .placeholder(R.drawable.ic_file)
                .error(R.drawable.avatar).into(holder.imageview);


    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        Log.e(TAG, "registerAdapterDataObserver: ");
    }

    @Override
    public long getItemId(int position) {
        Log.e(TAG, "getItemId: " + position);
        return super.getItemId(position);
    }


    @Override
    public int getItemViewType(int position) {
        Log.e(TAG, "getItemViewType: " + position);


        return super.getItemViewType(position);

    }

    @Override
    public int getItemCount() {

        Log.e(TAG, "getItemCount: ");
        return imageURIs.size();
    }

    static class ImageHolder extends RecyclerView.ViewHolder {
        ImageView imageview;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            Log.e(TAG, "ImageHolder: ");
            imageview = itemView.findViewById(R.id.imageview);
        }
    }
}
