package com.example.foodonline.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.example.foodonline.R
import com.example.foodonline.login.api.ApiLogin
import com.example.foodonline.login.model.Accound
import com.example.foodonline.login.model.SignIn
import com.example.foodonline.mainmenu.MainMenu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {
    private lateinit var editUsername: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPhone: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnSignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        editUsername = findViewById(R.id.edit_sn_name)
        editEmail = findViewById(R.id.edit_email)
        editPhone = findViewById(R.id.edit_Phone)
        editPassword = findViewById(R.id.edit_sn_phone)
        btnSignup = findViewById(R.id.bnt_login)

        btnSignup.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val username = editUsername.text.toString()
        val email = editEmail.text.toString()
        val phone = editPhone.text.toString()
        val password = editPassword.text.toString()

        // Kiểm tra xem tên người dùng, email, phone và mật khẩu có được nhập hay không
        if (username.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
            val account = Accound(username, password, phone, email)

            // Gọi API đăng ký và truyền dữ liệu tài khoản
            val call: Call<SignIn> = ApiLogin.apiLogin.signup(account)

            call.enqueue(object : Callback<SignIn> {
                override fun onResponse(call: Call<SignIn>, response: Response<SignIn>) {
                    if (response.isSuccessful) {
                        val signInResponse = response.body()
                        if (signInResponse != null) {
                            val intent = Intent(this@SignUp, MainMenu::class.java)
                            startActivity(intent)
                        } else {
                            // Xử lý khi response body là null
                        }
                    } else {
                        // Xử lý khi cuộc gọi API thất bại
                        // ...
                    }
                }

                override fun onFailure(call: Call<SignIn>, t: Throwable) {
                    t.printStackTrace()
                    // Xử lý khi cuộc gọi API thất bại
                    // ...
                }
            })
        } else {
            // Xử lý khi không nhập đủ thông tin tài khoản
            // ...
        }
    }
}
