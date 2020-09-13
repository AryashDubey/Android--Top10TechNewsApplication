package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent in=getIntent();
        int a=in.getIntExtra("itemno",0);
        WebView wb= findViewById(R.id.view) ;
        wb.setWebViewClient(new WebViewClient());
        wb.loadUrl(MainActivity.url.get(a));
    }
}