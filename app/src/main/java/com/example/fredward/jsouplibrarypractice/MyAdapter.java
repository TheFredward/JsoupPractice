package com.example.fredward.jsouplibrarypractice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final String TAG = "My Adapter";
    private ArrayList mImages;
    private Context mContext;
    private ArrayList<String> mImageNames;

    //TODO: add a new variable to myAdapter for the onclick response
    public MyAdapter(ArrayList mImages, Context mContext, ArrayList<String> names) {

        this.mImages = mImages;
        this.mContext = mContext;
        this.mImageNames = names;
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.i(TAG, "MyViewHolder initiated");
        //create view and inflate into grid_list_view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_list_view,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder myViewHolder, int i) {
        //set images and text of image
        myViewHolder.mImageNameView.setText(mImageNames.get(i));
        //load using glide
        //TODO: only showing one need to show the entire page of images (Complete)
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(i))
                .into(myViewHolder.mImageViewOne);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewOne;
        private TextView mImageNameView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewOne = itemView.findViewById(R.id.image_one);
            mImageNameView = itemView.findViewById(R.id.image_names);
        }
    }

    //TODO: Implement onClickListener using the best "Practice method"
}
