package com.divineventures.countryapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 6/1/2018.
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<MyData> myData;

    public MyAdapter(Context context, List<MyData> myData) {
        this.context = context;
        this.myData = myData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.name.setText(myData.get(position).getName());
        SvgLoader.pluck().with((Activity) context)
                .load(myData.get(position).getFlag_link(), holder.flag);
        holder.region.setText("Region: "+myData.get(position).getRegion());
        holder.population.setText("Population: "+myData.get(position).getPopulation());
        holder.cardView.setCardBackgroundColor(Color.GRAY);
        //Toast.makeText(context,myData.get(position).getFlag_link(),Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }


    public void setFilter(ArrayList<MyData> countryList) {
        myData = new ArrayList<>();
        myData.addAll(countryList);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, population, region;
        public ImageView flag;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            flag = itemView.findViewById(R.id.cflag);
            population = itemView.findViewById(R.id.poptxt);
            region = itemView.findViewById(R.id.region);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
