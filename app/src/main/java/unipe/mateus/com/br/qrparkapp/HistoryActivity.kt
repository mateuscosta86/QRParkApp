package unipe.mateus.com.br.qrparkapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import unipe.mateus.com.br.Adapter.HistoryAdapter
import unipe.mateus.com.br.model.ParkRecord

class HistoryActivity : AppCompatActivity() {

    var recyclerView : RecyclerView? = null
    var historyAdapter : HistoryAdapter? = null
    var history = ArrayList<ParkRecord>()

    private val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.rvHistory)

        val layoutManager = LinearLayoutManager(this)
        (recyclerView as RecyclerView).layoutManager = layoutManager

        historyAdapter = HistoryAdapter(history)

        (recyclerView as RecyclerView).adapter = historyAdapter
        (historyAdapter as HistoryAdapter).notifyDataSetChanged()
    }

    override fun onStop() {

        if (historyAdapter != null && user != null ){
            (historyAdapter as HistoryAdapter).RemoveHistoryListener(user.uid)
        }
        super.onStop()
    }
}
