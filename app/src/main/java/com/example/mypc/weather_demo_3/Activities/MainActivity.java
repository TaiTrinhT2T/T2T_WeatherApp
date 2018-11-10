package com.example.mypc.weather_demo_3.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mypc.weather_demo_3.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch, btnChangeActivity, btnAllUsers;
    TextView txtName, txtCountry, txtStatus, txtTemp, txtHumidity, txtCloud, txtWind, txtDay, txtUserName;
    ImageView imgIcon;
    String City = "";

    String emailFromIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        emailFromIntent = getIntent().getStringExtra("EMAIL");
        txtUserName.setText(emailFromIntent);
//        lấy 1 thành phố mặc định: London hoặc Sài Gòn
        GetCurrentWeatherData("London");
        btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtSearch.getText().toString();
                if (city.equals("")) {
                    City = "London";
                    GetCurrentWeatherData(City);
                }else{
// phát lệnh gán
                    City = city;
                    GetCurrentWeatherData(City);
                }
            }
        });


        btnChangeActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this,FeatureWeatherActivity.class);
                intent.putExtra("name",city);
                startActivity(intent);
            }
        });

        btnAllUsers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, UsersListActivity.class);
                intent.putExtra("EMAIL", emailFromIntent);
                startActivity(intent);
            }
        });
    }

    public void GetCurrentWeatherData(String data)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url =
//                London
                "https://api.openweathermap.org/data/2.5/weather?q="+data+"&lang=vi&mode=json&appid=3e29e62e2ddf6dd3d2ebd28aed069215";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse (String response){
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtName.setText("Tên thành phố: "+name);
                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                            String Day = simpleDateFormat.format(date);

                            txtDay.setText(Day);
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            // set cùng tên mảng, mảng có tên là jsonArrayWeather , giá trị ban đầu là 0
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);

                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imgIcon);
                            txtStatus.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");
//                            Nhiệt độ trả về kiểu dữ liệu double - gỡ lỗi symbol

                            Double a = Double.valueOf(nhietdo)/10;
                            DecimalFormat df = new DecimalFormat(".0");

                            String Nhietdo = df.format(a);

                            txtTemp.setText(Nhietdo+"℃");
                            txtHumidity.setText(doam+"%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("speed");
                            txtWind.setText(gio+"m/s");

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectClouds.getString("all");
                            txtCloud.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");

                            txtCountry.setText("Tên quốc gia: "+ country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        Log.d("KetQua",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                });

        requestQueue.add(stringRequest);
    }

    private void AnhXa()
    {
        edtSearch = (EditText) findViewById(R.id.edittextSearch);
        btnSearch = (Button) findViewById(R.id.buttonSearch);
        btnChangeActivity = (Button) findViewById(R.id.buttonChangeActivity);
        txtName = (TextView) findViewById(R.id.textviewName);
        txtCountry = (TextView) findViewById(R.id.textviewCountry);
        txtTemp = (TextView) findViewById(R.id.textviewTemp);
        txtStatus = (TextView) findViewById(R.id.textviewStatus);
        txtHumidity = (TextView) findViewById(R.id.textviewHumid);
        txtCloud = (TextView) findViewById(R.id.textviewCloud);
        txtWind = (TextView) findViewById(R.id.textviewWind);
        txtDay = (TextView) findViewById(R.id.textviewDay);
        imgIcon = (ImageView) findViewById(R.id.imageIcon);
        btnAllUsers = findViewById(R.id.btn_all_users);
        txtUserName = findViewById(R.id.tv_user_name);
    }}
