package com.app.learning.atmos;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataModel
{
    String mTemperature;
    int mCondition;
    String mCity;
    int mBacgroundIndex;
    int mBackgroundRealIndex;
    static String mday_night;

    public static WeatherDataModel fromJSON(JSONObject jsonObject)
    {
        WeatherDataModel weatherData=new WeatherDataModel();

        try {
            weatherData.mCity = jsonObject.getString("name");
            weatherData.mCondition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.mday_night= jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
            weatherData.mBacgroundIndex=getBackground(weatherData.mCondition);
            weatherData.mBackgroundRealIndex=getRealBackground(weatherData.mCondition);
            weatherData.mTemperature=Integer.toString((int)(Math.round(jsonObject.getJSONObject("main").getDouble("temp")-273.15)));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }

        return weatherData;
    }

    private static int getRealBackground(int condition) {
        int background = 0;

        if(condition==731|| condition==761|| condition==751|| condition==762|| condition==771||condition==741||condition==721||condition==711||condition==701)
        {//haze
            background = 2;
        }
        else if(condition==231 || condition==232||condition==501 || condition==521||condition==502 || condition==503 || condition==504 || condition==522 || condition==531||condition==500 || condition==520||(condition>=300 && condition<=321))
        {//rain
                background=3;
        }
        else if(condition==781)
        {//tornado
            background=8;
        }
        else if((condition>=211 && condition<=230)||(condition>=200 && condition<=210))
        {//thunderstorm
            background=7;
        }
        else if(condition==600|| condition==601|| condition==602|| (condition>=616 && condition<=622))
        {//snow
            background=9;
        }
        else if(condition==800)
        {//clear
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=6;
            else
                background=0;
        }
        else if(condition==801||condition==802)
        {//partlyCloudy
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=4;
            else
                background=5;
        }
        else if(condition==803||condition==804)
        {//cloudy
            background=1;
        }




        return background;
    }

    private static int getBackground(int condition)
    {
        int background=0;

        if(condition>=200 && condition<=210)
            {
                if(mday_night.charAt(mday_night.length()-1)=='d')
                    background=26;
                else
                    background=27;
            }
        else if(condition>=211 && condition<=230)
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=28;
            else
                background=29;
        }
        else if(condition==231 || condition==232)
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=15;
            else
                background=16;
        }
        else if(condition>=300 && condition<=321)
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=5;
            else
                background=6;
        }
        else if(condition==500 || condition==520)
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=24;
            else
                background=25;
        }
        else if(condition==501 || condition==521)
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=22;
            else
                background=23;
        }
        else if(condition==502 || condition==503 || condition==504 || condition==522 || condition==531)
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=13;
            else
                background=14;
        }
        else if(condition==511 || condition==611|| condition==613)
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=30;
            else
                background=31;
        }
        else if(condition==600|| condition==601|| condition==602|| (condition>=616 && condition<=622))
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=33;
            else
                background=34;
        }

        else if(condition==731|| condition==761|| condition==751|| condition==762|| condition==771)
            background=7;

        else if(condition==741)
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=8;
            else
                background=9;
        }
        else if(condition==721)
        {
            background=12;
        }
        else if(condition==711||condition==701)
        {
            background=32;
        }
        else if(condition==781)
        {
            background=35;
        }
        else if(condition==800)
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=19;
            else
                background=4;
        }
        else if(condition==803||condition==804)
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=17;
            else
                background=18;
        }
        else if(condition==801||condition==802)
        {
            if(mday_night.charAt(mday_night.length()-1)=='d')
                background=20;
            else
                background=21;
        }



        return background;
    }



    public String getTemperature() {
        return mTemperature;
    }

    public String getCity() {
        return mCity;
    }

    public int getBackgroundIndex() {
        return mBacgroundIndex;
    }

    public int getBackgroundRealIndex(){
        return mBackgroundRealIndex;
    }
}
