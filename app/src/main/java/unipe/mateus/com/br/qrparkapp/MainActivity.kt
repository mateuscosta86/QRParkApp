package unipe.mateus.com.br.qrparkapp

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import unipe.mateus.com.br.database.AuthManager
import unipe.mateus.com.br.database.Database
import unipe.mateus.com.br.helpers.Helper
import unipe.mateus.com.br.model.User


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

        (toggle as ActionBarDrawerToggle).syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        SetNavigationViewListener()

        if (user == null) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            Toast.makeText(this, "Not logged in", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show()
        }

    }

    override fun onStop() {
        if (!Helper.IsKeepLoggedChecked(applicationContext) && user != null) {
            FirebaseAuth.getInstance().signOut()
            PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().clear().apply()
        }
        super.onStop()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when ( item.itemId ) {
            R.id.qrCode -> {
                System.out.println("Clicked")
            }
            R.id.relatorio -> {
                startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().clear().apply()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
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
