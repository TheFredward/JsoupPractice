package com.example.fredward.jsouplibrarypractice;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mimageURL = "https://www.wallpapermaiden.com/resolution/1080x1920";
    ProgressDialog mProgressDialog;
    private String sourceImage;
    ArrayList imageList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GetImage getImage = new GetImage();
        getImage.execute();

    }


    /**
     * recycleViewInit used to start recycler view with the custom layoutmana
     * ger and the MyAdapter
     */
    public void recycleViewInit() {
        mRecyclerView = findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(imageList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class GetImage extends AsyncTask<Void, Void, Void> {

        Bitmap bitmap;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //connect to website
                Document document = Jsoup.connect(mimageURL).get();
                //Use elements from website page to get the class data
                Elements image = document.getElementsByTag("img");
                //for loop to iterate on webpage and find the source images
                for(Element el:image){
                    sourceImage = el.absUrl("src");
                    imageList.add(sourceImage);

                }
                Log.i(TAG,"Number of Images:" + image.size());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recycleViewInit();
            mProgressDialog.dismiss();

        }
    }
}