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
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import unipe.mateus.com.br.helpers.Helper


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawerLayout : DrawerLayout? = null
    private var toggle : ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        (drawerLayout as DrawerLayout).addDrawerListener(toggle as ActionBarDrawerToggle)

        (toggle as ActionBarDrawerToggle).syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        SetNavigationViewListener()

        if (!Helper.IsLoggedIn(applicationContext)) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            Toast.makeText(this, "Não está logado", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Está logado", Toast.LENGTH_LONG).show()
        }

        // TODO: Se der close na aplicação, verificar a variavel keepConnected,
        // Se verdeira, não dá signout, se falso dar signout
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when ( item.itemId ) {
            R.id.qrCode -> {
                System.out.println("Clicked")
            }
            R.id.logout -> {
                var auth = FirebaseAuth.getInstance()
                auth.signOut()
                PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().clear().commit()
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

    private fun SetNavigationViewListener() : Unit {
        var navView : NavigationView = findViewById(R.id.navMenu)
        navView.setNavigationItemSelectedListener(this)
    }
}
