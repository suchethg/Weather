package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


EditText editText;
TextView weatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText =   findViewById(R.id.editTextTextPersonName);
        weatherInfo = findViewById(R.id.weatherInfo);


    }

    public void getWeather(View view)
    {

        Task task = new Task();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q="+editText.getText().toString()+"&appid=cb10d5dcf20ca4844dfc55be840f9522");

    }
    public class Task extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection;
            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data!=-1)
                {
                    char current = (char)data;
                    result +=current;
                    data = reader.read();
                }
                return result;

            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String info = jsonObject.getString("weather");
                Log.i("weather Info ",info);
                JSONArray jarray = new JSONArray(info);
                String main = "";
                String desc = " ";
                for(int i=0;i<jarray.length();i++)
                {
                    JSONObject jsonPart = jarray.getJSONObject(i);

                    main = jsonPart.getString("main");
                    desc =jsonPart.getString("description");

                }
                weatherInfo.setText(main+" : " +desc);
            }
            catch (Exception e)
            {
                e.printStackTrace();

            }

        }
    }


}