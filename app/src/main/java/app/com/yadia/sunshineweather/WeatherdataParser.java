package app.com.yadia.sunshineweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yadia on 8/3/16.
 */
public class WeatherdataParser {

    /**
     * Given a string of the form returned by the api call:
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
     * retrieve the maximum temperature for the day indicated by dayIndex
     * (Note: 0-indexed, so 0 would refer to the first day).
     */

    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex)
        throws JSONException {
        //TODO add json parsing code here

        JSONObject weather = new JSONObject(weatherJsonStr);
        JSONArray days = weather.getJSONArray("list");
        JSONObject dayInfo = days.getJSONObject(dayIndex);
        JSONObject temperatureInfo = dayInfo.getJSONObject("temp");

        return temperatureInfo.getDouble("max");
    }
}
