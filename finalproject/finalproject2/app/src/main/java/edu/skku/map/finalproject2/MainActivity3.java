package edu.skku.map.finalproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity3 extends AppCompatActivity {

    ListView listview_upcoming;
    ListviewAdapter1 listviewAdapter1;
    ArrayList<MovieDataModel> items;

    Context mContext;
    RecyclerViewAdapter madapter;
    RecyclerView mRecyclerView;
    ArrayList <MovieDataModel> mArrayList;


    TextView tv1,tv2;
    String genre_num;
    Integer num=0;
    public static final String EXT_ID="ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mContext=getApplicationContext();
        mRecyclerView =findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mArrayList=new ArrayList<>();
        madapter=new RecyclerViewAdapter(mContext,mArrayList);


        listview_upcoming=findViewById(R.id.listview2);
        items=new ArrayList<MovieDataModel>();
        tv1=findViewById(R.id.textView);
        tv2=findViewById(R.id.textView8);
        Intent intent=getIntent();
        String msg=intent.getStringExtra(MainActivity2.EXT_MSG2);
        String username=intent.getStringExtra(MainActivity2.EXT_NAME);
        Log.i("username",username);

        String usergenre=intent.getStringExtra(MainActivity2.EXT_GENRE);
        Log.i("usergenre", usergenre);
        tv1.setText("Hi,"+username+"!\n below movies are your favorite " +usergenre+" movie");
        Log.i("msg2",msg);
//        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

        //genre
        OkHttpClient client=new OkHttpClient();
        HttpUrl.Builder urlBuilder2 = HttpUrl.parse("https://api.themoviedb.org/3/genre/movie/list").newBuilder();
        urlBuilder2.addQueryParameter("api_key","5b355a2d70d398bb7d7b91cd3e94ae16");

        String genre_match_url=urlBuilder2.build().toString();
        Log.i("genre_match_url",genre_match_url);

        Request req2=new Request.Builder().url(genre_match_url).build();

        client.newCall(req2).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String genre_match_response=response.body().string();
                Log.i("genre_match_response",genre_match_response);
                Gson gson=new GsonBuilder().create();
                indiv_movieDataModel data2=gson.fromJson(genre_match_response,indiv_movieDataModel.class);
                MainActivity3.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(Genre g: data2.getGenres()){
                            Log.i("genrename",g.getName());
                            Log.i("user_genre",usergenre);
                            if(usergenre.equals(g.getName())){
                                Log.i("good","there is!");
                                genre_num=String.valueOf(g.getId());
                                Log.i("user_genrenum",genre_num);
                            }

                        }
                    }
                });

            }
        });

        //upcoming
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.themoviedb.org/3/movie/upcoming/").newBuilder();
        urlBuilder.addQueryParameter("api_key","5b355a2d70d398bb7d7b91cd3e94ae16");

        String upcoming_movie_url=urlBuilder.build().toString();
        Log.i("url",upcoming_movie_url);

        Request req=new Request.Builder().url(upcoming_movie_url).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                Gson gson=new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                MovieDataModel[] results=gson.fromJson(rootObject, MovieDataModel[].class);
                MainActivity3.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(MovieDataModel result :results){
                            Log.i("movie list","*****************");
                            Log.i("title",result.getTitle());
                            Log.i("poster_path",result.getPoster_path());
                            Log.i("release data" , result.getRelease_date());
                            for( Integer genre: result.getGenre_ids()){
                                String genres=String.valueOf(genre);
                                Log.i("genre list",genres);

                            }

                            Log.i("movie unique id",result.getId());

//                            Intent intent=getIntent();
//                            String genres=intent.getStringExtra(MainActivity4.EXT_GENRE);
                            items.add(new MovieDataModel(result.getId(),result.getPoster_path(),result.getTitle(),result.getGenre_ids(),result.getRelease_date(),"go"));
                            num=num+1;

                        }



                        Log.i("cnt",String.valueOf(num));
                        for(int i=0;i<num;i++){
                            for(MovieDataModel result :results) {
                                for (Integer genre : result.getGenre_ids()) {
                                    Log.i("genre", String.valueOf(genre));
                                    Log.i("genre_user",genre_num);
                                    if(genre_num.equals(String.valueOf(genre))){
                                        Log.i("match","!!!");
                                        Log.i("genre_movie_img",result.getPoster_path());
                                        mArrayList.add(new MovieDataModel(result.getId(),result.getPoster_path()));

                                    }
                                }
                            }

                        }

                        madapter.notifyItemInserted(mArrayList.size()-1);

                        listviewAdapter1=new ListviewAdapter1(getApplicationContext(),items);
                        listview_upcoming.setAdapter(listviewAdapter1);

                        mRecyclerView.setAdapter(madapter);



                    }
                });

            }
        });

        listview_upcoming.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity3.this,MainActivity4.class);
                intent.putExtra(EXT_ID,items.get(i).getId());
                startActivity(intent);
            }
        });

        madapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent=new Intent(MainActivity3.this,MainActivity4.class);
                intent.putExtra(EXT_ID,mArrayList.get(position).getId());
                startActivity(intent);
            }
        });





    }
}