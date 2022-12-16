package com.example.lx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button About_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        Button buttonAddress = (Button) findViewById(R.id.address);
        buttonAddress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("https://github.com/l-wave/lx_study");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        Button buttonEmail = (Button) findViewById(R.id.email);
        buttonEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri_1 = Uri.parse("https://2083236231@qq.com/");
                Intent intent_1 = new Intent(Intent.ACTION_VIEW, uri_1);
                startActivity(intent_1);
            }
        });

        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        String rating = String.valueOf(ratingBar.getRating());
        Toast.makeText(AboutActivity.this, "您的评分是"+rating+"星", Toast.LENGTH_SHORT).show();

        About_ok = (Button)findViewById(R.id.about_ok_back);
        About_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this,BookListMainActivity.class));
            }
        });
    }
}