package com.example.appfirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View

class DeleteActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        val db = Firebase.firestore

        val btnDeletar: Button = findViewById(R.id.btnDeletar)
        val btnConfirmar: Button = findViewById(R.id.btnConfirmar)

        val txtPessoa: TextView = findViewById(R.id.txtPessoa)

        val selectID: EditText = findViewById(R.id.edtSelecionarID)
        val ids: MutableList<String> = mutableListOf()

        var id: String = ""


        db.collection("cadastro").get().addOnSuccessListener {
                result ->
            for(document in result) {
                ids.add(document.id)
            }
        }

        btnDeletar.setOnClickListener {
            try{
                if(selectID.text.toString().toInt() >= ids.size){
                    Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
                } else {
                    id = ids[selectID.text.toString().toInt()]
                    db.collection("cadastro").document(id).get().addOnSuccessListener {
                            document ->
                        if(document != null) {
                            txtPessoa.text = getString(R.string.dados, id, document["nome"], document["cep"], document["bairro"], document["endereco"])
                            btnConfirmar.visibility = View.VISIBLE
                        } else {
                            txtPessoa.text = "Pessoa não encontrada"
                            btnConfirmar.visibility = View.INVISIBLE
                        }
                    }
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
            }

        }

        btnConfirmar.setOnClickListener {
            db.collection("cadastro").document(id)
            .delete()
            .addOnSuccessListener { Toast.makeText(this, "Deletado com sucesso", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { e -> Toast.makeText(this, "Erro: $e", Toast.LENGTH_SHORT).show() }
            selectID.text.clear()
            txtPessoa.text = ""
        }
    }

    }