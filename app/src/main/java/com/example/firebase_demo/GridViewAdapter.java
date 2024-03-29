package com.example.firebase_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    ArrayList<Place> list;
    Context context;
    int layout;

    public GridViewAdapter(ArrayList<Place> list, Context context, int layout) {
        this.list = list;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder {
        ImageView imgPlace;
        TextView tvTitle;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(layout, null);

            holder.imgPlace = view.findViewById(R.id.img_place_gv);
            holder.tvTitle = view.findViewById(R.id.tv_title_gv);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Place place = list.get(i);

        Picasso.get().load(place.imageUrl).into(holder.imgPlace);
        holder.tvTitle.setText(place.title);

        return view;
    }
}
