package com.example.mypc.weather_demo_3;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by MyPC on 20/12/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTiet> arrayList;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
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

        ThoiTiet ThoiTiet = arrayList.get(i);

        TextView txtDay = (TextView) view.findViewById(R.id.textviewNgay);
        TextView txtStatus = (TextView) view.findViewById(R.id.textviewTrangthai);
        TextView txtMaxTemp = (TextView) view.findViewById(R.id.textviewMaxTemp);
        TextView txtMinTemp = (TextView) view.findViewById(R.id.textviewMinTemp);
        ImageView imgStatus = (ImageView) view.findViewById(R.id.imageviewTrangthai);

        txtDay.setText(ThoiTiet.Day);
        txtStatus.setText(ThoiTiet.Status);
        txtMaxTemp.setText(ThoiTiet.MaxTemp+"℃");
        txtMinTemp.setText(ThoiTiet.MinTemp+"℃");
//lấy dữ liệu, load dữ liệu lên
       Picasso.with(context).load("http://openweathermap.org/img/w/"+ThoiTiet.Image+".png").into(imgStatus);
        return view;
    }
}
