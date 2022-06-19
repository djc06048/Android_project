package edu.skku.map.finalproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity4 extends AppCompatActivity {

    TextView tv5,tv6,tv7,tv8,tv9,tv10;
    Button like_btn,love_list_btn;
    ImageButton link_btn;
    ImageView imageView2;
    String genres="";
    public static final String EXT_GENRE="GENRE";
    public static final String EXT_MSG="MSG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Intent intent=getIntent();
        String movieId=intent.getStringExtra(MainActivity3.EXT_ID);
        Log.i("id",movieId);

        tv5=findViewById(R.id.movie_title);
        tv6=findViewById(R.id.movie_genre);
        tv7=findViewById(R.id.movie_release_date);
        tv8=findViewById(R.id.movie_run_time);
        tv9=findViewById(R.id.movie_overview);
        tv10=findViewById(R.id.movie_watch_url);
        like_btn=findViewById(R.id.like_button);
        link_btn=findViewById(R.id.link_button);
        imageView2=findViewById(R.id.imageView2);
        love_list_btn=findViewById(R.id.love_list_btn);


        OkHttpClient client=new OkHttpClient();
        HttpUrl.Builder urlBuilder=HttpUrl.parse("https://api.themoviedb.org/3/movie/"+movieId).newBuilder();
        urlBuilder.addQueryParameter("api_key","5b355a2d70d398bb7d7b91cd3e94ae16");

        String url=urlBuilder.build().toString();

        Request req=new Request.Builder().url(url).build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String movie_response=response.body().string();
                Log.i("response",movie_response);
                Gson gson=new GsonBuilder().create();
                indiv_movieDataModel data=gson.fromJson(movie_response,indiv_movieDataModel.class);


                MainActivity4.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String imageurl="https://image.tmdb.org/t/p/w500"+data.getPoster_path();
                        Log.i("poster_path",imageurl);
                        Uri url= Uri.parse(imageurl);
                        final Bitmap[] bitmap = new Bitmap[1];
                        Thread mThread =new Thread(){
                            @Override
                            public void run(){
                                try{
                                    URL url=new URL(imageurl);

                                    HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
                                    conn.setDoInput(true);
                                    conn.connect();

                                    InputStream is= conn.getInputStream();
                                    bitmap[0] = BitmapFactory.decodeStream(is);

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        mThread.start();
                        try{
                            mThread.join();
                            imageView2.setImageBitmap(bitmap[0]);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i("title",data.getTitle());
                        tv5.setText(data.getTitle());
//                        Log.i("genres", data.getGenres().get(0).toString());

                        for(Genre g: data.getGenres()){
                            genres=genres+" "+g.getName();
                            Log.i("genre_name" ,g.getName());
                        }
                        Log.i("genres_name",genres);
                        tv6.setText(genres);

                        Log.i("released date",data.getRelease_date());
                        tv7.setText(data.getRelease_date());
                        Log.i("runtime",String.valueOf(data.getRuntime()));
                        tv8.setText(String.valueOf(data.getRuntime())+"minutes");
                        Log.i("overview",data.getOverview());
                        tv9.setText(data.getOverview());
                        Log.i("watch url",data.getHomepage());
                        link_btn.setOnClickListener(view-> {
                            Uri uri=Uri.parse(data.getHomepage());
                            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                            if(intent.resolveActivity(getPackageManager())!=null) {
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "unable to resolve activity", Toast.LENGTH_SHORT).show();
                            }});

                        tv10.setText(data.getHomepage());


                        like_btn.setOnClickListener(view->{

                            LikemovieDataModel data2=new LikemovieDataModel();
                            data2.setMovieId(movieId);
                            data2.setMovieTitle(data.getTitle());
                            Log.i("title",data.getTitle());
                            data2.setMovieGenre(genres);
                            Log.i("genre",genres);
                            data2.setMovieRelease_date(data.getRelease_date());
                            Log.i("releasedate",data.getRelease_date());
                            data2.setMovieHomepage(data.getHomepage());
                            Log.i("homepage",data.getHomepage());


                            data2.setMovieRuntime(String.valueOf(data.getRuntime()));
                            Log.i("runtime",String.valueOf(data.getRuntime()));
                            String json=gson.toJson(data2,LikemovieDataModel.class);
                            Log.i("json",json);
                            HttpUrl.Builder urlBuilder_request=HttpUrl.parse("https://iakee67ox5.execute-api.ap-northeast-2.amazonaws.com/dev/like").newBuilder();

                            String request_url = urlBuilder_request.build().toString();
                            Log.i("post url",request_url);
                            Request req2 = new Request.Builder().url(request_url).post(RequestBody.create(MediaType.parse("application/json"),json)).build();

                            client.newCall(req2).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    final String likeMovie_response=response.body().string();
                                    Log.i("is there like movie?",likeMovie_response);
                                    Gson gson2=new Gson();
                                    final sucessModel data3=gson2.fromJson(likeMovie_response,sucessModel.class);
                                    Log.v("success or not",data3.getSuccess());

                                        MainActivity4.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (data3.getSuccess().equals("true")) {
                                                    Toast.makeText(getApplicationContext(), "ok!", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext(), "already!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });




                                }

                            });
                        });
                        love_list_btn.setOnClickListener(view->{
                            Intent intent=new Intent(MainActivity4.this,MainActivity5.class);
                            intent.putExtra(EXT_MSG,"your like movie list");
                            startActivity(intent);
                        });



                    }
                });

            }
        });

    }
}