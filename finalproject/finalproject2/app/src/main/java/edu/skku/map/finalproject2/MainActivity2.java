package edu.skku.map.finalproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {
    Button btn;
    EditText name,password,genre;
    public static final String EXT_MSG2="MSG2";
    public static final String EXT_NAME="NAME";
    public static final String EXT_GENRE="GENRE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn=findViewById(R.id.button2);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        genre=findViewById(R.id.genre);

//
//        Intent intent=getIntent();
//        String msg=intent.getStringExtra(RegisterActivity.EXT_REGISTER);
//        Log.i("msg",msg);
//        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();




    }
    public void startNewActivity2(View view) {
        String username=name.getText().toString();
        String userpassword=password.getText().toString();
        String usergenre=String.valueOf(genre.getText());

        Intent intent2=new Intent(MainActivity2.this, MainActivity3.class);
        intent2.putExtra(EXT_MSG2,"login success!");
        intent2.putExtra(EXT_NAME,username);
        intent2.putExtra(EXT_GENRE,usergenre);


        OkHttpClient client=new OkHttpClient();
        Gson gson=new Gson();

        UserModel data=new UserModel();
        data.setName(username);
        data.setPassword(userpassword);

        String json=gson.toJson(data, UserModel.class);
        Log.i("json",json);
        HttpUrl.Builder urlBuilder=HttpUrl.parse("https://iakee67ox5.execute-api.ap-northeast-2.amazonaws.com/dev/login").newBuilder();
        String request_url = urlBuilder.build().toString();
        Log.i("url",request_url);
        Request req=new Request.Builder().url(request_url).post(RequestBody.create(MediaType.parse("application/json"),json)).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse=response.body().string();
                Gson gson=new Gson();
                final sucessModel data=gson.fromJson(myResponse,sucessModel.class);
                MainActivity2.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("response",myResponse);
                        if(data.getSuccess().equals("true")){
                            Toast.makeText(getApplicationContext(),"login success!",Toast.LENGTH_SHORT).show();
                            startActivity(intent2);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"login failed!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }
}