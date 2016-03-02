package com.example.frank.networkdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button btn_start;
    private ProgressBar prg_status;
    private ImageView img_logo;
    private RequestQueue mQueue;
    private TextView tv_name;
    private TextView tv_id;
    private TextView tv_phone;
    private TextView tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = (Button) findViewById(R.id.btn_start);
        img_logo = (ImageView) findViewById(R.id.img_logo);
        prg_status = (ProgressBar) findViewById(R.id.prg_status);
        prg_status.setVisibility(View.GONE);

        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_name = (TextView) findViewById(R.id.tv_name);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://dev-vn.magestore.com/simicart/1800/index.php/connector/catalog/get_product_detail/data/{\"width\":\"320\",\"product_id\":\"16\",\"height\":\"320\"}";
                getProductDetail(url);

            }
        });
        mQueue = Volley.newRequestQueue(this);
    }

    private void getProductDetail(String url) {
        prg_status.setVisibility(View.VISIBLE);

        Response.Listener<JSONObject> processData = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                prg_status.setVisibility(View.GONE);
                String data = response.toString();
                tv_name.setText(data);
            }
        };

        Response.ErrorListener processError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prg_status.setVisibility(View.GONE);
                String message = error.getMessage();
                tv_email.setText(message);
            }
        };

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, processData, processError);
        mQueue.add(jsonRequest);
    }


    protected void reqeustWithVolley(String link) {
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
        imageLoader.get(link, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
                if (null != bitmap) {
                    img_logo.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


    }


}
