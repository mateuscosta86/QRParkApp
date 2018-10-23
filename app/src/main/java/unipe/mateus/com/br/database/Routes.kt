package unipe.mateus.com.br.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Routes {
    companion object {
        private var dataBase : FirebaseDatabase? = null

        fun GetBaseRef() : DatabaseReference {
            if ( dataBase == null ) {
                dataBase = FirebaseDatabase.getInstance()
                (dataBase as FirebaseDatabase).setPersistenceEnabled(true)
            }
            return (dataBase as FirebaseDatabase).reference
        }

        fun users() : DatabaseReference {

            return GetBaseRef().child("users")
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
            return  GetBaseRef().child("registros").child(uid)
        }

        fun entry(uid: String) : DatabaseReference {
            return userWithId(uid).child("entrada")
        }

        fun exit(uid: String) : DatabaseReference {
            return userWithId(uid).child("saida")
        }


    }
}