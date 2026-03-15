package mx.edu.itson.practica06

data class Pelicula(
    var id: Int,
    var titulo: String,
    var image: Int,
    var header: Int,
    var sinopsis: String,
    var seats: ArrayList<Cliente>
)
