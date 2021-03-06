package unipe.mateus.com.br.qrparkapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import unipe.mateus.com.br.database.Database
import unipe.mateus.com.br.model.User

class RegisterActivity : AppCompatActivity() {

    var name : EditText? = null
    var lastName: EditText? = null
    var btnRegister : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        name = findViewById( R.id.edName )
        lastName = findViewById( R.id.edLastName )
        btnRegister = findViewById( R.id.btnRegister )

        (btnRegister as Button).setOnClickListener {

            val currentUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser

            if ( currentUser != null ) {
                var nameString = (name as EditText).text.toString()
                var lastNameString = (lastName as EditText).text.toString()

                if (!nameString.isEmpty() && !lastNameString.isEmpty() ) {

                    var newUser = User(currentUser.uid, nameString, lastNameString)
                    Database.CreateUser(newUser)
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Campos de nome ou sobrenome vazios.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Houve um problema. Tente refazer o login.", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }

        }

    }
}
