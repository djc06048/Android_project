package edu.skku.map.finalproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class movielikeListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> items;
    public movielikeListAdapter(Context mContext, ArrayList<String> items){
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
        if(view==null){
            LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.activity_movielike_list_adapter,viewGroup,false);
        }
        TextView tv;
        tv=view.findViewById(R.id.textView9);

        tv.setText(items.get(i));
        return view;
    }
}