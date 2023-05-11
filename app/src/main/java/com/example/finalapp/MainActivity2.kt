package com.example.finalapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Button
import android.widget.EditText
import com.example.finalapp.databinding.ActivityMain2Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database

class MainActivity2 : AppCompatActivity() {
    private  lateinit var  auth: FirebaseAuth

    private  lateinit var  binding: ActivityMain2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=Firebase.auth

        val user=Firebase.auth.currentUser
        val send=findViewById<Button>(R.id.send)

        intent?.extras?.let{
            val value=it.getString("key1")
            val email=findViewById<TextView>(R.id.email)
            email.text=value.toString()
        }
        val database=Firebase.database
        val userRef = database.getReference("user")
        val myRef=database.getReference("message")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 取得節點資料的程式碼寫在這裡
                val TextView1=findViewById<TextView>(R.id.`articles＿1`)
                val value1=dataSnapshot.getValue(String::class.java)
                TextView1.text=value1
            }

            override fun onCancelled(error: DatabaseError) {
                // 取得節點資料失敗時的程式碼寫在這裡
            }
        })
        send.setOnClickListener{
            val Text=findViewById<EditText>(R.id.sendarticle)
            val article=Text.text.toString()


            val email = user?.email
            val emailAsNode =email?.replace(".", "_")
            if (emailAsNode != null) {

                userRef.child(emailAsNode).child("articles").setValue(article)
                val TextView=findViewById<TextView>(R.id.article)
                TextView.text=article.toString()
            }


        }
    }
}