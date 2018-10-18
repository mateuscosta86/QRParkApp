package unipe.mateus.com.br.database

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import unipe.mateus.com.br.model.User
class Database {
    companion object {
        fun CreateUser(user : User) : Unit {
            var data : HashMap<String, String> = HashMap()
            data["name"] = user.name
            data["lastName"] = user.lastName
            Routes.userWithId(user.id).setValue(data)
        }

        fun GetUserById(id : String, completionBlock : (innerUser : User) -> Unit ) : Unit {

            var user : User = User("","","")

            System.out.println("Getting User: $id")
            Routes.userWithId(id).addChildEventListener(object : ChildEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    System.out.println("Changed")
                    System.out.println(p0.toString())
                    user.id = id
                    if (p0.key == "name") {
                        user.name = p0.value.toString()
                    } else if (p0.key == "lastName") {
                        user.lastName = p0.value.toString()
                    }
                    System.out.println("User na funcao: $user")
                    completionBlock(user)
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    System.out.println(p0.toString())
                    if (p0.key == "name") {
                        user.name = p0.value.toString()
                    } else if (p0.key == "lastName") {
                        user.lastName = p0.value.toString()
                    }
                    completionBlock(user)
                    System.out.println("User na funcao added: $user")
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }
}

