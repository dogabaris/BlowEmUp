package com.bigapps.doga;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by shadyfade on 06.05.2016.
 */
public class Main extends Activity {
    private int ses = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_oyun);

            Button btn_yeni = (Button) findViewById(R.id.btn_yenioyun);
            Button btn_ses = (Button) findViewById(R.id.btn_ses);
            Button btn_cikis = (Button) findViewById(R.id.btn_cikis);
            Button btn_hakkinda = (Button) findViewById(R.id.btn_hakkinda);

            btn_yeni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent oyun = new Intent(v.getContext(), Oyun.class);
                    startActivity(oyun);
                }
            });

            btn_ses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ses==0){
                        ses=1;
                        Ayarlar.get().setSes(1);
                        Toast.makeText(Main.this,"Ses Açıldı!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ses=0;
                        Ayarlar.get().setSes(0);
                        Toast.makeText(Main.this,"Ses Kapatıldı!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_hakkinda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent hakkinda = new Intent(v.getContext(), Hakkinda.class);
                    startActivity(hakkinda);
                }
            });

            btn_cikis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    System.exit(0);
                }
            });
        } catch (Exception e) {
            Log.e("Activate", "Error in activity", e);

            Toast.makeText(getApplicationContext(),
                    e.getClass().getName() + " " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
