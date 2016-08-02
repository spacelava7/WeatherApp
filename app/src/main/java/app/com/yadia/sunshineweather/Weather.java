package app.com.yadia.sunshineweather;

/**
 * Created by yadia on 8/1/16.
 */
public class Weather {
    private String mDay;
    private String mTemp;
    private String mWeather;

    public Weather(String degree, String day, String weather){
        mTemp = degree;
        mDay = day;
        mWeather = weather;
    }

    public void setWeather(String weather){
        mWeather = weather;
    }

    public String getWeather(){
        return  mWeather;
    }

    public void setDay(String day){
        mDay = day;
    }

    public String getDay(){
        return mDay;
    }

    public void setTemp(String temp){
        mTemp = temp;
    }

    public String getTemp(){
        return mTemp;
    }
}
