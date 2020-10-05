package com.example.tekkomundip.kpangkasapura2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;


public class AboutActivity2 extends AppCompatActivity {
    TextView weburl,instagram,twitter,facebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        open();
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void initView(){
        weburl = findViewById(R.id.tv_web);
        instagram = findViewById(R.id.tv_ig);
        twitter = findViewById(R.id.tv_twit);
        facebook = findViewById(R.id.tv_fb);
    }
    public void onBackPressed() {
        Intent reg = new Intent(this, MainActivity2.class);
        startActivity(reg);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        finish();
    }
    public void open(){
        weburl.setPaintFlags(weburl.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        weburl.setOnClickListener(V -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.angkasapura2.co.id/"));
                    startActivity(browserIntent);
                }
        );

        instagram.setPaintFlags(instagram.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        instagram.setOnClickListener(V -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/angkasapura2/"));
                    startActivity(browserIntent);
                }
        );

        twitter.setPaintFlags(twitter.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        twitter.setOnClickListener(V -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/AngkasaPura_2"));
                    startActivity(browserIntent);
                }
        );

        facebook.setPaintFlags(facebook.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        facebook.setOnClickListener(V -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/airport138/?ref=br_tf"));
                    startActivity(browserIntent);
                }
        );
    }
}
