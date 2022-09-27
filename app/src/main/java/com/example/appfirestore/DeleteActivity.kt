package com.example.appfirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DeleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        val db = Firebase.firestore

        val ids: MutableList<String> = mutableListOf()

        var selected: String = ""

        db.collection("cadastro").get().addOnSuccessListener {
            result ->
            for(document in result) {
                ids.add(document.id)
            }
        }

        val btnDeletar: Button = findViewById(R.id.btnDeletar)

        btnDeletar.setOnClickListener {
            if(selected.isEmpty()) {
                Toast.makeText(this, "Por favor selecione um registro", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Registro ${selected} deletado", Toast.LENGTH_SHORT).show()
            }
        }

        val spinner: Spinner = findViewById(R.id.spinner)
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ids)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selected = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selected = ""
            }
        }
    }
}