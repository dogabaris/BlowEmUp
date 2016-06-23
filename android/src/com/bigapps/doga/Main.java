package com.bigapps.doga;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.FileReader;

import mehdi.sakout.fancybuttons.FancyButton;

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

            FancyButton btn_yeni = (FancyButton) findViewById(R.id.btn_yenioyun);
            FancyButton btn_ses = (FancyButton) findViewById(R.id.btn_ses);
            FancyButton btn_cikis = (FancyButton) findViewById(R.id.btn_cikis);

            ContextWrapper c = new ContextWrapper(Main.this);
            String AbsolutePath = c.getFilesDir().getPath();
            FileReader file;
            boolean bool=false;

            Intent Reklam = new Intent(this, Reklam.class);
            startActivity(Reklam);

            /*try{
                file = new FileReader(AbsolutePath+"/data.json");
                JsonValue json = new JsonReader().parse(file);
                bool = json.has("bitti");
            }catch (IOException e) {
                e.printStackTrace();
            }

            if(bool){
                Intent Reklam = new Intent(this, Reklam.class);
                startActivity(Reklam);
            }*/

            btn_yeni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent secim = new Intent(v.getContext(), Secim.class);
                    startActivity(secim);
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
