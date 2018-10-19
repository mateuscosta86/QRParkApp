package unipe.mateus.com.br.qrparkapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import unipe.mateus.com.br.Adapter.HistoryAdapter
import unipe.mateus.com.br.model.ParkRecord

class HistoryActivity : AppCompatActivity() {

    var recyclerView : RecyclerView? = null
    var historyAdapter : HistoryAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.rvHistory)

        val layoutManager = LinearLayoutManager(this)
        (recyclerView as RecyclerView).layoutManager = layoutManager

        var history = ArrayList<ParkRecord>()

        history.add(ParkRecord("13hr", "14h", 4.0f))
        history.add(ParkRecord("13hr", "14h", 4.0f))
        history.add(ParkRecord("13hr", "14h", 4.0f))
        history.add(ParkRecord("13hr", "14h", 4.0f))
        history.add(ParkRecord("13hr", "14h", 4.0f))
        history.add(ParkRecord("13hr", "14h", 4.0f))
        history.add(ParkRecord("13hr", "14h", 4.0f))
        history.add(ParkRecord("13hr", "14h", 4.0f))
        history.add(ParkRecord("13hr", "14h", 4.0f))
        history.add(ParkRecord("13hr", "14h", 4.0f))


        historyAdapter = HistoryAdapter(history)

        (recyclerView as RecyclerView).adapter = historyAdapter

    }
}
