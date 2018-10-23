package unipe.mateus.com.br.Adapter

import android.content.Context
import android.provider.ContactsContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import unipe.mateus.com.br.database.Routes
import unipe.mateus.com.br.model.ParkRecord
import unipe.mateus.com.br.qrparkapp.R

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    val historySource : List<ParkRecord>
    var historyListener : ChildEventListener? = null

    constructor(source : ArrayList<ParkRecord>) {
        this.historySource = source

        if ( user != null ) {

            historyListener = object : ChildEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                    var newRecord : ParkRecord = ParkRecord("", "", 4.0f)
                    var obj = p0.value as HashMap<String, String>
                    newRecord.entrada = obj["entrada"]
                    newRecord.saida = obj["saida"]
                    historySource.add(newRecord)
                    notifyDataSetChanged()
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }
            Routes.historyById(user.uid).addChildEventListener(historyListener as ChildEventListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)

        val view : View = layoutInflater.inflate(R.layout.history_ticket, parent, false)

        return HistoryViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return historySource.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        var record : ParkRecord = historySource[position]

        holder.entrada.text = "Hora da entrada: "  + record.entrada
        holder.saida.text = "Hora da saída: " + record.saida
        holder.preco.text = "Preço: R$ " + String.format("%.2f", record.preco)
    }

    fun RemoveHistoryListener(id : String) {
        if ( user != null ) {
            Routes.historyById(user.uid).removeEventListener(historyListener as ChildEventListener)
        }
    }

    class HistoryViewHolder : RecyclerView.ViewHolder {

        val entrada : TextView
        val saida : TextView
        val preco : TextView

        constructor(itemView: View, context: Context) : super(itemView) {
            this.entrada = itemView.findViewById(R.id.txtEntry)
            this.saida   = itemView.findViewById((R.id.txtExit))
            this.preco   = itemView.findViewById(R.id.txtPayment)

            itemView.setOnClickListener {
                Toast.makeText(context, "Abrir localização", Toast.LENGTH_SHORT).show()
            }
        }
    }
}