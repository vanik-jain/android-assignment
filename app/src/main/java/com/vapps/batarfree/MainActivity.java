package com.vapps.batarfree;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity

{
    Double converted = 1.0;
    String symbol;
    Double conversionRate = 1.0;
    Double getConversionRate = 1.0;
    TextView textView;
    TextView textView1;
    EditText editText;
    RadioButton radioButtonDollar;
    RadioButton radioButtonRuppee;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView =findViewById(R.id.textView);
        textView1 =findViewById(R.id.result);
        editText = findViewById(R.id.editText);

        radioButtonDollar = findViewById(R.id.radio1);
        radioButtonRuppee = findViewById(R.id.radio2);


        try
        {


            DataDownload dataDownload = new DataDownload();


            dataDownload.execute("https://free.currencyconverterapi.com/api/v6/convert?q=USD_INR");


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }









    }




    public void setConvertlate(View view)
    {


        Double amount = Double.parseDouble(editText.getText().toString());


        converted = amount*conversionRate;



        textView1.setText(symbol+converted.toString());

    }

    public void onRadioButtonClicked(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();

        if(view.getId() == R.id.radio1)
        {
            if (checked)
            {
                conversionRate =1/getConversionRate;
                symbol = "$";
            }
        }

       else if (view.getId() == R.id.radio2)
        {
            if(checked)
            {
                conversionRate =getConversionRate;
                symbol = "₹";
            }
        }

    }








    public class DataDownload extends AsyncTask<String,Void,String>
    {

        String result;
        String result1;


        @Override
        protected String doInBackground(String... urls)
        {

            try {

                URL url = new URL(urls[0]);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();




                InputStream inputStream = httpURLConnection.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data != -1 )
                {
                    char c = (char)data;

                    result += c;

                    data = inputStreamReader.read();

                }

                result1 = result.replaceFirst("null","");
                return result;



            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // String message = "";
            String main = "";
            String description= "";
            try
            {



                Log.i("result1",result);


                JSONObject jsonObject = new JSONObject(result1);


                JSONObject results  = jsonObject.getJSONObject("results");

                JSONObject USD_INR  = results.getJSONObject("USD_INR");


                getConversionRate = USD_INR.getDouble("val");



            }

            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    }
















}
