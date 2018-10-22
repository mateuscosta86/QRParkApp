package unipe.mateus.com.br.qrparkapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.KeyEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_qrcode.*
import unipe.mateus.com.br.database.Database
import unipe.mateus.com.br.model.ParkRecord
import java.util.*


class QRCodeActivity : AppCompatActivity() {

    private val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    var isInitializing = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        var parkRecord = ParkRecord("", "", 4f)
        var isParked = false
        val codeImage = ivCode

        System.out.println("IS INITIALIZING: " + isInitializing)

        if ( user != null ) {

            Database.GetUserById(user.uid) { usr, status ->
                parkRecord.entrada = usr.entrada
                parkRecord.saida = usr.saida
                isParked = status
                if ( isParked ) {
                    tvParkStatus.text = "Parked"
                } else {
                    tvParkStatus.text = "Not Parked"
                }
            }

            Database.getExitById(user.uid ) {
                if (isInitializing) {
                    isInitializing = false
                    System.out.println("IS INITIALIZING: " + isInitializing)
                } else {
                    parkRecord.saida = it
                    Database.feedHistory(user.uid, parkRecord)
                    System.out.println()
                }
            }

            codeImage.setOnClickListener {

                if (isParked) {
                    Database.setExit(user.uid, !isParked)
                } else {
                    Database.setEntry(user.uid, !isParked)
                }
            }
        }
    }

    override fun onStop() {
        if ( user != null ){
            Database.RemoveExitListener(user.uid)
        }
        super.onStop()
    }
}
