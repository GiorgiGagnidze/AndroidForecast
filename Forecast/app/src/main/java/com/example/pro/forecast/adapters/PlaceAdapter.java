package com.example.pro.forecast.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pro.forecast.App;
import com.example.pro.forecast.R;
import com.example.pro.forecast.model.Icon;
import com.example.pro.forecast.model.Item;

import java.util.ArrayList;

public class PlaceAdapter extends BaseAdapter{
    private ArrayList<Item> items;
    private Context context;

    public PlaceAdapter(Context context,ArrayList<Item> items){
        this.items = items;
        this.context = context;
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
        if (view == null){
            view = View.inflate(context, R.layout.place_item,null);

            Holder h = new Holder();
            h.imgV = (ImageView)view.findViewById(R.id.img);
            h.txtV = (TextView)view.findViewById(R.id.item);
            h.temp = (TextView)view.findViewById(R.id.temp);
            h.sum = (TextView)view.findViewById(R.id.summary);
            view.setTag(h);
        }
        Holder h = (Holder)view.getTag();
        Item item = items.get(i);


        switch (item.getIcon()){
            case Icon.C_DAY:
                h.imgV.setImageResource(R.drawable.sun);
                break;
            case Icon.C_NIGHT:
                h.imgV.setImageResource(R.drawable.brightmoon);
                break;
            case Icon.CLOUDY:
                h.imgV.setImageResource(R.drawable.clouds);
                break;
            case Icon.FOG:
                h.imgV.setImageResource(R.drawable.fogday);
                break;
            case Icon.P_CLOUDY_DAY:
                h.imgV.setImageResource(R.drawable.partlycloudyday);
                break;
            case Icon.P_CLOUDY_NIGHT:
                h.imgV.setImageResource(R.drawable.partlycloudynight);
                break;
            case Icon.RAIN:
                h.imgV.setImageResource(R.drawable.rain);
                break;
            case Icon.SLEET:
                h.imgV.setImageResource(R.drawable.sleet);
                break;
            case Icon.SNOW:
                h.imgV.setImageResource(R.drawable.snow);
                break;
            case Icon.WIND:
                h.imgV.setImageResource(R.drawable.dust);
                break;
            case App.NO_INFO:
                h.imgV.setImageResource(R.drawable.attention);
                break;
            default:
                h.imgV.setImageResource(R.drawable.storm);
        }

        h.txtV.setText(item.getTimePlace());
        h.temp.setText(item.getTemperature());
        h.sum.setText(item.getSummary());

        return view;
    }

    public static class Holder {
        TextView txtV;
        TextView temp;
        ImageView imgV;
        TextView sum;
    }
}
