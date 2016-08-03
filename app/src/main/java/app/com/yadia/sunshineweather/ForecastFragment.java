package app.com.yadia.sunshineweather;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by yadia on 8/3/16.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_refresh:
                FetchWeatherTask weatherTask = new FetchWeatherTask();
                weatherTask.execute("Taipei");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final ArrayList<Weather> data = new ArrayList<Weather>();
        data.add(new Weather("31", "Mon 6/23", "Sunny"));

        WeatherAdapter weatherAdapter = new WeatherAdapter(getActivity(), data);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(weatherAdapter);


           /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                }
            });
            */

        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, Void>{

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(String... params) {

            if (params.length == 0){
                return null;
            }

            //need to be declared outside the try catch in order to close them
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;

            String format = "json";
            String units = "metric";
            int numDays = 7;

            try {

                final String FORECAST_BASE_URL =
                        "http://api.openweathermap.org/data/2.5/forecast/daily?q=Taipei&mode=json&units=metric&cnt=7&appid=6c2a6ec796edde80a3a23ca222454d20";
                final String QUERRY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String DAYS_PARAM = "units";
                final String APPID_PARAM = "&appid=6c2a6ec796edde80a3a23ca222454d20";
                //String apiKey = "&appid=6c2a6ec796edde80a3a23ca222454d20";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon().build();

                URL url = new URL(builtUri.toString());

                //creates a request to the OpenWeatherMap and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();  //must handle IOExceptions
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //read Input stream into String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                //creates input reader for buffer
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    ///Since it's JSON, adding a newline isn't necessary (it won't affect parsing
                    // makes it more readable when printing it
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    //stream is empty at 0. no need to keep parsing
                    return null;
                }

                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error in reader " + e.toString());
                    }
                }
            }
            return null;
        }


    }

    private String getReadableDateString (long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd");
        return simpleDateFormat.format(time);
    }

    // prepares the weather high/low for presentation
    private String formatHighLows(double high, double low){
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr =  roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

    private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays){

        //the names of the Json objects needed to be extracted
        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MIN = "min";
        final String OWM_MAX = "max";
        final String OWM_DESCRIPTION = "main";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        Time dayTime = new Time();
        dayTime.setTime();
        int julianStartDay = Time;
    }


}
