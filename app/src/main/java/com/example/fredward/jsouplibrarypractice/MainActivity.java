package com.example.fredward.jsouplibrarypractice;

import android.app.ProgressDialog;
import android.content.Intent;
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

//TODO: (completed)implement the method from My adapter
//TODO: (completed)create a new Java class that will add the details to the layout
public class MainActivity extends AppCompatActivity implements MyAdapter.OnClickStatus {
    private static final String TAG = "Main Activity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mimageURL = "https://www.wallpapermaiden.com/category/anime/1080x1920";
    ProgressDialog mProgressDialog;
    private String sourceImage, sourceName;
    ArrayList<String> imageList = new ArrayList<String>();
    ArrayList<String> imageName = new ArrayList<>();

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
        mAdapter = new MyAdapter(imageList, this,imageName,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     *
     * @param i position of the itme that is clicked
     *          it will create an intent with the data requested by the user
     *          to detail_activity
     */

    @Override
    public void OnClickReact(int i) {
        Log.i(TAG, "OnClickReact: initiated due to user click");
        Intent intent =  new Intent(this,DetailActivity.class);
        intent.putExtra("image_url",imageList.get(i));
        intent.putExtra("image_name",imageName.get(i));
        startActivity(intent);
    }

    private class GetImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //connect to website
                Document document = Jsoup.connect(mimageURL).get();
                //Use elements from website page to get the class data
                Elements image = document.getElementsByTag("img");
                //for loop to iterate on webpage and find the source images
                for(Element el:image){
                    sourceName = el.attr("alt");
                    sourceImage = el.absUrl("src");
                        imageName.add(sourceName);
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