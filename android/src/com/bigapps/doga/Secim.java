package com.bigapps.doga;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by shadyfade on 11.05.2016.
 */
public class Secim extends Activity{
    private String backgroundSecim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secim);

        final ImageView background =(ImageView)findViewById(R.id.iw_background);
        Button btn_magara = (Button) findViewById(R.id.btn_background1);
        Button btn_duvar = (Button) findViewById(R.id.btn_background2);
        Button btn_kumsal = (Button) findViewById(R.id.btn_background3);
        Button btn_oyna = (Button) findViewById(R.id.btn_oyna);

        btn_magara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setImageResource(R.drawable.background1);
                Ayarlar.get().setBackground(1);
            }
        });

        btn_duvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setImageResource(R.drawable.background2);
                Ayarlar.get().setBackground(2);
            }
        });

        btn_kumsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setImageResource(R.drawable.background3);
                Ayarlar.get().setBackground(3);
            }
        });

        btn_oyna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oyun = new Intent(v.getContext(), Oyun.class);
                startActivity(oyun);
            }
        });
    }
}
