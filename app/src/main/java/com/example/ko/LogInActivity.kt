package com.example.ko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var  email : EditText
    private lateinit var pw : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth =Firebase.auth
        val currentUser = Firebase.auth.currentUser

            if(currentUser!= null){
                val userName = currentUser.email.toString()
                val intent = Intent(this,HaberlerActivity::class.java)
                startActivity(intent)
                Toast.makeText(this,"Hosgeldin ${userName}",Toast.LENGTH_LONG).show()
                finish()
            }




    }
    fun kayitOl(view : View){
        email = findViewById(R.id.emailText)
        val getEmail = email.text.toString()
        pw = findViewById(R.id.pwText)
        val getPw = pw.text.toString()
        if(getEmail.isEmpty() && getPw.isEmpty()){
            Toast.makeText(this,"EMAİL VEYA ŞİFRE GİRMEDİNİZ",Toast.LENGTH_LONG).show()
        }else
        {
            auth.createUserWithEmailAndPassword(getEmail,getPw).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent = Intent(this,HaberlerActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }
    fun girisYap(view : View){
        email = findViewById(R.id.emailText)
        pw = findViewById(R.id.pwText)
        val getEmail = email.text.toString()
        val getPw = pw.text.toString()
        if(getEmail.isEmpty()||getPw.isEmpty()){
            Toast.makeText(this,"EMAİL VEYA PASSWORD GİRMEDİNİZ",Toast.LENGTH_LONG).show()
        }else{
            auth.signInWithEmailAndPassword(getEmail,getPw).addOnCompleteListener {
                if (it.isSuccessful){
                    val currentUser = auth.currentUser!!.email
                    Toast.makeText(this,"Hoşgeldiniz ${currentUser}",Toast.LENGTH_LONG).show()
                    val intent = Intent(this,HaberlerActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }


    }



}