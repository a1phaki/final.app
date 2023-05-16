package com.example.finalapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.finalapp.databinding.ActivityMain2Binding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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
        //接收MainActivity intente過來的值
        intent?.extras?.let{
            val value=it.getString("key1")
            val email=findViewById<TextView>(R.id.email)
            email.text=value.toString()
        }
        val database=Firebase.database
        val userRef = database.getReference("user")
        var i=1
        val email = user?.email

        //將user的email的.取代成＿（firebase的節點不能有.)
        val emailAsNode =email?.replace(".", "_")

        //
        val linearLayout = findViewById<LinearLayout>(R.id.container)
        val sortedQuery=userRef.child("articles").orderByChild("number")
//        //設定資料庫中的單次觸發事件 將資料庫中articles節點的子節點依照"number"的值來排序
//        sortedQuery.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (childSnapshot in snapshot.children) {
//                    val values = childSnapshot.value as? HashMap<String, Any>
//
//                    if (values != null) {
//                        //設定取得的values資料指定為content,email,number,date
//                        val content = values["content"] as? String
//                        val email = values["email"] as? String
//                        val number = values["number"] as? Long
//                        val date=values["date"]as?String
//
//                        //新增的textview
//                        val textView = TextView(this@MainActivity2)
//                        //設定textview中的文字
//                        textView.text = email.toString() + " : \n\n" + content + "\n\n"+date+"\n-------------------------------------------------------------------"
//                        val params = LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.WRAP_CONTENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT
//                        )
//                        params.height = 400 // 設定高度為 400 像素
//                        textView.layoutParams = params //將textview的layout參數設定為params
//                        textView.textSize = 20f //設定textview的文字大小
//
//                        linearLayout.addView(textView,0) //將textview加入到指定的linearLayout並指定index為0(將新增的text加到linearLayout的最上方）
//                        i = i + 1 //紀錄跑的迴圈次數
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                print("error")
//            }
//        })
        sortedQuery.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val values = snapshot.value as? HashMap<String, Any>

                if (values != null) {
                    //設定取得的values資料指定為content,email,number,date
                    val content = values["content"] as? String
                    val email = values["email"] as? String
                    val number = values["number"] as? Long
                    val date=values["date"]as?String
                    //新增的textview
                    val textView = TextView(this@MainActivity2)
                    //設定textview中的文字
                    textView.text = email.toString() + " : \n\n" + content + "\n\n"+date+"\n-------------------------------------------------------------------"
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.height = 400 // 設定高度為 400 像素
                    textView.layoutParams = params //將textview的layout參數設定為params
                    textView.textSize = 20f //設定textview的文字大小

                    linearLayout.addView(textView,0) //將textview加入到指定的linearLayout並指定index為0(將新增的text加到linearLayout的最上方）
                    i = i + 1 //紀錄跑的迴圈次數
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val values = snapshot.value as? HashMap<String, Any>

                if (values != null) {
                    //設定取得的values資料指定為content,email,number,date
                    val content = values["content"] as? String
                    val email = values["email"] as? String
                    val number = values["number"] as? Long
                    val date=values["date"]as?String
                    //新增的textview
                    val textView = TextView(this@MainActivity2)
                    //設定textview中的文字
                    textView.text = email.toString() + " : \n\n" + content + "\n\n"+date+"\n-------------------------------------------------------------------"
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.height = 400 // 設定高度為 400 像素
                    textView.layoutParams = params //將textview的layout參數設定為params
                    textView.textSize = 20f //設定textview的文字大小

                    linearLayout.addView(textView,0) //將textview加入到指定的linearLayout並指定index為0(將新增的text加到linearLayout的最上方）
                    i = i + 1 //紀錄跑的迴圈次數
                }
            }


            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

        //送出按鈕點擊事件
        send.setOnClickListener{
            val Text=findViewById<EditText>(R.id.sendarticle)
            val article=Text.text.toString()
            val currentTime = Date()

            // 設定時間的格式
            val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())

            // 將時間格式化成字串
            val formattedTime = dateFormat.format(currentTime)
            //設定articles為一整組的資料email,number,contnet,date
            val articles = HashMap<String, Any>()
            articles["email"] = emailAsNode.toString()
            articles["number"] = i
            articles["content"] = article
            articles["date"]=formattedTime
            //在articles的節點下加入子節點articles"i"並設定值為articles
            userRef.child("articles").child("articles"+i).setValue(articles)


        }
        //logout按鈕觸發事件
        binding.logout.setOnClickListener {
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)

        }


    }
}