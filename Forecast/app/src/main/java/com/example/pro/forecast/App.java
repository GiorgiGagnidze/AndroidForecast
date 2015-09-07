package com.example.pro.forecast;

import android.app.Application;

import com.example.pro.forecast.adapters.PlaceAdapter;
import com.example.pro.forecast.model.Item;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.util.ArrayList;
import java.util.HashMap;


public class App extends Application {
    private ArrayList<Item> items;
    public static final String NO_INFO = "no";
    private HashMap<Integer,WeatherResponse> weatherResponses = new HashMap<>();
    private int counter = 0;
    private PlaceAdapter adapter;

    @Override
    public void onCreate() {
        super.onCreate();
        String[] names = getResources().getStringArray(R.array.place_names);
        items = new ArrayList<>();
        String noInfo = getResources().getString(R.string.no_info);
        for (int i=0; i<names.length; i++)
            items.add(new Item(noInfo,NO_INFO,names[i],noInfo));
        adapter = new PlaceAdapter(this,items);
    }

    public PlaceAdapter getAdapter() {
        return adapter;
    }

    public synchronized void increment(){
        counter++;
    }

    public synchronized void decrement(){
        counter--;
    }

    public int getCounter() {
        return counter;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public HashMap<Integer,WeatherResponse> getWeatherResponses() {
        return weatherResponses;
    }
}
