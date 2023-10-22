package com.example.foodonline.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodonline.R
import com.example.foodonline.login.api.ApiLogin
import com.example.foodonline.login.model.SignIn
import com.example.foodonline.login.model.User
import com.example.foodonline.mainmenu.MainMenu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextName = findViewById(R.id.edit_Username)
        editTextPassword = findViewById(R.id.edit_Password)
        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            clickLogin()
        }

        val textView = findViewById<TextView>(R.id.sign_up)
        textView.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

    private fun clickLogin() {
        val username = editTextName.text.toString()
        val password = editTextPassword.text.toString()
        val user = User(username, password)

        // Kiểm tra xem tên người dùng và mật khẩu có được nhập hay không
        if (username.isNotEmpty() && password.isNotEmpty()) {
            // Gọi API đăng nhập và truyền tên người dùng và mật khẩu
            ApiLogin.apiLogin.login(user).enqueue(object : Callback<SignIn> {

                override fun onResponse(call: Call<SignIn>, response: Response<SignIn>) {
                    if (response.isSuccessful) {
                        val signInResponse = response.body()
                        if (signInResponse != null) {
                            // Log kết quả từ API

                            if (signInResponse.status) {
                                // Nếu đăng nhập thành công, chuyển sang màn hình MainMenu
                                val intent = Intent(this@Login, MainMenu::class.java)
                                startActivity(intent)
                            } else {
                                // Xử lý trường hợp đăng nhập thất bại
                                // Hiển thị thông báo lỗi bằng Toast
                                Toast.makeText(this@Login, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Xử lý khi response body là null
                        }
                    } else {
                        // Xử lý khi cuộc gọi API thất bại
                        // Hiển thị thông báo lỗi bằng Toast
                        Log.e("Login", "API call failed with code: ${response.code()}")
                        Toast.makeText(this@Login, "Lỗi: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignIn>, t: Throwable) {
                    t.printStackTrace()
                    // Xử lý khi cuộc gọi API thất bại
                    // Hiển thị thông báo lỗi bằng Toast
                    Log.e("Login", "API call failed with exception: ${t.message}")
                    Toast.makeText(this@Login, "Lỗi: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            // Xử lý khi tên người dùng hoặc mật khẩu không được nhập
            // Hiển thị thông báo lỗi bằng Toast
            Log.d("Login", "Please enter both username and password")
            Toast.makeText(this@Login, "Please enter both username and password", Toast.LENGTH_SHORT).show()
        }
    }
}
