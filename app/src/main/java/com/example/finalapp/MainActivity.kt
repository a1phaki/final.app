package com.example.finalapp


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.finalapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.util.Log
import android.view.LayoutInflater
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database


class MainActivity : AppCompatActivity() {
    private  lateinit var  auth: FirebaseAuth

    private  lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=Firebase.auth


        binding.signUp.setOnClickListener{
            var intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.signIn.setOnClickListener{
            if(binding.email.text.toString().isEmpty()){
                showMessage("請輸入帳號")
            }else if(binding.password.text.toString().isEmpty()){
                showMessage("請輸入密碼")
            }else{
                signIn()
            }
        }
        binding.logout.setOnClickListener{
            auth.signOut()

            val user=Firebase.auth.currentUser
            updateUI(user)
        }
        binding.jump.setOnClickListener {
            var intent=Intent(this,MainActivity2::class.java)
            intent.putExtra("key1",binding.email.text.toString())
            startActivity(intent)
        }

    }
    private  fun signIn(){
        val email=binding.email.text.toString()
        val password=binding.password.text.toString()

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    println("------signInWithEmail:success------")
                    val user=auth.currentUser
                    updateUI(user)
                }else{
                    it.exception?.message?.let {  }
                    println("------error------")
                    showMessage("登入失敗，帳號或密碼錯誤")
                    updateUI(null)
                }
            }
    }
    private  fun showMessage(message: String){
        val alertDialog:AlertDialog.Builder=AlertDialog.Builder(this@MainActivity)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("確定"){dialog,which->}
        alertDialog.show()


    }
    private  fun updateUI(user: FirebaseUser?){
        if(user!=null){
            binding.email.visibility =View.GONE
            binding.password.visibility=View.GONE
            binding.signIn.visibility=View.GONE
            binding.signUp.visibility=View.GONE
            binding.logout.visibility=View.VISIBLE
            binding.jump.visibility=View.VISIBLE
        }else{
            binding.email.visibility=View.VISIBLE
            binding.password.visibility=View.VISIBLE
            binding.signIn.visibility=View.VISIBLE
            binding.signUp.visibility=View.VISIBLE
            binding.logout.visibility=View.GONE
            binding.jump.visibility=View.GONE
        }
    }

}
