package com.app.learning.atmos;


import org.json.JSONException;
import org.json.JSONObject;
public class WeatherForcastDataModel {
    String today;
    String tomorrow;
    String day_after_tomorrow;
    int today_index;
    int tomorrow_index;
    int day_after_tomorrow_index;
    int[] today_temp=new int[2];
    int[] tomorrow_temp=new int[2];
    int[] day_aftr_tom_temp=new int[2];
    public static WeatherForcastDataModel fromJSON(JSONObject jsonObject)
    {
        WeatherForcastDataModel weatherData=new WeatherForcastDataModel();
        try {

            weatherData.today=jsonObject.getJSONArray("daily").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
            weatherData.tomorrow= jsonObject.getJSONArray("daily").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("description");
            weatherData.day_after_tomorrow=jsonObject.getJSONArray("daily").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("description");
            weatherData.today_index=getBackground(jsonObject.getJSONArray("daily").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getInt("id"));
            weatherData.tomorrow_index=getBackground(jsonObject.getJSONArray("daily").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getInt("id"));
            weatherData.day_after_tomorrow_index=getBackground(jsonObject.getJSONArray("daily").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getInt("id"));

            weatherData.today_temp[0]= (int) Math.round(jsonObject.getJSONArray("daily").getJSONObject(0).getJSONObject("temp").getDouble("min")-273.15);
            weatherData.today_temp[1]= (int) Math.round(jsonObject.getJSONArray("daily").getJSONObject(0).getJSONObject("temp").getDouble("max")-273.15);
            weatherData.tomorrow_temp[0]= (int) Math.round(jsonObject.getJSONArray("daily").getJSONObject(1).getJSONObject("temp").getDouble("min")-273.15);
            weatherData.tomorrow_temp[1]= (int) Math.round(jsonObject.getJSONArray("daily").getJSONObject(1).getJSONObject("temp").getDouble("max")-273.15);
            weatherData.day_aftr_tom_temp[0]= (int) Math.round(jsonObject.getJSONArray("daily").getJSONObject(2).getJSONObject("temp").getDouble("min")-273.15);
            weatherData.day_aftr_tom_temp[1]= (int) Math.round(jsonObject.getJSONArray("daily").getJSONObject(2).getJSONObject("temp").getDouble("max")-273.15);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        return weatherData;
    }
    private static int getBackground(int condition)
    {
        int background=0;

        if(condition>=200 && condition<=210)
        {
                background=26;
        }
        else if(condition>=211 && condition<=230)
        {
                background=28;
        }
        else if(condition==231 || condition==232)
        {
                background=15;
        }
        else if(condition>=300 && condition<=321)
        {
                background=5;
        }
        else if(condition==500 || condition==520)
        {
                background=24;
        }
        else if(condition==501 || condition==521)
        {
                background=22;
        }
        else if(condition==502 || condition==503 || condition==504 || condition==522 || condition==531)
        {
                background=13;
        }
        else if(condition==511 || condition==611|| condition==613)
        {
                background=30;
        }
        else if(condition==600|| condition==601|| condition==602|| (condition>=616 && condition<=622))
        {
                background=33;
        }

        else if(condition==731|| condition==761|| condition==751|| condition==762|| condition==771)
            background=7;

        else if(condition==741)
        {
                background=8;
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
                background=19;
        }
        else if(condition==803||condition==804)
        {
                background=17;
        }
        else if(condition==801||condition==802)
        {
                background=20;
        }



        return background;
    }

    public String getToday() {
        return today;
    }

    public String getTomorrow() {
        return tomorrow;
    }

    public String getDay_after_tomorrow() {
        return day_after_tomorrow;
    }

    public int getToday_index() {
        return today_index;
    }

    public int getTomorrow_index() {
        return tomorrow_index;
    }

    public int getDay_after_tomorrow_index() {
        return day_after_tomorrow_index;
    }

    public int[] getToday_temp() {
        return today_temp;
    }

    public int[] getTomorrow_temp() {
        return tomorrow_temp;
    }

    public int[] getDay_aftr_tom_temp() {
        return day_aftr_tom_temp;
    }
}
