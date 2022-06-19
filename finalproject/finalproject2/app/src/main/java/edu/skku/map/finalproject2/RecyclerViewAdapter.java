package edu.skku.map.finalproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<MovieDataModel> mArrayList;
    public RecyclerViewAdapter(Context context, ArrayList<MovieDataModel> arraylist){
        this.mArrayList=arraylist;
        this.mContext=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(R.layout.activity_list_view_adapter2,parent,false);
        ViewHolder vh=new ViewHolder(view);

        return vh;
    }
    //click listener
    interface OnItemClickListener{
        void onItemClick(View v, int position);
    }
    private OnItemClickListener mListener=null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
       MovieDataModel data=mArrayList.get(position);
        String genre_imageurl="https://image.tmdb.org/t/p/w500"+mArrayList.get(position).getPoster_path();

        final Bitmap[] bitmap = new Bitmap[1];
        Thread mThread =new Thread(){
            @Override
            public void run(){
                try{
                    URL url=new URL(genre_imageurl);

                    HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is= conn.getInputStream();
                    bitmap[0] = BitmapFactory.decodeStream(is);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start();
        try{
            mThread.join();
            holder.imageView3.setImageBitmap(bitmap[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.imageView3=itemView.findViewById(R.id.imageView3);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.onItemClick(view,position);
                        }
                    }
                }
            });
        }

    }





}