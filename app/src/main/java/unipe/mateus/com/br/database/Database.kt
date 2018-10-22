package unipe.mateus.com.br.database

import android.renderscript.Sampler
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import unipe.mateus.com.br.model.ParkRecord
import unipe.mateus.com.br.model.User

import java.util.*
import kotlin.collections.HashMap

class Database {
    companion object {

        var exitListener: ValueEventListener? = null
        var getUserListener: ValueEventListener? = null
        var parkingStatusListener: ValueEventListener? = null

        fun CreateUser(user: User): Unit {

            var data: HashMap<String, String?> = HashMap()
            data["name"] = user.name
            data["lastName"] = user.lastName

            Routes.userWithId(user.id).setValue(data)
        }

        fun GetUserById(id: String, completionBlock: (innerUser: User, status: Boolean) -> Unit): Unit {

            var user = User("", "", "")

            getUserListener = object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {

                    var data = p0.value as HashMap<String, Any>

                    user.id = id
                    user.name = data["name"].toString()
                    user.lastName = data["lastName"].toString()
                    user.entrada = data["entrada"].toString()
                    user.saida = data["saida"].toString()

                    var status = data["isParked"].toString().toBoolean()
                    completionBlock(user, status)
                }
            }
            Routes.userWithId(id).addValueEventListener( getUserListener as ValueEventListener)
        }

        fun GetParkingStatusById(id: String, completionBlock: (situation: Boolean) -> Unit) {

            Routes.statusById(id).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val status = p0.value.toString()
                    completionBlock(status.toBoolean())
                }
            })
        }

        fun setParkStatus(id: String, status: Boolean) {
            Routes.statusById(id).setValue(status)
        }

        fun setEntry(id: String, status: Boolean) {
            val date = Calendar.getInstance()
            var dateStr = date.get(Calendar.DAY_OF_MONTH).toString() + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR) + " " + String.format("%02d:%02d:%02d", date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), date.get(Calendar.SECOND))

            Routes.entry(id).setValue(dateStr)
            setParkStatus(id, status)
        }

        fun setExit(id: String, status: Boolean) {
            val date = Calendar.getInstance()
            var dateStr = date.get(Calendar.DAY_OF_MONTH).toString() + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR) + " " + String.format("%02d:%02d:%02d", date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), date.get(Calendar.SECOND))

            Routes.exit(id).setValue(dateStr)
            setParkStatus(id, status)
        }

        fun feedHistory(id: String, parkRecord: ParkRecord) {

            var record = HashMap<String, String?>()
            record["entrada"] = parkRecord.entrada
            record["saida"] = parkRecord.saida
            record["preco"] = parkRecord.preco.toString()

            Routes.historyById(id).push().setValue(record)
        }

        fun getExitById(id: String, completionBlock: (exitSt: String) -> Unit) {

            exitListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.key == "saida") {
                        completionBlock(p0.value.toString())
                    }
                }
            }
            Routes.lastExitById(id).addValueEventListener(exitListener as ValueEventListener)
        }

        fun RemoveExitListener(id: String) {
            Routes.lastExitById(id).removeEventListener(exitListener as ValueEventListener)
        }

        fun RemoveGetUserListener(id: String) {
            Routes.userWithId(id).removeEventListener(getUserListener as ValueEventListener)
        }
    }
}

