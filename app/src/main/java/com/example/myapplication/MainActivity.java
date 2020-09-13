package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
    public class dha extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection=null;
            InputStream inputStream;
            String aa ="";
            try{
                url = new URL(strings[0]);
                urlConnection= (HttpURLConnection) url.openConnection();
                inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while (data!=-1){
                    char a = (char) data;
                    aa+=a;
                    data = inputStreamReader.read();

                }

                return aa;
            }
            catch (Exception e){
                e.printStackTrace();
                return "FAILLLLLL!!!!";
            }
        }

        /*@Override
        protected void onPostExecute(String s) {
            JSONObject object = new JSONObject();
            try {
                object.get(s);
                String dha = object.getString("");
                Toast.makeText(MainActivity.this, dha, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }*/
    }

    ArrayList<String> newslist = new ArrayList<>();
    static ArrayList<String> url = new ArrayList<>();
    ArrayList<String> listno = new ArrayList<>();

    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dha dhamaka = new dha();
        dha dhamaka3 = new dha();

        String ad= "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
        String apicall= "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
        try {
             ad =dhamaka.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty/").get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ad=ad.replace("[",",");
        Pattern p = Pattern.compile(",(.*?),");
        Matcher m = p.matcher(ad);

        while (m.find()){
            if(listno.size()<=10){
                listno.add(m.group(1));
            }
            }
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,newslist);


        try {
                for(int i =0;i<=10;i++){
                    dha dhamaka2 = new dha();
                    String aa =dhamaka2.execute("https://hacker-news.firebaseio.com/v0/item/"+listno.get(i).replace(" ","")+".json?print=pretty").get();
                    Pattern p1 = Pattern.compile("\"title\" : \"(.*?)\"");
                    Matcher m1 = p1.matcher(aa);
                    while (m1.find()){
                        newslist.add(m1.group(1));
                    }
                    arrayAdapter.notifyDataSetChanged();
                    Pattern p2 = Pattern.compile("\"url\" : \"(.*?)\"");
                    Matcher m2 = p2.matcher(aa);
                    while (m2.find()){
                        url.add(m2.group(1));
                    }

                }


        }catch (Exception e){
                e.printStackTrace();
            }
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                intent.putExtra("itemno",i);
                startActivity(intent);
            }
        });
    }
}