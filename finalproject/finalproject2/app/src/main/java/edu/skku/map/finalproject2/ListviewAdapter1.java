package edu.skku.map.finalproject2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class ListviewAdapter1 extends BaseAdapter {
    private Context mContext;
    private ArrayList<MovieDataModel> items;
    public ListviewAdapter1(Context mContext, ArrayList<MovieDataModel> items){
        this.mContext=mContext;
        this.items=items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.activity_list_view_adapter1,viewGroup,false);
        ImageView imageView=view.findViewById(R.id.imageview);
        TextView tv2= view.findViewById(R.id.textView2);
        TextView tv3=view.findViewById(R.id.textView3);
        TextView tv4=view.findViewById(R.id.textView4);
        Button btn2=view.findViewById(R.id.button2);
        String imageurl="https://image.tmdb.org/t/p/w500"+items.get(i).getPoster_path();


        tv2.setText(items.get(i).getTitle());
        tv3.setText(items.get(i).getGenre_ids().toString());
        tv4.setText(items.get(i).getRelease_date());

        btn2.setText(items.get(i).getBtn());
        final Bitmap[] bitmap = new Bitmap[1];
        Thread mThread =new Thread(){
            @Override
            public void run(){
                try{
                    URL url=new URL(imageurl);

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
            imageView.setImageBitmap(bitmap[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return view;
    }
}
