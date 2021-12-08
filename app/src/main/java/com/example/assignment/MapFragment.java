package com.example.assignment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private GoogleMap mMap;
    private MapView mapView;
    private Location currentLocation;
    private int LOCATION_PERMISSION_REQUEST_CODE = 1234;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        checkLocationPermission();
        return view;
    }









    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize( getContext() );
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        //if location permission is not allowed, locate this instead
        LatLng temp = new LatLng( 54.572720, -5.959151 );
        mMap.addMarker( new MarkerOptions().position( temp ).title( "Temporary Location" ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( temp, 12 ) );

        // Add a marker in a location
        LatLng loc1 = new LatLng(3.1281627946385404, 101.59842635254633);
        mMap.addMarker(new MarkerOptions().position(loc1).title("Marker in loc1")
                // below line is use to add custom marker on our map.
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerloc)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc1));

        // Add a marker in a location
        LatLng loc2 = new LatLng(3.1210986014141002, 101.60200933298778);
        mMap.addMarker(new MarkerOptions().position(loc2).title("Marker in loc2")
                // below line is use to add custom marker on our map.
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerloc)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc2));

        // Add a marker in a location
        LatLng loc3 = new LatLng(3.120903672634574, 101.59665254297104);
        mMap.addMarker(new MarkerOptions().position(loc3).title("Marker in loc3")
                // below line is use to add custom marker on our map.
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerloc)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc3));
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // GPS may be turned off
            if (location == null) {
                return;
            }

            Double lat = location.getLatitude();
            Double lng = location.getLongitude();

            currentLocation = location;
            Toast.makeText( getActivity(), "Updated Location: " + lat + lng, Toast.LENGTH_SHORT ).show();
        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public boolean checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission( getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED)
        {


            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale( getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION ))
            {
                //Prompt the user once explanation has been shown
                requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE
                );



            } else {
                // No explanation needed, we can request the permission.
                requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE );

            }
            return false;
        }
        else
        {
            return true;
        }
    }

}