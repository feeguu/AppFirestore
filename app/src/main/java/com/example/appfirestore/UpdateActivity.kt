package com.example.appfirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.set
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UpdateActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        db = Firebase.firestore

        val selectID: EditText = findViewById(R.id.edtSelecionarID)

        val edtNome: EditText = findViewById(R.id.edtNome)
        val edtCep: EditText = findViewById(R.id.edtCep)
        val edtBairro: EditText = findViewById(R.id.edtBairro)
        val edtEndereco: EditText = findViewById(R.id.edtEndereco)

        val btnProcurar: Button = findViewById(R.id.btnProcurar)
        val btnAtualizar: Button = findViewById(R.id.btnAtualizar)

        val ids: MutableList<String> = mutableListOf()

        var id: String = ""


        db.collection("cadastro").get().addOnSuccessListener {
                result ->
            for(document in result) {
                ids.add(document.id)
            }
        }

        btnProcurar.setOnClickListener {
            try {
                if(selectID.text.toString().toInt() >= ids.size){
                    Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
                } else {
                    id = ids[selectID.text.toString().toInt()]
                    db.collection("cadastro")
                        .document(id)
                        .get()
                        .addOnSuccessListener {
                                document ->
                            if(document != null) {
                                edtNome.setText(document["nome"] as String)
                                edtCep.setText(document["cep"] as String)
                                edtBairro.setText(document["bairro"] as String)
                                edtEndereco.setText(document["endereco"] as String)
                            }
                        }
                }
            } catch (e:java.lang.NumberFormatException) {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
            }

        }

        btnAtualizar.setOnClickListener {
            val nome = edtNome.text.toString()
            val cep = edtCep.text.toString()
            val bairro = edtBairro.text.toString()
            val endereco = edtEndereco.text.toString()
            if(nome.isEmpty() || cep.isEmpty() || bairro.isEmpty() || endereco.isEmpty()){
                Toast.makeText(this, "Campos inválidos", Toast.LENGTH_SHORT).show()
            } else {
                val pessoa = hashMapOf("nome" to nome, "cep" to cep, "bairro" to bairro, "endereco" to endereco)
                db.collection("cadastro")
                    .document(id)
                    .set(pessoa)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Cadastro atualizado", Toast.LENGTH_SHORT).show()
                        edtNome.text.clear()
                        edtCep.text.clear()
                        edtEndereco.text.clear()
                        edtBairro.text.clear()
                        selectID.text.clear()
                    }
            }
        }
    }
}