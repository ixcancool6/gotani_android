package com.nyoba.mylongtitude;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import static com.nyoba.mylongtitude.R.id.latitude;
import static com.nyoba.mylongtitude.R.id.text;
import static com.nyoba.mylongtitude.R.id.textlatitude;
import static com.nyoba.mylongtitude.R.id.textlongtitude;

public class MainActivity extends AppCompatActivity implements LocationListener,AdapterView.OnItemSelectedListener {

    Button getLocationBtn;
    TextView locationText;
    TextView textLongtitude;
    TextView textLatitude;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLocationBtn = (Button)findViewById(R.id.getLocationBtn);
        locationText = (TextView)findViewById(R.id.locationText);
        textLongtitude = (TextView)findViewById(R.id.textlongtitude);
        textLatitude = (TextView)findViewById(R.id.textlatitude);
        Button submit = (Button)findViewById(R.id.submit);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> ph_tanah = ArrayAdapter.createFromResource(this,
                R.array.ph_tanah, android.R.layout.simple_spinner_item);
        ph_tanah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> curah_hujan = ArrayAdapter.createFromResource(this,
                R.array.curah_hujan, android.R.layout.simple_spinner_item);
        curah_hujan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(ph_tanah);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setAdapter(curah_hujan);
        spinner2.setOnItemSelectedListener(this);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TampilData.class);
                TextView vLongtitude = (TextView) findViewById(R.id.textlongtitude);
                String longtitude = vLongtitude.getText().toString();
                TextView vLatitude = (TextView) findViewById(R.id.textlatitude);
                String latitude = vLatitude.getText().toString();
                Spinner TSpinner = (Spinner) findViewById(R.id.spinner1);
                String ph_tanah = TSpinner.getSelectedItem().toString();
                Spinner HSpinner = (Spinner) findViewById(R.id.spinner2);
                String curah_hujan= HSpinner.getSelectedItem().toString();
                i.putExtra("curah_hujan", curah_hujan);
                i.putExtra("ph_tanah", ph_tanah);
                i.putExtra("latitude", latitude);
                i.putExtra("longtitude", longtitude);
                startActivity(i);

            }
        });
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Double latitude = location.getLatitude();
        Double longtitude = location.getLongitude();
        locationText.setText("LATI: " + latitude + "\n LONGI: " + longtitude);
        textLongtitude.setText(""+longtitude);
        textLatitude.setText(""+latitude);


        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));

        }catch(Exception e)
        {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) parent.getChildAt(0)).setTextSize(21);
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
