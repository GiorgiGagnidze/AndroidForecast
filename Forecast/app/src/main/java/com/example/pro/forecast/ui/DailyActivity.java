package com.example.pro.forecast.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pro.forecast.App;
import com.example.pro.forecast.MainActivity;
import com.example.pro.forecast.R;
import com.example.pro.forecast.adapters.PlaceAdapter;
import com.example.pro.forecast.model.Item;
import com.johnhiott.darkskyandroidlib.models.DataPoint;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int index = getIntent().getExtras().getInt(MainActivity.INDEX_KEY);
        App app = (App)getApplication();
        WeatherResponse response = app.getWeatherResponses().get(index);
        TextView textView = (TextView)findViewById(R.id.place_name);
        Item item = app.getItems().get(index);
        String place = item.getTimePlace();
        Resources resources = getResources();
        if (response == null){
            textView.setText(resources.getString(R.string.not_downloaded)+" "+place);
        } else {
            textView.setText(place);
            ArrayList<Item> items = new ArrayList<>();
            List<DataPoint> data = response.getDaily().getData();
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            day++;
            items.add(new Item("","",resources.getString(R.string.today),""));
            for (int i=0; i<6; i++){
                if (day > Calendar.SATURDAY)
                    day = Calendar.SUNDAY;
                String weekDay = "";
                switch (day){
                    case Calendar.MONDAY:
                        weekDay = resources.getString(R.string.monday);
                        break;
                    case Calendar.TUESDAY:
                        weekDay = resources.getString(R.string.tuesday);
                        break;
                    case Calendar.WEDNESDAY:
                        weekDay = resources.getString(R.string.wednesday);
                        break;
                    case Calendar.THURSDAY:
                        weekDay = resources.getString(R.string.thursday);
                        break;
                    case Calendar.FRIDAY:
                        weekDay = resources.getString(R.string.friday);
                        break;
                    case Calendar.SATURDAY:
                        weekDay = resources.getString(R.string.saturday);
                        break;
                    case Calendar.SUNDAY:
                        weekDay = resources.getString(R.string.sunday);
                        break;
                }
                items.add(new Item("","",weekDay,""));
                day++;
            }
            for (int i=0; i<items.size(); i++){
                DataPoint point = data.get(i);
                Item next = items.get(i);
                next.setTemperature(point.getTemperatureMin()+" ℃ - "+point.getTemperatureMax()+" ℃");
                next.setIcon(point.getIcon());
                next.setSummary(point.getSummary());
            }
            ListView listView = (ListView)findViewById(R.id.list);
            PlaceAdapter adapter = new PlaceAdapter(this,items);
            listView.setAdapter(adapter);
        }
    }
}
