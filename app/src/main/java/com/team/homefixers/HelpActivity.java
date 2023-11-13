package com.team.homefixers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    private TextView textViewHelpTitle;
    private TextView textViewLinkTelegram;
    private TextView textViewLinkVK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initViews();
        textViewHelpTitle.setText("Всем привет, баги это процесс создания приложения." +
                " Я извиняюсь за причинённые неудобства, вы можете написать мне и я исправлю ошибки" +
                " которые вам мешают. Большое спасибо за понимание.");

        onClickLink();
    }

    private void onClickLink(){
        textViewLinkTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://t.me/Kilanis18");
                Intent webIntentTelegram = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntentTelegram);
            }
        });

        textViewLinkVK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://vk.com/gregori18");
                Intent webIntentVK = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntentVK);
            }
        });

    }

    private void initViews(){
        textViewHelpTitle = findViewById(R.id.textViewHelpTitle);
        textViewLinkTelegram = findViewById(R.id.textViewLinkTelegram);
        textViewLinkVK = findViewById(R.id.textViewLinkVK);
    }



    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, HelpActivity.class);
        return intent;
    }
}