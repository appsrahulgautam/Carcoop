package com.carcoop.helpme.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.carcoop.helpme.R;

import java.util.ArrayList;
import java.util.Objects;


public class ImagesViewpagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> images;
    LayoutInflater layoutInflater;
    private int i;


    public ImagesViewpagerAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void initposition(int position) {
        this.i = position;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // inflating the item.xml
        View itemView = layoutInflater.inflate(R.layout.full_screen_image, container, false);
        // referencing the image view from the item.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageview);
        // setting the image in the imageView
        Glide.with(context).load(images.get(position))
                .placeholder(R.drawable.ic_file)
                .error(R.drawable.avatar).into(imageView);
        // Adding the View
        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ConstraintLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
