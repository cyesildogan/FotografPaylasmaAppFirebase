package com.example.ko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class KullaniciProfilActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_profil)
        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        getDataKullanici()
    }
    fun getData(){
        val guncelkullaniciemail = auth.currentUser!!.email.toString()
        database.collection("Post").whereEqualTo("guncelkullaniciemail","${guncelkullaniciemail}").addSnapshotListener { value, error ->
            if (error != null){
                Toast.makeText(applicationContext,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else {
                        value?.let {
                            val documents = value.documents
                            for(document in documents){
                                val kullaniciemail = document.get("guncelkullaniciemail") as String
                                val txtview = findViewById<TextView>(R.id.textView2)
                                txtview.text = kullaniciemail
                        }

                    }
                }
            }

        }
    fun getDataKullanici(){
        val guncelkullaniciemail = auth.currentUser!!.email.toString()
        val txtview = findViewById<TextView>(R.id.textView2)
        txtview.text = guncelkullaniciemail
    }
    }
