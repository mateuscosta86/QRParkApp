package unipe.mateus.com.br.database

import android.content.Context
import android.preference.PreferenceManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Routes {
    companion object {
        private val baseRef : DatabaseReference = FirebaseDatabase.getInstance().reference

        fun users() : DatabaseReference {

            return baseRef.child("users")
        }

        fun userWithId(uid : String) : DatabaseReference {

            return users().child(uid)
        }


    }
}