package com.example.ko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_app.*

class HaberlerActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseFirestore   
    private lateinit var recyclerViewAdapter : HaberRecyclerAdapter
    var postList = ArrayList<Post>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        auth= Firebase.auth
        val currentUser = auth.currentUser
        database = FirebaseFirestore.getInstance()
        getData()

        val layoutmanager = LinearLayoutManager(this)
        recyclerView.layoutManager=layoutmanager
        recyclerViewAdapter = HaberRecyclerAdapter(postList)
        recyclerView.adapter = recyclerViewAdapter
    }
    fun getData(){
        database.collection("Post").orderBy("gunceltarih",Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->

            if(error!=null){
                Toast.makeText(applicationContext,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if (value!=null){
                    if(!value.isEmpty){

                        val documents = value.documents
                        postList.clear()
                        for (document in documents ){
                            val kullaniciemail = document.get("guncelkullaniciemail") as String
                            val kullaniciyorumu = document.get("kullaniciyorum") as String
                            val gunceltarih = document.get("gunceltarih") as Timestamp
                            val kullanicigorseli = document.get("gorselurl") as String

                            val indirilenPost = Post(kullaniciemail,kullaniciyorumu,kullanicigorseli)
                            postList.add(indirilenPost)
                         }
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.secenekler_menusu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.cikisYap){
            val currentUser = auth.currentUser
            if (currentUser!=null){
                val userName = auth.currentUser!!.email.toString()
                Toast.makeText(this,"Çıkış Yaptınız ${userName}",Toast.LENGTH_LONG).show()
                auth.signOut()
                val intent = Intent(this,LogInActivity::class.java)
                startActivity(intent)
                finish()
            }


        }
        if(item.itemId==R.id.fotografPaylas){
            val intent = Intent(this,FotografPaylasActivity::class.java)
            startActivity(intent)
        }
        if(item.itemId == R.id.profilim){
            val intent = Intent(this,KullaniciProfilActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}