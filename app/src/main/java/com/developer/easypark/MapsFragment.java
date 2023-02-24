package com.developer.easypark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.developer.easypark.Modele.Geopoint;
import com.developer.easypark.Modele.Parking;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapsFragment extends Fragment  {

    private GoogleMap gMap;
    Geopoint loc = new Geopoint();
    FusedLocationProviderClient fusedLocationProviderClient;
    int PERMISSION_ID = 44;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         *
         *
         *
         *
         *
         *
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         *
         *
         *
         *
         *
         *
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {


            gMap = googleMap;

            try {

                googleMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLat(), loc.getLog())).title("Vous"));

            } catch (Exception e) {
                System.out.println("Latitude localisaton : " + loc.getLat());
                System.out.println("Erreur sur la localisation" + e.getMessage());
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(loc.getLat(), loc.getLog())));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));

            FirebaseFirestore.getInstance().collection("parking")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            List<Parking> parkings = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot doc : task.getResult()) {
                                    LatLng latLng = new LatLng((double) doc.get("latLng.latitude"), (double) doc.get("latLng.longitude"));

                                    googleMap.addMarker(new MarkerOptions().position(latLng).title(doc.get("nom").toString())
                                            .icon(BitmapFromVector(getActivity(), R.mipmap.logo_map_icon_foreground))
                                    );

                                }
                            }
                        }
                    });

            gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {

                   /* ArrayList<LatLng> directionPoint = new ArrayList<>();
                    directionPoint.add(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
                    directionPoint.add(new LatLng(loc.getLat(), loc.getLog()));
                    PolylineOptions rectLine = new PolylineOptions().width(8).color(
                            Color.RED);

                    for (int i = 0; i < directionPoint.size(); i++) {
                        rectLine.add(directionPoint.get(i));
                    }

                    // Adding route on the map
                    gMap.addPolyline(rectLine);

                    */

                    List<LatLng> path = new ArrayList();

                    Toast.makeText(requireContext(), "Position : " + marker.getTitle(), Toast.LENGTH_LONG).show();
                    return false;
                }
            });


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        getLocation();
        return inflater.inflate(R.layout.fragment_maps, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        getLocation();


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


    public void getLocation() {

        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                fusedLocationProviderClient.getLastLocation()
                        .addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();

                                } else {
                                    Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                                    List<Address> address = null;
                                    try {
                                        address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                        loc.setLat(location.getLatitude());
                                        loc.setLog(location.getLongitude());
                                        System.out.println(address.get(0).getLatitude());
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }
                        });
            } else {
                Toast.makeText(requireContext(), "Allumez le GPS", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }

        } else {
            requestPermissions();
        }


    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            loc.setLat(location.getLatitude());
                            loc.setLog(location.getLongitude());
                            System.out.println("Latitude : " + location.getLatitude());
                        }
                    }
                });
            } else {
                Toast.makeText(requireContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }



    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            System.out.println("Latitude: " + mLastLocation.getLatitude() + "");
            System.out.println("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }




}