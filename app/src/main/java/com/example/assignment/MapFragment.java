package com.example.assignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // Obtain the SupportMapFragment and get notified
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we  add markers around PJ, Selangor.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     **/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/

        mMap = googleMap;

        // Add a marker in a location and move the camera
        LatLng loc1 = new LatLng(3.1281627946385404, 101.59842635254633);
        mMap.addMarker(new MarkerOptions().position(loc1).title("Marker in loc1")
                // below line is use to add custom marker on our map.
                .icon(BitmapFromVector(getActivity().getApplicationContext(), R.drawable.ic_flag)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc1));

        // Add a marker in a location and move the camera
        LatLng loc2 = new LatLng(3.1210986014141002, 101.60200933298778);
        mMap.addMarker(new MarkerOptions().position(loc2).title("Marker in loc2")
                // below line is use to add custom marker on our map.
                .icon(BitmapFromVector(getActivity().getApplicationContext(), R.drawable.ic_flag)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc2));

        // Add a marker in a location and move the camera
        LatLng loc3 = new LatLng(3.120903672634574, 101.59665254297104);
        mMap.addMarker(new MarkerOptions().position(loc3).title("Marker in loc3")
                // below line is use to add custom marker on our map.
                .icon(BitmapFromVector(getActivity().getApplicationContext(), R.drawable.ic_flag)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc3));


    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}