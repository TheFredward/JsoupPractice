package com.example.fredward.jsouplibrarypractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_view);
        //Verify that that there is data and then pass it to each of the needed views
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")) {
            String mImageURL = getIntent().getStringExtra("image_url");
            String mImageName = getIntent().getStringExtra("image_name");

            setIntentData(mImageURL, mImageName);
        }


    }

    //method to fetch data and populate the UI
    private void setIntentData(String mURL, String mName){
        TextView imageName = findViewById(R.id.detail_text);
        ImageView imageView = findViewById(R.id.detail_image);


            imageName.setText(mName);
            Glide.with(this)
                    .asDrawable()
                    .load(mURL)
                    .into(imageView);
    }
}
