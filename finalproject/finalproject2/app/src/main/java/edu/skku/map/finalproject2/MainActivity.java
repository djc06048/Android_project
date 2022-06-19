package edu.skku.map.finalproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button old_btn,new_btn;
    public static final String EXT_MSG="MSG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        old_btn=findViewById(R.id.old_btn);
        new_btn=findViewById(R.id.new_btn);

        new_btn.setOnClickListener(view->{
            Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
            intent.putExtra(EXT_MSG,"Welcome,enter your info!");
            startActivity(intent);
        });

        old_btn.setOnClickListener(view->{
            Intent intent2=new Intent(MainActivity.this, MainActivity2.class);
            intent2.putExtra(EXT_MSG,"Welcome,enter your info!");
            startActivity(intent2);
        });
    }



}