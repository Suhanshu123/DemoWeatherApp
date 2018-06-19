package com.example.suhanshu.theweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity {



    TextView names,mainll,descriptions,humidity,temperatue,pressure,speed;
    ImageView iconView;
    EditText editText;
    String s=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names=findViewById(R.id.name_view);
        mainll=findViewById(R.id.main_View);
        descriptions=findViewById(R.id.description_view);
        humidity=findViewById(R.id.humidity_view);
        temperatue=findViewById(R.id.temperatue_view);
        pressure=findViewById(R.id.pressure_view);
        speed=findViewById(R.id.speed_view);
        iconView=findViewById(R.id.icon_view);
        editText=findViewById(R.id.city_view);

        if(editText.getText().toString()!=null){
            s=editText.getText().toString();
        }
         String URL="api.openweathermap.org/data/2.5/weather?q="+s+"&appid=c7d741b3ba22373fe2ab864875516167";

        StringRequest stringRequest=new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder=new GsonBuilder();
                Gson gson=gsonBuilder.create();
                User user=gson.fromJson(response,User.class);

                names.setText(user.getName());

                List<Weather> weathers=user.getWeather();
//                Weather weather=weathers.get()

//                Log.d("aaaaaaaaaaaa",weathers.get(0).getDescription());
                descriptions.setText(weathers.get(0).getDescription());
                mainll.setText(weathers.get(0).getMain());

                Wind wind=user.getWind();
                speed.setText(wind.getSpeed()+" ");

                Main main=user.getMain();
                temperatue.setText(main.getTemp()+" ");
                pressure.setText(main.getPressure()+" ");
                humidity.setText(main.getHumidity()+" ");

                Glide.with(getApplicationContext()).load("http://openweathermap.org/img/w/" +
                        weathers.get(0).getIcon()+ ".png").into(iconView);






            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_LONG).show();

            }
        });


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    }
}
