package edu.uoc.android.pec3app

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import edu.uoc.android.pec3app.models.Element
import edu.uoc.android.pec3app.models.Museums
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    val API_URL: String = "https://do.diba.cat"
    val PAG_INI: String = "1"
    val PAG_FI: String = "165"
    lateinit var listMuseums: MutableList<Element> //list of returned museums

    override fun onMarkerClick(p0: Marker?) = false
    //manage the map from this activity

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient : FusedLocationProviderClient //access user location
    private lateinit var lastLocation: Location //user location

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        //get listMuseums (retrofit)
        dataApi()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // get the client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.setOnMarkerClickListener(this)


        setUpMap()

        //add markers

        listMuseums.forEach {
            val coord = it.getLocalitzacio().split(",")
            val lat = coord[0].toDouble()
            val lon = coord[1].toDouble()
            val museuLoc = LatLng(lat,lon)
            val museuTit = it.getAdrecaNom()

            placesMarker(museuLoc, museuTit)
        }



        //val museo = LatLng(41.3640002,2.1674931)
        //placesMarker(museo)

    }

    private fun placesMarker(place: LatLng, museu: String) {
        val markerOption = MarkerOptions().position(place)
        markerOption.title(museu)
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_museum_map))
        mMap.addMarker(markerOption)
    }

    private fun setUpMap() {
        // permission for getting the user’s location
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        // set user location
        mMap.isMyLocationEnabled = true
        // Terrain type map
        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN

        // getting de user's location
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->

            if (location != null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                // move the camera to the user's location
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))

            }

        }
    }

    private fun dataApi() {
        //Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val museumService = retrofit.create(MuseumService::class.java)

        val call = museumService.museums(PAG_INI,PAG_FI)
        //call to the api and get the list of museums
        call.enqueue(object: Callback<Museums> {
            //send the request async
            override fun onResponse(call: Call<Museums>, response: Response<Museums>) {
                if (response.code() == 200) {
                    val museums = response.body()!!
                    listMuseums = museums.getElements()
                    Log.d("museus", "${listMuseums}")
                }
            }
            override fun onFailure(call: Call<Museums>, t: Throwable) {
                Log.d("museums", "onFailure" )
            }
        })
    }

}
