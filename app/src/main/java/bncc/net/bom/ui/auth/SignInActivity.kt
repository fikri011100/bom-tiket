package bncc.net.bom.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import bncc.net.bom.R
import bncc.net.bom.model.User
import bncc.net.bom.ui.home.HomeActivity
import bncc.net.bom.ui.payment.BookingPaymentActivity
import com.google.firebase.database.*

class SignInActivity : AppCompatActivity() {
    lateinit var iUsername:String
    lateinit var iPassword:String
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Database Firebase
        mDatabase = FirebaseDatabase.getInstance("https://bom-ticket-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

        // Declare Variable for Component
        val button_signIn = findViewById<Button>(R.id.btn_signIn)
        val button_signUp = findViewById<Button>(R.id.btn_signUp)

        val et_username = findViewById<EditText>(R.id.et_username)
        val et_password = findViewById<EditText>(R.id.et_password)

        // Sign In
        button_signIn.setOnClickListener {
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            // Check Validlity Data
            if (iUsername.equals("")) {
                et_username.error = "Silahkan tuliskan username anda"
                et_username.requestFocus() // Memberi Kursor
            } else if (iPassword.equals("")) {
                et_password.error = "Silahkan tuliskan password anda"
                et_password.requestFocus() // Memberi kursor
            } else {
                LogIn(iUsername, iPassword)
            }
        }

        // Sign Up
        button_signUp.setOnClickListener {
            var intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    // Function for Log In
    private fun LogIn(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object: ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan",
                        Toast.LENGTH_LONG).show()
                } else {
                    if (user.password.equals(iPassword)) {
                        var intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignInActivity, "Password Anda Salah",
                        Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message,
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}