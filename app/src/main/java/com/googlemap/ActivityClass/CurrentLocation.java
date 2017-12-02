package com.googlemap.ActivityClass;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.googlemap.MapsActivity;
import com.googlemap.Model.LatLngModel;
import com.googlemap.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class CurrentLocation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
     String Latitude,Longitude;

    private TextView latTV, lonTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        latTV = (TextView) findViewById(R.id.latTV);
        lonTV = (TextView) findViewById(R.id.lonTV);
        googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
  //ActivityChange();

    }

    public void ActivityChange(){
        Intent i=new Intent(CurrentLocation.this, MapsActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        googleApiClient.disconnect();
        super.onPause();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
      //  Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        locationRequest = LocationRequest.create()
                .setInterval(8000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        LatLngModel latlon=new LatLngModel();
        latlon.setLongitude(location.getLongitude());
       latlon.setLatitude(location.getLatitude());

        Latitude=String.valueOf(latlon.getLatitude());
        Longitude=String.valueOf(latlon.getLongitude());
        latTV.setText(String.valueOf(latlon.getLatitude()));
        lonTV.setText(String.valueOf(latlon.getLongitude()));
      //  Log.d("Final","Testing1 : "+latlon.getLatitude());


    }

    public void ChangeActivity(View view) {
        Intent i=new Intent(CurrentLocation.this, MapsActivity.class);



        Log.d("strin","data " +Latitude);



        i.putExtra("latitude",Latitude);
        i.putExtra("longitude",Longitude);

//Add the bundle to the intent




        startActivity(i);
    }
}