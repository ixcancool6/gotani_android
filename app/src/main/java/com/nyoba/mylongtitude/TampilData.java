package com.nyoba.mylongtitude;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by JamesAndrew on 12/30/2018.
 */

public class TampilData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampil_data);

        TextView bcurah_hujan = (TextView)findViewById(R.id.curah_hujan);
        TextView bph_tanah = (TextView)findViewById(R.id.ph_tanah);
        TextView blongtitude = (TextView)findViewById(R.id.longtitude);
        TextView blatitude = (TextView)findViewById(R.id.latitude);


        String tcurah_hujan = getIntent().getStringExtra("curah_hujan");
        String tph_tanah = getIntent().getStringExtra("ph_tanah");
        String tlongtitude = getIntent().getStringExtra("longtitude");
        String tlatitude = getIntent().getStringExtra("latitude");

        bcurah_hujan.setText(tcurah_hujan);
        bph_tanah.setText(tph_tanah);
        blongtitude.setText(tlongtitude);
        blatitude.setText(tlatitude);
    }


}
