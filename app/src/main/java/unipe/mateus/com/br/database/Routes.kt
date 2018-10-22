package unipe.mateus.com.br.database

import android.content.Context
import android.preference.PreferenceManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Routes {
    companion object {
        private val baseRef : DatabaseReference = FirebaseDatabase.getInstance().reference

        fun users() : DatabaseReference {

            return baseRef.child("users")
        }

        fun statusById(id : String) : DatabaseReference{
            return userWithId(id).child("isParked")
        }

        fun lastExitById(id : String) : DatabaseReference {
            return userWithId(id).child("saida")
        }

        fun userWithId(uid : String) : DatabaseReference {

            return users().child(uid)
        }

        fun historyById(uid : String ) : DatabaseReference {
            return  baseRef.child("registros").child(uid)
        }

        fun entry(uid: String) : DatabaseReference {
            return userWithId(uid).child("entrada")
        }

        fun exit(uid: String) : DatabaseReference {
            return userWithId(uid).child("saida")
        }


    }
}