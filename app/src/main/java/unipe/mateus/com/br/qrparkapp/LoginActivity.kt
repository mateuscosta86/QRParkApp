package unipe.mateus.com.br.qrparkapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import unipe.mateus.com.br.helpers.Helper

class LoginActivity : AppCompatActivity() {

    var email : EditText? = null
    var password : EditText? = null
    var btnSignIn : Button? = null
    var btnSignUp : Button? = null
    var cbKeepConnect : CheckBox? = null

    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById( R.id.txtEmail )
        password = findViewById( R.id.txtPassword )
        btnSignIn = findViewById( R.id.btnSignIn )
        btnSignUp = findViewById( R.id.btnSignUp )
        cbKeepConnect = findViewById( R.id.cbKeepConnected )

        auth = FirebaseAuth.getInstance()


        (btnSignUp as Button).setOnClickListener {
            val emailStr = (email as EditText).text.toString()
            val passwordStr = (password as EditText).text.toString()

            val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val editor = prefs.edit()

            if( (cbKeepConnect as CheckBox).isChecked ) {
                Toast.makeText(this, "Checkbox ticked", Toast.LENGTH_LONG).show()
                editor.putBoolean("keepConnected", true)
                editor.apply()
            } else {
                editor.putBoolean("keepConnected", false)
                editor.apply()
            }

            if (!emailStr.isEmpty() && !passwordStr.isEmpty() ) {
                CreateUserAndLogin(emailStr, passwordStr)
            }
        }

        (btnSignIn as Button).setOnClickListener {
            val emailStr = (email as EditText).text.toString()
            val passwordStr = (password as EditText).text.toString()

            val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val editor = prefs.edit()

            if( (cbKeepConnect as CheckBox).isChecked ) {
                Toast.makeText(this, "Checkbox ticked", Toast.LENGTH_LONG).show()
                editor.putBoolean("keepConnected", true)
                editor.commit()
            } else {
                editor.putBoolean("keepConnected", false)
                editor.commit()
            }

            if (!emailStr.isEmpty() && !passwordStr.isEmpty() ) {

                UserLogin(emailStr, passwordStr)
            }
        }

        auth!!.addAuthStateListener {
            if ( auth == null ) {

            } else if ( auth != null ) {

            }
        }

    }

    fun CreateUserAndLogin(email: String, password: String) {

        auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if ( task.isSuccessful ) {
                Toast.makeText(this, "Logging succeeded", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            } else if (task.isCanceled) {
                System.out.println(task.exception)
            } else {
                Toast.makeText(this, "Oops! Something wrong happened.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun UserLogin(email: String, password: String) {

        if ( auth != null ) {
            (auth as FirebaseAuth).signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if ( task.isSuccessful ) {
                    Toast.makeText(this, "Logging in", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else if (task.isCanceled) {
                    System.out.println(task.exception)
                } else {
                    Toast.makeText(this, "Oops! Something wrong happened.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}
