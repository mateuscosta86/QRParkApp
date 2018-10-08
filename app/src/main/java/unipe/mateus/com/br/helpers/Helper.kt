package unipe.mateus.com.br.helpers

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity


class Helper {
    companion object {
        fun IsLoggedIn(context: Context) : Boolean {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getBoolean("keepConnected", false)
        }


    }
}