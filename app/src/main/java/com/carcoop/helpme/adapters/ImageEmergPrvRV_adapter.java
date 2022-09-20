package com.carcoop.helpme.adapters;

import static com.carcoop.helpme.Constance.CLICK_IMAGE_POSITION;
import static com.carcoop.helpme.Constance.IMAGE_ARRAY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.carcoop.helpme.Activity.Image_fullScreen;
import com.carcoop.helpme.R;

import java.util.ArrayList;

public class ImageEmergPrvRV_adapter extends RecyclerView.Adapter<ImageEmergPrvRV_adapter.ImageHolder> {

    private Context context;
    private ArrayList<String> imageURIs;

    public ImageEmergPrvRV_adapter(Context context, ArrayList<String> imageURIs) {
        this.context = context;
        this.imageURIs = imageURIs;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.imageview_rvadapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Glide.with(context).load(imageURIs.get(position))
                .placeholder(R.drawable.ic_file)
                .error(R.drawable.avatar).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Image_fullScreen.class);
                intent.putExtra(CLICK_IMAGE_POSITION, position);
                intent.putExtra(IMAGE_ARRAY, imageURIs);
                context.startActivity(intent);
            }
        });

        holder.imagecancle_IV.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return imageURIs.size();
    }

    static class ImageHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView imagecancle_IV;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.myZoomageView);
            imagecancle_IV = itemView.findViewById(R.id.imagecancle_IV);

        }
    }
}
