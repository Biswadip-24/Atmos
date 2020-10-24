package com.app.learning.atmos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.security.Permission;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    final String URL = "http://api.openweathermap.org/data/2.5/weather";
    final String APP_ID = "c3d74e1b1bcfbf6a4e6fbff21c7a50f2";
    final String URL_FORECAST="https://api.openweathermap.org/data/2.5/onecall?";

    int image[] = {R.drawable.blizzard,
            R.drawable.blizzard_night,
            R.drawable.breezy,
            R.drawable.breezy_snow,
            R.drawable.clear_night,
            R.drawable.drizzle,
            R.drawable.drizzle_night,
            R.drawable.dust,
            R.drawable.fog,
            R.drawable.fog_night,
            R.drawable.hail,
            R.drawable.hail_night,
            R.drawable.haze,
            R.drawable.heavy_rain,
            R.drawable.heavy_rain_night,
            R.drawable.mix_rainfall,
            R.drawable.mix_rainfall_night,
            R.drawable.mostly_cloudy,
            R.drawable.mostly_cloudy_night,
            R.drawable.mostly_sunny,
            R.drawable.party_cloudy,
            R.drawable.party_cloudy_night,
            R.drawable.rain,
            R.drawable.rain_night,
            R.drawable.scattered_showers,
            R.drawable.scattered_showers_night,
            R.drawable.scattered_thunderstorm,
            R.drawable.scattered_thunderstorm_night,
            R.drawable.severe_thunderstorm,
            R.drawable.severe_thunderstorm_night,
            R.drawable.sleet,
            R.drawable.sleet_night,
            R.drawable.smoke,
            R.drawable.snow,
            R.drawable.snow_night,
            R.drawable.tornado
    };

    int background_image[]={
            R.drawable.clearnight_back,
            R.drawable.cloudy_back,
            R.drawable.haze_back,
            R.drawable.rain_back,
            R.drawable.partlycloudy_back,
            R.drawable.nightpartlycloudy_back,
            R.drawable.sunny_back,
            R.drawable.thunderstorm_back,
            R.drawable.tornado_back,
            R.drawable.snow_back
    };

    int description[]={
            R.string.description0,
            R.string.description1,
            R.string.description2,
            R.string.description3,
            R.string.description4,
            R.string.description5,
            R.string.description6,
            R.string.description7,
            R.string.description8,
            R.string.description9,
            R.string.description10,
            R.string.description11,
            R.string.description12,
            R.string.description13,
            R.string.description14,
            R.string.description15,
            R.string.description16,
            R.string.description17,
            R.string.description18,
            R.string.description19,
            R.string.description20,
            R.string.description21,
            R.string.description22,
            R.string.description23,
            R.string.description24,
            R.string.description25,
            R.string.description26,
            R.string.description27,
            R.string.description28,
            R.string.description29,
            R.string.description30,
            R.string.description31,
            R.string.description32,
            R.string.description33,
            R.string.description34,
            R.string.description35,



    };


    private String Day;
    private static final String TAG = "Atmos";
    int LOCATION_REQUEST_CODE = 10001;

    FusedLocationProviderClient mFusedLocationProviderClient;
    private TextView mTemperature;
    private TextView mCity;
    private TextView mDate;
    private ConstraintLayout mBackground;
    private int index;
    private TextView mDescription;
    private int mBackgroundIndex;

    private TextView mToday_weather;
    private TextView mTomorrow_weather;
    private TextView mDayAfterTomorrow_weather;
    private ImageView mToday_icon;
    private ImageView mTomorrow_icon;
    private ImageView mDayAfterTomorrow_icon;
    private TextView mTodayTemp;
    private TextView mTomorrowTemp;
    private TextView mDayAfterTomTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mTemperature= findViewById(R.id.Temperature);
        mCity= findViewById(R.id.City);

        mDate=findViewById(R.id.Date);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        String[] day={"SAT","SUN","MON","TUE","WED","THU","FRI"};
        String s=date.substring(0,date.indexOf(","));
        for(int i=0;i<7;i++)
        {
            if(s.equalsIgnoreCase(day[i]))
            {
                Day=day[(i+2)%8];
                break;
            }
        }
        mDate.setText(date);

        mBackground =(ConstraintLayout) findViewById(R.id.Background_Layout);
        mDescription = findViewById(R.id.weather_description);

        mToday_weather = (TextView)findViewById(R.id.today_weather);
        mTomorrow_weather = (TextView)findViewById(R.id.tomorrow_wather);
        mDayAfterTomorrow_weather = (TextView)findViewById(R.id.day_after_tomorrow_weather);
        mToday_icon=(ImageView)findViewById(R.id.today_icon);
        mTomorrow_icon=(ImageView)findViewById(R.id.tomorrow_icon);
        mDayAfterTomorrow_icon=(ImageView)findViewById(R.id.day_after_tomorrow_icon);

        mTodayTemp = (TextView) findViewById(R.id.today_tmp);
        mTomorrowTemp = (TextView) findViewById(R.id.tomorrow_tmp);
        mDayAfterTomTemp = (TextView) findViewById(R.id.day_aftr_tom_tmp);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            askLocationPermission();
        }
    }

    private void askLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "askLocationPermission: you should show an alert dialog...");
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }

            /**NOTE:
             * If you are wanting location inside a fragment then instead of
             * using "ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);"
             * use : "requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);"
             * For more details look for Covid-19 app.
             */
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {//This function is automatically called after some response to the request Permission is done, like if you allow or deny the request
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)//If Request granted
            {
                // Permission granted
                Log.d(TAG,"Permission granted");
                getLastLocation();
            } else//If request not granted
            {
                Toast.makeText(this, "Location Permission denied", Toast.LENGTH_LONG).show();
                Log.d("MainActivity","Permission not granted");
            }
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationTask = mFusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    //We have a location

                    /**IF YOU WANT MORE DETAILS OF YOUR LOCATION, LIKE STATE, COUNTRY NAME,ETC THEN ADD THIS CODE:
                     Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                     List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                     Log.d("Location", "Hello");
                     String s = addresses.toString();

                     * HERE THE STRING S CONTAIN YOUR ENTIRE LOCATION ADDRESS
                     */



                    RequestParams params=new RequestParams();
                    params.put("lat",location.getLatitude());
                    params.put("lon",location.getLongitude());
                    params.put("appid",APP_ID);
                    letsDoSomeNetworking(params);
                    letsDoSomeMoreNetworking(params);

                    Log.d(TAG, "onSuccess: " + location.toString());
                    Log.d(TAG, "onSuccess: " + location.getLatitude());
                    Log.d(TAG, "onSuccess: " + location.getLongitude());
                } else  {
                    Log.d(TAG, "onSuccess: Location was null...");
                }
            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.getLocalizedMessage() );
            }
        });
    }

    public void search_city(View v)
    {
        Toast.makeText(MainActivity.this,"This Feature will be added in further updates",Toast.LENGTH_SHORT).show();
    }


    private void letsDoSomeNetworking(RequestParams params){
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(URL,params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Weather","SUCCESS! JSON: "+response.toString());
                WeatherDataModel weatherData=WeatherDataModel.fromJSON(response);
                mTemperature.setText(weatherData.getTemperature());
                mCity.setText(weatherData.getCity());
                index=weatherData.getBackgroundIndex();
                mBackgroundIndex=weatherData.getBackgroundRealIndex();
                backgrnd_change();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Weather","Fail "+throwable.toString());
                Log.d("Weather","Status Code : "+statusCode);
                Toast.makeText(MainActivity.this,"Request Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backgrnd_change()
    {
        Log.d("Weather","Index"+index);


        mBackground.setBackgroundResource(background_image[mBackgroundIndex]);

        mDescription.setText(description[index]);
    }


    private void letsDoSomeMoreNetworking(RequestParams params)
    {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(URL_FORECAST,params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Weather","SUCCESS! JSON: "+response.toString());
                WeatherForcastDataModel weatherData=WeatherForcastDataModel.fromJSON(response);

                Log.d(TAG,""+weatherData.getToday_index());
                Log.d(TAG,""+weatherData.getTomorrow_index());
                Log.d(TAG,""+weatherData.getDay_after_tomorrow_index());


                mToday_weather.setText("Today · "+getResources().getString(description[weatherData.getToday_index()]));
                mTomorrow_weather.setText("Tomorrow · "+getResources().getString(description[weatherData.getTomorrow_index()]));
                mDayAfterTomorrow_weather.setText(Day+" · "+getResources().getString(description[weatherData.getDay_after_tomorrow_index()]));

                mToday_icon.setImageResource(image[weatherData.getToday_index()]);
                mTomorrow_icon.setImageResource(image[weatherData.getTomorrow_index()]);
                mDayAfterTomorrow_icon.setImageResource(image[weatherData.getDay_after_tomorrow_index()]);

                mTodayTemp.setText(weatherData.getToday_temp()[1]+"°/"+weatherData.getToday_temp()[0]+"°");
                mTomorrowTemp.setText(weatherData.getTomorrow_temp()[1]+"°/"+weatherData.getTomorrow_temp()[0]+"°");
                mDayAfterTomTemp.setText(weatherData.getDay_aftr_tom_temp()[1]+"°/"+weatherData.getDay_aftr_tom_temp()[0]+"°");


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Weather","Fail "+throwable.toString());
                Log.d("Weather","Status Code : "+statusCode);
                Toast.makeText(MainActivity.this,"Request Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
