package com.example.pro.forecast;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pro.forecast.adapters.PlaceAdapter;
import com.example.pro.forecast.model.Item;
import com.example.pro.forecast.ui.DailyActivity;
import com.johnhiott.darkskyandroidlib.ForecastApi;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.DataPoint;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;


import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity {
    private static final String API_KEY = "4da0c6cc192eeb00c30c373c07ecee08";
    private static final String DELIMITER = "/";
    public static final String INDEX_KEY = "index";
    private ArrayList<Item> items;
    private App app;
    private PlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (App)getApplication();

        items = app.getItems();

        final Activity activity = this;
        ListView listView = (ListView)findViewById(R.id.places);
        adapter = app.getAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(activity, DailyActivity.class);
                intent.putExtra(INDEX_KEY,i);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);

        if (app.getCounter() == 0)
            downloadWeather();
    }

    private void downloadWeather(){
        Resources resources = getResources();
        ForecastApi.create(API_KEY);
        String[] lat = resources.getStringArray(R.array.latitudes);
        String[] lng = resources.getStringArray(R.array.longitudes);
        final HashMap<String,Integer> indexes = new HashMap<>();
        final Activity activity = this;
        final HashMap<Integer,WeatherResponse> weatherResponses = app.getWeatherResponses();
        for (int i=0; i<lat.length; i++){
            app.increment();
            RequestBuilder weather = new RequestBuilder();

            String pair = lng[i]+DELIMITER+lat[i];
            indexes.put(pair,i);

            Request request = new Request();
            request.setLat(lat[i]);
            request.setLng(lng[i]);
            request.setUnits(Request.Units.UK);
            request.setLanguage(Request.Language.ENGLISH);

            weather.getWeather(request, new Callback<WeatherResponse>() {
                @Override
                public void success(WeatherResponse weatherResponse, Response response) {
                    String pair = weatherResponse.getLongitude()+DELIMITER+weatherResponse.getLatitude();
                    int index = indexes.get(pair);
                    Item item = items.get(index);
                    weatherResponses.put(index,weatherResponse);

                    DataPoint point = weatherResponse.getCurrently();
                    item.setIcon(point.getIcon());
                    item.setSummary(point.getSummary());
                    item.setTemperature(point.getTemperature() + " ℃");
                    adapter.notifyDataSetChanged();
                    app.decrement();
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    if (app.getCounter() == 1)
                        Toast.makeText(activity,getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                    app.decrement();
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
