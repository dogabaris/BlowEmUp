package com.bigapps.doga;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by shadyfade on 11.05.2016.
 */
public class Secim extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secim);

        final ImageView background =(ImageView)findViewById(R.id.iw_background);
        Button btn_magara = (Button) findViewById(R.id.btn_background1);
        Button btn_duvar = (Button) findViewById(R.id.btn_background2);
        Button btn_kumsal = (Button) findViewById(R.id.btn_background3);
        Button btn_oyna = (Button) findViewById(R.id.btn_oyna);

        background.setImageResource(R.drawable.background1);

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
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                JSONObject json = new JSONObject();
                try {
                    json.put("ses", Ayarlar.get().getSes());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    json.put("background", Ayarlar.get().getBackground());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ContextWrapper c = new ContextWrapper(Secim.this);
                String AbsolutePath = c.getFilesDir().getPath();//klasörün yolu

                try (FileWriter file = new FileWriter(AbsolutePath+"/data.json")) {
                    file.write(json.toString());
                    System.out.println("\nJSON Object: " + json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Secim.this.finish();
                Intent oyun = new Intent(v.getContext(), Oyun.class);
                startActivity(oyun);
            }
        });
    }
}
