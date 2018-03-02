package com.rush.rush;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class rush extends FragmentActivity implements OnMapReadyCallback,

        GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    public Marker mLastSelectedMarker;
    GoogleApiClient myGoogleApiClient;
    Location myLastLocation;
    Marker myCurrLocationMarker;
    LocationRequest myLocationRequest;
    static final LatLng firstBRM = new LatLng(40.7589, -73.9851);
    Marker mfirstBRM;
    int first_BRM_button;

    private class CustomInfoWindowAdapter implements InfoWindowAdapter {

        // These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;

        private final View mContents;

        CustomInfoWindowAdapter() {

            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {

            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {

            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            int star1,star2,star3,star4,star5,charger,accessibility,money,more,wifi;

            // Use the equals() method on a Marker to check for equals.  Do not use ==.
            if (marker.equals(mfirstBRM)) {
                star1 = R.drawable.starfull;
                star2 = R.drawable.starfull;
                star3 = R.drawable.starfull;
                star4 = R.drawable.starfull;
                star5 = R.drawable.starempty;
                charger=R.drawable.charger;
                money=R.drawable.money;
                accessibility=R.drawable.accessibility;
                more=R.drawable.more;
                wifi=R.drawable.wifi;
                first_BRM_button=R.id.infomation;


            } else {
                star1 =0;
                star2 =0;
                star3 =0;
                star4 =0;
                star5 =0;
                charger=0;
                money=0;
                accessibility=0;
                more=0;
                wifi=0;
            }
            ((ImageView) view.findViewById(R.id.star1)).setImageResource(star1);
            ((ImageView) view.findViewById(R.id.star2)).setImageResource(star2);
            ((ImageView) view.findViewById(R.id.star3)).setImageResource(star3);
            ((ImageView) view.findViewById(R.id.star4)).setImageResource(star4);
            ((ImageView) view.findViewById(R.id.star5)).setImageResource(star5);
            ((ImageView) view.findViewById(R.id.charger)).setImageResource(charger);
            ((ImageView) view.findViewById(R.id.money)).setImageResource(money);
            ((ImageView) view.findViewById(R.id.accessibility)).setImageResource(accessibility);
            ((ImageView) view.findViewById(R.id.more)).setImageResource(more);
            ((ImageView) view.findViewById(R.id.wifi)).setImageResource(wifi);


            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.BLACK),0, titleText.length(), 0);
                titleUi.setText(titleText);

            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null ) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.DKGRAY),0,snippetText.length(), 0);

                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rush);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.equals(mfirstBRM))
            {

                Intent intent = new Intent(getApplicationContext(), more_info.class);
                startActivity(intent);
            }

            }
        });


        //MapsInitializer.initialize(getApplicationContext());
        //Initialize Google Play Services
        mfirstBRM= mMap.addMarker(new MarkerOptions()
                .position(firstBRM)
                .title("Bathroom No.1")
                .alpha(0.7f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.smile))
                .flat(true)
                .snippet("Hello Bathroom"));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }

        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }



    }




    protected synchronized void buildGoogleApiClient() {
        myGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        myGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        myLocationRequest = new LocationRequest();
        myLocationRequest.setInterval(1000);
        myLocationRequest.setFastestInterval(1000);
        myLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(myGoogleApiClient, myLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        myLastLocation = location;
        if (myCurrLocationMarker != null) {
            myCurrLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("RUSH");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        myCurrLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        if (myGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(myGoogleApiClient, this);
        }

    }






    //public final void setOnInfoWindowClickListener) (

   // public void onInfoWindowClick(Marker marker) { }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean onMarkerClick(final Marker marker) {

        mLastSelectedMarker = marker;

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],  int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (myGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    Toast.makeText(this, "Permission not given.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}