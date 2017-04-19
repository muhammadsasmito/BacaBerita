package com.project.sash.bacaberita;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by User on 15/04/2017.
 */

public class FeedAdapter extends ArrayAdapter<FeedData> {

    Context context;
    ArrayList<FeedData> values;

    public FeedAdapter(@NonNull Context context, ArrayList<FeedData> values) {
        super(context, R.layout.list_feed,values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_feed,parent,false);
        TextView tvTitle= (TextView) view.findViewById(R.id.tvFeedTitle);
        TextView tvPubDate= (TextView) view.findViewById(R.id.tvPubDate);
        TextView tvLink= (TextView) view.findViewById(R.id.tvFeedLink);
        ImageView ivFeed = (ImageView) view.findViewById(R.id.ivFeedImage);

        tvTitle.setText(values.get(position).feedTitle);
        tvPubDate.setText(values.get(position).feedPubDate);
        tvLink.setText(values.get(position).feedLink);

        LoadImage loadImage = new LoadImage(ivFeed);
        loadImage.execute(values.get(position).feedImage);


        return view;

    }

    private class LoadImage extends AsyncTask<String,String,Bitmap>
    {

        private ImageView currentImageView;

        public  LoadImage(ImageView iv){
            currentImageView = iv;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            Bitmap bmp = null;
            URL url = null;

            try{
                url = new URL(strings[0]);
                bmp = BitmapFactory.decodeStream(
                        url.openConnection().getInputStream()
                );
            } catch (MalformedURLException e) {
                System.out.println("URL Salah");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Gagal Download");
                e.printStackTrace();
            }

            return  bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap!=null){
                currentImageView.setImageBitmap(bitmap);
            }else{
                currentImageView.setImageResource(R.mipmap.ic_launcher);
            }
        }
    }

}

