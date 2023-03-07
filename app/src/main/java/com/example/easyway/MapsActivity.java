package com.example.easyway;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.easyway.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng origenLng; //Coordenadas del origen
    private LatLng destinoLng;// Coodenadas del destino
    private String origen;
    private String destino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inicializamos el SDK de places
        Places.initialize(getApplicationContext(),"AIzaSyCdMKRfadoWuNNGPoryDudB1sCsyOnynvg");
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                TextView placeNameTextView = findViewById(R.id.place_name);
                origenLng= place.getLatLng();
                placeNameTextView.setText("Origen: "+place.getName()+" Cordenadas:"+ origenLng);
                origen=place.getName();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 16));
            }
            @Override
            public void onError(@NonNull Status status) {
                // Handle the error
            }
        });


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteDestino = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_destiny);

        // Specify the types of place data to return.
        autocompleteDestino.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));

        autocompleteDestino.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                TextView placeNameTextView = findViewById(R.id.destiny_name);
                destinoLng= place.getLatLng();
                placeNameTextView.setText("Destino: "+place.getName()+" Cordenadas:"+ destinoLng);
                destino=place.getName();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 16));


            }
            @Override
            public void onError(@NonNull Status status) {
                // Handle the error
            }

        });
    }

    // conseguir formato de la solicitud de la API directions
    private String getDirectionsUrl(LatLng origen, LatLng destino) {
        String str_origen = "origin=" + origen.latitude + "," + origen.longitude;
        String str_destino = "destination=" + destino.latitude + "," + destino.longitude;
        String mode = "mode=transit"; //cambiar a transit para transporte público
        String apiKey= "key=AIzaSyCdMKRfadoWuNNGPoryDudB1sCsyOnynvg";
        //String transitOptions = "transitOptions=mode:subway";
        String alter="alternatives=true";
        String parameters = str_origen + "&" + str_destino + "&" + mode +"&"+alter+"&"+ apiKey;
        String url = "https://maps.googleapis.com/maps/api/directions/json?" + parameters;
        return url;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng uni = new LatLng(40.45266, -3.73388);
        //mMap.addMarker(new MarkerOptions().position(uni).title("Marker in Madrid, Spain"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(uni));
    }

    public void vistaRutas(){
        Intent intent = new Intent(MapsActivity.this, ShowRoutesActivity.class);
        startActivity(intent);
    }
    public void mostarRutas(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        if(origenLng==null || destinoLng==null){
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setMessage("No se ha introducido un origen y/o un destino. Revise la sintaxis e inténtelo de nuevo");
            alertDialog.show();
        }

        else {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = getDirectionsUrl(origenLng, destinoLng);
            System.out.println(url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray routesArray = response.getJSONArray("routes"); //El array con los datos de ruta :DDDD
                                ListRutaSyngleton.getInstance().onReset();
                                for (int k = 0; k < routesArray.length(); k++) {
                                    JSONObject route = routesArray.getJSONObject(k); // Cogemos la k ruta del array

                                    JSONArray legs = route.getJSONArray("legs");
                                    for (int i = 0; i < legs.length(); i++) {
                                        JSONObject leg = legs.getJSONObject(i);
                                        JSONObject duration = leg.getJSONObject("duration");
                                        int duracionSegundo=Integer.parseInt(duration.getString("value"));
                                        String durationText = duration.getString("text");
                                        String distancia = leg.getJSONObject("distance").getString("text");
                                        String tFin="Desconocido";
                                        if(leg.has("arrival_time"))
                                            tFin = leg.getJSONObject("arrival_time").getString("text");
                                        String tInicio= "Desconocido";
                                        if(leg.has("departure_time"))
                                             tInicio = leg.getJSONObject("departure_time").getString("text");

                                        ListRutaSyngleton.getInstance().addElement(new Ruta(origen,destino,durationText,distancia,tInicio,tFin,duracionSegundo));

                                        //Desglose de pasos
                                        System.out.println("Desglose ruta "+ k);
                                        JSONArray steps = leg.getJSONArray("steps");
                                        for (int j = 0; j < steps.length(); j++) {
                                            JSONObject step = steps.getJSONObject(j);
                                            String instruction = step.getString("html_instructions");
                                            System.out.println(instruction);
                                            int distance = step.getJSONObject("distance").getInt("value");
                                            String distanceText = step.getJSONObject("distance").getString("text");
                                        }
                                    }
                                }ListRutaSyngleton.getInstance().onSort();
                            /*
                            //Prueba de dibujar la ruta en el mapa
                            JSONObject overviewPolyline = route.getJSONObject("overview_polyline"); //sacamos el previsualizado de la ruta
                            String poly = overviewPolyline.getString("points"); // sacamos los puntos de la ruta
                            List<LatLng> path = PolyUtil.decode(poly); //decodificamos puntos de ruta
                            PolylineOptions polylineOptions = new PolylineOptions() // Opciones de puntos
                                    .addAll(path)
                                    .color(Color.RED)
                                    .width(10);
                            Polyline polyline = mMap.addPolyline(polylineOptions);// mostramos los puntos de la ruta en el mapa

                             */
                                vistaRutas();
                            } catch (JSONException e) {
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.setMessage("Error al interpretar las rutas");
                                alertDialog.show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.setMessage("No se se ha podido procesar la solicitud, intentelo de nuevo");
                            alertDialog.show();
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        }
    }
}