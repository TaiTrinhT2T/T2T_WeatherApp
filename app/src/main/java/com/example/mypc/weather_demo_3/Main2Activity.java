package com.example.mypc.weather_demo_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    String tenthanhpho = "";
    ImageView imageback;
    TextView txtName;
    ListView lv;
    // Khai báo lớp ánh xạ
    // Ánh xa bắc cầu từ CustomAdapter.java, khai báo class đã tạo
    // Sau đó tạo mảng lưu trữ value chain
    CustomAdapter customAdapter;
    ArrayList<ThoiTiet> WeatherArray;// khai báo mảng
    DecimalFormat df = new DecimalFormat(".0");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Anhxa();
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("ketqua", "Dữ liệu truyền qua : " + city);
        if (city.equals("")) {
            tenthanhpho = "London";
            Get7DaysData(tenthanhpho);
        } else {
            tenthanhpho = city;
            Get7DaysData(tenthanhpho);
        }
        // bắt event
        imageback.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private  void Anhxa(){// gọi các mảng
        imageback = (ImageView) findViewById(R.id.imageviewBack);
        txtName = (TextView) findViewById(R.id.textviewTenthanhpho);
        lv = (ListView) findViewById(R.id.listview);
        WeatherArray = new ArrayList<ThoiTiet>();// mảng ThoiTiet
        customAdapter = new CustomAdapter(Main2Activity.this,WeatherArray);
        lv.setAdapter(customAdapter);
    }
    private void Get7DaysData(String data) {
//        CHÚ Ý PHẦN NÀY
        // https://api.openweathermap.org/data/2.5/forecast?q=london&cnt=7&appid=7ab61a290618e8e869e233db0ca90be2
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + data + "&lang=vi&mode=json&cnt=7&appid=3e29e62e2ddf6dd3d2ebd28aed069215";
        RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("ketqua", "Json: " + response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");//
                            txtName.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");//
                            for (int i = 0; i < jsonArrayList.length();i++)//
                            {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt");
// Dùng lại
                                long l = Long.valueOf(ngay);
                                Date date = new Date(l+1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String Day = simpleDateFormat.format(date);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                                String max = jsonObjectTemp.getString("temp_max");
                                String min = jsonObjectTemp.getString("temp_min");

                                Double a = Double.valueOf(max)/10;
                                Double b = Double.valueOf(min)/10;
                                String NhietdoMax = df.format(a);
                                String NhietdoMin = df.format(b);

//                                JSONArray jsonArrayWeather = jsonArrayList.getJSONArray("weather");
                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
//                                String status = jsonArrayWeather.getString("description");// ????
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");

                                WeatherArray.add(new ThoiTiet(Day,status,icon,NhietdoMax,NhietdoMin));
                            }
                            customAdapter.notifyDataSetChanged();
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }}

