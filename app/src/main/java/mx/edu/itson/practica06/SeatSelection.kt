package mx.edu.itson.practica06

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SeatSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seat_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title: TextView = findViewById<TextView>(R.id.titleSeats)
        var posMovie = -1

        val bundle = intent.extras
        if(bundle!=null){
            title.setText(bundle.getString("name"))
            posMovie = bundle.getInt("id")
        }

        val pelicula = Catalogo.peliculas.find { it.id == posMovie } ?: Catalogo.series.find { it.id == posMovie }

        val row1: RadioGroup = findViewById(R.id.row1)
        val row2: RadioGroup = findViewById(R.id.row2)
        val row3: RadioGroup = findViewById(R.id.row3)
        val row4: RadioGroup = findViewById(R.id.row4)

        pelicula?.seats?.forEach { cliente ->
            val seatId = resources.getIdentifier("s${cliente.asiento}", "id", packageName)
            if (seatId != 0) {
                val radioButton: RadioButton = findViewById(seatId)
                radioButton.isEnabled = false
                radioButton.setBackgroundResource(R.drawable.radio_disabled)
            }
        }

        val confirm: Button = findViewById(R.id.confirm)
        confirm.setOnClickListener {
            val selectedId = when {
                row1.checkedRadioButtonId != -1 -> row1.checkedRadioButtonId
                row2.checkedRadioButtonId != -1 -> row2.checkedRadioButtonId
                row3.checkedRadioButtonId != -1 -> row3.checkedRadioButtonId
                row4.checkedRadioButtonId != -1 -> row4.checkedRadioButtonId
                else -> -1
            }

            if (selectedId != -1) {
                val radioButton: RadioButton = findViewById(selectedId)
                val asiento = radioButton.text.toString().toInt()

                pelicula?.seats?.add(Cliente("Usuairo", "TC", asiento))

                Toast.makeText(this, "Enjoy the movie! :D", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Please select a seat", Toast.LENGTH_SHORT).show()
            }
        }

        row1.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId> -1){
                row2.clearCheck()
                row3.clearCheck()
                row4.clearCheck()

                row1.check(checkedId)
            }
        }

        row2.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId> -1){
                row1.clearCheck()
                row3.clearCheck()
                row4.clearCheck()

                row2.check(checkedId)
            }
        }

        row3.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId> -1){
                row2.clearCheck()
                row1.clearCheck()
                row4.clearCheck()

                row3.check(checkedId)
            }
        }

        row4.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId> -1){
                row2.clearCheck()
                row3.clearCheck()
                row1.clearCheck()

                row4.check(checkedId)
            }
        }
    }
}