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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import unipe.mateus.com.br.helpers.Helper


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawerLayout : DrawerLayout? = null
    private var toggle : ActionBarDrawerToggle? = null

    private val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                Toast.makeText(this, "Not logged in", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        if (user == null) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            Toast.makeText(this, "Not logged in", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when ( item.itemId ) {
            R.id.qrCode -> {
                startActivity(Intent(this@MainActivity, QRCodeActivity::class.java))
            }
            R.id.perfil -> {
                startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            }
            R.id.relatorio -> {
                startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().clear().apply()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
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
