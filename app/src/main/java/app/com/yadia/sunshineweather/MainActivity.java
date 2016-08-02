package app.com.yadia.sunshineweather;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add the fragment to the FrameLayout
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceHolderFragment())
                    .commit();

        }
    }


    public static class PlaceHolderFragment extends android.support.v4.app.Fragment {


        public PlaceHolderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            // Create some dummy data for the ListView.  Here's a sample weekly forecast
      /*  String[] data = {
                "Mon 6/23 - Sunny - 31/17",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        }; */

            final ArrayList<Weather> data = new ArrayList<Weather>();
            data.add(new Weather("31", "Mon 6/23", "Sunny"));


            WeatherAdapter weatherAdapter = new WeatherAdapter(getActivity(), data);
            ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
            listView.setAdapter(weatherAdapter);


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            //Contains the saw JSON as a string
            String forecastJsonStr = null;

            try{
                String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Taipei&mode=json&units=metric&cnt=7&appid=6c2a6ec796edde80a3a23ca222454d20";
                String apiKey = "&appid=6c2a6ec796edde80a3a23ca222454d20";
                URL url = new URL(baseUrl);

                //creates a request to the OpenWeatherMap and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();  //must handle IOExceptions
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //read Input stream into String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null){
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

                if (buffer.length() == 0 ){
                    //stream is empty at 0. no need to keep parsing
                    return null;
                }

                forecastJsonStr = buffer.toString();
            } catch (IOException e){
                Log.e("MainActivity ", "Error: " + e.toString());
                return null;
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }

                if ( reader != null) {
                    try{
                        reader.close();
                    } catch (final IOException e){
                        Log.e("PlaceholderFragment", "Error in reader " + e.toString());
                    }
                }
            }




           /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                }
            });
            */

            return rootView;
        }
    }

}
