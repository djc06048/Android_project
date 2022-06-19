package edu.skku.map.finalproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity5 extends AppCompatActivity {
    EditText et;
    ListView listview;
    movielikeListAdapter adapter;
    ArrayList<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        listview=findViewById(R.id.db_listview);
        items=new ArrayList<>();
        Intent intent=getIntent();
        String msg=intent.getStringExtra(MainActivity4.EXT_MSG);
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        OkHttpClient client=new OkHttpClient();
        HttpUrl.Builder urlBuilder_request2=HttpUrl.parse("https://iakee67ox5.execute-api.ap-northeast-2.amazonaws.com/dev/getmovies").newBuilder();

        String request_url2 = urlBuilder_request2.build().toString();
        Log.i("get url",request_url2);
        Request req2 = new Request.Builder().url(request_url2).build();
        client.newCall(req2).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String movies=response.body().string();
                Log.i("like movies",movies);

                Gson gson=new Gson();
                DbModel db=gson.fromJson(movies,DbModel.class);
                Log.i("e",db.getMovies().toString());

                MainActivity5.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("e",db.getMovies().toString());
                        for(DbModel2 m: db.getMovies()){

                            Log.i("movie title",m.getTitle());
                            Log.i("movie genre",m.getGenre());
                            Log.i("movie homepage",m.getHomepage());
                            Log.i("movie runtime",m.getRuntime());
                            Log.i("movie release date",m.getRelease_date());
                            Log.i("~~~~~~~~~~~~~~~","~~~~~~~~~~~~~");
                            items.add(m.getTitle());


                        }
                        adapter=new movielikeListAdapter(getApplicationContext(),items);
                        listview.setAdapter(adapter);
                    }
                });




            }

        });
    }
}