package com.devon.findtheweather;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

/**
 * Created by Devon on 2/5/2018.
 */

public class CustomAdapter extends ArrayAdapter<WeatherValues> {

    private Context context;
    List<WeatherValues> list;

    public CustomAdapter(Context context, int resource, List<WeatherValues> list) {
        super(context, R.layout.custom_layout, list);

        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public WeatherValues getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup viewGroup) {
        WeatherValues weatherValues = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View views = inflater.inflate(R.layout.custom_layout, viewGroup, false);

         ImageView imageView = (ImageView) views.findViewById(R.id.icon);
         imageView.setImageResource(weatherValues.setImage());

        TextView description = (TextView) views.findViewById(R.id.description);
        description.setText(weatherValues.getDescription());

        TextView high_temp = (TextView) views.findViewById(R.id.high_temp);
        high_temp.setText(Double.toString(weatherValues.getDayTemperature()) + "°");

        TextView low_temp = (TextView) views.findViewById(R.id.low_temp);
        low_temp.setText(Double.toString(weatherValues.getLowTemperature()) + "°");

        TextView date = (TextView) views.findViewById(R.id.date);
        date.setText(weatherValues.getDate());

        return views;
    }

}

