package com.devon.findtheweather;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ListView listView;
    ArrayAdapter<WeatherValues> adapter;
    List<WeatherValues> Vector;
    DownloadTask task;
    Handler messageHandler = new Handler();
    int numImgDownloaded = 0;

    public void getWeather(View view) {
        try {
            task = new DownloadTask();
            String cname = URLEncoder.encode(editText.getText().toString(), "UTF-8");
            task.execute("http://api.openweathermap.org/data/2.5/forecast/daily?q=" + cname + "&units=metric&cnt=7&lang=en&appid=c0c4a4b4047b97ebc5948ac9c48c0559");
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public void help(View view) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert).setTitle("Enter: {city name},{country code} \n Example: London,uk")
                .setMessage("")
                .setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .show();
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                displayError();
                task.cancel(true);
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null)
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
            try {
                JSONObject jsonObject = new JSONObject(s);
                Log.i("json", jsonObject.toString(1));
                JSONArray weatherArray = jsonObject.getJSONArray("list");
                JSONObject city = jsonObject.getJSONObject("city");
                TextView cityName = findViewById(R.id.city);
                cityName.setText(city.getString("name"));
                Vector = new ArrayList<WeatherValues>();

                for (int i = 0; i < weatherArray.length(); i++) {
                    Date date;
                    double high = 0;
                    double low = 0;
                    double day = 0;
                    int weatherId;

                    JSONObject forecast = weatherArray.getJSONObject(i);
                    JSONArray weatherDescription = forecast.getJSONArray("weather");
                    JSONObject arr = weatherDescription.getJSONObject(0);
                    String description = arr.getString("description");
                    String icon = arr.getString("icon");
                    String main = arr.getString("main");

                    JSONObject temperatureObject = forecast.getJSONObject("temp");
                    Log.i("temp", temperatureObject.toString());
                    String tempString = temperatureObject.toString();
                    JSONObject forecast1 = new JSONObject(tempString);
                    Log.i("String", tempString);
                    high = forecast1.getDouble("max");

                    Log.i("date", Double.toString(high));
                    low = forecast1.getDouble("min");
                    day = forecast1.getDouble("day");

                    WeatherValues weather = new WeatherValues();
                    weather.setDayTemperature(day);
                    weather.setHighTemperature(high);
                    weather.setLowTemperature(low);
                    weather.setDescription(description);
                    weather.setIcon(icon);
                    weather.setMainDescription(main);

                    if (i != 0)
                        weather.setDate(getDate(i));
                    else
                        weather.setDate("Today");
                    Vector.add(weather);
                    Log.i("vector", Double.toString(day));
                }
                updateWeather();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }
    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                Bitmap img = BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
                Vector.get(numImgDownloaded).setImg(img);
                numImgDownloaded++;
                return img;
            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = (ListView) findViewById(R.id.listView);
        List<WeatherValues> vb = new ArrayList<WeatherValues>();
        editText = (EditText) findViewById(R.id.edittext);
    }



    private void updateWeather() {
        adapter = new CustomAdapter(this, R.layout.custom_layout, Vector);
        listView.setAdapter(adapter);
    }

    private String getDate(int i) {
        Calendar calender = Calendar.getInstance();
        String currentWeekDay = "";
        i = calender.get(Calendar.DAY_OF_WEEK) + i;
        if(i > 7)
            i = i-7;
        switch (i) {
            case Calendar.SUNDAY:
                currentWeekDay = "Sunday";
                break;

            case Calendar.MONDAY:
                currentWeekDay = "Monday";
                break;

            case Calendar.TUESDAY:
                currentWeekDay = "Tuesday";
                break;

            case Calendar.WEDNESDAY:
                currentWeekDay = "Wednesday";
                break;

            case Calendar.THURSDAY:
                currentWeekDay = "Thursday";
                break;


            case Calendar.FRIDAY:
                currentWeekDay = "Friday";
                break;

            case Calendar.SATURDAY:
                currentWeekDay = "Saturday";
                break;
        }

        return currentWeekDay;
    }

    public void displayError() {
        Runnable doDisplayError = new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
            }
        };
        messageHandler.post(doDisplayError);
    }
}
