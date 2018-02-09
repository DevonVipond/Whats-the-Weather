package com.devon.findtheweather;

import android.graphics.Bitmap;

/**
 * Created by Devon on 2/5/2018.
 */

public class WeatherValues {
    public double highTemperature;
    public double lowTemperature;
    public double dayTemperature;
    String description;
    String date;
    String icon;
    Bitmap img;
    String mainDescription;

    public String getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    WeatherValues() {
        highTemperature = 0;
        lowTemperature = 0;
        dayTemperature = 0;

        description = "";
        date = "";
        icon = "";
    }

    public double getHighTemperature() {
        return highTemperature;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setHighTemperature(double highTemperature) {
        this.highTemperature = highTemperature;
    }

    public double getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(double lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public double getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(double dayTemperature) {
        this.dayTemperature = dayTemperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int setImage() {

        if (icon.equals("01d") || icon.equals("01n")) {
            return R.drawable.a01d;
        } else if (icon.equals("02d") || icon.equals("02n")) {
            return R.drawable.a02d;
        } else if (icon.equals("03d") || icon.equals("03n")) {
            return R.drawable.a03d;
        } else if (icon.equals("04d") || icon.equals("04n")) {
            return R.drawable.a04d;
        } else if (icon.equals("09d") || icon.equals("09n")) {
            return R.drawable.a09d;
        } else if (icon.equals("10d") || icon.equals("10n")) {
            return R.drawable.a10d;
        } else if (icon.equals("11d") || icon.equals("11n")) {
            return R.drawable.a11d;
        } else if (icon.equals("13d") || icon.equals("13n")) {
            return R.drawable.a13d;
        } else if (icon.equals("50d") || icon.equals("50n")) {
            return R.drawable.a50d;
        } else return -1;
    }

}
