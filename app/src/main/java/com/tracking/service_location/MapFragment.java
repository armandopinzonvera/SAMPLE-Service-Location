package com.tracking.service_location;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    float latitudOrigen, longitudOrigen;

    public MapFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_name);
        mapFragment.getMapAsync(this);
        return view;
    }
    public void setLocation(float latitud, float longitud){
        this.latitudOrigen = latitud;
        this.longitudOrigen = longitud;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LatLng miPosicion = new LatLng(latitudOrigen, longitudOrigen);
        mMap.addMarker(new MarkerOptions()
                .position(miPosicion)
                .title("marca"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(miPosicion));
    }
}
