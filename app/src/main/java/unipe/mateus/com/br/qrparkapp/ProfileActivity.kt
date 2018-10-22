package unipe.mateus.com.br.qrparkapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_profile.*
import unipe.mateus.com.br.database.Database

class ProfileActivity : AppCompatActivity() {

    private val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val name = tvName
        val lastName = tvLastName

        if ( user != null ) {
            Database.GetUserById(user.uid) { usr, status ->

                name.text = usr.name
                lastName.text = usr.lastName
            }
        }
    }
}
