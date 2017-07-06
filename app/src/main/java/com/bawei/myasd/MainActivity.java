package com.bawei.myasd;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity implements View.OnClickListener {

    //开启一个线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    private Button btn_get;
    private Button get_Synchronization;
    private Button get_Asynchronous;
    private Button post_Synchronization;
    private Button post_Asynchronous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        get_Synchronization = (Button) findViewById(R.id.get_Synchronization);
        get_Asynchronous = (Button) findViewById(R.id.get_Asynchronous);
        post_Synchronization = (Button) findViewById(R.id.post_Synchronization);
        post_Asynchronous = (Button) findViewById(R.id.post_Asynchronous);

        get_Synchronization.setOnClickListener(this);
        get_Asynchronous.setOnClickListener(this);
        post_Synchronization.setOnClickListener(this);
        post_Asynchronous.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //get同步请求
            case R.id.get_Synchronization:
                get_Synchronization();
                break;
            //get异步请求
            case R.id.get_Asynchronous:
                get_Asynchronous();
                break;
            //post同步请求
            case R.id.post_Synchronization:
                post_Synchronization();
                break;
            //post异步请求
            case R.id.post_Asynchronous:
                post_Asynchronous();
                break;
        }
    }

    private void get_Synchronization() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //先实例化一个OKHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();
                    Response response = client.newCall(request).execute();
                    System.out.println("response = " + response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void get_Asynchronous() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //先实例化一个OKHttpClient对象
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("response = " + response.body().string());
                    }
                });
            }
        });
    }

    String url = "http://publicobject.com/helloworld.txt";

    private void post_Synchronization() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //先实例化一个OKHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("username", "8541").add("password", "111111").add("postkey", "1503d").build();
                    Request request = new Request.Builder().url(url).post(requestBody).build();
                    Response execute = client.newCall(request).execute();
                    System.out.println("execute = " + execute.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void post_Asynchronous() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //先实例化一个OKHttpClient对象
                OkHttpClient client = new OkHttpClient();
                RequestBody formBoby = new FormBody.Builder().add("username", "8451").add("password", "111111").add("postkey", "1503d").build();
                Request request = new Request.Builder().url(url).post(formBoby).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("response = " + response.body().string());
                    }
                });
            }
        });
    }
}
