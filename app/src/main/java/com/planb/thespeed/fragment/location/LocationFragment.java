package com.planb.thespeed.fragment.location;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.planb.thespeed.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by channarith.bong on 12/20/17.
 */

public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private LatLng mLocation;
    private Button btnConfirm;
    private MapView mapView;
    private TextView txt_location_detection;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocation = new LatLng(12.565679, 104.990963);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_map_locator, container, false);
        txt_location_detection = view.findViewById(R.id.txt_location_detection);
        btnConfirm = view.findViewById(R.id.btn_confirm_location);
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);*/
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        configureCameraIdle();
        mMap.setOnCameraIdleListener(onCameraIdleListener);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 12));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mLocation = latLng;
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 12));
                // Toast.makeText(RegisterLocationActivity.this, "LatLng : " + latLng.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     *
     */
    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                LatLng latLng = mMap.getCameraPosition().target;
                Geocoder geocoder = new Geocoder(getActivity());

                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();
                        if (!locality.isEmpty() && !country.isEmpty()){};
                        txt_location_detection.setText(locality + "  " + country);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
