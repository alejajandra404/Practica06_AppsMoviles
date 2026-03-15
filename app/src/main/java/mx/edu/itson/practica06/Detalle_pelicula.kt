package mx.edu.itson.practica06

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Detalle_pelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_pelicula)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val iv_pelicula_image: ImageView = findViewById(R.id.iv_pelicula_imagen)
        val tv_nombre_pelicula: TextView = findViewById(R.id.tv_nombre_pelicula)
        val tv_pelicula_desc: TextView = findViewById(R.id.tv_pelicula_desc)

        val buyButton: Button = findViewById(R.id.buyTickets)

        val bundle = intent.extras
        if (bundle != null) {
            iv_pelicula_image.setImageResource(bundle.getInt("header"))
            tv_nombre_pelicula.setText(bundle.getString("titulo"))
            tv_pelicula_desc.setText(bundle.getString("sinopsis"))
        }

        actualizarAsientos()

        buyButton.setOnClickListener {
            val intent = Intent(this, SeatSelection::class.java)
            intent.putExtra("name", bundle?.getString("titulo"))
            intent.putExtra("id", bundle?.getInt("id"))
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        actualizarAsientos()
    }

    private fun actualizarAsientos() {
        val disponibles: TextView = findViewById(R.id.seatLeft)
        val id = intent.getIntExtra("id", -1)
        val pelicula =
            Catalogo.peliculas.find { it.id == id } ?: Catalogo.series.find { it.id == id }
        pelicula?.let {
            val count = 20 - it.seats.size
            disponibles.text = "$count seats available"
        }
    }
}
