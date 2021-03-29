package com.example.h10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    WebView web;
    EditText url;
    ImageButton search;
    ImageButton refresh;
    ImageButton back;
    ImageButton forward;
    Button shoutout;
    Button initialize;
    String urlString = "www.google.fi";
    ArrayList<String> urlList = new ArrayList<String>();
    ListIterator<String> iterator;
    int index = urlList.size() - 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        web = (WebView) findViewById(R.id.webView);
        url = (EditText) findViewById(R.id.urlEditText);
        search = (ImageButton) findViewById(R.id.searchButton);
        refresh = (ImageButton) findViewById(R.id.refreshButton);
        back = (ImageButton) findViewById(R.id.goBackButton);
        forward = (ImageButton) findViewById(R.id.goForwardButton);
        shoutout = (Button) findViewById(R.id.shoutOutButton);
        initialize = (Button) findViewById(R.id.initializeButton);

        web.setWebViewClient(new WebViewClient());
        web.loadUrl("https://"+urlString);

        url.setInputType(InputType.TYPE_NULL);
        web.getSettings().setJavaScriptEnabled(true);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                urlString = url.getText().toString();
                if (urlString.equals("")){
                    // Do nothing
                }else if (urlString.equals("index.html")){
                    web.loadUrl("file:///android_asset/index.html");
                }else {
                    if (index < (urlList.size() - 1)){
                        iterator = urlList.listIterator(index+1);
                        while (iterator.hasNext()){
                            iterator.next();
                            iterator.remove();
                        }
                    }
                    if (urlList.size() > 10) {
                        urlList.remove(0);
                    }
                    urlList.add(urlString);
                    index = urlList.size() - 1;
                    web.loadUrl("https://" + urlString);
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (urlString.equals("index.html")) {
                    web.loadUrl("file:///android_asset/index.html");
                }else{
                    web.loadUrl("https://"+urlString);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (index == -1){
                    // Do nothing
                }else{
                    iterator = urlList.listIterator(index);
                    if (iterator.hasPrevious()) {
                        urlString = iterator.previous();
                        web.loadUrl("https://" + urlString);
                        index--;
                    }
                }
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iterator = urlList.listIterator(index + 1);
                if (iterator.hasNext()){
                    urlString = iterator.next();
                    web.loadUrl("https://"+urlString);
                    index++;
                }
            }
        });

        shoutout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                web.evaluateJavascript("javascript:shoutOut()", null);
            }
        });
        initialize.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                web.evaluateJavascript("javascript:initialize()", null);
            }
        });
    }
}