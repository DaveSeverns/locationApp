package com.sevdev.locationapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LocationManager lm;
    LocationListener ll;
    Location currentLocation;
    TextView lat;
    TextView longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lat = findViewById(R.id.latText);
        longitude = findViewById(R.id.longitudeText);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
                lat.setText(location.getLatitude()+"");
                longitude.setText(location.getLongitude()+"");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        };
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED){
            requestUpdates();
        }else{
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }

    }

    @SuppressWarnings("MissingPermission")
    private void requestUpdates(){
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0,
                0,
                ll);
        lm .requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,ll);
        lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,0,0,ll);
    }

    @Override
    protected void onPause(){
        super.onPause();
        lm.removeUpdates(ll);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            requestUpdates();
        }else{
            Toast.makeText(this, "SOL homie", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
