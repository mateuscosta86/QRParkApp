package unipe.mateus.com.br.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import unipe.mateus.com.br.model.ParkRecord
import unipe.mateus.com.br.qrparkapp.R

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    var historySource : List<ParkRecord>

    constructor(source : List<ParkRecord>) {
        this.historySource = source
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