package unipe.mateus.com.br.qrparkapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import unipe.mateus.com.br.Adapter.HistoryAdapter
import unipe.mateus.com.br.model.ParkRecord

class HistoryActivity : AppCompatActivity() {

    var recyclerView : RecyclerView? = null
    var historyAdapter : HistoryAdapter? = null
    var history = ArrayList<ParkRecord>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.rvHistory)

        val layoutManager = LinearLayoutManager(this)
        (recyclerView as RecyclerView).layoutManager = layoutManager

        historyAdapter = HistoryAdapter(history)

        (recyclerView as RecyclerView).adapter = historyAdapter
    }
}
