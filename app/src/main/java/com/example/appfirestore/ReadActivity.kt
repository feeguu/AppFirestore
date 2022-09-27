package com.example.appfirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        val text: TextView = findViewById(R.id.text)
        val btnVoltar: Button = findViewById(R.id.btnVoltar)

        btnVoltar.setOnClickListener {
            goHome()
        }

        val db = Firebase.firestore
        db.collection("cadastro").get().addOnSuccessListener { result ->
            for (document in result) {
                text.append(getString(R.string.dados,
                    document.id,
                    document.data["nome"],
                    document.data["cep"],
                    document.data["bairro"],
                    document.data["endereco"]
                )
                )
            }
        }
    }
}