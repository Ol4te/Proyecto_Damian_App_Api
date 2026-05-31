package com.example.proyecto_damian_compose.modelo

import androidx.compose.runtime.toMutableStateList
import com.example.proyecto_damian_compose.R
import androidx.compose.runtime.mutableStateListOf

object DatosDemo {

    //Generar IDs automáticamente
    fun generarIdPelicula(): Int {
        var id: Int
        do {
            id = (10000..99999).random()
        } while (peliculas.any { it.id == id })
        return id;
    }

    fun generarIdUsuario(): Int {
        var id: Int
        do {
            id = (10000..99999).random()
        } while (usuarios.any { it.id == id })
        return id
    }

    //Como primero hay que hacer una comprobacion es mejor usar un fun : Boolean
    fun registrar_Usuario(
        nombre: String,
        apellido: String,
        email: String,
        contraseña: String
    ): Boolean {
        if (usuarios.any { it.email == email }) return false //Si ya hay alguien con ese email, devuelve false
        if (usuarios.add(Usuario(generarIdUsuario(),nombre, apellido, email, contraseña))) return true
        return false
    }

    fun confirmar_Email(email: String): Boolean {
        if (usuarios.any { it.email == email }) return false
        return true
    }

    fun confirmar_Contraseña(contraseña: String, email: String): Boolean {
        val usuario = usuarios.find { it.email == email }
        return usuario?.contraseña == contraseña //Nos aseguramos de que no sea nulo
        //Si es el usuario existe y su contraseña coincide devuelve true
    }

    fun comprobarLogin(email: String, contraseña: String): Boolean {
        return usuarios.find { it.email == email }?.contraseña == contraseña
    }

    //Imagenes obtenidas de SensaCine


    val peliculas = mutableStateListOf(
        Pelicula(
            10001,
            "Inception",
            "Ciencia Ficción",
            "Christopher Nolan",
            9.2,
            R.drawable.inception
        ),
        Pelicula(
            10002,
            "Interstellar",
            "Ciencia Ficción",
            "Christopher Nolan",
            9.9,
            R.drawable.interstellar
        ),
        Pelicula(10003, "El Padrino", "Drama", "Francis Ford Coppola", 9.0, R.drawable.el_padrino),
        Pelicula(
            10004,
            "Pulp Fiction",
            "Thriller",
            "Quentin Tarantino",
            8.7,
            R.drawable.pulp_fiction
        ),
        Pelicula(
            10005,
            "The Dark Knight",
            "Acción",
            "Christopher Nolan",
            9.0,
            R.drawable.dark_knight
        ),
        Pelicula(
            10006,
            "Oppenheimer",
            "Drama Histórico",
            "Christopher Nolan",
            8.8,
            R.drawable.oppenheimer
        ),
        Pelicula(
            10007,
            "Pearl Harbour",
            "Drama Romántico",
            "Michael Bay",
            6.5,
            R.drawable.pearl_harbor
        ),
        Pelicula(
            10008,
            "Sherlock Holmes",
            "Acción",
            "Guy Ritchie",
            7.9,
            R.drawable.sherlock_holmes
        ),
        Pelicula(
            10009,
            "Sherlock Holmes: Juego de Sombras",
            "Acción",
            "Guy Ritchie",
            7.7,
            R.drawable.sherlock_holmes_sombras
        ),
        Pelicula(
            10010,
            "El Señor de los Anillos: La Comunidad del Anillo",
            "Fantasía",
            "Peter Jackson",
            9.1,
            R.drawable.esdla_1
        ),
        Pelicula(
            10011,
            "El Señor de los Anillos: Las Dos Torres",
            "Fantasía",
            "Peter Jackson",
            9.0,
            R.drawable.esdla_2
        ),
        Pelicula(10012, "Gladiator", "Acción", "Ridley Scott", 8.5, R.drawable.gladiator),
        Pelicula(
            10013,
            "Matrix",
            "Ciencia Ficción",
            "Lana y Lilly Wachowski",
            8.7,
            R.drawable.matrix
        ),
        Pelicula(10014, "Forrest Gump", "Drama", "Robert Zemeckis", 8.8, R.drawable.forrest_gump),
        Pelicula(
            10015,
            "El Club de la Lucha",
            "Thriller",
            "David Fincher",
            8.8,
            R.drawable.fight_club
        ),
        Pelicula(10016, "Joker", "Drama", "Todd Phillips", 8.4, R.drawable.joker),
        Pelicula(
            10017,
            "Avengers: Endgame",
            "Acción",
            "Anthony y Joe Russo",
            8.4,
            R.drawable.endgame
        ),
        Pelicula(10018, "Dune", "Ciencia Ficción", "Denis Villeneuve", 6.0, R.drawable.dune),
        Pelicula(10019, "1917", "Bélica", "Sam Mendes", 8.3, R.drawable.mil_novecientos_diecisiete),
        Pelicula(
            10020,
            "El Gran Gatsby",
            "Drama Romántico",
            "Baz Luhrmann",
            7.2,
            R.drawable.gran_gatsby
        )
    )

    val usuarios = mutableListOf(
        Usuario(
            20001,
            "Damian",
            "García",
            "damian@gmail.com",
            "123456",
            peliculas.shuffled().take((3..10).random()).toMutableStateList()
        ),
        Usuario(
            20002,
            "Ana",
            "López",
            "ana@gmail.com",
            "123456",
            peliculas.shuffled().take((3..10).random()).toMutableStateList()
        ),
        Usuario(
            20003,
            "Carlos",
            "Martínez",
            "carlos@gmail.com",
            "123456",
            peliculas.shuffled().take((3..10).random()).toMutableStateList()
        ),
        Usuario(
            20004,
            "Laura",
            "Sánchez",
            "laura@gmail.com",
            "123456",
            peliculas.shuffled().take((3..10).random()).toMutableStateList()
        ),
        Usuario(
            20005,
            "Miguel",
            "Fernández",
            "miguel@gmail.com",
            "123456",
            peliculas.shuffled().take((3..10).random()).toMutableStateList()
        ),
        Usuario(
            20006,
            "Sofía",
            "Ramírez",
            "sofia@gmail.com",
            "123456",
            peliculas.shuffled().take((3..10).random()).toMutableStateList()
        ),
        Usuario(
            20007,
            "Pablo",
            "Torres",
            "pablo@gmail.com",
            "123456",
            peliculas.shuffled().take((3..10).random()).toMutableStateList()
        ),
        Usuario(
            20008,
            "Elena",
            "Díaz",
            "elena@gmail.com",
            "123456",
            peliculas.shuffled().take((3..10).random()).toMutableStateList()
        ),
        Usuario(
            20009,
            "Javier",
            "Moreno",
            "javier@gmail.com",
            "123456",
            peliculas.shuffled().take((3..10).random()).toMutableStateList()
        ),
        Usuario(
            20010,
            "Lucía",
            "Jiménez",
            "lucia@gmail.com",
            "123456",
            peliculas.shuffled().take((3..10).random()).toMutableStateList()
        )
    )
}
