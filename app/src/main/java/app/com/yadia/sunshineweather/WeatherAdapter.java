package app.com.yadia.sunshineweather;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yadia on 8/1/16.
 */
public class WeatherAdapter extends ArrayAdapter<Weather> {

    private int mWeatherIcon;

    /**
     * Provides builder for the ArrayAdapter
     * @param context -- activity
     * @param weatherForecast -- arrayList with the data
     */
    public WeatherAdapter(Activity context, ArrayList<Weather> weatherForecast){
        super(context,0, weatherForecast);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_forcast,
                    parent, false);
        }

        Weather weatherForecast = getItem(position);

        TextView tempText = (TextView) listView.findViewById(R.id.list_item_temp);
        tempText.setText(weatherForecast.getTemp());
        TextView day = (TextView) listView.findViewById(R.id.list_item_date);
        day.setText(weatherForecast.getDay());
        TextView weather = (TextView) listView.findViewById(R.id.list_item_weather);
        weather.setText(weatherForecast.getWeather());

        return listView;
    }
}
