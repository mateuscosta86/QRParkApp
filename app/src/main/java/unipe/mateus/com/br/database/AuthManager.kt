package unipe.mateus.com.br.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthManager {
    companion object {
        var currentUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    }
}