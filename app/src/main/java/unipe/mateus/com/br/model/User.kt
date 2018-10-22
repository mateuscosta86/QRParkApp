package unipe.mateus.com.br.model

class User {

    var id : String
    var name : String?
    var lastName : String?
    var entrada : String? = null
    var saida : String? = null

    constructor(id : String, name : String, lastName : String) {
        this.id = id
        this.name = name
        this.lastName = lastName
    }

    override fun toString(): String {
        return "User(id='$id', name='$name', lastName='$lastName')"
    }

}