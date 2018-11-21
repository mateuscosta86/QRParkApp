package unipe.mateus.com.br.qrparkapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import unipe.mateus.com.br.helpers.Helper

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private var drawerLayout : DrawerLayout? = null
    private var toggle : ActionBarDrawerToggle? = null

    private val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        (drawerLayout as DrawerLayout).addDrawerListener(toggle as ActionBarDrawerToggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (toggle as ActionBarDrawerToggle).syncState()

        SetNavigationViewListener()

        var lastProcessID = PreferenceManager.getDefaultSharedPreferences(applicationContext).getInt("PROCESS_ID", 0)
        Log.i("CARAIO", lastProcessID.toString())

        var editor = PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
        editor.putInt( "PROCESS_ID", android.os.Process.myPid() ).apply()

        var currentProcessID = PreferenceManager.getDefaultSharedPreferences(applicationContext).getInt("PROCESS_ID", 0)

        Log.i("CARAIO", currentProcessID.toString())

        if (currentProcessID != lastProcessID && lastProcessID != 0 ) {
            if (!Helper.IsKeepLoggedChecked(applicationContext) && user != null) {
                FirebaseAuth.getInstance().signOut()
                PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().clear().apply()
                startActivity(Intent(this@MapsActivity, LoginActivity::class.java))
                Toast.makeText(this, "Not logged in", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        if (user == null) {
            startActivity(Intent(this@MapsActivity, LoginActivity::class.java))
            Toast.makeText(this, "Not logged in", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show()
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when ( item.itemId ) {
            R.id.qrCode -> {
                startActivity(Intent(this@MapsActivity, QRCodeActivity::class.java))
            }
            R.id.perfil -> {
                startActivity(Intent(this@MapsActivity, ProfileActivity::class.java))
            }
            R.id.relatorio -> {
                startActivity(Intent(this@MapsActivity, HistoryActivity::class.java))
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().clear().apply()
                startActivity(Intent(this@MapsActivity, LoginActivity::class.java))
                finish()
            }
        }

        if ( drawerLayout != null ) {
            (drawerLayout as DrawerLayout).closeDrawer(GravityCompat.START)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if ( toggle != null ) {
            if ( (toggle as ActionBarDrawerToggle).onOptionsItemSelected(item) ) {
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun SetNavigationViewListener() {
        var navView : NavigationView = findViewById(R.id.navMenu)
        navView.setNavigationItemSelectedListener(this)
    }

}