package edu.skku.map.finalproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {
    EditText id,pw;
    Button btn;
    String name;
    String password;

    public static final String EXT_REGISTER="REGISTER";
    public static final String EXT_ID="ID";
    public static final String EXT_PASSWORD="PASSWORD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Intent intent=getIntent();
        String msg=intent.getStringExtra(MainActivity.EXT_MSG);
        Log.i("msg",msg);



        btn=findViewById(R.id.loginbutton);
        id=findViewById(R.id.input_id);
        pw=findViewById(R.id.input_pw);
        btn.setOnClickListener(view->{
            OkHttpClient client=new OkHttpClient();
            Gson gson=new Gson();
            name=id.getText().toString();
            password=pw.getText().toString();


            RegisterModel data=new RegisterModel();
            data.setName(name);
            data.setPassword(password);

            String json=gson.toJson(data, RegisterModel.class);
            Log.i("json",json);
            HttpUrl.Builder urlBuilder=HttpUrl.parse("https://iakee67ox5.execute-api.ap-northeast-2.amazonaws.com/dev/register").newBuilder();
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
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("response",myResponse);
                            if(data.getSuccess().equals("true")){
                                //Toast.makeText(getApplicationContext(),"your info is registered right now!",Toast.LENGTH_SHORT).show();

                                Intent intent2=new Intent(RegisterActivity.this, MainActivity2.class);
                                intent2.putExtra(EXT_REGISTER,"success registered, please login");
                                intent2.putExtra(EXT_ID,name);
                                intent2.putExtra(EXT_PASSWORD,password);
                                startActivity(intent2);
                            }
                            else{
                                //Toast.makeText(getApplicationContext(),"already exists!, try another one",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        });



    }
}