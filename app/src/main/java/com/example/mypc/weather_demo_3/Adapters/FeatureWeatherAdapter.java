package com.example.mypc.weather_demo_3.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypc.weather_demo_3.Models.Weather;
import com.example.mypc.weather_demo_3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MyPC on 20/12/2017.
 */

public class FeatureWeatherAdapter extends BaseAdapter {
    Context context;
    ArrayList<Weather> arrayList;

    public FeatureWeatherAdapter(Context context, ArrayList<Weather> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dong_line_listview,null);

        Weather weather = arrayList.get(i);

        TextView txtDay = (TextView) view.findViewById(R.id.textviewNgay);
        TextView txtStatus = (TextView) view.findViewById(R.id.textviewTrangthai);
        TextView txtMaxTemp = (TextView) view.findViewById(R.id.textviewMaxTemp);
        TextView txtMinTemp = (TextView) view.findViewById(R.id.textviewMinTemp);
        ImageView imgStatus = (ImageView) view.findViewById(R.id.imageviewTrangthai);

        txtDay.setText(weather.Day);
        txtStatus.setText(weather.Status);
        txtMaxTemp.setText(weather.MaxTemp+"℃");
        txtMinTemp.setText(weather.MinTemp+"℃");
//lấy dữ liệu, load dữ liệu lên
       Picasso.with(context).load("http://openweathermap.org/img/w/"+weather.Image+".png").into(imgStatus);
        return view;
    }
}
