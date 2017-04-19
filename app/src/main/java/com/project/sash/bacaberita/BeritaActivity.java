package com.project.sash.bacaberita;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BeritaActivity extends AppCompatActivity {

    ListView lvFeed;

    ArrayList<FeedData> feedList;
    FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        lvFeed = (ListView) findViewById(R.id.lvFeed);

//        android.app.ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_back_mini);


        Intent i = getIntent();

//        toolbar.setTitle(i.getStringExtra("judulkat")+"");
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        feedList = new ArrayList<>();
        feedAdapter = new FeedAdapter(this,feedList);
        lvFeed.setAdapter(feedAdapter);

        switch (i.getStringExtra("katid")){
            case "1":
                    new HttpDownloadTask().execute("http://rss.detik.com/index.php/indeks");
                break;
            case "2":
                    new HttpDownloadTask().execute("http://rss.detik.com/index.php/finance");
                break;
            case "3":
                    new HttpDownloadTask().execute("http://rss.detik.com/index.php/sport");
                break;
            case "4":
                    new HttpDownloadTask().execute("http://rss.detik.com/index.php/hot");
                break;
            case "5":
                    new HttpDownloadTask().execute("http://rss.detik.com/index.php/health");
                break;
            case "6":
                    new HttpDownloadTask().execute("http://rss.detik.com/index.php/inet");
                break;
            case "7":
                    new HttpDownloadTask().execute("http://rss.detik.com/index.php/otomotif");
                break;

            default:
                break;
        }

        final Intent intent = new Intent(this,WebBacaActivity.class);
        lvFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FeedData feedData = (FeedData) lvFeed.getItemAtPosition(i);
                intent.putExtra("data",feedData);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Membaca",Toast.LENGTH_SHORT);
            }
        });

    }



    private class HttpDownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String urlstr = strings[0];
            try{
                URL url = new URL(urlstr);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int response = connection.getResponseCode();
                Log.d("debugTask","response code"+ response);

                BufferedReader r = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()
                        )
                );

                StringBuilder total = new StringBuilder();
                String line;
                while((line = r.readLine())!=null){
                    total.append(line);
                }

                Log.d("debugTask","Output: "+total.toString());

                parseXML(total.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return  null;
        }

        private  void parseXML(String is){
            try{
                XmlPullParserFactory factory =
                        XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new StringReader(is));
                FeedData data = null;
                String currTag = "";
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT){
                    if (eventType == XmlPullParser.START_DOCUMENT){

                    }else if (eventType == XmlPullParser.START_TAG){
                        if (xpp.getName().equals("item")){
                            data = new FeedData();
                            currTag = "item";
                        }else if(xpp.getName().equals("title")){
                            currTag = "title";
                        }else if(xpp.getName().equals("pubDate")){
                            currTag = "pubDate";
                        }else if(xpp.getName().equals("description")){
                            currTag = "description";
                        }else if(xpp.getName().equals("link")){
                            currTag = "link";
                        }else if(xpp.getName().equals("enclosure")){
                            currTag = "enclosure";
                            data.feedImage = xpp.getAttributeValue(null,"url");
                        }else{
                            currTag = "";
                        }
                    }else if(eventType == XmlPullParser.END_TAG){
                        if (xpp.getName().equals("item")){
                            feedList.add(data);
                        }
                    }else if (eventType == XmlPullParser.TEXT){
                        String content = xpp.getText();
                        content = content.trim();
                        if (data!=null && content.length()!=0){
                            switch (currTag){
                                case "title":
                                    data.feedTitle=content;
                                    break;
                                case "pubDate":
                                    data.feedPubDate=content;
                                    break;
                                case "description":
                                    data.feedDesc=content;
                                    break;
                                case "link":
                                    data.feedLink=content;
                                    break;
                                case "enclosure":
                                    break;

                            }
                        }
                    }
                    try{
                        eventType = xpp.next();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            feedAdapter.notifyDataSetChanged();
        }
    }


}
